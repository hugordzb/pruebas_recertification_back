package com.truper.recertification.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.truper.recertification.dao.ReCuentasUsuarioDAO;
import com.truper.recertification.dao.RePerfilSistemaDAO;
import com.truper.recertification.dao.ReSistemaDAO;
import com.truper.recertification.dao.ReUsuarioDAO;
import com.truper.recertification.ldap.repository.LDAPRepository;
import com.truper.recertification.model.PKCuentasUsuario;
import com.truper.recertification.model.ReCuentasUsuarioEntity;
import com.truper.recertification.model.ReSistemaEntity;
import com.truper.recertification.service.DetailEmployeeService;
import com.truper.recertification.vo.answer.CountsEmployeeVO;
import com.truper.recertification.vo.answer.sistemas.CiatDataVO;
import com.truper.recertification.vo.answer.sistemas.AcountsVO;
import com.truper.recertification.vo.answer.sistemas.ListAcountsVO;
import com.truper.recertification.vo.answer.sistemas.SapDataVO;
import com.truper.recertification.vo.answer.sistemas.TelDataVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DetailEmployeeServiceImpl implements DetailEmployeeService{

	@Autowired
	private ReCuentasUsuarioDAO daoCuentas;

	@Autowired
	private ReSistemaDAO daoSistema;
	
	@Autowired
	private ReUsuarioDAO daoUsuario;
	
	@Autowired
	private RePerfilSistemaDAO daoPerfil;
	
	@Autowired
	private LDAPRepository ldapRepository;
	
	public CountsEmployeeVO findEmployDetail(String strIdUsuario) {
		CountsEmployeeVO employeeVO = new CountsEmployeeVO();
			
		if(ldapRepository.findByUsername(strIdUsuario) != null) {
			List<ReCuentasUsuarioEntity> lstCuenta = daoCuentas.findByIdCuentaUsuarioIdUsuario(strIdUsuario);
			try {
				employeeVO.setIdEmpleado(strIdUsuario);
				employeeVO.setEmpleado(daoUsuario.findById(strIdUsuario).get().getNombre());
				
				ListAcountsVO lstAcounts = new ListAcountsVO();
				
				List<TelDataVO> lstTel = new ArrayList<>();
				List<SapDataVO> lstSap = new ArrayList<>();
				List<CiatDataVO> lstCiat = new ArrayList<>();
				
				for(int j = 0; j<lstCuenta.size(); j++) {
					this.findAcounts(lstCuenta.get(j), lstTel, lstSap, lstCiat);
				}
				lstAcounts.setTel(lstTel);
				lstAcounts.setSap(lstSap);
				lstAcounts.setCiat(lstCiat);
					
				employeeVO.setCuentas(this.orderCounts(lstAcounts));	
			} catch (Exception e) {
				log.error("El usuario " + strIdUsuario);
				log.info(e.getMessage());
			}
		}
		return employeeVO;
	}
	
	public void findAcounts(ReCuentasUsuarioEntity cuentasUsuario, List<TelDataVO> lstTel,
			List<SapDataVO> lstSap, List<CiatDataVO> lstCiat) {
		
		TelDataVO telVO = new TelDataVO();
		SapDataVO sapVO = new SapDataVO();
		CiatDataVO ciatVO = new CiatDataVO();
		
		PKCuentasUsuario pkUsuario = cuentasUsuario.getIdCuentaUsuario();
		
		int intIdPerfil = pkUsuario.getIdPerfil();
		String strSistema = daoPerfil.findById(intIdPerfil).get().getIdSistema();
		
		ReSistemaEntity sistemaEntity = daoSistema.findById(strSistema).get();
		
		String strCuenta = sistemaEntity.getSistema();
		String strPerfil = daoPerfil.findById(intIdPerfil).get().getPerfil();
		String cuentaSistema = pkUsuario.getCuentaSistema();
		
		switch (strCuenta) {
		case "TEL":
				telVO.setCuenta(cuentaSistema);
				telVO.setPerfil(strPerfil);
				lstTel.add(telVO);
			break;
		case "SAP":
				sapVO.setCuenta(cuentaSistema);
				sapVO.setPerfil(strPerfil);
				lstSap.add(sapVO);
				break;
		case "CIAT":
				ciatVO.setCuenta(cuentaSistema);
				ciatVO.setPerfil(strPerfil);
				lstCiat.add(ciatVO);
			break;
		default:
			break;			
		}
	}
	
	public List<AcountsVO> orderCounts(ListAcountsVO lstAcountsVO) {
		List<AcountsVO> lstCounts = new ArrayList<>();
		
		int intTel = 0;
		int intSap = 0;
		int intCiat = 0;

		if(lstAcountsVO.getCiat() != null) {
			intCiat = lstAcountsVO.getCiat().size();
		}
		
		if(lstAcountsVO.getSap() != null) {
			intSap = lstAcountsVO.getSap().size();
		}
		
		if(lstAcountsVO.getTel() != null) {
			intTel = lstAcountsVO.getTel().size();
		}
		
		int[] numeros = {intTel, intCiat, intSap};
		Arrays.sort(numeros);
		
		for(int i=0; i < numeros[2]; i++) {
			AcountsVO countsVO = new AcountsVO();
			
			if(intCiat != 0 && i < intCiat) {
			countsVO.setCCiat(lstAcountsVO.getCiat().get(i).getCuenta());
			countsVO.setPCiat(lstAcountsVO.getCiat().get(i).getPerfil());
			}
			if(intSap != 0 && i < intSap) {
			countsVO.setCSap(lstAcountsVO.getSap().get(i).getCuenta());
			countsVO.setPSap(lstAcountsVO.getSap().get(i).getPerfil());
			}
			if(intTel != 0 && i < intTel) {
			countsVO.setCTel(lstAcountsVO.getTel().get(i).getCuenta());
			countsVO.setPTel(lstAcountsVO.getTel().get(i).getPerfil());
			}
			lstCounts.add(countsVO);
		}
		return lstCounts;	
	}

}

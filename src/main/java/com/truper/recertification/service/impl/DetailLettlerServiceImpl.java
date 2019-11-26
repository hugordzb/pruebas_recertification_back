package com.truper.recertification.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.truper.recertification.dao.ReCuentasUsuarioDAO;
import com.truper.recertification.dao.RePerfilSistemaDAO;
import com.truper.recertification.dao.ReSistemaDAO;
import com.truper.recertification.dao.ReUsuarioDAO;
import com.truper.recertification.model.ReCuentasUsuarioEntity;
import com.truper.recertification.model.ReSistemaEntity;
import com.truper.recertification.service.DetailLetterService;
import com.truper.recertification.vo.answer.DetailCountsEmployeeVO;
import com.truper.recertification.vo.answer.systems.AcountsLettersVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DetailLettlerServiceImpl implements DetailLetterService{
	@Autowired
	private ReCuentasUsuarioDAO daoCuentas;

	@Autowired
	private ReSistemaDAO daoSistema;
	
	@Autowired
	private ReUsuarioDAO daoUsuario;
	
	@Autowired
	private RePerfilSistemaDAO daoPerfil;
	
	@Override
	public DetailCountsEmployeeVO findEmployDetail(String strIdUsuario) {
		DetailCountsEmployeeVO employeeVO = new DetailCountsEmployeeVO();
			
		if(this.daoUsuario.findByIdUsuario(strIdUsuario).isEstatus()) {
			List<ReCuentasUsuarioEntity> lstCuenta = daoCuentas.findByIdCuentaUsuarioIdUsuario(strIdUsuario);
			try {
				employeeVO.setEmpleado(this.daoUsuario.findById(strIdUsuario).get().getNombre());
					
				List<AcountsLettersVO> lstAcounts = new ArrayList<>();
				AcountsLettersVO countsVO = new AcountsLettersVO();
				
				for(int j = 0; j<lstCuenta.size(); j++) {
					
					int intIdPerfil = lstCuenta.get(j).getIdCuentaUsuario().getIdPerfil();
					String strSistema = daoPerfil.findById(intIdPerfil).get().getIdSistema();
					ReSistemaEntity sistemaEntity = daoSistema.findById(strSistema).get();
					
					String strCuenta = sistemaEntity.getSistema();
					String strPerfil = daoPerfil.findById(intIdPerfil).get().getPerfil();
					String cuentaSistema = lstCuenta.get(j).getIdCuentaUsuario().getCuentaSistema();
					
					switch (strCuenta) {
					case "TEL":
						countsVO.setCTel(countsVO.getCTel() + "</br>" + cuentaSistema);
						countsVO.setPTel(countsVO.getPTel() + "</br>" + strPerfil);
						break;
					case "SAP":
						countsVO.setCSap(countsVO.getCSap() + "</br>" + cuentaSistema);
						countsVO.setPSap(countsVO.getCSap() + "</br>" + strPerfil);
							break;
					case "CIAT":
						countsVO.setCCiat(countsVO.getCCiat() + "</br>" + cuentaSistema);
						countsVO.setPCiat(countsVO.getPCiat() + "</br>" + strPerfil);
						break;
					default:
						break;			
					}
				}
				log.info("cuanteas: " + countsVO.getCCiat());
				log.info("cuanteas: " + countsVO.getCSap());
				log.info("cuanteas: " + countsVO.getCTel());
				
				if(countsVO.getCCiat() == null) {
					countsVO.setCCiat(" ");
					countsVO.setPCiat(" ");
				}
				if(countsVO.getCSap() == null) {
					countsVO.setCSap(" ");
					countsVO.setPSap(" ");
				}
				if(countsVO.getCTel() == null) {
					countsVO.setCTel(" ");
					countsVO.setPTel(" ");
				}
				
				lstAcounts.add(countsVO);
				//employeeVO.setCuentas(lstAcounts);
				
			} catch (Exception e) {
				log.error("El usuario " + strIdUsuario);
				log.info(e.getMessage());
			}
		}
		return employeeVO;
	}

}
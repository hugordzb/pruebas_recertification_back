package com.truper.recertification.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.truper.recertification.dao.ReCuentasUsuarioDAO;
import com.truper.recertification.dao.ReDetalleJefeDAO;
import com.truper.recertification.dao.ReJerarquiaDAO;
import com.truper.recertification.dao.RePerfilSistemaDAO;
import com.truper.recertification.dao.ReSistemaDAO;
import com.truper.recertification.dao.ReUsuarioDAO;
import com.truper.recertification.ldap.repository.LDAPRepository;
import com.truper.recertification.model.PKCuentasUsuario;
import com.truper.recertification.model.PKPerfilSistema;
import com.truper.recertification.model.ReCuentasUsuarioEntity;
import com.truper.recertification.model.ReDetalleJefeEntity;
import com.truper.recertification.model.ReJerarquiaEntity;
import com.truper.recertification.model.ReSistemaEntity;
import com.truper.recertification.service.AuditoryService;
import com.truper.recertification.vo.answer.CountsByUserVO;
import com.truper.recertification.vo.answer.CountsBossVO;
import com.truper.recertification.vo.answer.sistemas.CiatDataVO;
import com.truper.recertification.vo.answer.sistemas.CountsVO;
import com.truper.recertification.vo.answer.sistemas.ListAcountsVO;
import com.truper.recertification.vo.answer.sistemas.SapDataVO;
import com.truper.recertification.vo.answer.sistemas.TelDataVO;

@Service
public class AuditoryServiceImpl implements AuditoryService{

	@Autowired
	private ReCuentasUsuarioDAO daoCuentas;

	@Autowired
	private ReSistemaDAO daoSistema;
	
	@Autowired
	private ReJerarquiaDAO daoJerarquia;

	@Autowired
	private ReDetalleJefeDAO daoDetalleJefe;
	
	@Autowired
	private ReUsuarioDAO daoUsuario;
	
	@Autowired
	private RePerfilSistemaDAO daoPerfil;
	
	@Autowired
	private LDAPRepository ldapRepository;
	
	@Override
	public Map<String, Object> findCuentas() {
		List<ReCuentasUsuarioEntity> lstCuentas = daoCuentas.findAll();
		
		Map<String, Object> systemsMap = new HashMap<>();
		List<String> listaTEL = new ArrayList<>();
		List<String> listaCIAT = new ArrayList<>();
		List<String> listaSAP = new ArrayList<>();
		
		for(int i = 0; i<lstCuentas.size(); i++) {
			String strCount = lstCuentas.get(i).getIdCuentaUsuario().getCuentaSistema();
			String strIdSystem = lstCuentas.get(i).getIdCuentaUsuario().getIdSistema();
			String strSystem = daoSistema.findById(strIdSystem).get().getSistema();
			
			if(strSystem.equals("TEL")) {
				listaTEL.add(strCount);
			}else if (strSystem.equals("SAP")) {
				listaSAP.add(strCount);
			}else if (strSystem.equals("CIAT")) {
				listaCIAT.add(strCount);
			}
		}	
		
		systemsMap.put("SAP", listaSAP);
		systemsMap.put("TEL", listaTEL);
		systemsMap.put("CIAT", listaCIAT);
				
		return systemsMap;
	}

	@Override
	public Map<String, Object> findCuentasSistema() {
		
		Map<String, Object> jefesMap = new HashMap<String, Object>();
		List<CountsBossVO> lstJefes = new ArrayList<>();
		
		List<ReDetalleJefeEntity> lstCuentas = daoDetalleJefe.findAll();
		
		for(int i = 0; i<lstCuentas.size(); i++) {
			
			List<CountsByUserVO> lstEmpleados = new ArrayList<>();
			CountsBossVO bossVO = new CountsBossVO();
			
			String strIdJefe = lstCuentas.get(i).getIdJefe();
			
			List<ReJerarquiaEntity> lstJerarquia = daoJerarquia.findByIdEmpleadoJefeIdJefe(strIdJefe);
			
			bossVO.setIdJefe(strIdJefe);
			bossVO.setJefe(lstCuentas.get(i).getNombre());
			
			if(ldapRepository.findByUsername(strIdJefe) != null) {
				bossVO.setInAD(true);
			}
			
			for(int j = 0; j<lstJerarquia.size(); j++) {
				CountsByUserVO empleadoVO = new CountsByUserVO();
				ListAcountsVO aux = new ListAcountsVO();
				
				List<TelDataVO> lstTel = new ArrayList<>();
				List<SapDataVO> lstSap = new ArrayList<>();
				List<CiatDataVO> lstCiat = new ArrayList<>();
				
				String strIdUsuario = lstJerarquia.get(j).getIdEmpleadoJefe().getIdUsuario();
				
				if(ldapRepository.findByUsername(strIdUsuario) != null) {
					List<ReCuentasUsuarioEntity> lstCuenta = daoCuentas.findByIdCuentaUsuarioIdUsuario(strIdUsuario);
					
					empleadoVO.setIdEmpleado(strIdUsuario);
					empleadoVO.setEmpleado(daoUsuario.findById(strIdUsuario).get().getNombre());
										
					for(int k = 0; k<lstCuenta.size(); k++) {
						TelDataVO telVO = new TelDataVO();
						SapDataVO sapVO = new SapDataVO();
						CiatDataVO ciatVO = new CiatDataVO();
						
						PKCuentasUsuario pkUsuario = lstCuenta.get(k).getIdCuentaUsuario();
						
						String strSistema = pkUsuario.getIdSistema();
						int intIdPerfil = pkUsuario.getIdPerfil();
						
						ReSistemaEntity sistemaEntity = daoSistema.findById(strSistema).get();
						
						String strCuenta = sistemaEntity.getSistema();
						String strPerfil = daoPerfil.findById(new PKPerfilSistema(intIdPerfil, strSistema)).get().getPerfil();
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
					aux.setTel(lstTel);
					aux.setSap(lstSap);
					aux.setCiat(lstCiat);
					
					empleadoVO.setCuentas(this.orderCounts(aux));
					lstEmpleados.add(empleadoVO);
				}
			}
			
			if(!(lstEmpleados.isEmpty() || lstEmpleados == null)) {
				bossVO.setEmpleados(lstEmpleados);
				lstJefes.add(bossVO);
			}
		}
		jefesMap.put("jefes", lstJefes);
		return jefesMap;
	}

	private List<CountsVO> orderCounts(ListAcountsVO lstAcountsVO) {
		List<CountsVO> lstCounts = new ArrayList<>();
		
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
			CountsVO countsVO = new CountsVO();
			
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
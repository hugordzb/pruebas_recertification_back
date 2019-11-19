package com.truper.recertification.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.truper.recertification.dao.ReCuentasUsuarioDAO;
import com.truper.recertification.dao.ReDetalleJefeDAO;
import com.truper.recertification.dao.ReJerarquiaDAO;
import com.truper.recertification.dao.ReSistemaDAO;
import com.truper.recertification.ldap.repository.LDAPRepository;
import com.truper.recertification.model.ReCuentasUsuarioEntity;
import com.truper.recertification.model.ReDetalleJefeEntity;
import com.truper.recertification.model.ReJerarquiaEntity;
import com.truper.recertification.service.AuditoryService;
import com.truper.recertification.vo.answer.CountsEmployeeVO;
import com.truper.recertification.vo.answer.CountsBossVO;

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
	private LDAPRepository ldapRepository;
	
	@Autowired
	private DetailEmployeeServiceImpl detailEmployee;
	
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
		
		Map<String, Object> jefesMap = new HashMap<>();
		List<CountsBossVO> lstJefes = new ArrayList<>();
		
		List<ReDetalleJefeEntity> lstCuentas = daoDetalleJefe.findAll();
		
		for(int i = 0; i<lstCuentas.size(); i++) {
						
			String strIdJefe = lstCuentas.get(i).getIdJefe();
			CountsBossVO bossVO = this.findByBoss(strIdJefe);
			
			if(!(bossVO.getEmpleados().isEmpty() || bossVO.getEmpleados() == null)) {
				lstJefes.add(bossVO);
			}
		}
		jefesMap.put("jefes", lstJefes);
		return jefesMap;
	}

	@Override
	public CountsBossVO findByBoss(String strIdBoss) {
		CountsBossVO bossVO = new CountsBossVO();
		
		List<CountsEmployeeVO> lstEmpleados = new ArrayList<>();		
		List<ReJerarquiaEntity> lstJerarquia = daoJerarquia.findByIdEmpleadoJefeIdJefe(strIdBoss);
		
		for(int j = 0; j<lstJerarquia.size(); j++) {
			String strIdemployee = lstJerarquia.get(j).getIdEmpleadoJefe().getIdUsuario();
			lstEmpleados.add(this.findEmployeeAcounts(strIdemployee));
		}
		
		bossVO.setEmpleados(lstEmpleados);
		bossVO.setIdJefe(strIdBoss);
		bossVO.setJefe(daoDetalleJefe.findByIdJefe(strIdBoss).getNombre());
		
		if(ldapRepository.findByUsername(strIdBoss) != null) {
			bossVO.setInAD(true);
		}
				
		return bossVO;
	}
	
	@Override
	public CountsEmployeeVO findEmployeeAcounts(String strIdEmployee) {
		return detailEmployee.findEmployDetail(strIdEmployee);
	}

}
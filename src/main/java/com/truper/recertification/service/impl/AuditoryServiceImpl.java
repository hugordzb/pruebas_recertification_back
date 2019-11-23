package com.truper.recertification.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.truper.recertification.dao.ReDetalleJefeDAO;
import com.truper.recertification.dao.ReJerarquiaDAO;
import com.truper.recertification.ldap.repository.LDAPRepository;
import com.truper.recertification.model.ReDetalleJefeEntity;
import com.truper.recertification.model.ReJerarquiaEntity;
import com.truper.recertification.service.AuditoryService;
import com.truper.recertification.vo.answer.CountsEmployeeVO;
import com.truper.recertification.vo.answer.systems.AcountsVO;

import lombok.extern.slf4j.Slf4j;

import com.truper.recertification.vo.answer.CountsBossVO;

@Slf4j
@Service
public class AuditoryServiceImpl implements AuditoryService{
	
	@Autowired
	private ReJerarquiaDAO daoJerarquia;

	@Autowired
	private ReDetalleJefeDAO daoDetalleJefe;
	
	@Autowired
	private LDAPRepository ldapRepository;
	
	@Autowired
	private DetailEmployeeServiceImpl detailEmployee;
	
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
		String strSystems = null;
		
		List<CountsEmployeeVO> lstEmpleados = new ArrayList<>();		
		List<ReJerarquiaEntity> lstJerarquia = daoJerarquia.findByIdEmpleadoJefeIdJefe(strIdBoss);
		
		for(int j = 0; j<lstJerarquia.size(); j++) {
			String strIdemployee = lstJerarquia.get(j).getIdEmpleadoJefe().getIdUsuario();
			lstEmpleados.add(this.findEmployeeAcounts(strIdemployee));
		}
		strSystems = this.systems(lstEmpleados);
		bossVO.setSistemas(strSystems);
		bossVO.setEmpleados(lstEmpleados);
		bossVO.setIdJefe(strIdBoss);
		
		try {
			bossVO.setJefe(daoDetalleJefe.findById(strIdBoss).get().getNombre());
		} catch (Exception e) {
			log.error("El jefe no ha sido dado de alta");
			log.info(e.getMessage());
		}
		
		if(ldapRepository.findByUsername(strIdBoss) != null)
			bossVO.setInAD(true);
				
		return bossVO;
	}
	
	@Override
	public CountsEmployeeVO findEmployeeAcounts(String strIdEmployee) {
			return detailEmployee.findEmployDetail(strIdEmployee);
	}
	
	private String systems(List<CountsEmployeeVO> lstEmpleados) {
		String tel = "";
		String ciat = "";
		String sap = "";

		for(int i = 0; i < lstEmpleados.size(); i++) {
			List<AcountsVO> lstAcounts = lstEmpleados.get(i).getCuentas();
			
			for(int j = 0; j < lstAcounts.size(); j++) {
				AcountsVO acountsVO = lstAcounts.get(j);
				
				if(tel.isEmpty() && acountsVO.getCCiat() != null)
					ciat = "CIAT, ";
				if(tel.isEmpty() && acountsVO.getPSap() != null)
					sap = "SAP, ";
				if(tel.isEmpty() && acountsVO.getCTel() != null)
					tel = "TEL,";
			}
		}
		return ciat + sap + tel;
	}

}
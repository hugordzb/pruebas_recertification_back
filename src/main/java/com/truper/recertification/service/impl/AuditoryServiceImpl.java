package com.truper.recertification.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.truper.recertification.dao.ReDetalleJefeDAO;
import com.truper.recertification.dao.ReUsuarioDAO;
import com.truper.recertification.model.ReDetalleJefeEntity;
import com.truper.recertification.model.ReUsuarioEntity;
import com.truper.recertification.service.AuditoryService;
import com.truper.recertification.vo.answer.AcountsBossVO;
import com.truper.recertification.vo.answer.DetailCountsEmployeeVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AuditoryServiceImpl implements AuditoryService{
	
	@Autowired
	private ReUsuarioDAO daoUsuario;

	@Autowired
	private ReDetalleJefeDAO daoDetalleJefe;
	
	@Autowired
	private DetailEmployeeServiceImpl detailEmployee;
	
	@Override
	public Map<String, Object> findCuentasSistema() {
		
		Map<String, Object> jefesMap = new HashMap<>();
		List<AcountsBossVO> lstJefes = new ArrayList<>();
		
		List<ReDetalleJefeEntity> lstCuentas = daoDetalleJefe.findAll();
		
		for(int i = 0; i<lstCuentas.size(); i++) {
						
			String strIdJefe = lstCuentas.get(i).getIdJefe();
			AcountsBossVO bossVO = this.findByBoss(strIdJefe);
			
			if(!(bossVO.getEmpleados().isEmpty() || bossVO.getEmpleados() == null)) {
				lstJefes.add(bossVO);
			}
		}
		jefesMap.put("jefes", lstJefes);
		return jefesMap;
	}
	
	@Override
	public AcountsBossVO findByBoss(String strIdBoss) {
		AcountsBossVO bossVO = new AcountsBossVO();
		String strSystems = null;
		
		List<DetailCountsEmployeeVO> lstEmpleados = new ArrayList<>();
		List<ReUsuarioEntity> listEmpleados = this.daoUsuario.findUsuariosByBoss(strIdBoss);
		
		if(listEmpleados != null) {
			listEmpleados.forEach( emp -> {
				lstEmpleados.add(this.detailEmployee.findEmployeeDetail(emp));
			});
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
		/*
		if(ldapRepository.findByUsername(strIdBoss) != null)
			bossVO.setInAD(true);
		*/
				
		return bossVO;
	}

	@Override
	public List<DetailCountsEmployeeVO> generateLetterByBoss(String strIdBoss) {
		List<DetailCountsEmployeeVO> lstEmpleados = new ArrayList<>();		
		
		List<ReUsuarioEntity> listEmpleados = this.daoUsuario.findUsuariosByBoss(strIdBoss);
		if(listEmpleados != null) {
			listEmpleados.forEach( emp -> {
				lstEmpleados.add(this.detailEmployee.findEmployeeDetail(emp));
			});
		}
		
		return lstEmpleados;
	}
	
	private String systems(List<DetailCountsEmployeeVO> lstEmpleados) {
		String tel = "";
		String ciat = "";
		String sap = "";
		/*
		for(int i = 0; i < lstEmpleados.size(); i++) {
			List<AcountsVO> lstAcounts = lstEmpleados.get(i).getCuentas();
			
			for(int j = 0; j < lstAcounts.size(); j++) {
				AcountsVO acountsVO = lstAcounts.get(j);
				
				if(tel.isEmpty() && acountsVO.getCCiat() != null)
					ciat = "CIAT, ";
				//if(tel.isEmpty() && acountsVO.getPSap() != null)
					//sap = "SAP, ";
				if(tel.isEmpty() && acountsVO.getCTel() != null)
					tel = "TEL,";
			}
		}*/
		return ciat + sap + tel;
	}

}
package com.truper.recertification.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.truper.recertification.dao.ReCuentasUsuarioDAO;
import com.truper.recertification.dao.ReJerarquiaDAO;
import com.truper.recertification.dao.ReSistemaDAO;
import com.truper.recertification.model.ReCuentasUsuarioEntity;
import com.truper.recertification.model.ReSistemaEntity;
import com.truper.recertification.service.AuditoryService;
import com.truper.recertification.vo.answer.SystemsVO;

@Service
public class AuditoryServiceImpl implements AuditoryService{

	@Autowired
	private ReCuentasUsuarioDAO daoCuentas;

	@Autowired
	private ReSistemaDAO daoSistema;
	
	@Autowired
	private ReJerarquiaDAO daoJerarquia;

	@Override
 	public List<String> getSystems() {
		
		List<String> listSystem = new ArrayList<String>();
		List<ReSistemaEntity> list = daoSistema.findAll();
		
		for(int i = 0; i<list.size(); i++) {
			listSystem.add(list.get(i).getSistema());
		}
		return listSystem;
	}
	
	@Override
	public Map<String, Object> findCuentasSistema() {
		
		List<ReCuentasUsuarioEntity> lstCuentas = daoCuentas.findAll();
		
		Map<String, Object> systemsMap = new HashMap<>();
		List<SystemsVO> listaTEL = new ArrayList<>();
		List<SystemsVO> listaCIAT = new ArrayList<>();
		List<SystemsVO> listaSAP = new ArrayList<>();
		
		for(int i = 0; i<lstCuentas.size(); i++) {
			
			SystemsVO systemsVO = new SystemsVO();
			
			ReCuentasUsuarioEntity cuentasEntity = lstCuentas.get(i);
						
			systemsVO.setEmpleado(cuentasEntity.getIdCuentaUsuario().getIdUsuario());
			systemsVO.setCuentaSistema(cuentasEntity.getIdCuentaUsuario().getCuentaSistema());
			
			String strIdSystem = cuentasEntity.getIdCuentaUsuario().getIdSistema();
			String strSystem = daoSistema.findById(strIdSystem).get().getSistema();
			
//			daoJerarquia.findById(id)
			if(strSystem.equals("TEL")) {
				listaTEL.add(systemsVO);
			}else if (strSystem.equals("SAP")) {
				listaSAP.add(systemsVO);
			}else if (strSystem.equals("CIAT")) {
				listaCIAT.add(systemsVO);
			}
		}	
		
		systemsMap.put("SAP", listaSAP);
		systemsMap.put("TEL", listaTEL);
		systemsMap.put("CIAT", listaCIAT);
				
		return systemsMap;
	}

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
}

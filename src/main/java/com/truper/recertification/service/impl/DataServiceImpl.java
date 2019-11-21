package com.truper.recertification.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.truper.recertification.dao.ReBitacoraCambiosDAO;
import com.truper.recertification.dao.ReControlCambiosDAO;
import com.truper.recertification.dao.RePerfilSistemaDAO;
import com.truper.recertification.dao.ReSistemaDAO;
import com.truper.recertification.model.ReBitacoraCambiosEntity;
import com.truper.recertification.model.ReControlCambiosEntity;
import com.truper.recertification.model.RePerfilSistemaEntity;
import com.truper.recertification.model.ReSistemaEntity;
import com.truper.recertification.service.DataService;
import com.truper.recertification.vo.answer.SystemsVO;
import com.truper.recertification.vo.answer.ProfileSystemListVO;
import com.truper.recertification.vo.answer.ProfileSystemVO;
import com.truper.recertification.vo.answer.SystemsListVO;

@Service
public class DataServiceImpl implements DataService{

	@Autowired
	private ReSistemaDAO daoSistema;
	
	@Autowired
	private RePerfilSistemaDAO daoPerfilSystem;
	
	@Autowired
	private ReControlCambiosDAO daoControl;
	
	@Autowired
	private ReBitacoraCambiosDAO daoBitacora;
	
	@Override
 	public SystemsListVO getSystems() {
		
		SystemsListVO systemVO = new SystemsListVO();
		SystemsVO vo = new SystemsVO();
		
		List<ReSistemaEntity> list = daoSistema.findAll();
		List<SystemsVO> lista = new ArrayList<>();
		
		for(int i = 0; i<list.size(); i++) {
			ReSistemaEntity  sistema = list.get(i);	
			
			vo.setIdSistema(sistema.getIdSistema());
			vo.setSistema(sistema.getSistema());
			
			lista.add(vo);
		}
		systemVO.setSystems(lista);
		return systemVO;
	}

	@Override
	public ProfileSystemListVO getProfileSytem() {
		
		List<ProfileSystemVO> lstAnswerPS = new ArrayList<>();
		List<RePerfilSistemaEntity> lstPS = daoPerfilSystem.findAll();
		
		ProfileSystemListVO lstPSVO = new ProfileSystemListVO();
		
		for(int i = 0; i<lstPS.size(); i++) {
			ProfileSystemVO answerPSVO = new ProfileSystemVO();
			RePerfilSistemaEntity perfilSistemaVO = lstPS.get(i);
			
			String idSystem = perfilSistemaVO.getIdSistema();
			String system = daoSistema.findById(idSystem).get().getSistema();
			
			answerPSVO.setIdPerfil(perfilSistemaVO.getIdPerfil());
			answerPSVO.setPerfil(perfilSistemaVO.getPerfil());
			answerPSVO.setSystemData(new SystemsVO(idSystem, system));
			
			lstAnswerPS.add(answerPSVO);
		}
		lstPSVO.setPerfiles(lstAnswerPS);
		return lstPSVO;
	}

	@Override
	public Map<String, Object> getChangeControl() {
		
		Map<String, Object> mapControl = new HashMap<>();
		List<ReControlCambiosEntity> lstControl = daoControl.findByEstatus(false);
		List<ReBitacoraCambiosEntity> lstDetailControl = new ArrayList<>();
		
		for(int i = 0; i < lstControl.size(); i ++) {
			Integer intIdMov = lstControl.get(i).getIdMovimiento();
			lstDetailControl.add(daoBitacora.findById(intIdMov).get());
		}
		mapControl.put("movimientos", lstDetailControl);
		return mapControl;
	}
	
	@Override
	public ReControlCambiosEntity getByTicket(int intTicket) {
		return daoControl.findById(intTicket).get();
	}
}

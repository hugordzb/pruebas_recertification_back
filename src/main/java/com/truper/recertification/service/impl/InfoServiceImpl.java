package com.truper.recertification.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.truper.recertification.dao.RePerfilSistemaDAO;
import com.truper.recertification.dao.ReSistemaDAO;
import com.truper.recertification.model.RePerfilSistemaEntity;
import com.truper.recertification.model.ReSistemaEntity;
import com.truper.recertification.service.InfoService;
import com.truper.recertification.vo.answer.SystemsVO;
import com.truper.recertification.vo.answer.ProfileSystemListVO;
import com.truper.recertification.vo.answer.ProfileSystemVO;
import com.truper.recertification.vo.answer.SystemsListVO;

@Service
public class InfoServiceImpl implements InfoService{

	@Autowired
	private ReSistemaDAO daoSistema;
	
	@Autowired
	private RePerfilSistemaDAO daoPerfilSystem;
	
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
			
			String idSystem = perfilSistemaVO.getIdPerfilSistema().getIdSistema();
			String system = daoSistema.findById(idSystem).get().getSistema();
			
			answerPSVO.setIdPerfil(perfilSistemaVO.getIdPerfilSistema().getIdPerfil());
			answerPSVO.setPerfil(perfilSistemaVO.getPerfil());
			answerPSVO.setSystemData(new SystemsVO(idSystem, system));
			
			lstAnswerPS.add(answerPSVO);
		}
		lstPSVO.setPerfiles(lstAnswerPS);
		return lstPSVO;
	}
}

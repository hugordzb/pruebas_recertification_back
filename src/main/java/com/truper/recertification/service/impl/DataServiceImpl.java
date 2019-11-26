package com.truper.recertification.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.truper.recertification.dao.ReBitacoraCambiosDAO;
import com.truper.recertification.dao.ReControlCambiosDAO;
import com.truper.recertification.dao.ReDepartamentoDAO;
import com.truper.recertification.dao.ReDetalleJefeDAO;
import com.truper.recertification.dao.RePerfilSistemaDAO;
import com.truper.recertification.dao.ReRecertificacionDAO;
import com.truper.recertification.dao.ReSistemaDAO;
import com.truper.recertification.model.PKRecertificacion;
import com.truper.recertification.model.ReBitacoraCambiosEntity;
import com.truper.recertification.model.ReControlCambiosEntity;
import com.truper.recertification.model.ReDetalleJefeEntity;
import com.truper.recertification.model.RePerfilSistemaEntity;
import com.truper.recertification.model.ReRecertificacionEntity;
import com.truper.recertification.model.ReSistemaEntity;
import com.truper.recertification.service.DataService;
import com.truper.recertification.vo.answer.SystemsVO;
import com.truper.recertification.vo.answer.BossDetailVO;
import com.truper.recertification.vo.answer.ProfileSystemVO;

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
	
	@Autowired
	private ReDetalleJefeDAO daoJefe;
	
	@Autowired
	private ReDepartamentoDAO daoDepartamento;
	
	@Autowired
	private ReRecertificacionDAO daoRecertificacion;
	
	@Override
 	public List<SystemsVO> getSystems() {
		
		List<SystemsVO> lstSystemVO = new ArrayList<>();
		SystemsVO systemVO = new SystemsVO();
		
		List<ReSistemaEntity> list = daoSistema.findAll();
		List<SystemsVO> lista = new ArrayList<>();
		
		for(int i = 0; i<list.size(); i++) {
			ReSistemaEntity  sistema = list.get(i);	
			
			systemVO.setIdSistema(sistema.getIdSistema());
			systemVO.setSistema(sistema.getSistema());
			
			lista.add(systemVO);
		}
		lstSystemVO.addAll(lista);
		return lstSystemVO;
	}

	@Override
	public List<ProfileSystemVO> getProfileSytem() {
		
		List<ProfileSystemVO> lstAnswerPS = new ArrayList<>();
		List<RePerfilSistemaEntity> lstPS = daoPerfilSystem.findAll();
		List<ProfileSystemVO> lstProfiles = new ArrayList<>();
		
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
		lstProfiles.addAll(lstAnswerPS);
		return lstProfiles;
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

	@Override
	public BossDetailVO findByBoss(String idJefe) {
		
		BossDetailVO bossVO = new BossDetailVO();
		
		ReDetalleJefeEntity detailBoss = daoJefe.findById(idJefe).get();
		bossVO.setIdJefe(idJefe);
		bossVO.setNombre(detailBoss.getNombre());
		bossVO.setCorreo(detailBoss.getCorreo());
		bossVO.setCorreoCC(detailBoss.getCorreoCC());
		bossVO.setDepartamento(daoDepartamento.findById(
				detailBoss.getIdDepartamento()).get().getDepartamento());
		
		return bossVO;
	}

	@Override
	public Map<String, Object> findByAllBoss(String periodo) {
		Map<String, Object> bossMap = new HashMap<>();
		
		List<ReDetalleJefeEntity> lstDetail = daoJefe.findAll();
		List<BossDetailVO> lstBoss = new ArrayList<>();
		
		for(int i = 0; i < lstDetail.size(); i++) {
			lstBoss.add(this.findBossForRecertification(lstDetail.get(i).getId(), periodo));
		}
		bossMap.put("jefes",lstBoss);
		
		return bossMap;
	}

	@Override
	public BossDetailVO findBossForRecertification(String idJefe, String periodo) {
		
		BossDetailVO bossVO = new BossDetailVO();
		
		ReDetalleJefeEntity detailBoss = daoJefe.findById(idJefe).get();
		bossVO.setIdJefe(idJefe);
		bossVO.setNombre(detailBoss.getNombre());
		bossVO.setCorreo(detailBoss.getCorreo());
		bossVO.setCorreoCC(detailBoss.getCorreoCC());
		bossVO.setDepartamento(daoDepartamento.findById(
				detailBoss.getIdDepartamento()).get().getDepartamento());
		
		ReRecertificacionEntity jefeRecertificado = null;
		
		try {
			jefeRecertificado = daoRecertificacion.findById(PKRecertificacion.builder()
					.idJefe(idJefe).periodo(periodo).build()).get();
			if(jefeRecertificado != null) {
				bossVO.setInRecertificacion(true);
				if(jefeRecertificado.isEstatus()) {
					bossVO.setRecertificado(true);
				}else {
					bossVO.setRecertificado(false);
				}
			}else {
				bossVO.setInRecertificacion(false);
			}
		}catch(NoSuchElementException nsee ) {
			bossVO.setInRecertificacion(false);
			bossVO.setRecertificado(false);
		}
		
		return bossVO;
	}
}

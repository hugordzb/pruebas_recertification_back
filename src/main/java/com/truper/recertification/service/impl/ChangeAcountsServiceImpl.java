package com.truper.recertification.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.truper.recertification.dao.ReBitacoraCambiosDAO;
import com.truper.recertification.dao.RePerfilSistemaDAO;
import com.truper.recertification.dao.ReSistemaDAO;
import com.truper.recertification.model.ReBitacoraCambiosEntity;
import com.truper.recertification.service.ChangeAcountsService;
import com.truper.recertification.vo.request.RequestAcountVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ChangeAcountsServiceImpl implements ChangeAcountsService{

	@Autowired
	private ReBitacoraCambiosDAO daoCambios;
  
	@Autowired
	private RePerfilSistemaDAO daoPerfil;
	
	@Autowired 
	private ReSistemaDAO daoSistema;

	@Override
	public String requestAcount(String json) {
		
		Gson gson = new Gson();
		RequestAcountVO requestVO = gson.fromJson(json, RequestAcountVO.class);
		return this.validateParams(requestVO);
	}

	private String validateParams(RequestAcountVO requestVO) {
		String answer = "";
		try {
			String strIdSistema = daoSistema.findBySistema(requestVO.getSistema()).getIdSistema();
			String strNIdSistema = daoSistema.findBySistema(requestVO.getNSistema()).getIdSistema();
			
			if(strIdSistema == null || strNIdSistema == null) {
				answer = "El sistema no es valido";
			}
			else {
				int intIdPerfil = daoPerfil.findByPerfilAndIdPerfilSistemaIdSistema(requestVO.getPerfil(), strIdSistema)
				.getIdPerfilSistema().getIdPerfil();
				if(daoPerfil.findByPerfilAndIdPerfilSistemaIdSistema(requestVO.getPerfil(), strIdSistema)
						.getIdPerfilSistema().getIdPerfil() == null) {
					answer = "No existe la relacion entre el sistema-perfil indicado";
				}
				else {
					this.mapRequest(requestVO, intIdPerfil, strIdSistema);
				}
			}
		} catch (Exception e) {
			log.info("Error en el sistema: " + e);
		}
		
		return answer;
	}
	
	private String mapRequest(RequestAcountVO requestVO, int intIdPerfil, String strIdSistema) {
		String answer = "";
		daoCambios.save(ReBitacoraCambiosEntity
				.builder()
				.tipoMov(requestVO.getTipoMov())
				.idUsuario(requestVO.getIdUsuario())
				.idSistema(strIdSistema)
				.idPerfil(intIdPerfil)
				.idJefe(requestVO.getIdJefe())
				.cuentaSistema(requestVO.getCuentaSistema())
				.nIdUsuario(requestVO.getNIdUsuario())
				.nIdSistema(requestVO.getNSistema())
				.nIdPerfil(Integer.parseInt(requestVO.getNPerfil()))
				.nIdJefe(requestVO.getNIdJefe())
				.nCuentaSistema(requestVO.getNCuentaSistema())
				.solicitante(requestVO.getSolicitante())
				.build());
		int dMov = 1;
		this.generateControlChange(dMov);
		
		return answer;		
	}
	
	private void generateControlChange(int dMov) {
		
//		daoCambios.save(ReControlCambiosEntity
//				.builder()
//				.idMovimiento(dMov)
//				.estatus(false)
//				.build());
	}

}

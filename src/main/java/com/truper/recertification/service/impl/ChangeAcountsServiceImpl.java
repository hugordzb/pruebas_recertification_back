package com.truper.recertification.service.impl;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.truper.recertification.dao.ReBitacoraCambiosDAO;
import com.truper.recertification.model.ReBitacoraCambiosEntity;
import com.truper.recertification.model.ReControlCambiosEntity;
import com.truper.recertification.service.ChangeAcountsService;
import com.truper.recertification.service.ValidateAcountsService;
import com.truper.recertification.vo.request.ProcessChangeVO;
import com.truper.recertification.vo.request.RequestChangeVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ChangeAcountsServiceImpl implements ChangeAcountsService{

	@Autowired
	private ValidateAcountsService validateAcounts;
	
	@Autowired
	private ReBitacoraCambiosDAO daoBitacora; 
	
	@Override
	public Integer requestChange(String json) {
		
		ReControlCambiosEntity control = new ReControlCambiosEntity();
		Gson gson = new Gson();
		
		RequestChangeVO requestVO = gson.fromJson(json, RequestChangeVO.class);
		
		String strIdSistema= validateAcounts.validateRequest(requestVO);
		log.info("String: " +strIdSistema);
		int intIdMov = validateAcounts.mapRequest(requestVO, strIdSistema);
		
		Date today = new Date();
		control.setAtendio("usuarioDefault");
		control.setFechaAtencion(new Timestamp(today.getTime()));
		control.setIdMovimiento(intIdMov);
		control.setEstatus(false);
		
		validateAcounts.generateControlChange(control);
		
		return intIdMov;
	}

	@Override
	public void processChange(String json) {
		Date today = new Date();
		Gson gson = new Gson();
		
		ProcessChangeVO processVO = gson.fromJson(json, ProcessChangeVO.class);
		ReControlCambiosEntity control = new ReControlCambiosEntity();
		
		control.setIdMovimiento(Integer.parseInt(processVO.getIdMovimiento()));
		control.setAtendio(processVO.getAtendio());
		control.setEstatus(Boolean.parseBoolean(processVO.getEstatus()));
		control.setFechaAtencion(new Timestamp(today.getTime()));
		control.setComentarios(processVO.getComentarios());
		
		if(control.isEstatus()) {
			ReBitacoraCambiosEntity bitacora = daoBitacora.findById(control.getIdMovimiento()).get();
			validateAcounts.processRequest(bitacora);
		}
		
		validateAcounts.generateControlChange(control);
	}
	
}

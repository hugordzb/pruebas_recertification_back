package com.truper.recertification.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.truper.recertification.dao.ReBitacoraCambiosDAO;
import com.truper.recertification.dao.ReControlCambiosDAO;
import com.truper.recertification.model.ReControlCambiosEntity;
import com.truper.recertification.service.ReviewChangeService;
import com.truper.recertification.vo.request.ReviewTicketVO;


public class ReviewChangeServiceImpl implements ReviewChangeService{

	@Autowired
	private ReControlCambiosDAO daoControl;
	
	@Autowired
	private ReBitacoraCambiosDAO daoBitacora;
	
	@Override
	public List<Integer> identityTickets() {
		List<Integer> lstNew = new ArrayList<Integer>();
		List<ReControlCambiosEntity> lstControl = daoControl.findByEstatus(false);

		for(int i = 0; i<lstControl.size(); i++) {
			lstNew.add(lstControl.get(i).getIdMovimiento());
		}
		return lstNew;
	}

	@Override
	public ReControlCambiosEntity reviewTicket(int intTicket) {
		return daoControl.findById(intTicket).get();
	}

	@Override
	public void updateStatusTicket(String json) {
		Gson gson = new Gson();
		ReviewTicketVO reviewVO = gson.fromJson(json, ReviewTicketVO.class);
		
		this.updateBitacora(reviewVO);
	}
	
	private void updateBitacora(ReviewTicketVO reviewVO) {
		
		Date today = new Date();
		ReControlCambiosEntity control = daoControl.findById(Integer.parseInt(reviewVO.getIdMovimiento())).get();
				
		control.setAtendio(reviewVO.getAtendio());
		control.setEstatus(true);
		control.setFechaAtencion(new Timestamp(today.getTime()));
		control.setComentarios(reviewVO.getComentarios());
		daoControl.save(control);
		
		this.updateByTypeMov(daoBitacora.findById(control.getIdMovimiento()).get().getTipoMov());
	}
	
	private void updateByTypeMov(String strTipoMov) {
		
		switch (strTipoMov) {
		case "A":
			
			break;
		case "B":
			
			break;
		case "M":
			
			break;

		default:
			break;
		}
	}

}

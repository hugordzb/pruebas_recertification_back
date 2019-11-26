package com.truper.recertification.service;

import com.truper.recertification.model.ReDetalleJefeEntity;

public interface RecertificationMailService {

	/**
	 * This method send email by boss
	 * @param strIdJefe
	 * @return
	 */
	public boolean sendMail(String strIdJefe);
	
	/**
	 * This method set email data
	 * @param detailBoss
	 */
	public void generateMail(ReDetalleJefeEntity detailBoss);
	
	/**
	 * This method update insert on DB
	 * @param detailBoss
	 */
	public void updateDB(ReDetalleJefeEntity detailBoss);
}

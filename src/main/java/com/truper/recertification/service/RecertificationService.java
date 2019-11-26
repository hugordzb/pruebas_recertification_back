package com.truper.recertification.service;

import com.truper.recertification.model.ReDetalleJefeEntity;

public interface RecertificationService {

	/**
	 * This method send email by boss
	 * @param strIdJefe
	 * @return
	 */
	public boolean sendMail(String strIdJefe);
	
	/**
	 * This method checks the recertification status of the boss and updates if to the opposite value it has 
	 * in the moment it is checked.
	 * @param strIdJefe
	 * @param srtPeriodo
	 * @return true if the boss is now recertified and false if it is not
	 */
	public boolean recertifyBoss(String strIdJefe, String strPeriodo);
	
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

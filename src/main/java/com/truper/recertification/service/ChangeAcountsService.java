package com.truper.recertification.service;

public interface ChangeAcountsService {

	/**
	 * This method read request Json, validate data and insert DB on RE_BITACORA_CAMBIOS
	 * @param json
	 * @return idMovement generated
	 */
	public Integer requestChange(String json);
	
	/**
	 * This method read Json, validate answer of "Mesa de Servicio", 
	 * update DB on table RECONTROL_CAMBIOS, 
	 * process data if the change was accepted
	 * @param json
	 */
	public boolean processChange(String json);
}

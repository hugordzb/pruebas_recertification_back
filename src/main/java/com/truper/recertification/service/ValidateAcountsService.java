package com.truper.recertification.service;

import com.truper.recertification.json.request.vo.RequestChangeVO;
import com.truper.recertification.model.ReBitacoraCambiosEntity;
import com.truper.recertification.model.ReControlCambiosEntity;

public interface ValidateAcountsService {

	/**
	 * This method validate params to prepare data by movement type
	 * @param requestVO
	 */
	public String validateRequest(RequestChangeVO requestVO);
	
	/**
	 * This method 
	 * @param requestVO
	 */
	public String processRequest(ReBitacoraCambiosEntity bitacora);

	/**
	 * Insert on ReBitacora_Cambios changes that are requested.
	 * @param requestVO
	 * @param intIdPerfil
	 * @param strIdSistema
	 * @return idMovement
	 */
	public Integer mapRequest(RequestChangeVO requestVO, String strIdSistema) throws Exception;

	/**
	 * Insert on ReControl_Cambios the new request
	 * @param control
	 */
	public void generateControlChange(ReControlCambiosEntity control);
	
	/**
	 * This method validate relation between system - profile
	 * @param strIdSistema
	 * @param strPerfil
	 * @return
	 */
	public Integer validateSystemProfile(String strIdSistema, String strPerfil);
	
}

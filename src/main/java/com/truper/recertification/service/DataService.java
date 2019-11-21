package com.truper.recertification.service;

import java.util.Map;

import com.truper.recertification.model.ReControlCambiosEntity;
import com.truper.recertification.vo.answer.ProfileSystemListVO;
import com.truper.recertification.vo.answer.SystemsListVO;

public interface DataService {

	/**
	 * This method return all systems
	 * @return idSystems and Systems
	 */
	public SystemsListVO getSystems();

	/**
	 * This method return relation system-profile
	 * @return relation system-profile and id's
	 */
	public ProfileSystemListVO getProfileSytem();
	
	/**
	 * This method identify control changes that have status 0
	 * @return REBitacora_Cambios data
	 */
	public Map<String, Object> getChangeControl();
	
	/**
	 * This method contain the ticket detail
	 * @param intTicket
	 * @return Detail by movement
	 */
	public ReControlCambiosEntity getByTicket(int intTicket);
	
}

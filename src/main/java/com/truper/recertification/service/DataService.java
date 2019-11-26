package com.truper.recertification.service;

import java.util.List;
import java.util.Map;

import com.truper.recertification.model.ReControlCambiosEntity;
import com.truper.recertification.vo.answer.BossDetailVO;
import com.truper.recertification.vo.answer.ProfileSystemVO;
import com.truper.recertification.vo.answer.SystemsVO;

public interface DataService {

	/**
	 * This method return all systems
	 * @return idSystems and Systems
	 */
	public List<SystemsVO> getSystems();

	/**
	 * This method return relation system-profile
	 * @return relation system-profile and id's
	 */
	public List<ProfileSystemVO> getProfileSytem();
	
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
	
	/**
	 * This method contain boss data
	 * @param idJefe
	 * @return
	 */
	public BossDetailVO findByBoss(String idJefe);
	
	/**
	 * This method finds the boss data for the recertification process, it returns flags
	 * to know an email has been sent to the boss and if it is recertified.
	 * @param String idJefe, is the boss id
	 * @param String periodo, is the period of the recertififcation 
	 */
	public BossDetailVO findBossForRecertification(String idJefe, String periodo);
	
	/**
	 * This method that contain all boss data from period
	 * @return
	 */
	public Map<String, Object> findByAllBoss(String periodo);
}

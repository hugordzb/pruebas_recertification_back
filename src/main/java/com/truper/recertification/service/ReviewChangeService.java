package com.truper.recertification.service;

import java.util.List;

import com.truper.recertification.model.ReControlCambiosEntity;

public interface ReviewChangeService {

	/**
	 * This method find all unattended movements
	 * @return unattended movements list
	 */
	public List<Integer> identityTickets();
	
	/**
	 * This method contain the ticket detail
	 * @param intTicket
	 * @return Detail by movement
	 */
	public ReControlCambiosEntity reviewTicket(int intTicket);
	
	/**
	 * This method read a json and update database
	 * @param json
	 */
	public void updateStatusTicket(String json);
}

package com.truper.recertification.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.truper.recertification.model.ReControlCambiosEntity;

/**
 * This class content methods to save or find data on RE_CONTROL_CAMBIOS table
 * @author mgmolinae
 */
public interface ReControlCambiosDAO extends JpaRepository<ReControlCambiosEntity, Integer>{

	/**
	 * This method find data on DB by user that review the change
	 * @param atendio
	 * @return List<ReControlCambiosEntity>
	 */
	public List<ReControlCambiosEntity> findByAtendio(String atendio);
	
	/**
	 * This method find data on DB by status
	 * @param estatus
	 * @return List<ReControlCambiosEntity>
	 */
	public List<ReControlCambiosEntity> findByEstatus(boolean estatus);
}

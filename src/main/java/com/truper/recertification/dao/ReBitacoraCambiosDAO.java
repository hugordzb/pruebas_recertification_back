package com.truper.recertification.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.truper.recertification.model.ReBitacoraCambiosEntity;
import com.truper.recertification.model.ReControlCambiosEntity;

/**
 * This class content methods to save or find data on RE_BITACORA_CAMBIOS table
 * @author mgmolinae
 */
public interface ReBitacoraCambiosDAO extends JpaRepository<ReBitacoraCambiosEntity, Integer>{

	/**
	 * This method find by movement type
	 * @param tipoMov
	 * @return List<ReBitacoraCambiosEntity>
	 */
	public List<ReBitacoraCambiosEntity> findByTipoMov(String tipoMov);
	
	/**
	 * This method find by user boss that request the change
	 * @param solicitante
	 * @return List<ReBitacoraCambiosEntity>
	 */
	public List<ReBitacoraCambiosEntity> findBySolicitante(String solicitante);

	/**
	 * This method save data on RE_BTACORA_CAMBIOS table
	 * @param control
	 */
	public void save(ReControlCambiosEntity control);

}
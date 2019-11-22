package com.truper.recertification.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.truper.recertification.model.PKRecertificacion;
import com.truper.recertification.model.ReRecertificacionEntity;

/**
 * This class content methods to find data on RE_RECERTIFICATION table
 * @author mgmolinae
 */
public interface ReRecertificacionDAO extends JpaRepository<ReRecertificacionEntity, PKRecertificacion>{

	/**
	 * This method find data by status
	 * @param estatus
	 * @return List<ReRecertificacionEntity>
	 */
	public List<ReRecertificacionEntity> findByEstatus(boolean estatus);
	
	/**
	 * This method find data by period
	 * @param periodo
	 * @return List<ReRecertificacionEntity>
	 */
	public List<ReRecertificacionEntity> findByIdRecertificacionPeriodo(String periodo);
	
}

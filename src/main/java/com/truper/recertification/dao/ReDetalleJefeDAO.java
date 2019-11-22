package com.truper.recertification.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.truper.recertification.model.ReDetalleJefeEntity;

/**
 * This class content methods to find data on RE_DETALLE_JEFE table
 * @author mgmolinae
 */
public interface ReDetalleJefeDAO extends JpaRepository<ReDetalleJefeEntity, String>{

	/**
	 * This method find data by idDepartment
	 * @param idDepartamento
	 * @return ReDetalleJefeEntity
	 */
	public ReDetalleJefeEntity findByIdDepartamento(Integer idDepartamento);
	
	/**
	 * This method find data by boss name 
	 * @param nombre
	 * @return ReDetalleJefeEntity
	 */
	public ReDetalleJefeEntity findByNombre(String nombre);

}

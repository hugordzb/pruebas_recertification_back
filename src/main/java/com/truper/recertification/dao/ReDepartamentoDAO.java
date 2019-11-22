package com.truper.recertification.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.truper.recertification.model.ReDepartamentoEntity;

/**
 * This class content methods to find data on RE_DEPARTAMENTO table
 * @author mgmolinae
 */
public interface ReDepartamentoDAO extends JpaRepository<ReDepartamentoEntity, Integer>{

	/**
	 * This method find by department
	 * @param departamento
	 * @return ReDepartamentoEntity
	 */
	public ReDepartamentoEntity findByDepartamento(String departamento);
}

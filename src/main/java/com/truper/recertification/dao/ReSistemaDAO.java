package com.truper.recertification.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.truper.recertification.model.ReSistemaEntity;

/**
 * This class content methods to find data on RE_SISTEMA table
 * @author mgmolinae
 */
public interface ReSistemaDAO extends JpaRepository<ReSistemaEntity, String>{

	/**
	 * This method find data by system
	 * @param sistema
	 * @return ReSistemaEntity
	 */
	public ReSistemaEntity findBySistema(String sistema);
}

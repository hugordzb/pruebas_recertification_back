package com.truper.recertification.dao;

import java.sql.Timestamp;

import org.springframework.data.jpa.repository.JpaRepository;

import com.truper.recertification.model.ReTokenEntity;

/**
 * This class content methods to find data on RE_TOKEN table
 * @author mgmolinae
 */
public interface ReTokenDAO extends JpaRepository<ReTokenEntity, String> {

	/**
	 * This method find data by last Session Date
	 * @param ultimaSesion
	 * @return ReTokenEntity
	 */
	public ReTokenEntity findByUltimaSesion(Timestamp ultimaSesion);
}

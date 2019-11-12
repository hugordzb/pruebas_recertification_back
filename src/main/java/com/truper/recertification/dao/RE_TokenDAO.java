package com.truper.recertification.dao;

import java.sql.Timestamp;

import org.springframework.data.jpa.repository.JpaRepository;

import com.truper.recertification.model.ReTokenEntity;

public interface RE_TokenDAO extends JpaRepository<ReTokenEntity, String> {

	public ReTokenEntity findByUltimaSesion(Timestamp ultimaSesion);
}

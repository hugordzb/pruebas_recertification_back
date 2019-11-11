package com.truper.recertification.dao;

import java.sql.Timestamp;

import org.springframework.data.jpa.repository.JpaRepository;

import com.truper.recertification.model.RE_TokenEntity;

public interface RE_TokenDAO extends JpaRepository<RE_TokenEntity, String> {

	public RE_TokenEntity findByUltimaSesion(Timestamp ultimaSesion);
}

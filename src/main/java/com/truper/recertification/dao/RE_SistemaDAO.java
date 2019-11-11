package com.truper.recertification.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.truper.recertification.model.RE_SistemaEntity;

public interface RE_SistemaDAO extends JpaRepository<RE_SistemaEntity, String>{

	public RE_SistemaEntity findBySistema(String sistema);
}

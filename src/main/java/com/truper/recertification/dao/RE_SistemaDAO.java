package com.truper.recertification.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.truper.recertification.model.ReSistemaEntity;

public interface RE_SistemaDAO extends JpaRepository<ReSistemaEntity, String>{

	public ReSistemaEntity findBySistema(String sistema);
}

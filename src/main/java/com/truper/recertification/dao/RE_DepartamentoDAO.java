package com.truper.recertification.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.truper.recertification.model.ReDepartamentoEntity;

public interface RE_DepartamentoDAO extends JpaRepository<ReDepartamentoEntity, Integer>{

	public ReDepartamentoEntity findByDepartamento(String departamento);
}

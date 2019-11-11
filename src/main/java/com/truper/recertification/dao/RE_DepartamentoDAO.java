package com.truper.recertification.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.truper.recertification.model.RE_DepartamentoEntity;

public interface RE_DepartamentoDAO extends JpaRepository<RE_DepartamentoEntity, Integer>{

	public RE_DepartamentoEntity findByDepartamento(String departamento);
}

package com.truper.recertification.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.truper.recertification.model.RE_Control_CambiosEntity;

public interface RE_Control_CambiosDAO extends JpaRepository<RE_Control_CambiosEntity, Integer>{

	public List<RE_Control_CambiosEntity> findByAtendio(String atendio);
	
	public List<RE_Control_CambiosEntity> findByEstatus(boolean estatus);
}

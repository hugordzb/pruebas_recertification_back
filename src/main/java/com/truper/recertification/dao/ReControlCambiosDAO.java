package com.truper.recertification.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.truper.recertification.model.ReControlCambiosEntity;

public interface ReControlCambiosDAO extends JpaRepository<ReControlCambiosEntity, Integer>{

	public List<ReControlCambiosEntity> findByAtendio(String atendio);
	
	public List<ReControlCambiosEntity> findByEstatus(boolean estatus);
}

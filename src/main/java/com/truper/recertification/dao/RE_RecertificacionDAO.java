package com.truper.recertification.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.truper.recertification.model.RE_RecertificacionEntity;

public interface RE_RecertificacionDAO extends JpaRepository<RE_RecertificacionEntity, String>{

	public List<RE_RecertificacionEntity> findByEstatus(boolean estatus);
	
	public List<RE_RecertificacionEntity> findByPeriodo(String periodo);
	
	public RE_RecertificacionEntity findByIdJefeandIdPeriodo(String idJefe, String IdPeriodo);
}

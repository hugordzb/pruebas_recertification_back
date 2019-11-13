package com.truper.recertification.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.truper.recertification.model.PKRecertificacion;
import com.truper.recertification.model.ReRecertificacionEntity;

public interface ReRecertificacionDAO extends JpaRepository<ReRecertificacionEntity, PKRecertificacion>{

	public List<ReRecertificacionEntity> findByEstatus(boolean estatus);
	
	public List<ReRecertificacionEntity> findByIdRecertificacion(PKRecertificacion idRecertificacion);

	public List<ReRecertificacionEntity> findByIdRecertificacionPeriodo(String periodo);
}

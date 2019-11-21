package com.truper.recertification.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.truper.recertification.model.ReBitacoraCambiosEntity;
import com.truper.recertification.model.ReControlCambiosEntity;

public interface ReBitacoraCambiosDAO extends JpaRepository<ReBitacoraCambiosEntity, Integer>{

	public List<ReBitacoraCambiosEntity> findByTipoMov(String tipoMov);
	
	public List<ReBitacoraCambiosEntity> findBySolicitante(String solicitante);

	public void save(ReControlCambiosEntity control);
	
}
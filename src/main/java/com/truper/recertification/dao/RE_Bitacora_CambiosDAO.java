package com.truper.recertification.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.truper.recertification.model.RE_Bitacora_CambiosEntity;

public interface RE_Bitacora_CambiosDAO extends JpaRepository<RE_Bitacora_CambiosEntity, Integer>{

	public List<RE_Bitacora_CambiosEntity> findByTipoMov(String tipoMov);
	
	public List<RE_Bitacora_CambiosEntity> findBySolicitante(String solicitante);
}
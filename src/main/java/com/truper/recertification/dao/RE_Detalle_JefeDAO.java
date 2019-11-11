package com.truper.recertification.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.truper.recertification.model.RE_Detalle_JefeEntity;

public interface RE_Detalle_JefeDAO extends JpaRepository<RE_Detalle_JefeEntity, String>{

	public RE_Detalle_JefeEntity findByIdDepartamento(Integer idDepartamento);
	
	public List<RE_Detalle_JefeEntity> findByIdJefe(String idJefe);
	
}

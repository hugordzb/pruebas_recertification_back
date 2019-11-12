package com.truper.recertification.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.truper.recertification.model.ReDetalleJefeEntity;

public interface RE_Detalle_JefeDAO extends JpaRepository<ReDetalleJefeEntity, String>{

	public ReDetalleJefeEntity findByIdDepartamento(Integer idDepartamento);
	
	public List<ReDetalleJefeEntity> findByIdJefe(String idJefe);
	
}

package com.truper.recertification.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.truper.recertification.model.ReDetalleJefeEntity;

public interface ReDetalleJefeDAO extends JpaRepository<ReDetalleJefeEntity, String>{

	public ReDetalleJefeEntity findByIdDepartamento(Integer idDepartamento);
		
	public ReDetalleJefeEntity findByNombre(String nombre);

}

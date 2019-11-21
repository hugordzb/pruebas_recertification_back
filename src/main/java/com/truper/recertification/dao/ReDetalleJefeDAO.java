package com.truper.recertification.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.truper.recertification.model.ReDetalleJefeEntity;

public interface ReDetalleJefeDAO extends JpaRepository<ReDetalleJefeEntity, String>{

	public ReDetalleJefeEntity findByIdDetalleJefeIdDepartamento(Integer idDepartamento);
	
	public ReDetalleJefeEntity findByIdDetalleJefeIdJefe(String idJefe);
	
	public ReDetalleJefeEntity findByNombre(String nombre);

}

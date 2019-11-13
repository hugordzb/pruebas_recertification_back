package com.truper.recertification.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.truper.recertification.model.ReUsuarioEntity;

public interface ReUsuarioDAO extends JpaRepository<ReUsuarioEntity, String>{

	public ReUsuarioEntity findByNombre(String nombre);
	
	public ReUsuarioEntity findByNoEmpleado(Integer noEmpleado);
	
	public List<ReUsuarioEntity> findByEstatus(boolean estatus);
	
}

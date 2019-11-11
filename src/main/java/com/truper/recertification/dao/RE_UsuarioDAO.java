package com.truper.recertification.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.truper.recertification.model.RE_UsuarioEntity;

public interface RE_UsuarioDAO extends JpaRepository<RE_UsuarioEntity, String>{

	public RE_UsuarioEntity findByNombre(String nombre);
	
	public RE_UsuarioEntity findByNoEmpleado(Integer noEmpleado);
	
	public List<RE_UsuarioEntity> findByEstatus(boolean estatus);
	
}

package com.truper.recertification.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.truper.recertification.model.RePerfilSistemaEntity;

public interface RePerfilSistemaDAO extends JpaRepository<RePerfilSistemaEntity, Integer>{

	public List<RePerfilSistemaEntity> findByIdSistema(String idSistema);
	
	public List<RePerfilSistemaEntity> findByPerfil(String perfil);
	
	public RePerfilSistemaEntity findByRol(String rol);
	
	public RePerfilSistemaEntity findByIdSistemaAndPerfilAndRol(String idSistema, String perfil, String rol);
}

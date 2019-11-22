package com.truper.recertification.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.truper.recertification.model.RePerfilSistemaEntity;

/**
 * This class content methods to find data on RE_PERFIL_SISTEMA table
 * @author mgmolinae
 */
public interface RePerfilSistemaDAO extends JpaRepository<RePerfilSistemaEntity, Integer>{

	/**
	 * This method  find data by idSystem
	 * @param idSistema
	 * @return List<RePerfilSistemaEntity>
	 */
	public List<RePerfilSistemaEntity> findByIdSistema(String idSistema);
	
	/**
	 * This method find data by profile
	 * @param perfil
	 * @return List<RePerfilSistemaEntity>
	 */
	public List<RePerfilSistemaEntity> findByPerfil(String perfil);
	
	/**
	 * This method find data by rol
	 * @param rol
	 * @return RePerfilSistemaEntity
	 */
	public RePerfilSistemaEntity findByRol(String rol);
	
	/**
	 * This method find data by idSystem, profile and rol
	 * @param idSistema
	 * @param perfil
	 * @param rol
	 * @return RePerfilSistemaEntity
	 */
	public RePerfilSistemaEntity findByIdSistemaAndPerfilAndRol(String idSistema, String perfil, String rol);
}

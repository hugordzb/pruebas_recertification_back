package com.truper.recertification.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.truper.recertification.model.PKPerfilSistema;
import com.truper.recertification.model.RePerfilSistemaEntity;

public interface RePerfilSistemaDAO extends JpaRepository<RePerfilSistemaEntity, PKPerfilSistema>{

	public List<RePerfilSistemaEntity> findByIdPerfilSistemaIdSistema(String idSistema);
	
	public List<RePerfilSistemaEntity> findByPerfil(String perfil);
}

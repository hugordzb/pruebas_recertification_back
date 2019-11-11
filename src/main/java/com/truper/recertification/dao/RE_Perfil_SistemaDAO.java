package com.truper.recertification.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.truper.recertification.model.PK_Perfil_Sistema;
import com.truper.recertification.model.RE_Perfil_SistemaEntity;

public interface RE_Perfil_SistemaDAO extends JpaRepository<RE_Perfil_SistemaEntity, PK_Perfil_Sistema>{

	public List<RE_Perfil_SistemaEntity> findByIdPerfilSistemaIdSistema(String idSistema);
	
	public List<RE_Perfil_SistemaEntity> findByPerfil(String perfil);
}

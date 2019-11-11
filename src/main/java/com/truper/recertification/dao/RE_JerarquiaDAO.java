package com.truper.recertification.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.truper.recertification.model.PK_Jerarquia;
import com.truper.recertification.model.RE_JerarquiaEntity;

public interface RE_JerarquiaDAO extends JpaRepository<RE_JerarquiaEntity, PK_Jerarquia>{

	public List<RE_JerarquiaEntity> findByIdEmpleadoJefeIdJefe(String idJefe);
	
	public RE_JerarquiaEntity findByIdEmpleadoJefeIdUsuario(String idUsuario);
}

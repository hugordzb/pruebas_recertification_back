package com.truper.recertification.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.truper.recertification.model.PKJerarquia;
import com.truper.recertification.model.ReJerarquiaEntity;

public interface RE_JerarquiaDAO extends JpaRepository<ReJerarquiaEntity, PKJerarquia>{

//	public List<ReJerarquiaEntity> findByIdJerarquiaIdJefe(String idJefe);
	
//	public ReJerarquiaEntity findByIdJerarquiaIdUsuario(String idUsuario);
}

package com.truper.recertification.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.truper.recertification.model.PKJerarquia;
import com.truper.recertification.model.ReJerarquiaEntity;

public interface ReJerarquiaDAO extends JpaRepository<ReJerarquiaEntity, PKJerarquia>{

//	public List<ReJerarquiaEntity> findByIdJerarquiaIdJefe(String idJefe);
	
//	public List<ReJerarquiaEntity> findByIdEmpleadoJefe(PKJerarquia idEmpleadoJefe);;
}

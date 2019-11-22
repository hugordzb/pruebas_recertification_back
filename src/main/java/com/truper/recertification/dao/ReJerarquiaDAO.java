package com.truper.recertification.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.truper.recertification.model.PKJerarquia;
import com.truper.recertification.model.ReJerarquiaEntity;

/**
 * This class content methods to find data on RE_JERARQUIA table
 * @author mgmolinae
 */
public interface ReJerarquiaDAO extends JpaRepository<ReJerarquiaEntity, PKJerarquia>{

	/**
	 * This method find data by idBoss
	 * @param idJefe
	 * @return List<ReJerarquiaEntity>
	 */
	public List<ReJerarquiaEntity> findByIdEmpleadoJefeIdJefe(String idJefe);
	
	/**
	 * This method find data by idEmployee
	 * @param idUsuario
	 * @return ReJerarquiaEntity
	 */
	public ReJerarquiaEntity findByIdEmpleadoJefeIdUsuario(String idUsuario);
}

package com.truper.recertification.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.truper.recertification.model.ReUsuarioEntity;

/**
 * This class content methods to find data on RE_USUARIO table
 * @author mgmolinae
 */
public interface ReUsuarioDAO extends JpaRepository<ReUsuarioEntity, String>{

	/**
	 * This method find data by idUser and Status
	 * @param idUsuario
	 * @param estatus
	 * @return ReUsuarioEntity
	 */
	public ReUsuarioEntity findByIdUsuarioAndEstatus(String idUsuario, boolean estatus);
	
	/**
	 * This method find data by idUser
	 * @param idUsuario
	 * @return ReUsuarioEntity
	 */
	public ReUsuarioEntity findByIdUsuario(String idUsuario);
	
	/**
	 * This method find data by idUser and Status
	 * @param nombre
	 * @return ReUsuarioEntity
	 */
	public ReUsuarioEntity findByNombre(String nombre);
	
	/**
	 * This method find data by EmployNumber
	 * @param noEmpleado
	 * @return ReUsuarioEntity
	 */
	public ReUsuarioEntity findByNoEmpleado(Integer noEmpleado);
	
	/**
	 * This method find data by Status
	 * @param estatus
	 * @return List<ReUsuarioEntity> 
	 */
	public List<ReUsuarioEntity> findByEstatus(boolean estatus);
	
}

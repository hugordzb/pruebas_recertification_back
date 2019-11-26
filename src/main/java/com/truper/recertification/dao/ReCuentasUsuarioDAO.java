package com.truper.recertification.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.truper.recertification.model.PKCuentasUsuario;
import com.truper.recertification.model.ReCuentasUsuarioEntity;

/**
 * This class content methods to find data in RE_CUENTAS_USUARIO table
 * @author mgmolinae
 */
public interface ReCuentasUsuarioDAO extends JpaRepository<ReCuentasUsuarioEntity, PKCuentasUsuario>{

	/**
	 * This method find data by IdUsuario (could be idBoss or idEmploy)
	 * @param idUsuario
	 * @return List<ReCuentasUsuarioEntity>
	 */
	public List<ReCuentasUsuarioEntity> findByIdCuentaUsuarioIdUsuario(String idUsuario);

	/**
	 * This method find data by IdUsuario and systemAcount
	 * @param idUsuario
	 * @param cuentaSistema
	 * @return List<ReCuentasUsuarioEntity>
	 */
	public List<ReCuentasUsuarioEntity> findByIdCuentaUsuarioIdUsuarioAndIdCuentaUsuarioCuentaSistema(String idUsuario, String cuentaSistema);
		
	/**
	 * This method find data by systemAcount
	 * @param cuentaSistema
	 * @return List<ReCuentasUsuarioEntity>
	 */
	public List<ReCuentasUsuarioEntity> findByIdCuentaUsuarioCuentaSistema(String cuentaSistema);
	
	/**
	 * This method find data by systemAcount
	 * @param cuentaSistema
	 * @return List<ReCuentasUsuarioEntity>
	 */
	public List<ReCuentasUsuarioEntity> findByIdCuentaUsuarioIdPerfil(Integer idPerfil);
	
	
	public ReCuentasUsuarioEntity findByIdCuentaUsuarioIdPerfilAndIdCuentaUsuarioCuentaSistema(Integer idPerfil, String cuentaSistema);
}

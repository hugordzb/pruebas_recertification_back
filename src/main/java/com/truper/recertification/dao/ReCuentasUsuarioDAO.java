package com.truper.recertification.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.truper.recertification.model.PKCuentasUsuario;
import com.truper.recertification.model.ReCuentasUsuarioEntity;

public interface ReCuentasUsuarioDAO extends JpaRepository<ReCuentasUsuarioEntity, PKCuentasUsuario>{

	public List<ReCuentasUsuarioEntity> findByIdCuentaUsuarioIdUsuario(String idUsuario);
		
	public List<ReCuentasUsuarioEntity> findByIdCuentaUsuarioCuentaSistema(String cuentaSistema);
	
}

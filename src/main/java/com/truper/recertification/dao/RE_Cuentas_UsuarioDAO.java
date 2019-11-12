package com.truper.recertification.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.truper.recertification.model.PKCuentasUsuario;
import com.truper.recertification.model.ReCuentasUsuarioEntity;

public interface RE_Cuentas_UsuarioDAO extends JpaRepository<ReCuentasUsuarioEntity, PKCuentasUsuario>{

//	public List<ReCuentasUsuarioEntity> findByIdCuentasUsuarioIdUsuario(String idUsuario);
	
	public ReCuentasUsuarioEntity findByCuentaSistema(String cuentaSistema);
}

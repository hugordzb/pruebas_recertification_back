package com.truper.recertification.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.truper.recertification.model.PK_Cuentas_Usuario;
import com.truper.recertification.model.RE_Cuentas_UsuarioEntity;

public interface RE_Cuentas_UsuarioDAO extends JpaRepository<RE_Cuentas_UsuarioEntity, PK_Cuentas_Usuario>{

	public List<RE_Cuentas_UsuarioEntity> findbyidCuenta_UsuarioEntitiesIdUsuario(String idUsuario);
	
	public RE_Cuentas_UsuarioEntity findByCuentaSistema(String cuentaSistema);
}

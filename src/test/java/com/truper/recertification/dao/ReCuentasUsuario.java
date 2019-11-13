package com.truper.recertification.dao;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.truper.recertification.model.ReCuentasUsuarioEntity;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class ReCuentasUsuario {

	@Autowired
	private ReCuentasUsuarioDAO cuentaDAO;
	
	@Test
	public void findByIdCuentasUsuarioCuentasUsuario() {
		List<ReCuentasUsuarioEntity> lista = cuentaDAO.findByIdCuentaUsuarioCuentaSistema("jbaltazar");
		log.info("Lista: " + lista.get(0).getIdCuentaUsuario());
	}
	
}

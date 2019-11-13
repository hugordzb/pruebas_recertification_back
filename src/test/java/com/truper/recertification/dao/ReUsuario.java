package com.truper.recertification.dao;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.truper.recertification.model.ReUsuarioEntity;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class ReUsuario {

	@Autowired
	private ReUsuarioDAO usuarioDAO;
		
	@Test
	public void findByNombre() {
		ReUsuarioEntity lista = usuarioDAO.findByNombre("nombre");
		log.info("Lista: " + lista);
	}
	
	@Test
	public void findByNoEmpleado() {
		ReUsuarioEntity lista = usuarioDAO.findByNoEmpleado(1);
		log.info("Lista: " + lista);
	}
	
	@Test
	public void findByEstatusUsuario() {
		List<ReUsuarioEntity> lista = usuarioDAO.findByEstatus(true);
		log.info("Lista: " + lista.size());
	}
}

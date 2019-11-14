package com.truper.recertification.dao;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.truper.recertification.model.RePerfilSistemaEntity;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class RePerfilSistema {
	
	@Autowired
	private RePerfilSistemaDAO perfilDAO;
	
	@Test
	public void findByIdPerfilSistemaIdSistemaPerfil() {
		List<RePerfilSistemaEntity> lista = perfilDAO.findByIdPerfilSistemaIdSistema("S001");
		log.info("Lista: " + lista.size());
	}
	
	@Test
	public void findByPerfilAndIdPerfilSistemaIdSistema() {
		List<RePerfilSistemaEntity> lista = perfilDAO.findByPerfil("perfil");
		log.info("Lista: " + lista.size());
	}
	
	@Test
	public void findByIdEmpleadoJefeIdJefePerfil() {
		RePerfilSistemaEntity lista = perfilDAO.findByPerfilAndIdPerfilSistemaIdSistema("Admin", "S001");
		log.info("Lista: " + lista.getIdPerfilSistema());
	}	
}

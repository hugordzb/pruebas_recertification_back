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
		List<RePerfilSistemaEntity> lista = perfilDAO.findByIdSistema("S001");
		log.info("Lista: " + lista.get(0).getIdPerfil());
	}
	
	@Test
	public void findByPerfilAndIdPerfilSistemaIdSistema() {
		List<RePerfilSistemaEntity> lista = perfilDAO.findByPerfil("perfil");
		log.info("Lista: " + lista.size());
	}
	
	@Test
	public void findByIdEmpleadoJefeIdJefePerfil() {
		RePerfilSistemaEntity lista = perfilDAO.findByIdSistemaAndPerfilAndRol("S001","Admin", null);
		log.info("Lista: " + lista.getIdSistema());
		log.info("Lista: " + lista.getIdPerfil());
	}	
}

package com.truper.recertification.dao;

import java.sql.Timestamp;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.truper.recertification.model.PKRecertificacion;
import com.truper.recertification.model.ReBitacoraCambiosEntity;
import com.truper.recertification.model.ReControlCambiosEntity;
import com.truper.recertification.model.ReCuentasUsuarioEntity;
import com.truper.recertification.model.ReDepartamentoEntity;
import com.truper.recertification.model.ReDetalleJefeEntity;
import com.truper.recertification.model.RePerfilSistemaEntity;
import com.truper.recertification.model.ReRecertificacionEntity;
import com.truper.recertification.model.ReSistemaEntity;
import com.truper.recertification.model.ReTokenEntity;
import com.truper.recertification.model.ReUsuarioEntity;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class DAOTest {

	@Autowired
	private RE_Bitacora_CambiosDAO bitacoraDAO;
	
	@Autowired
	private RE_Control_CambiosDAO controlDAO;
	
	@Autowired
	private RE_Cuentas_UsuarioDAO cuentaDAO;
	
	@Autowired
	private RE_DepartamentoDAO departamentoDAO;
	
	@Autowired
	private RE_Detalle_JefeDAO detalleDAO;
	
	@Autowired
	private RE_JerarquiaDAO jerarquiaDAO;
	
	@Autowired
	private RE_Perfil_SistemaDAO perfilDAO;
	
	@Autowired
	private RE_RecertificacionDAO recertificacionDAO;
	
	@Autowired
	private RE_SistemaDAO sistemaDAO;
	
	@Autowired
	private RE_TokenDAO tokenDAO;
	
	@Autowired
	private RE_UsuarioDAO usuarioDAO;
	
	//Bitacora_Cambios
	@Test
	public void findByTipoMov() {
		List<ReBitacoraCambiosEntity> lista = bitacoraDAO.findByTipoMov("A");
		log.info("Lista: " + lista.size());
	}
	
	@Test
	public void findBySolicitante() {
		List<ReBitacoraCambiosEntity> lista = bitacoraDAO.findBySolicitante("solicitante");
		log.info("Lista: " + lista.size());
	}
	
	//Control_Cambios
	@Test
	public void findByAtendio() {
		List<ReControlCambiosEntity> lista = controlDAO.findByAtendio("atendio");
		log.info("Lista: " + lista.size());
	}

	@Test
	public void findByEstatus() {
		List<ReControlCambiosEntity> lista = controlDAO.findByEstatus(false);
		log.info("Lista: " + lista.size());
	}

	//Cuentas_Usuario
	@Test
	public void findbyidCuenta_UsuarioEntitiesIdUsuario() {
//		List<ReCuentasUsuarioEntity> lista = cuentaDAO.findByIdCuentasUsuarioIdUsuario("usuario");
//		log.info("Lista: " + lista.size());
	}
	
	@Test
	public void findByCuentaSistema() {
		ReCuentasUsuarioEntity lista = cuentaDAO.findByCuentaSistema("mgmolinae");
		log.info("Lista: " + lista);
	}
	
	//Departamento
	@Test
	public void findByDepartamento() {
		ReDepartamentoEntity lista = departamentoDAO.findByDepartamento("Departamento");
		log.info("Lista: " + lista);
	}
	
	//Detalle_Jefe
	@Test
	public void findByIdDepartamento() {
		ReDetalleJefeEntity lista = detalleDAO.findByIdDepartamento(1);
		log.info("Lista: " + lista);
	}

	@Test
	public void findByIdJefe() {
		List<ReDetalleJefeEntity> lista = detalleDAO.findByIdJefe("jefe");
		log.info("Lista: " + lista.size());
	}
	
	//Jerarquia
	@Test
	public void findByIdEmpleadoJefeIdJefe() {
//		List<ReJerarquiaEntity> lista = jerarquiaDAO.findByIdJerarquiaIdJefe("jefe");
//		log.info("Lista: " + lista.size());
	}

	@Test
	public void findByIdEmpleadoJefeIdUsuario() {
//		ReJerarquiaEntity lista = jerarquiaDAO.findByIdJerarquiaIdUsuario("usuario");
//		log.info("Lista: " + lista);
	}
	
	//Perfil_Sistema
	@Test
	public void findByIdPerfilSistemaIdSistemaPerfil() {
		List<RePerfilSistemaEntity> lista = perfilDAO.findByIdPerfilSistemaIdSistema("S001");
		log.info("Lista: " + lista.size());
	}
	
	@Test
	public void findByIdEmpleadoJefeIdJefePerfil() {
		List<RePerfilSistemaEntity> lista = perfilDAO.findByPerfil("perfil");
		log.info("Lista: " + lista.size());
	}
	
	//Recertificacion
	@Test
	public void findByEstatusRecertificacion() {
		List<ReRecertificacionEntity> lista = recertificacionDAO.findByEstatus(true);
		log.info("Lista: " + lista.size());
	}
	
	@Test
	public void findByPeriodo() {
		PKRecertificacion idRecertificacion = new PKRecertificacion("jefe", "0119");
		List<ReRecertificacionEntity> lista = recertificacionDAO.findByIdRecertificacion(idRecertificacion);
		log.info("Lista: " + lista.size());
	}
	
	@Test
	public void findByIdJefeandIdPeriodo() {
		List<ReRecertificacionEntity> lista = recertificacionDAO.findByIdRecertificacionPeriodo("0219");
		log.info("Lista: " + lista);
	}
	
	//Sistema
	@Test
	public void findBySistema() {
		ReSistemaEntity lista = sistemaDAO.findBySistema("CIAT");
		log.info("Lista: " + lista);
	}
	
	//Token
	@Test
	public void findByUltimaSesion() {
		ReTokenEntity lista = tokenDAO.findByUltimaSesion(new Timestamp(2019, 11, 11, 11, 11, 11, 11));
		log.info("Lista: " + lista);
	}
	
	//Usuario
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

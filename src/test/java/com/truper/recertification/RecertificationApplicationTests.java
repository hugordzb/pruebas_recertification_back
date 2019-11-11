package com.truper.recertification;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Timestamp;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.security.authentication.BadCredentialsException;

import com.truper.recertification.dao.RE_Bitacora_CambiosDAO;
import com.truper.recertification.dao.RE_Control_CambiosDAO;
import com.truper.recertification.dao.RE_Cuentas_UsuarioDAO;
import com.truper.recertification.dao.RE_DepartamentoDAO;
import com.truper.recertification.dao.RE_Detalle_JefeDAO;
import com.truper.recertification.dao.RE_JerarquiaDAO;
import com.truper.recertification.dao.RE_Perfil_SistemaDAO;
import com.truper.recertification.dao.RE_RecertificacionDAO;
import com.truper.recertification.dao.RE_SistemaDAO;
import com.truper.recertification.dao.RE_TokenDAO;
import com.truper.recertification.dao.RE_UsuarioDAO;
import com.truper.recertification.ldap.repository.LDAPRepository;
import com.truper.recertification.model.RE_Bitacora_CambiosEntity;
import com.truper.recertification.model.RE_Control_CambiosEntity;
import com.truper.recertification.model.RE_Cuentas_UsuarioEntity;
import com.truper.recertification.model.RE_DepartamentoEntity;
import com.truper.recertification.model.RE_Detalle_JefeEntity;
import com.truper.recertification.model.RE_JerarquiaEntity;
import com.truper.recertification.model.RE_Perfil_SistemaEntity;
import com.truper.recertification.model.RE_RecertificacionEntity;
import com.truper.recertification.model.RE_SistemaEntity;
import com.truper.recertification.model.RE_TokenEntity;
import com.truper.recertification.model.RE_UsuarioEntity;
import com.truper.recertification.vo.LDAPVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
class RecertificationApplicationTests {

	@Autowired
	private  LDAPRepository ldapRepo;
	
	@Autowired
	private LdapTemplate ldapTemplate;
	
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
	
	//ldap	
	@Test
	public void ldapFindByUsername() {
		
		String strUsuario="mgmolinae";
		String strPassword="password";
		
		AndFilter filter = new AndFilter();

		if(!strUsuario.matches("[0-9]*")) {
			ldapRepo.findByUsername(strUsuario);
			filter.and(new EqualsFilter("sAMAccountName", strUsuario));
			
		}else {
			ldapRepo.findByEmploy(Integer.parseInt(strUsuario));
			filter.and(new EqualsFilter("initials", strUsuario));	
		}
		
		boolean blnOk = this.ldapTemplate.authenticate("", filter.encode(), strPassword);
		if(!blnOk) {
			log.info("No entro");
			throw new BadCredentialsException("Usuario y/o constraseña incorrectos");
			
		}
		
		log.info("auth: "+ blnOk);
	}
		
	@Test
	public void ldapFindByNoEmpleado() {
		int noEmpleado = 4011038;
		LDAPVO ldapVo = ldapRepo.findByEmploy(noEmpleado);
		
		log.info("User: " + ldapVo);
		
		assertNotNull(ldapVo.getId());
		
		AndFilter filter = new AndFilter();
		filter.and(new EqualsFilter("initials", noEmpleado));
		
		Boolean auth = ldapTemplate.authenticate("", filter.encode(), "password");
		
		log.info("auth: "+ auth);
		assertTrue(auth);
	}
	
	@Test
	public void ldapFindEmail() {
		
		String email = "mgmolinae@truper.com";
		LDAPVO ldapVO = ldapRepo.findByMail(email);
		
		assertNotNull(ldapVO.getId());
		
		AndFilter filter = new AndFilter();
		filter.and(new EqualsFilter("mail", email));
		
		Boolean auth = ldapTemplate.authenticate("", filter.encode(), "password");
		log.info("auth: "+ auth);
		assertTrue(auth);
	}
	
	//Bitacora_Cambios
	@Test
	public void findByTipoMov() {
		List<RE_Bitacora_CambiosEntity> lista = bitacoraDAO.findByTipoMov("A");
		log.info("Lista: " + lista.size());
	}
	
	@Test
	public void findBySolicitante() {
		List<RE_Bitacora_CambiosEntity> lista = bitacoraDAO.findBySolicitante("solicitante");
		log.info("Lista: " + lista.size());
	}
	
	//Control_Cambios
	@Test
	public void findByAtendio() {
		List<RE_Control_CambiosEntity> lista = controlDAO.findByAtendio("atendio");
		log.info("Lista: " + lista.size());
	}

	@Test
	public void findByEstatus() {
		List<RE_Control_CambiosEntity> lista = controlDAO.findByEstatus(false);
		log.info("Lista: " + lista.size());
	}

	//Cuentas_Usuario
	@Test
	public void findbyidCuenta_UsuarioEntitiesIdUsuario() {
		List<RE_Cuentas_UsuarioEntity> lista = cuentaDAO.findbyidCuenta_UsuarioEntitiesIdUsuario("usuario");
		log.info("Lista: " + lista.size());
	}
	
	@Test
	public void findByCuentaSistema() {
		RE_Cuentas_UsuarioEntity lista = cuentaDAO.findByCuentaSistema("mgmolinae");
		log.info("Lista: " + lista);
	}
	
	//Departamento
	@Test
	public void findByDepartamento() {
		RE_DepartamentoEntity lista = departamentoDAO.findByDepartamento("Departamento");
		log.info("Lista: " + lista);
	}
	
	//Detalle_Jefe
	@Test
	public void findByIdDepartamento() {
		RE_Detalle_JefeEntity lista = detalleDAO.findByIdDepartamento(1);
		log.info("Lista: " + lista);
	}

	@Test
	public void findByIdJefe() {
		List<RE_Detalle_JefeEntity> lista = detalleDAO.findByIdJefe("jefe");
		log.info("Lista: " + lista.size());
	}
	
	//Jerarquia
	@Test
	public void findByIdEmpleadoJefeIdJefe() {
		List<RE_JerarquiaEntity> lista = jerarquiaDAO.findByIdEmpleadoJefeIdJefe("jefe");
		log.info("Lista: " + lista.size());
	}

	@Test
	public void findByIdEmpleadoJefeIdUsuario() {
		RE_JerarquiaEntity lista = jerarquiaDAO.findByIdEmpleadoJefeIdUsuario("jefe");
		log.info("Lista: " + lista);
	}
	
	//Perfil_Sistema
	@Test
	public void findByIdPerfilSistemaIdSistemaPerfil() {
		List<RE_Perfil_SistemaEntity> lista = perfilDAO.findByIdPerfilSistemaIdSistema("S004");
		log.info("Lista: " + lista.size());
	}
	
	@Test
	public void findByIdEmpleadoJefeIdJefePerfil() {
		List<RE_Perfil_SistemaEntity> lista = perfilDAO.findByPerfil("perfil");
		log.info("Lista: " + lista.size());
	}
	
	//Recertificacion
	@Test
	public void findByEstatusRecertificacion() {
		List<RE_RecertificacionEntity> lista = recertificacionDAO.findByEstatus(true);
		log.info("Lista: " + lista.size());
	}
	
	@Test
	public void findByPeriodo() {
		List<RE_RecertificacionEntity> lista = recertificacionDAO.findByPeriodo("0219");
		log.info("Lista: " + lista.size());
	}
	
	@Test
	public void findByIdJefeandIdPeriodo() {
		RE_RecertificacionEntity lista = recertificacionDAO.findByIdJefeandIdPeriodo("jefe", "0219");
		log.info("Lista: " + lista);
	}
	
	//Sistema
	@Test
	public void findBySistema() {
		RE_SistemaEntity lista = sistemaDAO.findBySistema("CIAT");
		log.info("Lista: " + lista);
	}
	
	//Token
	@Test
	public void findByUltimaSesion() {
		RE_TokenEntity lista = tokenDAO.findByUltimaSesion(new Timestamp(2019, 11, 11, 11, 11, 11, 11));
		log.info("Lista: " + lista);
	}
	
	//Usuario
	@Test
	public void findByNombre() {
		RE_UsuarioEntity lista = usuarioDAO.findByNombre("nombre");
		log.info("Lista: " + lista);
	}
	
	@Test
	public void findByNoEmpleado() {
		RE_UsuarioEntity lista = usuarioDAO.findByNoEmpleado(1);
		log.info("Lista: " + lista);
	}
	
	@Test
	public void findByEstatusUsuario() {
		List<RE_UsuarioEntity> lista = usuarioDAO.findByEstatus(true);
		log.info("Lista: " + lista.size());
	}
}

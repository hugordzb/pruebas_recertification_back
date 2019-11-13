package com.truper.recertification;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.BadCredentialsException;

import com.truper.recertification.common.mail.service.EmailService;
import com.truper.recertification.dao.ReCuentasUsuarioDAO;
import com.truper.recertification.dao.ReDepartamentoDAO;
import com.truper.recertification.dao.ReJerarquiaDAO;
import com.truper.recertification.excel.ReadExcel;
import com.truper.recertification.ldap.repository.LDAPRepository;
import com.truper.recertification.model.PKCuentasUsuario;
import com.truper.recertification.model.PKJerarquia;
import com.truper.recertification.model.ReDepartamentoEntity;
import com.truper.recertification.model.ReJerarquiaEntity;
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
	private JavaMailSender emisorCorreo;
	
	@Autowired
	private ReDepartamentoDAO daoDepa;
	
	@Autowired 
	private ReadExcel excel;
	
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
	
	@Autowired
	ReCuentasUsuarioDAO daoCuentas;
	
	//Excel
	@Test
	public void excel() {
		log.info("-----Start-------");
//		excel.leerFicheros();
		PKCuentasUsuario pk = new PKCuentasUsuario();
		pk.setIdUsuario("jbaltazar");
		pk.setIdSistema("S001");
		pk.setCuentaSistema("1");
		pk.setIdPerfil(1);
		log.info("Cuentas: " + daoCuentas.findById(pk));
		log.info("-----Finish-------");
		
	}
	
	@Test
	public void pruebaCorreo() throws MessagingException {
		List<String> lstDestinatarios = new ArrayList<String>();
		
		lstDestinatarios.add("mgmolinae@truper.com");
			
		MimeMessage message = emisorCorreo.createMimeMessage();
	      
	    MimeMessageHelper helper = new MimeMessageHelper(message, true);
	     
	    helper.setTo((String[])lstDestinatarios.toArray(new String[0]));
	    helper.setSubject("Prueba");
	    helper.setText("Prueba!!!!");
	    
	  
	    emisorCorreo.send(message);
	    log.info("Se envio");
	}
}

package com.truper.recertification;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.security.authentication.BadCredentialsException;

import com.truper.recertification.common.email.EmailService;
import com.truper.recertification.common.template.PdfView;
import com.truper.recertification.common.template.MailContentBuilder;
import com.truper.recertification.excel.RecertificationDocs;
import com.truper.recertification.ldap.repository.LDAPRepository;
import com.truper.recertification.service.RecertificationService;
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
	private RecertificationDocs recert;
		
	@Autowired
	private MailContentBuilder mailContentBuilder;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private PdfView pdf;

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
	public void ldapName() {

		String strUsuario="ysandoval";
		Boolean s = false;
		if(ldapRepo.findByUsername(strUsuario) != null) {
			s = true;
		}
		log.info("-----" + s);
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
	
	//Excel
	@Test
	public void excel() {
		log.info("-----Start-------");
		recert.selectRecertificationDoc();
		log.info("-----Finish-------");
		
	}
	
	//Excel
	@Test
	public void excelNewFile() {
		log.info("-----Start-------");
		recert.selectNewFormatDoc();
		log.info("-----Finish-------");
		
	}
	
	//Mail
	@Test
	public void pruebaCorreo() throws MessagingException {
	    
		// Email parameter variables are created
		List<String> lstDestinatarios = new ArrayList<>();
		List<String> lstCC = new ArrayList<>();
		List<String> lstCCO = new ArrayList<>();
		List<FileSystemResource> archivos = new ArrayList<>();
		
		// Recipients list is filled in
		lstDestinatarios.add("hdrodriguezb@truper.com");
		lstDestinatarios.add("mgmolinae@truper.com");
		
		log.info("Destinatarios:" + lstDestinatarios.toString());
		
		// Email parameters are set
		emailService.setLstDestinatario(lstDestinatarios);
		emailService.setLstCC(lstCC);
		emailService.setLstCCO(lstCCO);
		emailService.setLstAdjunto(archivos);
		
		//Armar correo
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");
		mailContentBuilder.setHtmlTemplateName("RecertificationMail");
		mailContentBuilder.addParametro("fecha", format.format(new Date()));
		mailContentBuilder.addParametro("idJefe", "--idJefe");
		String sistemas[] = {"SAP, ", "CIAT y ", "TEL"};
		mailContentBuilder.addParametro("sistemas", sistemas);
		mailContentBuilder.addParametro("correo","--@correo.com");
		
		emailService.sendTemplateMail("Envio para recertificacion", mailContentBuilder.build());
	  
	    log.info("Se envio");
	}

	@Autowired
	private RecertificationService recertification;
	
	@Test
	public void PruebaCorreo() {
		log.info("---start---");
		recertification.sendMail("mgmolinae");
		log.info("---finish---");
	}
	
	@Test
	public void generatePDF() {
		
	}
}

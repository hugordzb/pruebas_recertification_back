package com.truper.recertification;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.mail.MessagingException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.security.authentication.BadCredentialsException;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.pdf.PdfWriter;
import com.truper.recertification.common.email.EmailService;
import com.truper.recertification.common.template.MailContentBuilder;
import com.truper.recertification.excel.RecertificationDocs;
import com.truper.recertification.ldap.repository.LDAPRepository;
import com.truper.recertification.reports.RecertificacionCarta;
import com.truper.recertification.service.AuditoryService;
import com.truper.recertification.service.RecertificationService;
import com.truper.recertification.vo.LDAPVO;
import com.truper.recertification.vo.answer.CountsEmployeeVO;
import com.truper.recertification.vo.answer.systems.AcountsVO;

import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

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
	private AuditoryService auditoryService;
	
	@Value("${recertification.letters.url}")
	public String urlLetters;
	
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
		lstDestinatarios.add("mgmolinae@truper.com");
		
		archivos.add(new FileSystemResource(new File("/Users/mgmolinae/workspace/workspaceGit"
				+ "/recertification/iTextHelloWorld.pdf")));
		
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
	public void generatePDF() throws DocumentException, FileNotFoundException {

		Document document = new Document();
		PdfWriter.getInstance(document, new FileOutputStream("iTextHelloWorld.pdf"));
		 
		document.open();
		Font font = FontFactory.getFont(FontFactory.COURIER, 16);
		Chunk chunk = new Chunk("Hello World", font);
		 
		document.add(chunk);
		document.close();
		log.info("terminp");
	}
	
	@Test
	public void lstEmployee() {
		String boss = "Carlos García Sánchez";
		//List<CountsEmployeeVO> lstAcounts = auditoryService.generateLetterByBoss("mgmolinae");
		List<CountsEmployeeVO> lstAcounts = new LinkedList<>();
		
		lstAcounts.add(CountsEmployeeVO.builder()
				.nombre("Un Empleado")
				.sapAccounts("CUENTA1,Cuenta2".replaceAll(",", "<br />"))
				.sapRoles("ROL1, ROL2".replaceAll(",", "<br />"))
				.telAccounts("TEL1,TEL2".replaceAll(",", "<br />"))
				.telRoles("ROL_TEL1, ROL_TEL2".replaceAll(",", "<br />"))
				.ciatAccounts("CIAT,CIAT2".replaceAll(",", "<br />"))
				.ciatProfiles("PROF1, PROF2".replaceAll(",", "<br />"))
				.build());
		
		lstAcounts.add(CountsEmployeeVO.builder()
				.nombre("Un Empleado 2")
				.sapAccounts("CUENTA1,Cuenta2".replaceAll(",", "<br />"))
				.sapRoles("ROL1, ROL2".replaceAll(",", "<br />"))
				.telAccounts("TEL1,TEL2".replaceAll(",", "<br />"))
				.telRoles("ROL_TEL1, ROL_TEL2".replaceAll(",", "<br />"))
				.ciatAccounts("CIAT,CIAT2".replaceAll(",", "<br />"))
				.ciatProfiles("PROF1, PROF2".replaceAll(",", "<br />"))
				.build());
		
		lstAcounts.add(CountsEmployeeVO.builder()
				.nombre("Un Empleado 2")
				.sapAccounts("CUENTA1,Cuenta2".replaceAll(",", "<br />"))
				.sapRoles("ROL1, ROL2".replaceAll(",", "<br />"))
				.telAccounts("TEL1,TEL2".replaceAll(",", "<br />"))
				.telRoles("ROL_TEL1, ROL_TEL2".replaceAll(",", "<br />"))
				.ciatAccounts("CIAT,CIAT2".replaceAll(",", "<br />"))
				.ciatProfiles("PROF1, PROF2".replaceAll(",", "<br />"))
				.build());
		
		RecertificacionCarta carta = new RecertificacionCarta(boss, lstAcounts);
		JasperPrint jasperPrint = carta.build();
		
		try {
			JasperExportManager.exportReportToPdfFile(jasperPrint, this.urlLetters + "/" + boss + ".pdf");
		} catch (JRException e) {
			e.printStackTrace();
		}
	}
}

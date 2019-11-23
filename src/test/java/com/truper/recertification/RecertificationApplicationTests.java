package com.truper.recertification;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.pdf.PdfWriter;
import com.truper.recertification.common.email.EmailService;
import com.truper.recertification.common.template.MailContentBuilder;
import com.truper.recertification.dao.ReDetalleJefeDAO;
import com.truper.recertification.service.RecertificationService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
class RecertificationApplicationTests {

	@Autowired
	private MailContentBuilder mailContentBuilder;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private RecertificationService recertification;

	@Autowired
	private ReDetalleJefeDAO daoJefe;
		
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
		
		archivos.add(new FileSystemResource(new File("C:\\Users\\mgmolinae\\workspace\\workspaceGit\\recertification\\iTextHelloWorld.pdf")));
		
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
	public void lstEsmployee() {
		log.info("------Start-------");
//		List<CountsEmployeeVO> lstAcounts = auditoryService.generateLetterByBoss("mgmolinae");
//		log.info("Lista: " + lstAcounts.size());
		log.info("query: " + daoJefe.query("mgmolinae"));
		log.info("------Finish-------");
	}
	
}

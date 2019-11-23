package com.truper.recertification.service.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

import com.truper.recertification.common.email.EmailService;
import com.truper.recertification.common.template.MailTemplate;
import com.truper.recertification.dao.ReDetalleJefeDAO;
import com.truper.recertification.dao.ReRecertificacionDAO;
import com.truper.recertification.model.PKRecertificacion;
import com.truper.recertification.model.ReDetalleJefeEntity;
import com.truper.recertification.model.ReRecertificacionEntity;
import com.truper.recertification.service.RecertificationService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RecertificationServiceImpl implements RecertificationService{

	@Autowired
	private ReDetalleJefeDAO daoJefe;

	@Autowired
	private EmailService emailService;

	@Autowired
	private ReRecertificacionDAO daoRecertification;
	
	@Autowired
	private MailTemplate mailTemplate;
	
	@Value("${recertification.xlsx.letters.url}")
	public String urlLetters;
	
	@Override
	public boolean sendMail(String strIdJefe){
		boolean blnAnswer = false;
		
		try {
			ReDetalleJefeEntity detailBoss = daoJefe.findById(strIdJefe).get();
			this.generateMail(detailBoss);
			log.info("termino");
			blnAnswer = true;
		
		} catch (Exception e) {
			log.error("Error al generar el email");
			log.info(e.getMessage());
		}
		return blnAnswer;
	}
	
	public void generateMail(ReDetalleJefeEntity detailBoss) {
		List<String> lstDestinatarios = new ArrayList<>();
		List<String> lstCC = new ArrayList<>();
		List<FileSystemResource> archivos = new ArrayList<>();
		
		lstDestinatarios.add(detailBoss.getCorreo());
		lstCC.add(detailBoss.getCorreoCC());
		
		log.info("Destinatarios:" + lstDestinatarios.toString());
				
		//Falta agregar los archivos adjuntos;
		archivos.add(new FileSystemResource(new File(urlLetters + detailBoss.getNombre() +".pdf")));
		
		emailService.setLstDestinatario(lstDestinatarios);
		emailService.setLstCC(lstCC);
		emailService.setLstAdjunto(archivos);
		
		try {
			emailService.sendTemplateMail("Recertificacion", mailTemplate.recertificationTemplate(detailBoss).build());
			this.updateDB(detailBoss);
			
		} catch (Exception e) {
			log.info("Error al enviar el correo de recertificacion");
			log.error(e.getMessage());
		}
	}
	
	public void updateDB(ReDetalleJefeEntity detailBoss) {
		try {
			daoRecertification.save(ReRecertificacionEntity
					.builder()
					.idRecertificacion(PKRecertificacion
							.builder()
							.idJefe(detailBoss.getIdJefe())
							.periodo(this.selectPeriod())
							.build())
					.cartaSolicitud("guardar ip")
					.estatus(false)
					.build());
		} catch (Exception e) {
			log.error("Error al actualizar BD");
			log.info(e.getMessage());
		}
	}
	
	private String selectPeriod() {
		 Date myDate = new Date();
		 if(Integer.parseInt(new SimpleDateFormat("MM").format(myDate)) <= 06) {
		  	return "01" + new SimpleDateFormat("yy").format(myDate);
		 }else {
		  	return "02" + new SimpleDateFormat("yy").format(myDate);
		 }
	}

}

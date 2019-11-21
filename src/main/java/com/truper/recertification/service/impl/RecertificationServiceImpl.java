package com.truper.recertification.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.truper.recertification.common.mail.service.EmailService;
import com.truper.recertification.common.template.MailContentBuilder;
import com.truper.recertification.dao.ReDetalleJefeDAO;
import com.truper.recertification.dao.ReRecertificacionDAO;
import com.truper.recertification.model.PKRecertificacion;
import com.truper.recertification.model.ReDetalleJefeEntity;
import com.truper.recertification.model.ReRecertificacionEntity;
import com.truper.recertification.service.RecertificationService;
import com.truper.recertification.vo.EmailVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RecertificationServiceImpl implements RecertificationService{

	@Autowired
	private ReDetalleJefeDAO daoJefe;
		
	@Autowired
	private MailContentBuilder mailContentBuilder;
	
	@Autowired
	private EmailService emailService;

	@Autowired
	private ReRecertificacionDAO daoRecertification;
	
	@Override
	public boolean sendMail(String strIdJefe){
		ReDetalleJefeEntity detailBoss = daoJefe.findByIdJefe(strIdJefe);
		
		if(this.generateMail(detailBoss)) {
			this.updateDB(detailBoss);
			return true;
		}
		return false;
	}
	
	private boolean generateMail(ReDetalleJefeEntity detailBoss) {
		EmailVO email = new EmailVO();
	    email.setDestinatario(detailBoss.getCorreo());
	    email.setCc(detailBoss.getCorreoCC());
	    
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		
		mailContentBuilder.setHtmlTemplateName("RecertificationMail");
		mailContentBuilder.addParametro("fecha", format.format(new Date()));
		mailContentBuilder.addParametro("idJefe", detailBoss.getIdJefe());
		mailContentBuilder.addParametro("sistemas", "SAP, CIAT, TEL");
		mailContentBuilder.addParametro("correo","oacarmonac@truper.com");
		emailService.sendTemplateMail("Recertificacion", mailContentBuilder.build(), new EmailVO());
	    
		return true;
	}
	
	private void updateDB(ReDetalleJefeEntity detailBoss) {
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

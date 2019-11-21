package com.truper.recertification.common.mail.service.impl;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.truper.recertification.common.mail.service.EmailService;
import com.truper.recertification.vo.EmailVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EmailServiceImpl implements EmailService{

	@Value("${app.mail.emisor}")
	private String strEmisor;

	@Autowired
	private JavaMailSender javaMailSender;
	
	public void sendSimpleMail(String strAsunto, String strContenido, EmailVO emailVO) {
		this.sendMail(strAsunto, strContenido, false, false, emailVO);
	}
	
	public void sendMultipartMail(String strAsunto, String strContenido, EmailVO emailVO) {
		this.sendMail(strAsunto, strContenido, true, false, emailVO);
	}
	
	public void sendTemplateMail(String strAsunto, String strContenido, EmailVO emailVO) {
		this.sendMail(strAsunto, strContenido, true, true, emailVO);
	}
	
	/**
	 * This method sends a mail message specifying subject and message content 
	 * through the passed parameters and isHTML and isMultipart flags 
	 * indicating if the content is a HTML template and if it is a multipart message.
	 * Receivers, copied receivers and blind copied receivers need to be provided through
	 * setter methods before using this method.
	 * @param strAsunto
	 * @param strContenido
	 * @param isMultipart
	 * @param isHTML
	 * @throws MailException
	 */
	@Async
	private void sendMail(String strAsunto, String strContenido, boolean isMultipart, boolean isHTML, EmailVO emailVO){
		
		MimeMessagePreparator mimeMessagePreparator = mimeMessage -> {

			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, isMultipart, "UTF-8");
			mimeMessageHelper.setFrom(this.strEmisor);
			log.info("data: " + emailVO.getDestinatario());
			mimeMessageHelper.setTo(emailVO.getDestinatario());
			if (!emailVO.getCc().isEmpty())
				mimeMessageHelper.setCc(emailVO.getCc());
			
			if (!emailVO.getCco().isEmpty())
				mimeMessageHelper.setBcc(emailVO.getCco());
			
			// Subject is added
			mimeMessageHelper.setSubject( (strAsunto != null) ? strAsunto : "Sin asunto" );
			
			// Content is added and is specified if it is HTML content
			mimeMessageHelper.setText(strContenido, isHTML);
			
			if(isMultipart) {
				// Attachments are added
				emailVO.getLstAdjunto().forEach( v -> {		    
					 try {
						 mimeMessageHelper.addAttachment(v.getFilename(), v);
					 } catch (MessagingException e) {
						 log.error("Error al adjuntar archivo: " + v.getFilename() );
						 log.info(e.getMessage());
					}
			    });
			}
		};

		try {
			javaMailSender.send(mimeMessagePreparator);
		} catch (MailException e) {
			log.warn("Error al enviar el email. {}", e);
		}
	}
}

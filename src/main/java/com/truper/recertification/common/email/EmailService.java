package com.truper.recertification.common.email;

import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * This class provides the service to send e-mails through the configured SMTP server.
 * @author hdrodriguezb
 *
 */
@Service
@Slf4j
public class EmailService {
	/**
	 * This variable needs to be configured through the properties file.
	 */
	@Value("${app.mail.emisor}")
	private String strEmisor;

	@Autowired
	private JavaMailSender javaMailSender;
	
	@Getter
	@Setter
	private List<String> lstDestinatario;
	
	@Getter
	@Setter
	private List<String> lstCC;
	
	@Getter
	@Setter
	private List<String> lstCCO;
	
	@Getter
	@Setter
	private List<FileSystemResource> lstAdjunto;
	
	/**
	 * This method sends a simple email
	 * @param strAsunto
	 * @param strContenido
	 */
	public void sendSimpleMail(String strAsunto, String strContenido) {
		this.sendMail(strAsunto, strContenido, false, false);
	}
	
	/**
	 * THis method sends a multipart email message with attachments if they were provided before 
	 * calling it.
	 * @param strAsunto
	 * @param strContenido
	 */
	public void sendMultipartMail(String strAsunto, String strContenido) {
		this.sendMail(strAsunto, strContenido, true, false);
	}
	
	/**
	 * This method sends a multipart emailmessage with attachments and a specified HTML template
	 * @param strAsunto
	 * @param strContenido
	 */
	public void sendTemplateMail(String strAsunto, String strContenido) {
		this.sendMail(strAsunto, strContenido, true, true);
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
	private void sendMail(String strAsunto, String strContenido, boolean isMultipart, 
			boolean isHTML) throws MailException {
		
		MimeMessagePreparator mimeMessagePreparator = mimeMessage -> {
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, isMultipart, "UTF-8");
			mimeMessageHelper.setFrom(this.strEmisor);
			mimeMessageHelper.setTo((String[])lstDestinatario.toArray(new String[0]));
			
			// If copy receivers are provided, they are added
//			if (lstCC != null && !lstCC.isEmpty())
//				mimeMessageHelper.setCc((String[])lstCC.toArray(new String[0]));
			
			// If blind copy receivers are provided, they are added
			if (lstCCO != null && !lstCCO.isEmpty())
				mimeMessageHelper.setBcc((String[])lstCCO.toArray(new String[0]));
			
			// Subject is added
			mimeMessageHelper.setSubject( (strAsunto != null) ? strAsunto : "Sin asunto" );
			
			// Content is added and is specified if it is HTML content
			mimeMessageHelper.setText(strContenido, isHTML);
			
			if(isMultipart) {
				// Attachments are added
			    lstAdjunto.forEach( v -> {
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

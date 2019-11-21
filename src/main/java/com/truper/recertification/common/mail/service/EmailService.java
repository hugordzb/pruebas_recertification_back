package com.truper.recertification.common.mail.service;

import com.truper.recertification.vo.EmailVO;

public interface EmailService {

	/**
	 * This method sends a simple email
	 * @param strAsunto
	 * @param strContenido
	 */
	public void sendSimpleMail(String strAsunto, String strContenido, EmailVO email);
	
	/**
	 * THis method sends a multipart email message with attachments if they were provided before 
	 * calling it.
	 * @param strAsunto
	 * @param strContenido
	 */
	public void sendMultipartMail(String strAsunto, String strContenido, EmailVO email);
	
	/**
	 * This method sends a multipart emailmessage with attachments and a specified HTML template
	 * @param strAsunto
	 * @param strContenido
	 */
	public void sendTemplateMail(String strAsunto, String strContenido, EmailVO email);
	
}

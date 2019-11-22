package com.truper.recertification.common.template.mapper;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.truper.recertification.common.template.MailContentBuilder;
import com.truper.recertification.vo.EmailTemplateVO;

/**
 * This class insert data by templates
 * @author mgmolinae
 */
public class TemplateMapper {

	@Autowired
	private MailContentBuilder mailContentBuilder;
	
	/**
	 * This method insert data on Email Template
	 * @param emailTemplate
	 */
	public void templateMapper(EmailTemplateVO emailTemplate) {

//		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
//		
//		mailContentBuilder.setHtmlTemplateName("RecertificationMail");
//		mailContentBuilder.addParametro("fecha", format.format(new Date()));
//		mailContentBuilder.addParametro("idJefe", emailTemplate.getStrIdJefe());
//		mailContentBuilder.addParametro("sistemas", emailTemplate.getLstIdSistemas().toString());
//		mailContentBuilder.addParametro("correo", emailTemplate.getStrCorreo());

	}
}

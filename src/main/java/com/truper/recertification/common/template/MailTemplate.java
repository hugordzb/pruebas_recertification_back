package com.truper.recertification.common.template;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.truper.recertification.model.ReDetalleJefeEntity;
import com.truper.recertification.service.AuditoryService;
import com.truper.recertification.vo.answer.AcountsBossVO;

@Component
public class MailTemplate {
	
	@Autowired
	private MailContentBuilder mailContentBuilder;
	
	@Autowired
	private AuditoryService auditoryService;
	
	public MailContentBuilder recertificationTemplate(ReDetalleJefeEntity detailBoss) {
		AcountsBossVO countsBossVO = auditoryService.findByBoss(detailBoss.getId());
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		
		mailContentBuilder.setHtmlTemplateName("RecertificationMail");
		mailContentBuilder.addParametro("fecha", format.format(new Date()));
		mailContentBuilder.addParametro("idJefe", detailBoss.getIdJefe());
		mailContentBuilder.addParametro("sistemas", countsBossVO.getSistemas());
		mailContentBuilder.addParametro("correo", "oacarmonac@truper.com");
		
		return mailContentBuilder;
	}

}

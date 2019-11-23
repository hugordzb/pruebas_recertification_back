package com.truper.recertification.common.template;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.truper.recertification.model.ReDetalleJefeEntity;
import com.truper.recertification.service.AuditoryService;
import com.truper.recertification.vo.answer.CountsBossVO;
import com.truper.recertification.vo.answer.CountsEmployeeVO;
import com.truper.recertification.vo.answer.systems.AcountsVO;

@Component
public class MailTemplate {
	
	@Autowired
	private MailContentBuilder mailContentBuilder;
	
	@Autowired
	private AuditoryService auditoryService;
	
	public MailContentBuilder recertificationTemplate(ReDetalleJefeEntity detailBoss) {
		CountsBossVO countsBossVO = auditoryService.findByBoss(detailBoss.getId());
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		
		mailContentBuilder.setHtmlTemplateName("RecertificationMail");
		mailContentBuilder.addParametro("fecha", format.format(new Date()));
		mailContentBuilder.addParametro("idJefe", detailBoss.getIdJefe());
		mailContentBuilder.addParametro("sistemas", countsBossVO.getSistemas());
		mailContentBuilder.addParametro("correo", "oacarmonac@truper.com");
//		this.acountsDataTable(mailContentBuilder, countsBossVO.getEmpleados());
		
		return mailContentBuilder;
	}
	
//	private void acountsDataTable(MailContentBuilder mailContentBuilder, List<CountsEmployeeVO> lstEmployee) {
//		
//		for(int i = 0; i < lstEmployee.size(); i++) {
//			CountsEmployeeVO employeeAcounts = lstEmployee.get(i);
//			mailContentBuilder.addParametro("nombre", employeeAcounts.getEmpleado());
//			
//			for(int j = 0; j < employeeAcounts.getCuentas().size(); j++) {
//				AcountsVO acounts = employeeAcounts.getCuentas().get(j);
//				
//				mailContentBuilder.addParametro("sap", acounts.getCSap());
//				mailContentBuilder.addParametro("pSap", acounts.getPSap());
//				mailContentBuilder.addParametro("tel", acounts.getCTel());
//				mailContentBuilder.addParametro("pTel", acounts.getPTel());
//				mailContentBuilder.addParametro("ciat", acounts.getCCiat());
//				mailContentBuilder.addParametro("pCiat", acounts.getPCiat());
//			}
//		}
//	}
}

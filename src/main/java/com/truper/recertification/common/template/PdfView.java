package com.truper.recertification.common.template;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.truper.recertification.dao.ReDetalleJefeDAO;
import com.truper.recertification.model.ReDetalleJefeEntity;
import com.truper.recertification.service.AuditoryService;
import com.truper.recertification.vo.answer.CountsBossVO;
import com.truper.recertification.vo.answer.CountsEmployeeVO;
import com.truper.recertification.vo.answer.systems.AcountsVO;

@Component
public class PdfView extends AbstractPdfView{

	@Autowired
	private AuditoryService auditoryService;
		
	@Autowired
	private ReDetalleJefeDAO daoJefe;
	
	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		 PdfPTable table = new PdfPTable(5);

		 ReDetalleJefeEntity detailBoss = daoJefe.findById("mgmolinae").get();

		 CountsBossVO countsBossVO = auditoryService.findByBoss(detailBoss.getId());
		 
		 	table.addCell("Nombre");
	        table.addCell("SAP");
	        table.addCell("Rol SAP");
	        table.addCell("TEL");
	        table.addCell("ROL TEL");
	        table.addCell("CIAT");
	        table.addCell("PERFIL CIAT");

	        for(int i = 0; i < countsBossVO.getEmpleados().size(); i++) {
				CountsEmployeeVO employeeAcounts = countsBossVO.getEmpleados().get(i);
				
				for(int j = 0; j < countsBossVO.getEmpleados().get(i).getCuentas().size(); j++) {
					AcountsVO acounts = employeeAcounts.getCuentas().get(j);
				
					table.addCell(employeeAcounts.getEmpleado());
					table.addCell(acounts.getCSap());
		            table.addCell(acounts.getPSap());
		            table.addCell(acounts.getCTel());
		            table.addCell(acounts.getPTel());
		            table.addCell(acounts.getCCiat());
		            table.addCell(acounts.getPCiat());
		            
				}
			}

	        document.add(table);
	}
}

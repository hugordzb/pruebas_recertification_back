package com.truper.recertification.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
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
import com.truper.recertification.reports.RecertificationLetter;
import com.truper.recertification.service.DetailLetterService;
import com.truper.recertification.service.RecertificationService;
import com.truper.recertification.vo.answer.AcountEmployeeVO;
import com.truper.recertification.vo.answer.DetailCountsEmployeeVO;

import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;

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
	
	@Autowired
	private DetailLetterService detail;
	
	@Value("${recertification.letters.url}")
	public String urlLetters;
	
	@Override
	public boolean sendMail(String strIdJefe){
		boolean blnAnswer = false;
		
		try {
			ReDetalleJefeEntity detailBoss = daoJefe.findById(strIdJefe).get();
			this.generateMail(detailBoss);
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
		
		log.info("Destinatarios:" + lstDestinatarios.toString());
		
		if(!detailBoss.getCorreoCC().isEmpty() || detailBoss.getCorreoCC() != null) {
			lstCC.add(detailBoss.getCorreoCC());
		}
		
		archivos.add(new FileSystemResource(this.generatorPDF(detailBoss.getIdJefe())));
		
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
					.cartaSolicitud(this.urlLetters + detailBoss.getNombre() + ".pdf")
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
	
	private String generatorPDF(String boss) {
		List<AcountEmployeeVO> lstAcounts = new LinkedList<>();
		DetailCountsEmployeeVO counts = detail.findEmployDetail(boss);
		
		for(int i = 0; i < counts.getCuentas().size(); i++) {
			/*
			AcountsVO acountsVO = counts.getCuentas().get(i);
			lstAcounts.add( CountEmployeeVO.builder()
					.nombre(counts.getEmpleado())
					.ciatAccounts(acountsVO.getCCiat())
					.ciatProfiles(acountsVO.getPCiat())
					.sapAccounts(acountsVO.getCSap())
					.sapRoles(acountsVO.getPSap())
					.telAccounts(acountsVO.getCTel())
					.telRoles(acountsVO.getPTel())
					.build());*/
		}
		
		RecertificationLetter carta = new RecertificationLetter(boss, lstAcounts);
		JasperPrint jasperPrint = carta.build();
		boss = counts.getEmpleado() + ".pdf";
		try {
			JasperExportManager.exportReportToPdfFile(jasperPrint, this.urlLetters + boss);
		} catch (JRException e) {
			e.printStackTrace();
		}
		return this.urlLetters +boss;
	}
}

package com.truper.recertification.excel.service.impl;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.truper.recertification.excel.ReadExcel;
import com.truper.recertification.excel.component.InsertNewExcelData;
import com.truper.recertification.excel.recertification.vo.DataDocsVO;
import com.truper.recertification.excel.recertification.vo.RecertificationDocsVO;
import com.truper.recertification.excel.service.RecertificationDocsService;
import com.truper.recertification.exception.RecertificationException;

@Service
public class RecertificationDocsServiceImpl implements RecertificationDocsService{
	
	@Value("${recertification.xlsx.archivo.recertificacion}")
	private String strRecertificacion;
	
	@Value("${recertification.xlsx.archivo.recertificacion.activos}")
	private String strHojaRecert;
	
	@Value("${recertification.xlsx.archivo.sap.perfil}")
	private String strPerfilSAP;
	
	@Value("${recertification.xlsx.archivo.sap.perfil.perfiles}")
	private String strHojaPerfiles;
	
	@Value("${recertification.xlsx.archivo.sap.perfil.apo}")
	private String strHojaAPO;
	
	@Value("${recertification.xlsx.archivo.sap}")
	private String strSAP;
	
	@Value("${recertification.xlsx.archivo.ciat}")
	private String strCIAT;
	
	@Value("${recertification.xlsx.archivo.ciat.ciat}")
	private String strHojaCiat;
	
	@Value("${recertification.xlsx.archivo.ciat.ciatLinea}")
	private String strHojaCiatLinea;
	
	@Value("${recertification.xlsx.archivo.correo}")
	private String strCorreoJefe;
	
	@Value("${recertification.xlsx.archivo.newFile}")
	private String strNewFormat;
	
	@Autowired
	private ReadExcel readExcel;
	
	@Autowired
	private InsertNewExcelData insertNewData;
	
	@Override
	public void loadRecert(String strFileName, InputStream inputStream) throws RecertificationException {
		RecertificationDocsVO recertDocs = new RecertificationDocsVO();
		
		DataDocsVO dataObjetct = DataDocsVO.builder()
				.strNameFile(strFileName)
				.inputStream(inputStream)
				.build();
		
		//this.readExcel.readExcelData(dataObjetct, recertDocs);
		this.insertNewData.insertDataLastRecertification(recertDocs);
	}
}

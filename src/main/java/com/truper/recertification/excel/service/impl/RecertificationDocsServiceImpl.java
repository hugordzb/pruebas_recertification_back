package com.truper.recertification.excel.service.impl;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.truper.recertification.excel.ReadExcel;
import com.truper.recertification.excel.component.InsertExcelData;
import com.truper.recertification.excel.component.InsertNewExcelData;
import com.truper.recertification.excel.recertification.vo.DataDocsVO;
import com.truper.recertification.excel.recertification.vo.RecertificationDocsVO;
import com.truper.recertification.excel.service.RecertificationDocsService;

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
	private InsertExcelData insertData;
	
	@Autowired
	private InsertNewExcelData insertNewData;
	
	@Override
	public void selectRecertificationDoc(String strFileName, InputStream inputStream) {
		
		RecertificationDocsVO recertDocs = new RecertificationDocsVO();
		
		if(true) {
			readExcel.readExcelSheet(DataDocsVO.builder()
					.strNameFile(strFileName)
					.inputStream(inputStream)
					.build(), recertDocs);	
			insertNewData.insertDataLastRecertification(recertDocs);
		}else {
			
			if(strFileName == "Recertificacion.xlsx")
			readExcel.readExcelSheet(new DataDocsVO(strFileName, strHojaRecert), recertDocs);
			
			if(strFileName == "Usuarios SAP.xlsx") {
				readExcel.readExcelSheet(new DataDocsVO(strFileName, strHojaPerfiles), recertDocs);
				readExcel.readExcelSheet(new DataDocsVO(strFileName, strHojaAPO), recertDocs);
			}
			
			if(strFileName == "Usuarios CIAT.xlsx") {
				readExcel.readExcelSheet(new DataDocsVO(strFileName, strHojaCiat), recertDocs);
				readExcel.readExcelSheet(new DataDocsVO(strFileName, strHojaCiatLinea), recertDocs);
			}
			
				
//			insertData.insertDataLastRecertification(recertDocs);
		}
			
	}
}

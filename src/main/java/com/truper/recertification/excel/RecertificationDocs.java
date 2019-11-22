package com.truper.recertification.excel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.truper.recertification.excel.component.InsertExcelData;
import com.truper.recertification.excel.component.InsertNewExcelData;
import com.truper.recertification.excel.recertification.vo.DocsDataVO;
import com.truper.recertification.excel.recertification.vo.RecertificationDocsVO;

/**
 * Identify the documents that need to process to insert on DB
 * @author mgmolinae
 */
@Service
public class RecertificationDocs {
	
	@Value("${recertification.xlsx.url}")
	private String strRutaArchivo;

	@Value("${recertification.xlsx.url.newUrl}")
	private String strNewUrl;
	
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
	
	@Value("${recertification.xlsx.archivo.tel}")
	private String strTel;
	
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
	
	/**
	 * Read the first Charge and insert data on tables
	 */
	public void selectRecertificationDoc() {
		
		RecertificationDocsVO recertDocs = new RecertificationDocsVO();
		readExcel.readExcelSheet(new DocsDataVO(strRutaArchivo, strTel, null), recertDocs);
		readExcel.readExcelSheet(new DocsDataVO(strRutaArchivo, strPerfilSAP, strHojaPerfiles), recertDocs);
		readExcel.readExcelSheet(new DocsDataVO(strRutaArchivo, strPerfilSAP, strHojaAPO), recertDocs);
		readExcel.readExcelSheet(new DocsDataVO(strRutaArchivo, strSAP, null), recertDocs);
		readExcel.readExcelSheet(new DocsDataVO(strRutaArchivo, strCIAT, strHojaCiat), recertDocs);
		readExcel.readExcelSheet(new DocsDataVO(strRutaArchivo, strCIAT, strHojaCiatLinea), recertDocs);
		readExcel.readExcelSheet(new DocsDataVO(strRutaArchivo, strCorreoJefe, null), recertDocs);
		readExcel.readExcelSheet(new DocsDataVO(strRutaArchivo, strRecertificacion, strHojaRecert), recertDocs);
		
		insertData.insertDataLastRecertification(recertDocs);
	}
	
	public void selectNewFormatDoc() {
		RecertificationDocsVO recertDocs = new RecertificationDocsVO();
		readExcel.readExcelSheet(new DocsDataVO(strNewUrl, strNewFormat, null), recertDocs);
		
		insertNewData.insertDataLastRecertification(recertDocs);
	}
}

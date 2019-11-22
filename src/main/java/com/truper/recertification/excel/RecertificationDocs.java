package com.truper.recertification.excel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.truper.recertification.component.InsertExcelData;
import com.truper.recertification.vo.excel.DocsDataVO;
import com.truper.recertification.vo.excel.RecertificationDocsVO;

/**
 * Identify the documents that need to process to insert on DB
 * @author mgmolinae
 */
@Service
public class RecertificationDocs {
	
	@Value("${recertification.xlsx.url}")
	private String strRutaArchivo;
	
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
	
	@Autowired
	private ReadExcel readExcel;
	
	@Autowired
	private InsertExcelData insertData;
	
	/**
	 * Read the first Charge and insert data on tables
	 */
	public void selectRecertificationDoc() {
		
		RecertificationDocsVO recertDocs = new RecertificationDocsVO();
		readExcel.leerFicheros(new DocsDataVO(strRutaArchivo, strTel, null), recertDocs);
		readExcel.leerFicheros(new DocsDataVO(strRutaArchivo, strPerfilSAP, strHojaPerfiles), recertDocs);
		readExcel.leerFicheros(new DocsDataVO(strRutaArchivo, strPerfilSAP, strHojaAPO), recertDocs);
		readExcel.leerFicheros(new DocsDataVO(strRutaArchivo, strSAP, null), recertDocs);
		readExcel.leerFicheros(new DocsDataVO(strRutaArchivo, strCIAT, strHojaCiat), recertDocs);
		readExcel.leerFicheros(new DocsDataVO(strRutaArchivo, strCIAT, strHojaCiatLinea), recertDocs);
		readExcel.leerFicheros(new DocsDataVO(strRutaArchivo, strCorreoJefe, null), recertDocs);
		readExcel.leerFicheros(new DocsDataVO(strRutaArchivo, strRecertificacion, strHojaRecert), recertDocs);
		
		insertData.insertDataLastRecertification(recertDocs);
	}
}

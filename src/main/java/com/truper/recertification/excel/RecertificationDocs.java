package com.truper.recertification.excel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RecertificationDocs {
	
	@Value("${recertification.xlsx.url}")
	private String strRutaArchivo;
	
	@Value("${recertification.xlsx.archivo.recertificacion}")
	private String strRecertificacion;
	
	@Value("${recertification.xlsx.archivo.tel}")
	private String strTel;
	
	@Value("${recertification.xlsx.archivo.sap}")
	private String strSAP;
	
	@Value("${recertification.xlsx.archivo.ciat}")
	private String strCIAT;
	
	@Autowired
	private ReadExcel readExcel;
	
	public void selectRecertificationDoc() {
		
		//read docs para el perfil
		readExcel.leerFicheros(strRutaArchivo, strTel);
		readExcel.leerFicheros(strRutaArchivo, strSAP);
		readExcel.leerFicheros(strRutaArchivo, strCIAT);
		readExcel.leerFicheros(strRutaArchivo, strRecertificacion);
		
		//insert
		
	}
}

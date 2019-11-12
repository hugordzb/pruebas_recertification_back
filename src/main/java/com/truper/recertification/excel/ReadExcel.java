package com.truper.recertification.excel;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.truper.recertification.vo.ExcelVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ReadExcel {
	
	@Value("${recertification.xlsx.archivo}")
	private String strArchivo;
	
	@Value("${recertification.xlsx.url}")
	private String strRutaArchivo;
	
	@Value("${recertification.xlsx.sheet}")
	private String strHoja;
	
	@Autowired
	private RecertificacionExcelMapper excelMapper;
	
	public void leerFicheros() {
		
		try (FileInputStream file = new FileInputStream(new File(strRutaArchivo+strArchivo))) {
			
			XSSFWorkbook worbook = new XSSFWorkbook();
			XSSFSheet sheet = worbook.getSheet(strHoja);

			List<ExcelVO> list = new ArrayList<ExcelVO>();
			
			sheet.forEach( v -> list.add(excelMapper.excelMapper((XSSFRow)v)));
			
			log.info("1: " + list.get(0).getNombre());
			log.info("2: " + list.get(1).getNombre());

		} catch (Exception e) {
			e.getMessage();
			log.info("error: " + e);
		}
	}
}
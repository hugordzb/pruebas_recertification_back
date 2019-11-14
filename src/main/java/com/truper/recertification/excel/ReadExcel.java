package com.truper.recertification.excel;

import java.io.File;
import java.io.FileInputStream;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.truper.recertification.component.InsertExcelData;
import com.truper.recertification.excel.mapper.RecertificacionExcelMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ReadExcel {
	
	@Autowired
	private RecertificacionExcelMapper excelMapper;
	
	@Autowired
	private InsertExcelData insertData;
		
	public RecertificacionExcelMapper leerFicheros(String strRuta, String strArchivo) {
		
		List<List<String>> rowData = new LinkedList<>();
		DataFormatter formatter = new DataFormatter();

		try (FileInputStream file = new FileInputStream(new File(strRuta+strArchivo))) {
			
			XSSFWorkbook worbook = new XSSFWorkbook(file);
			Sheet sheet = worbook.getSheetAt(0);
			
			Row hssfRow;
			int rowInit = 2;
			int rows = sheet.getLastRowNum();
			
			
			if(rows >0) {
				List<String> cells = null;
				for(int r = rowInit; r<= rows; r++) {
					cells = new LinkedList<>();
					if(r > 0) {
						hssfRow = sheet.getRow(r);
						
						if(hssfRow == null) {
							break;
						}else {
							int i = 0;
							
							while(i < this.getMaxColumns()) {
								if(hssfRow.getCell(i) != null && hssfRow.getCell(i).getCellType() == Cell.CELL_TYPE_FORMULA) {
									switch(hssfRow.getCell(i).getCachedFormulaResultType()) {
										case Cell.CELL_TYPE_NUMERIC:
											cells.add(hssfRow.getCell(i).getNumericCellValue()+"");
											break;
										case Cell.CELL_TYPE_STRING:
											cells.add(hssfRow.getCell(i).getStringCellValue()+"");
											break;
										case Cell.CELL_TYPE_BOOLEAN:
											cells.add(hssfRow.getCell(i).getBooleanCellValue()+"");
											break;
										default:
											break;
									}
								}else {
									cells.add(formatter.formatCellValue(hssfRow.getCell(i)).trim());									
								}
								i++;
							}
							rowData.add(cells);
						}
					}
				}
			}
			this.mapData(rowData, strArchivo);
			log.info("archivo: " +strArchivo);
			
		} catch (Exception e) {
			e.printStackTrace();
			log.info("error: " + e);
		}		
		return excelMapper;
	}
	
	private int getMaxColumns() {
		return 25;
	}
	
	private void mapData(List<List<String>> list, String strArchivo) throws ParseException {
		
		switch (strArchivo) {
			case "Usuario TEL_NUEVO.xlsx":
				excelMapper.excelMapperTel(list);
			break;
			case "Usuarios SAP.xlsx":
				excelMapper.excelMapperSap(list);
			break;
			case "Usuarios (ciat).xlsx":
				excelMapper.excelMapperCiat(list);
			break;
			case "Replica.xlsx":
				insertData.insertDataLastRecertification(excelMapper.excelMapperRecert(list));	
			break;
			default:
				log.info("El nombre del documento no coincide");
			break;
		}
	}
}
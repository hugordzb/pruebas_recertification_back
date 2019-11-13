package com.truper.recertification.excel;

import java.io.File;
import java.io.FileInputStream;
import java.util.LinkedList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.truper.recertification.component.InsertExcelData;
import com.truper.recertification.excel.mapper.RecertificacionExcelMapper;

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
	
	@Autowired
	private InsertExcelData insertData;
	
	public RecertificacionExcelMapper leerFicheros() {
		
		List<List<String>> rowData = new LinkedList<>();
		DataFormatter formatter = new DataFormatter();

		try (FileInputStream file = new FileInputStream(new File(strRutaArchivo+strArchivo))) {
			
			XSSFWorkbook worbook = new XSSFWorkbook(file);
			Sheet sheet = worbook.getSheet(strHoja);
			
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
			insertData.insertDataLastRecertification(excelMapper.excelMapper(rowData));
			
		} catch (Exception e) {
			e.printStackTrace();
			log.info("error: " + e);
		}
		
		return excelMapper;
	}
	
	private Integer getMaxColumns() {
		return 24;
	}
}
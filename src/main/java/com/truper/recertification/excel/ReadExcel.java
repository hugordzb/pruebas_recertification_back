package com.truper.recertification.excel;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Component;

import com.truper.recertification.exception.RecertificationException;

import lombok.extern.slf4j.Slf4j;

/**
 * This class read excel files
 * @author mgmolinae
 */
@Slf4j
@Component
public class ReadExcel {
	
	/**
	 * This method review data on excel columns, and identify by sheets (if sheet's not defined read the first sheet) 
	 * @param docsData
	 * @param recertDocs
	 */
	public List<List<String>> readExcelData(InputStream streamFile, Integer sheetLocation, Integer maxColumns) throws RecertificationException {
		if(streamFile == null)
			throw new RecertificationException("No hay ningún archivo para leer");
		
		if(sheetLocation == null)
			throw new RecertificationException("Debes especificar una hoja a leer");
		
		List<List<String>> rowData = new LinkedList<>();
		DataFormatter formatter = new DataFormatter();

		try (Workbook workbook = WorkbookFactory.create(streamFile)) {
			Sheet xssfSheet = workbook.getSheetAt(sheetLocation);
			
			Row hssfRow;
			int rowInit = 2;
			int rows = xssfSheet.getLastRowNum();
			
			if(rows >0) {
				List<String> cells = null;
				for(int r = rowInit; r<= rows; r++) {
					cells = new LinkedList<>();
					if(r > 0) {
						hssfRow = xssfSheet.getRow(r);
						
						if(hssfRow == null) {
							break;
						}else {
							int i = 0;
							
							while(i < maxColumns) {
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
		} catch (Exception e) {
			log.info("error: " + e);
		}finally {
			if(streamFile != null) {
				try {
					streamFile.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}	
		return rowData;
	}

}
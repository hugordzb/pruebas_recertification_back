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

import com.lowagie.text.pdf.codec.Base64.InputStream;
import com.truper.recertification.excel.mapper.RecertificacionExcelMapper;
import com.truper.recertification.excel.recertification.vo.DataDocsVO;
import com.truper.recertification.excel.recertification.vo.RecertificationDocsVO;

import lombok.extern.slf4j.Slf4j;

/**
 * This class read excel files
 * @author mgmolinae
 */
@Slf4j
@Service
public class ReadExcel {
	
	@Autowired
	private RecertificacionExcelMapper excelMapper;
	
	/**
	 * This method review data on excel columns, and identify by sheets (if sheet's not defined read the first sheet) 
	 * @param docsData
	 * @param recertDocs
	 */
	public void readExcelSheet(DataDocsVO dataDocsVO, RecertificationDocsVO recertDocs) {
		
		List<List<String>> rowData = new LinkedList<>();
		DataFormatter formatter = new DataFormatter();

		try (FileInputStream file = new FileInputStream(new File(dataDocsVO.getStrNameFile()))) {
			
			XSSFWorkbook worbook = new XSSFWorkbook(file);
			Sheet sheet = worbook.getSheetAt(0);
			if(dataDocsVO.getStrHoja() != null) {
				sheet = worbook.getSheet(dataDocsVO.getStrHoja());
			}
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
			this.mapData(rowData, dataDocsVO, recertDocs);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("error: " + e);
		}
	}
	
	/**
	 * This method content the maximun number of columns in the docs
	 * @return integer
	 */
	private int getMaxColumns() {
		return 25;
	}
	
	/**
	 * This method identify docs and invoke maps
	 * @param list
	 * @param dataDocsVO
	 * @param recertDocs
	 * @return RecertificationDocsVO
	 * @throws ParseException
	 */
	private RecertificationDocsVO mapData(List<List<String>> list, DataDocsVO dataDocsVO, RecertificationDocsVO recertDocs) throws ParseException {		

		switch (dataDocsVO.getStrNameFile()) {
			case "Usuarios TEL.xlsx":
				recertDocs.setLstTel(excelMapper.excelMapperTel(list));
			break;
			case "Usuarios SAP.xlsx":
				recertDocs.setLstSap(excelMapper.excelMapperSap(list));
			break;
			case "Usuarios CIAT.xlsx":
				if(dataDocsVO.getStrHoja().equals("Ciat")) {
					recertDocs.setLstCiat(excelMapper.excelMapperCiat(list));
				}else if(dataDocsVO.getStrHoja().equals("Ciat en Linea")) {
					recertDocs.setLstCiatLinea(excelMapper.excelMapperCiatLinea(list));
				}
			break;
			case "Recertificacion.xlsx":
				if(dataDocsVO.getStrHoja().equals("Activos")) {
					recertDocs.setLstRecert(excelMapper.excelMapperRecert(list));
				}
			break;
			case "Perfiles SAP.xlsx":
				if(dataDocsVO.getStrHoja().equals("PERFILES")) {
					recertDocs.setLstSapProfiles(excelMapper.excelMapperSapProfiles(list));
				}else if(dataDocsVO.getStrHoja().equals("APO")) {
					recertDocs.setLstSapAPO(excelMapper.excelMapperSapAPO(list));
				}
			break;
			case "Correo Jefe.xlsx":
				recertDocs.setLstCorreoJefe(excelMapper.excelMapperCorreo(list));
				break;
			case "Archivo prueba Recertificacion.xlsx":
				recertDocs.setLstNewFile(excelMapper.excelMapperNewFormat(list));
				break;			
			default:
				log.info("El nombre del documento no coincide");
			break;
		}
		return recertDocs;
	}
}
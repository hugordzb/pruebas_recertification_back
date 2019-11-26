package com.truper.recertification.excel.mapper;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.truper.recertification.excel.ReadExcel;
import com.truper.recertification.excel.utils.constants.ExcelCiatSheet;
import com.truper.recertification.excel.utils.constants.ExcelRecertificationSheet;
import com.truper.recertification.excel.utils.constants.ExcelTelSheet;
import com.truper.recertification.exception.RecertificationException;

@Component
public class ExcelRowToVOService<T> {
	
	@Autowired
	private ReadExcel read;
	
	@Autowired
	private RecertificacionExcelMapper mapperRecert;
	
	@SuppressWarnings("unchecked")
	public List<T> readExcelRercertificationToVo(InputStream ios, ExcelRecertificationSheet sheet){
		List<Object> list = new LinkedList<>();
		
		try {
			List<List<String>> rowData = this.read.readExcelData(ios, sheet.getSheetLocation(), sheet.getMaxColumns());
			
			rowData.forEach(row -> {
				Object tmp = null;
				switch (sheet) {
					case ACTIVOS:
						tmp = this.mapperRecert.excelMapperRecert(row);
						break;
					case BAJAS:
						tmp = null;
						break;
					default:
					break;
				}
				
				if(tmp != null)
					list.add(tmp);
			});
		} catch (RecertificationException e) {
			e.printStackTrace();
		}
		
		return (List<T>) list;
	}
		
	@SuppressWarnings("unchecked")
	public List<T> readExcelCiatToVo(InputStream ios, ExcelCiatSheet sheet){
		List<Object> list = new LinkedList<>();
		
		try {
			List<List<String>> rowData = this.read.readExcelData(ios, sheet.getSheet(), sheet.getMaxColumns());
			rowData.forEach(row -> {
				Object tmp = null;
				switch (sheet) {
					case CIAT:
						tmp = this.mapperRecert.excelMapperCiat(row);
						break;
					case CIATENLINEA:
						tmp = this.mapperRecert.excelMapperCiat(row);
						break;
					default:
					break;
				}
				if(tmp != null)
					list.add(tmp);
			});
		} catch (RecertificationException e) {
			e.printStackTrace();
		}
		return (List<T>) list;
	}

	@SuppressWarnings("unchecked")
	public List<T> readExcelTelToVo(InputStream ios, ExcelTelSheet sheet){
		List<Object> list = new LinkedList<>();
		
		try {
			List<List<String>> rowData = this.read.readExcelData(ios, sheet.getSheetLocation(), sheet.getMaxColumns());
			rowData.forEach(row -> {
				Object tmp = null;
				switch (sheet) {
					case HOJA1:
						tmp = this.mapperRecert.excelMapperTel(row);
						break;
					default:
					break;
				}
				if(tmp != null)
					list.add(tmp);
			});
		} catch (RecertificationException e) {
			e.printStackTrace();
		}
		return (List<T>) list;
	}
}

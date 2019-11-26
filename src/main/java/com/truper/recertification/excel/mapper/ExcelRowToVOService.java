package com.truper.recertification.excel.mapper;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.truper.recertification.excel.ExcelRecertificationSheet;
import com.truper.recertification.excel.ReadExcel;
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
			List<List<String>> rowData = this.read.readExcelData(ios, sheet);
			
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
	
}

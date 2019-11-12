package com.truper.recertification.excel;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.springframework.stereotype.Component;

import com.truper.recertification.vo.ExcelVO;

@Component
public class RecertificacionExcelMapper{

	public ExcelVO excelMapper(XSSFRow row) {
		
		ExcelVO recertificacion = new ExcelVO();
		
		recertificacion.setNoEmpleado(row.getCell(0).getNumericCellValue());
	    recertificacion.setFechaIngreso(row.getCell(1).getDateCellValue());
	    recertificacion.setNombre(row.getCell(2).getStringCellValue());
	    recertificacion.setDepartamento(row.getCell(6).getStringCellValue());
	    recertificacion.setJefeJerarquico(row.getCell(8).getStringCellValue());
	    recertificacion.setAd(row.getCell(12).getStringCellValue());
	    recertificacion.setSap(row.getCell(13).getStringCellValue());
	    recertificacion.setTel(row.getCell(14).getStringCellValue());
	    recertificacion.setCiat(row.getCell(15).getStringCellValue());
		
	    return recertificacion;
	}
}

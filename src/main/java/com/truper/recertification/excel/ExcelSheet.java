package com.truper.recertification.excel;

import lombok.Getter;

public enum ExcelSheet {

	RECERTIFICACION(0),
	TEL(1),
	SAP(2),
	CIAT(3),
	PERFILES_SAP(4);
	
	@Getter
	private int sheetLocation;
	
	ExcelSheet(Integer sheet){
		this.sheetLocation = sheet;
	}
	
	public Integer getMaxColumns() {
		switch (this) {
		case RECERTIFICACION:
			return 24;
		case TEL:
			return 7;
		case SAP:
			return 4;
		case CIAT:
			return 6;
		case PERFILES_SAP:
			return 4;
		default:
			break;
		}
		return sheetLocation;
	}
}

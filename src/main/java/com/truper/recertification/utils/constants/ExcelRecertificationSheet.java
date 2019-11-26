package com.truper.recertification.utils.constants;

import lombok.Getter;

public enum ExcelRecertificationSheet {
	
	ACTIVOS(0),
	BAJAS(1),
	REPORTE(2),
	AVANCE(3);
	
	@Getter
	private Integer sheetLocation;
	
	ExcelRecertificationSheet(Integer sheetLocation) {
		this.sheetLocation = sheetLocation;
	}
	
	public Integer getMaxColumns() {
		switch(this) {
		case ACTIVOS:
			return 25;
		case BAJAS:
			return 17;
		case REPORTE:
			return 5;
		case AVANCE:
			return 5;
		default:
				return 0;
		}
	}
	
}

package com.truper.recertification.excel.utils.constants;

import lombok.Getter;

public enum ExcelTelSheet {

	HOJA1(0);
	
	@Getter
	private Integer sheetLocation;
	
	ExcelTelSheet(Integer sheetLocation) {
		this.sheetLocation = sheetLocation;
	}
	
	public Integer getMaxColumns() {
		switch(this) {
		case HOJA1:
			return 7;
		default:
				return 0;
		}
	}
}

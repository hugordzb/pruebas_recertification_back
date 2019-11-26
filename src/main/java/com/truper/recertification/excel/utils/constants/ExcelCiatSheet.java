package com.truper.recertification.excel.utils.constants;

import lombok.Getter;

public enum ExcelCiatSheet {

	CIAT(0),
	CIATENLINEA(1);
	
	@Getter
	private Integer sheetLocation;
	
	ExcelCiatSheet(Integer sheetLocation) {
		this.sheetLocation = sheetLocation;
	}
	public Integer getSheet() {
		switch(this) {
		case CIAT:
			return 0;
		case CIATENLINEA:
			return 1;
		default:
				return 0;
		}
	}
	
	public Integer getMaxColumns() {
		switch(this) {
		case CIAT:
			return 6;
		case CIATENLINEA:
			return 6;
		default:
				return 0;
		}
	}
}

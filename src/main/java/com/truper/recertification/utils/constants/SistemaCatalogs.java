package com.truper.recertification.utils.constants;

import lombok.Getter;

public enum SistemaCatalogs {
	
	UNDEFINED("S000"),
	CIAT("S001"),
	SAP("S002"),
	TEL("S003");
	
	@Getter
	private String code;
	
	SistemaCatalogs(String code){
		this.code = code;
	}
	
	public String getSystemName() {
		switch(this) {
			case UNDEFINED:
				return "UNDEFINED";
			case CIAT: 
				return "CIAT";
			case SAP:
				return "SAP";
			case TEL:
				return "TEL";
			default:
				return "NOT FOUND";
		}
	}
}

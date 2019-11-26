package com.truper.recertification.constants;

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
}

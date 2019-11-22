package com.truper.recertification.excel.vo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TelExcelVO implements Serializable{

	private static final long serialVersionUID = 1L;

	private String estatus;
	
	private String departamento;
	
	private String jefeJerarquico;
	
	private String usuarioTel;
	
	private String rol;
}

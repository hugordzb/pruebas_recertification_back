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
public class NewFileExcelVO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String idJefe;
	
	private String nombreJefe;
	
	private String departamento;
	
	private String idEmpleado;
	
	private String nombreEmpleado;
	
	private String cuentatel;
	
	private String rolTel;
	
	private String cuentaCiat;
	
	private String rolCiat;

	private String cuentaSap;
	
	private String rolSap;
	
	private String descRolSap;
}

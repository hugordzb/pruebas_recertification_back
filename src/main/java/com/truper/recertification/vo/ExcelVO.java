package com.truper.recertification.vo;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ExcelVO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private double noEmpleado;
	
	private Date fechaIngreso;
	
	private String nombre;
	
	private String departamento;
		
	private String jefeJerarquico;
	
	private String ad;
	
	private String sap;
	
	private String tel;
	
	private String ciat;
}

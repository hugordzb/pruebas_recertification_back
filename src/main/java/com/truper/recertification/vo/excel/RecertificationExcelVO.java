package com.truper.recertification.vo.excel;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RecertificationExcelVO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String noEmpleado;
	
	private String fechaIngreso;
	
	private String nombre;
	
	private String ubicacion;
	
	private String direccion;
	
	private String area;
	
	private String departamento;
	
	private String puesto;
	
	private String jefeJerarquico;
	
	private String nombreJefeFuncional;
	
	//no creo se necesite
	private String carta;

	//no creo se necesite
	private String Respuesta;
	
	private String ad;
	
	private String sap;
	
	private String tel;
	
	private String ciat;
	
	private boolean altas;
	
	private boolean bajas;
	
	private String cambioCuenta;
	
	private String cambioUsuario;
	
	private String ticket;
	
	private boolean cartaResp;
	
	private boolean activo;
	
	private String atendio;
}

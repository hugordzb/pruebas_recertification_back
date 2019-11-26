package com.truper.recertification.excel.vo;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecertificationExcelVO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Integer noEmpleado;
	
	private Date fechaIngreso;
	
	private String nombre;
	
	private String ubicacion;
	
	private String direccion;
	
	private String area;
	
	private String departamento;
	
	private String puesto;
	
	private String jefeJerarquico;
	
	private String correoJefe;
	
	private String nombreJefeFuncional;
	
	private String carta;

	private String respuesta;
	
	private String ad;
	
	private String sap;
	
	private String tel;
	
	private String ciat;
	
	private String altas;
	
	private String bajas;
	
	private String cambioCuenta;
	
	private String cambioUsuario;
	
	private String ticket;
	
	private String cartaResp;
	
	private String activo;
	
	private String atendio;
}

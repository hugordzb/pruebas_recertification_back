package com.truper.recertification.excel.vo;

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
public class CiatExcelVO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String usuario;
	
	private String nombre;
	
	private Integer perfil;
	
	private String nombrePerfil;
	
	//se encuentra vacio en el doc por lo que no se mapeo
	private Date fechaBaja;
	
	private String almacen;
}

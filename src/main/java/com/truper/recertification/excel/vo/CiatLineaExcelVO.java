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
public class CiatLineaExcelVO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String usuario;
	
	private String nombre;
	
	private Integer perfil;
	
	private String nombrePerfil;
	
	private String cdrs;
	
	private String estado;

}

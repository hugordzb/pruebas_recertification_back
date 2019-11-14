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
public class SapVO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String nombre;
	
	private String departamento;
	
	private String usuarioSAP;
	
	private String rol;
}

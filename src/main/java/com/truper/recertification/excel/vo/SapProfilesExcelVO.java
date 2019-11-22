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
public class SapProfilesExcelVO implements Serializable{

	private static final long serialVersionUID = 1L;

	private String usuarios;
	
	private String nombre;
	
	private String perfil;
	
	private String texto;
}

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
public class SapApoExcelVO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String usuarios;
	
	private String nombre;
	
	private String perfil;
	
	private String texto;

}

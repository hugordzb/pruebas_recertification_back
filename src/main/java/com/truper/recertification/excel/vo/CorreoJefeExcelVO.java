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
public class CorreoJefeExcelVO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String idJefe;
	
	private String nombre;

}

package com.truper.recertification.json.request.vo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ToProcessChangeVO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String idMovimiento;
	
	private String atendio;
	
	private String estatus;
	
	private String comentarios;
}

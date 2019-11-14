package com.truper.recertification.vo.answer;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SystemsVO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String jefe;
	
	private String empleado;
	
	private String cuentaSistema;
}

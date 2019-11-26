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
public class BossDetailVO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String idJefe;
	
	private String nombre;
	
	private String correo;

	private String correoCC;

	private String departamento;
	
	private boolean isInRecertificacion;
	
	private boolean isRecertificado;
	
	private String periodo;
	
}

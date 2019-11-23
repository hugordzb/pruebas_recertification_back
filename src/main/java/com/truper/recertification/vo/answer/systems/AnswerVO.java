package com.truper.recertification.vo.answer.systems;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AnswerVO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String idUsuario;

	private String cuentaSistema;
	
	private String perfil;
	
	private String sistema;
		
}

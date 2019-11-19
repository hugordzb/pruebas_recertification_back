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
public class ProfileSystemVO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer idPerfil;
	
	private String perfil;

	private SystemsVO systemData;
	
}

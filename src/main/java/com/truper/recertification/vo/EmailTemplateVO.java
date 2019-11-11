package com.truper.recertification.vo;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EmailTemplateVO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String strIdJefe;
	
	private List<String> lstIdSistemas;
	
	private String strCorreo;
}

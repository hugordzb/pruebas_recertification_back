package com.truper.recertification.excel.recertification.vo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DocsDataVO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String strRuta;
	
	private String strArchivo;
	
	private String strHoja;
	
}

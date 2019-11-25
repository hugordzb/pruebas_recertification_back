package com.truper.recertification.excel.vo;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FileVO implements Serializable{

	//preguntar porq no es 1L
	private static final long serialVersionUID = 19274875838282L;

	private String strName;
	
	private String strUrl;
	
	private Long length;
	
	private Date dteCreateTime;
	
	private Date dteLastModTime;
	
}

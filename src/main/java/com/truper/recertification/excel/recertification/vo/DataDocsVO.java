package com.truper.recertification.excel.recertification.vo;

import java.io.InputStream;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DataDocsVO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String strNameFile;
	
	private InputStream inputStream;
	
	private String strHoja;
	
}

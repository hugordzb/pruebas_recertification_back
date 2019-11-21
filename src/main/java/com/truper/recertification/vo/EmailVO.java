package com.truper.recertification.vo;

import java.io.Serializable;
import java.util.List;

import org.springframework.core.io.FileSystemResource;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EmailVO implements Serializable{

	private static final long serialVersionUID = 1L;

	private String destinatario;
	
	private String cc;
	
	private String cco;
	
	private List<FileSystemResource> lstAdjunto;
	
}
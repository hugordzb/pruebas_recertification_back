package com.truper.recertification.vo.request;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RequestAcountVO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String tipoMov;
	
	private String idUsuario;
	
	private String nIdUsuario;
	
	private String perfil;
	
	private String nPerfil;
	
	private String sistema;
	
	private String nSistema;
	
	private String cuentaSistema;
	
	private String nCuentaSistema;
	
	private String idJefe;
	
	private String nIdJefe;
		
	private String solicitante; 
}

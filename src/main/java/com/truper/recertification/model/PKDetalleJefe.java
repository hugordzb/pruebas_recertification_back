package com.truper.recertification.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PKDetalleJefe implements Serializable{

	private static final long serialVersionUID = 1L;

	@Column(name = "IDJEFE")
	private String idJefe;
	
	@Column(name = "IDDEPARTAMENTO")
	private Integer idDepartamento;

}

package com.truper.recertification.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class PKJerarquia implements Serializable{

	private static final long serialVersionUID = 1L;

	@Column(name = "IDUSUARIO")
	private String idUsuario;

	@Column(name = "IDJEFE")
	private String idJefe;

}

package com.truper.recertification.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

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
public class PKPerfilSistema implements Serializable{

	private static final long serialVersionUID = 1L;

	@Column(name = "IDPERFIL")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idPerfil;
	
	@Column(name = "IDSISTEMA")
	private String idSistema;

}

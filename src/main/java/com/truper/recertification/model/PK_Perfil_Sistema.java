package com.truper.recertification.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;

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
public class PK_Perfil_Sistema implements Serializable{

	private static final long serialVersionUID = 1L;

	@Column(name = "IDPERFIL")
	@SequenceGenerator(schema = "CONFRONTADAT", name = "RE_IDPERFIL", sequenceName = "SEQ_RE_IDPERFIL", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RE_IDPERFIL")
	private Integer idPerfil;
	
	@Column(name = "IDSISTEMA")
	private String idSistema;

}

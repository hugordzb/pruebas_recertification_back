package com.truper.recertification.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.data.domain.Persistable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "RE_PERFIL_SISTEMA")
public class RePerfilSistemaEntity implements Serializable, Persistable<PKPerfilSistema>{

	private static final long serialVersionUID = 1L;
	
	@Column(name = "IDPERFIL")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idPerfil;

	@EmbeddedId
	private PKPerfilSistema idPerfilSistema;
		
	@Transient
	@Builder.Default
	private boolean exist = false;
	
	@Override
	public PKPerfilSistema getId() {
		return this.idPerfilSistema;
	}

	@Override
	public boolean isNew() {
		return this.isExist();
	}
}
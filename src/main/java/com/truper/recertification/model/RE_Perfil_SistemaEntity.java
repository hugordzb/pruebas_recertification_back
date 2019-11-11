package com.truper.recertification.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
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
public class RE_Perfil_SistemaEntity implements Serializable, Persistable<PK_Perfil_Sistema>{

	private static final long serialVersionUID = 1L;

	@Embedded
	private PK_Perfil_Sistema idPerfilSistema;
		
	@Column(name = "PERFIL")
	private String perfil;

	@Transient
	@Builder.Default
	private boolean exist = false;
	
	@Override
	public PK_Perfil_Sistema getId() {
		return this.idPerfilSistema;
	}

	@Override
	public boolean isNew() {
		return this.isExist();
	}
}
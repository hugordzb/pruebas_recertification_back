package com.truper.recertification.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
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
@Table(name = "RE_CUENTAS_USUARIO")
public class RE_Cuentas_UsuarioEntity implements Serializable, Persistable<PK_Cuentas_Usuario>{

	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private PK_Cuentas_Usuario idCuentaUsuario;
	
	@Column(name = "CUENTASISTEMA")
	private String cuentaSistema;
	
	@Transient
	@Builder.Default
	private boolean exist = false;

	@Override
	public PK_Cuentas_Usuario getId() {
		return this.idCuentaUsuario;
	}

	@Override
	public boolean isNew() {
		return this.isExist();
	}
	
}

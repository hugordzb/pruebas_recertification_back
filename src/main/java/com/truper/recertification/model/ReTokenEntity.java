package com.truper.recertification.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "RE_STOKEN")
public class ReTokenEntity implements Serializable, Persistable<String>{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "IDUSUARIO")
	private String idUsuario;
	
	@Column(name = "TOKEN")
	private String token;
	
	@Column(name = "ULTIMASESION")
	private Date ultimaSesion;

	@Transient
	@Builder.Default
	private boolean exist = false;
	
	@Override
	public String getId() {
		return this.getIdUsuario();
	}

	@Override
	public boolean isNew() {
		return this.isExist();
	}
}

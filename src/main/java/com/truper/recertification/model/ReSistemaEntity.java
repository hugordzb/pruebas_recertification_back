package com.truper.recertification.model;

import java.io.Serializable;

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
@Table(name = "RE_SISTEMA")
public class ReSistemaEntity implements Serializable, Persistable<String>{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "IDSISTEMA")
	private String idSistema;
	
	@Column(name = "SISTEMA")
	private String sistema;
	
	@Transient
	@Builder.Default
	private boolean exist = false;

	@Override
	public String getId() {
		return this.getIdSistema();
	}

	@Override
	public boolean isNew() {
		return this.isExist();
	}
	
}

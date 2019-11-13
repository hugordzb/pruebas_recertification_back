package com.truper.recertification.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name = "RE_DEPARTAMENTO")
public class ReDepartamentoEntity implements Serializable, Persistable<Integer>{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IDDEPARTAMENTO")
	private Integer idDepartamento;
	
	@Column(name = "DEPARTAMENTO")
	private String departamento;

	@Transient
	@Builder.Default
	private boolean exist = false;

	@Override
	public Integer getId() {
		return this.getIdDepartamento();
	}

	@Override
	public boolean isNew() {
		return this.isExist();
	}
}

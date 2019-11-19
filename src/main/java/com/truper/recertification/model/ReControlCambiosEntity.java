package com.truper.recertification.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
@Table(name = "RE_CONTROL_CAMBIOS")
public class ReControlCambiosEntity implements Serializable, Persistable<Integer>{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "IDMOVIMIENTO")
	private Integer idMovimiento;

	@Column(name = "ATENDIO")
	private String atendio;
	
	@Column(name = "ESTATUS")
	private boolean estatus;
	
	@Column(name = "FECHAATENCION")
	private Timestamp fechaAtencion;
	
	@Column(name = "COMENTARIOS")
	private String comentarios;
	
	@Override
	public Integer getId() {
		return this.idMovimiento;
	}

	@Override
	public boolean isNew() {
		return this.isEstatus();
	}

}
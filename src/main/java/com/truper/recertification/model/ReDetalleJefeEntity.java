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
@Table(name = "RE_DETALLE_JEFE")
public class ReDetalleJefeEntity implements Serializable, Persistable<PKDetalleJefe>{
	
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private PKDetalleJefe idDetalleJefe;
	
	@Column(name = "NOMBRE")
	private String nombre;
	
	@Column(name = "CORREO")
	private String correo;

	@Column(name = "CORREOCC")
	private String correoCC;

	@Transient
	@Builder.Default
	private boolean exist = false;
	
	@Override
	public PKDetalleJefe getId() {
		return this.idDetalleJefe;
	}

	@Override
	public boolean isNew() {
		return this.isExist();
	}

}
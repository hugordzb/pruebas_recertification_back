package com.truper.recertification.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
@Table(name = "RE_RECERTIFICACION")
public class ReRecertificacionEntity implements Serializable, Persistable<PKRecertificacion>{

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private PKRecertificacion idRecertificacion;
	
	@Column(name = "CARTASOLICITUD")
	private String cartaSolicitud;
	
	@Column(name = "CARTAAPROBADA")
	private String cartaAprobada;
	
	@Column(name = "ESTATUS")
	private boolean estatus;
	
	@Column(name = "ATENDIO")
	private String atendio;
	
	@Column(name = "COMENTARIOS")
	private String comentarios;
	
	@Column(name = "FECHATERMINO")
	private Date fechaTermino;
	
	@Override
	public PKRecertificacion getId() {
		return this.getIdRecertificacion();
	}

	@Override
	public boolean isNew() {
		return this.isEstatus();
	}
	
}
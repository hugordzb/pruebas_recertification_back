package com.truper.recertification.model;

import java.io.Serializable;

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
@Table(name = "RE_JERARQUIA")
public class ReJerarquiaEntity implements Serializable, Persistable<PKJerarquia>{

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private PKJerarquia idEmpleadoJefe;
	
	@Transient
	@Builder.Default
	private boolean exist = false;
	
	@Override
	public PKJerarquia getId() {
		return this.getIdEmpleadoJefe();
	}

	@Override
	public boolean isNew() {
		return this.isExist();
	}
}

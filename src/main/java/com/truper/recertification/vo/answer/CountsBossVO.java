package com.truper.recertification.vo.answer;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CountsBossVO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String idJefe;
	
	private String jefe;
	
	private boolean inAD;
	
	private List<CountsByUserVO> empleados;

}

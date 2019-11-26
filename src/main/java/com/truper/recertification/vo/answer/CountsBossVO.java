package com.truper.recertification.vo.answer;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CountsBossVO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String idJefe;
	
	private String jefe;
	
	private String sistemas;

	private boolean inAD;
	
	private List<DetailCountsEmployeeVO> empleados;

}

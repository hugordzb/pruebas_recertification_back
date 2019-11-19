package com.truper.recertification.vo.answer;

import java.io.Serializable;
import java.util.List;

import com.truper.recertification.vo.answer.sistemas.CiatDataVO;
import com.truper.recertification.vo.answer.sistemas.SapDataVO;
import com.truper.recertification.vo.answer.sistemas.TelDataVO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CountsByUserVO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String idEmpleado;
	
	private String empleado;
	
	private List<SapDataVO> sap;

	private List<TelDataVO> tel;
	
	private List<CiatDataVO> ciat;
}

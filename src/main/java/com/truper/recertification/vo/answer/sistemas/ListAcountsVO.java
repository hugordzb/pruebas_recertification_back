package com.truper.recertification.vo.answer.sistemas;

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
public class ListAcountsVO implements Serializable{

	private static final long serialVersionUID = 1L;

	List<TelDataVO> tel;
	
	List<SapDataVO> sap;
	
	List<CiatDataVO> ciat;
}

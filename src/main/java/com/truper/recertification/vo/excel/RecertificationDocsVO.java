package com.truper.recertification.vo.excel;

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
public class RecertificationDocsVO implements Serializable{

	private static final long serialVersionUID = 1L;
		
	private List<SapProfilesVO> lstSapProfiles;
	
	private List<SapApoExcelVO> lstSapAPO;
	
	private List<SapExcelVO> lstSap;
	
	private List<TelExcelVO> lstTel;
	
	private List<CiatExcelVO> lstCiat;
	
	private List<CiatLineaExcelVO> lstCiatLinea;
	
	private List<CorreoJefeVO> lstCorreoJefe;

	private List<RecertificationExcelVO> lstRecert;

}

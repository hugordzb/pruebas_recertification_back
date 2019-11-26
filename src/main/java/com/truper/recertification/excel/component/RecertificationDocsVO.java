package com.truper.recertification.excel.component;

import java.io.Serializable;
import java.util.List;

import com.truper.recertification.excel.vo.CiatExcelVO;
import com.truper.recertification.excel.vo.CiatLineaExcelVO;
import com.truper.recertification.excel.vo.EmailBossExcelVO;
import com.truper.recertification.excel.vo.NewFileExcelVO;
import com.truper.recertification.excel.vo.RecertificationExcelVO;
import com.truper.recertification.excel.vo.SapApoExcelVO;
import com.truper.recertification.excel.vo.SapExcelVO;
import com.truper.recertification.excel.vo.SapProfilesExcelVO;
import com.truper.recertification.excel.vo.TelExcelVO;

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
		
	private List<SapProfilesExcelVO> lstSapProfiles;
	
	private List<SapApoExcelVO> lstSapAPO;
	
	private List<SapExcelVO> lstSap;
	
	private List<TelExcelVO> lstTel;
	
	private List<CiatExcelVO> lstCiat;
	
	private List<CiatLineaExcelVO> lstCiatLinea;
	
	private List<EmailBossExcelVO> lstCorreoJefe;

	private List<RecertificationExcelVO> lstRecert;
	
	private List<NewFileExcelVO> lstNewFile;

}

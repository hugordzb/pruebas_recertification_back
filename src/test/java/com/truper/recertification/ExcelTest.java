package com.truper.recertification;

import java.io.File;
import java.io.FileInputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.truper.recertification.excel.ExcelRecertificationSheet;
import com.truper.recertification.excel.ReadExcel;
import com.truper.recertification.excel.mapper.ExcelRowToVOService;
import com.truper.recertification.excel.service.impl.RecertificationDocsServiceImpl;
import com.truper.recertification.excel.vo.RecertificationExcelVO;
import com.truper.recertification.service.LoadLayoutRecertService;
import com.truper.recertification.util.FiltersUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@ActiveProfiles("dev")
public class ExcelTest {

	@Autowired
	private RecertificationDocsServiceImpl recert;
	
	@Autowired
	private ReadExcel read;
	
	@Autowired
	private ExcelRowToVOService<RecertificationExcelVO> readRecert;
	
	@Autowired
	private LoadLayoutRecertService layService;
	
	@Test
	public void unicos() {
		List<RecertificationExcelVO> listData = new LinkedList<>();
		listData.add(RecertificationExcelVO
				.builder()
				.jefeJerarquico("LUPIS")
				.build());
		
		
		listData.add(RecertificationExcelVO
				.builder()
				.jefeJerarquico("LUPIS")
				.build());
		
		listData.add(RecertificationExcelVO
				.builder()
				.jefeJerarquico("LAPIZ")
				.build());
		
		List<RecertificationExcelVO> jefesUniquesList = listData.stream()
				.filter(FiltersUtils.distinctByKey( p -> p.getJefeJerarquico()))
				.collect(Collectors.toList());
		
		jefesUniquesList.forEach(v -> {
			System.out.println(v.getJefeJerarquico());
		});
	}
	
	@Test
	public void excel() {
		log.info("-----Start-------");
		File file = new File("/tmp/RECERT_DUMMY.xlsx");
		List<RecertificationExcelVO> listData = null;
		
		try (FileInputStream fios = new FileInputStream(file)) {
			listData = this.readRecert.readExcelRercertificationToVo(fios, ExcelRecertificationSheet.ACTIVOS);
			
			if(listData  != null) {
				this.layService.insertUsersData(listData);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info("-----Finish-------");
	}
		
	//Excel
	@Test
	public void excelNewFile() {
		log.info("-----Start-------");
//		recert.selectNewFormatDoc();
		log.info("-----Finish-------");
		
	}
}

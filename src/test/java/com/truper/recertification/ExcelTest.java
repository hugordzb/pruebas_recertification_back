package com.truper.recertification;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.truper.recertification.excel.RecertificationDocs;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class ExcelTest {

	@Autowired
	private RecertificationDocs recert;
	
	//Excel
	@Test
	public void excel() {
		log.info("-----Start-------");
		recert.selectRecertificationDoc();
		log.info("-----Finish-------");
		
	}
		
	//Excel
	@Test
	public void excelNewFile() {
		log.info("-----Start-------");
		recert.selectNewFormatDoc();
		log.info("-----Finish-------");
		
	}
}

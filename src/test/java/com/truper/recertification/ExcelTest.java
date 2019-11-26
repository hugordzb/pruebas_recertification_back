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

import com.truper.recertification.dao.ReUsuarioDAO;
import com.truper.recertification.excel.ExcelRecertificationSheet;
import com.truper.recertification.excel.ReadExcel;
import com.truper.recertification.excel.mapper.ExcelRowToVOService;
import com.truper.recertification.excel.service.impl.RecertificationDocsServiceImpl;
import com.truper.recertification.excel.vo.RecertificationExcelVO;
import com.truper.recertification.model.ReUsuarioEntity;
import com.truper.recertification.service.AuditoryService;
import com.truper.recertification.service.LoadLayoutRecertService;
import com.truper.recertification.util.FiltersUtils;
import com.truper.recertification.vo.answer.CountsBossVO;

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
	
	@Autowired
	private AuditoryService audService;
	
	@Autowired
	private ReUsuarioDAO userDAO;
	
	@Test
	public void pruebaUnit() {
		List<ReUsuarioEntity> usuarios = this.userDAO.findUsuariosByBoss("fmacedoniom");
		if(usuarios != null) {
			usuarios.forEach( v -> {
				System.out.println(v.getNombre());
			});
		}
	}
	
	@Test
	public void unicos() {
		log.info("Inicio...");
		CountsBossVO detailJefe = this.audService.findByBoss("fmacedoniom");
		if(detailJefe != null) {
			detailJefe.getEmpleados().forEach( emp -> {
				System.out.println("Empleado " + emp.getEmpleado());
				
				if(emp.getCuentas() != null && !emp.getCuentas().isEmpty()) {
					emp.getCuentas().forEach( v -> {
						System.out.println("\t"+ v.getSystem() + " - " + v.getCuenta() + " - " + v.getPerfil());
					});
				}else {
					System.out.println("\t El usuario no tiene cuentas");
				}
			});
		}
		log.info("Fin");
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

package com.truper.recertification;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.truper.recertification.dao.ReUsuarioDAO;
import com.truper.recertification.excel.mapper.ExcelRowToVOService;
import com.truper.recertification.excel.service.LoadLayoutCiatService;
import com.truper.recertification.excel.service.LoadLayoutRecertService;
import com.truper.recertification.excel.utils.constants.ExcelCiatSheet;
import com.truper.recertification.excel.utils.constants.ExcelRecertificationSheet;
import com.truper.recertification.excel.vo.CiatExcelVO;
import com.truper.recertification.excel.vo.RecertificationExcelVO;
import com.truper.recertification.model.ReUsuarioEntity;
import com.truper.recertification.service.AuditoryService;
import com.truper.recertification.vo.answer.AcountsBossVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@ActiveProfiles("dev")
public class ExcelTest {

	@Autowired
	private ExcelRowToVOService<RecertificationExcelVO> readRecert;
	
	@Autowired
	private ExcelRowToVOService<CiatExcelVO> readCiat;
	
	@Autowired
	private LoadLayoutRecertService layService;
	
	@Autowired
	private LoadLayoutCiatService ciatService;
	
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
		AcountsBossVO detailJefe = this.audService.findByBoss("fmacedoniom");
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
	public void excelRecert() {
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
	
	@Test
	public void excelCiat() {
		log.info("-----Start-------");
		File file = new File("/tmp/Usuarios CIAT.xlsx");
		List<CiatExcelVO> listData = new ArrayList<>();
		
		try (FileInputStream fios = new FileInputStream(file)) {
//			listData = this.readCiat.readExcelCiatToVo(fios, ExcelCiatSheet.CIATENLINEA);
//			listData = this.readCiat.readExcelCiatToVo(fios, ExcelCiatSheet.CIAT);
			listData.addAll(this.readCiat.readExcelCiatToVo(fios, ExcelCiatSheet.CIAT));
			log.info("cambio hoja");
			listData.addAll(this.readCiat.readExcelCiatToVo(fios, ExcelCiatSheet.CIATENLINEA));
			
			if(listData  != null) {
				this.ciatService.insertUsersData(listData);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info("-----Finish-------");
	}
}

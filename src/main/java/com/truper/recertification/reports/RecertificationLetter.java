package com.truper.recertification.reports;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jfree.util.Log;

import com.truper.recertification.vo.answer.AcountEmployeeVO;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class RecertificationLetter {
	
	private String jefe;
	private List<AcountEmployeeVO> empleados;

	public RecertificationLetter(String jefe, List<AcountEmployeeVO> empleados) {
		this.jefe = jefe;
		this.empleados = empleados;
	}
	
	
	public JasperPrint build() {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("txtBossName", this.jefe);
		parameters.put("listEmpleados", new JRBeanCollectionDataSource(this.empleados, false));
		
		try(BufferedInputStream bufferedInputStream 
				= new BufferedInputStream(
						new FileInputStream("\\recert\\Recertification_template.jrxml"))) {

			JasperReport jasperReport = JasperCompileManager.compileReport(bufferedInputStream);

			JRDataSource dataSource = new JREmptyDataSource();
			
			return JasperFillManager.fillReport(jasperReport, parameters, dataSource);
		} catch (Exception e) {
			Log.error("Error al generar el archivo de recertificacion adjunto");
			Log.info(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
}

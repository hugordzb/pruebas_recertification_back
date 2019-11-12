package com.truper.recertification;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.BadCredentialsException;

import com.truper.recertification.common.mail.service.EmailService;
import com.truper.recertification.dao.RE_Bitacora_CambiosDAO;
import com.truper.recertification.dao.RE_Control_CambiosDAO;
import com.truper.recertification.dao.RE_Cuentas_UsuarioDAO;
import com.truper.recertification.dao.RE_DepartamentoDAO;
import com.truper.recertification.dao.RE_Detalle_JefeDAO;
import com.truper.recertification.dao.RE_JerarquiaDAO;
import com.truper.recertification.dao.RE_Perfil_SistemaDAO;
import com.truper.recertification.dao.RE_RecertificacionDAO;
import com.truper.recertification.dao.RE_SistemaDAO;
import com.truper.recertification.dao.RE_TokenDAO;
import com.truper.recertification.dao.RE_UsuarioDAO;
import com.truper.recertification.ldap.repository.LDAPRepository;
import com.truper.recertification.model.PKRecertificacion;
import com.truper.recertification.model.ReBitacoraCambiosEntity;
import com.truper.recertification.model.ReControlCambiosEntity;
import com.truper.recertification.model.ReCuentasUsuarioEntity;
import com.truper.recertification.model.ReDepartamentoEntity;
import com.truper.recertification.model.ReDetalleJefeEntity;
import com.truper.recertification.model.ReJerarquiaEntity;
import com.truper.recertification.model.RePerfilSistemaEntity;
import com.truper.recertification.model.ReRecertificacionEntity;
import com.truper.recertification.model.ReSistemaEntity;
import com.truper.recertification.model.ReTokenEntity;
import com.truper.recertification.model.ReUsuarioEntity;
import com.truper.recertification.vo.LDAPVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
class RecertificationApplicationTests {

	@Autowired
	private  LDAPRepository ldapRepo;
	
	@Autowired
	private LdapTemplate ldapTemplate;
	
	@Autowired
	private RE_Bitacora_CambiosDAO bitacoraDAO;
	
	@Autowired
	private RE_Control_CambiosDAO controlDAO;
	
	@Autowired
	private RE_Cuentas_UsuarioDAO cuentaDAO;
	
	@Autowired
	private RE_DepartamentoDAO departamentoDAO;
	
	@Autowired
	private RE_Detalle_JefeDAO detalleDAO;
	
	@Autowired
	private RE_JerarquiaDAO jerarquiaDAO;
	
	@Autowired
	private RE_Perfil_SistemaDAO perfilDAO;
	
	@Autowired
	private RE_RecertificacionDAO recertificacionDAO;
	
	@Autowired
	private RE_SistemaDAO sistemaDAO;
	
	@Autowired
	private RE_TokenDAO tokenDAO;
	
	@Autowired
	private RE_UsuarioDAO usuarioDAO;
	
	@Autowired
	private JavaMailSender emisorCorreo;
	
	@Autowired
	private EmailService correoService;
	
	//ldap	
	@Test
	public void ldapFindByUsername() {
		
		String strUsuario="mgmolinae";
		String strPassword="password";
		
		AndFilter filter = new AndFilter();

		if(!strUsuario.matches("[0-9]*")) {
			ldapRepo.findByUsername(strUsuario);
			filter.and(new EqualsFilter("sAMAccountName", strUsuario));
			
		}else {
			ldapRepo.findByEmploy(Integer.parseInt(strUsuario));
			filter.and(new EqualsFilter("initials", strUsuario));	
		}
		
		boolean blnOk = this.ldapTemplate.authenticate("", filter.encode(), strPassword);
		if(!blnOk) {
			log.info("No entro");
			throw new BadCredentialsException("Usuario y/o constraseña incorrectos");
			
		}
		
		log.info("auth: "+ blnOk);
	}
		
	@Test
	public void ldapFindByNoEmpleado() {
		int noEmpleado = 4011038;
		LDAPVO ldapVo = ldapRepo.findByEmploy(noEmpleado);
		
		log.info("User: " + ldapVo);
		
		assertNotNull(ldapVo.getId());
		
		AndFilter filter = new AndFilter();
		filter.and(new EqualsFilter("initials", noEmpleado));
		
		Boolean auth = ldapTemplate.authenticate("", filter.encode(), "password");
		
		log.info("auth: "+ auth);
		assertTrue(auth);
	}
	
	@Test
	public void ldapFindEmail() {
		
		String email = "mgmolinae@truper.com";
		LDAPVO ldapVO = ldapRepo.findByMail(email);
		
		assertNotNull(ldapVO.getId());
		
		AndFilter filter = new AndFilter();
		filter.and(new EqualsFilter("mail", email));
		
		Boolean auth = ldapTemplate.authenticate("", filter.encode(), "password");
		log.info("auth: "+ auth);
		assertTrue(auth);
	}
	
	//Excel
	@Test
	public void excel() {
		String strNombreArchivo = "Prueba.xlsx";
		String strRutaArchivo = "C:\\Users\\mgmolinae\\Downloads\\" + strNombreArchivo;
		String strHoja = "Activos";
		
		try (FileInputStream file = new FileInputStream(new File(strRutaArchivo))) {
			
			// leer archivo excel
			XSSFWorkbook worbook = new XSSFWorkbook(file);
			//obtener la hoja que se va leer
			XSSFSheet sheet = worbook.getSheet(strHoja);
			
			//obtener todas las filas de la hoja excel
			Iterator<Row> rowIterator = sheet.iterator();

			Row row;
			// se recorre cada fila hasta el final
			while (rowIterator.hasNext()) {
				row = rowIterator.next();
				//se obtiene las celdas por fila
				Iterator<Cell> cellIterator = row.cellIterator();
				Cell cell;
				
				//se recorre cada celda
				while (cellIterator.hasNext()) {
					// se obtiene la celda en específico y se la imprime
					cell = cellIterator.next();
					
//					log.info(cell.getStringCellValue()+"|");
					System.out.print(cell.getStringCellValue()+"|");
				}
				
//				while (cellIterator.hasNext()) 
//                {
//                    Cell cell = cellIterator.next();
                    //Check the cell type and format accordingly
//                    switch (cell.getCellType()) 
//                    {
                    	
//                            System.out.print(cell.getNumericCellValue() + "t");
//                            System.out.print(cell.getStringCellValue() + "t");
                      
//                    }
//                }
			}
		} catch (Exception e) {
			e.getMessage();
		}
	}
	@Test
	public void pruebaCorreo1() throws MessagingException {
		List<String> lstDestinatarios = new ArrayList<String>();
		
		lstDestinatarios.add("mgmolinae@truper.com");
		lstDestinatarios.add("hdrodriguezb@truper.com");
		
	
		
		MimeMessage message = emisorCorreo.createMimeMessage();
	      
	    MimeMessageHelper helper = new MimeMessageHelper(message, true);
	     
	    helper.setTo((String[])lstDestinatarios.toArray(new String[0]));
	    helper.setSubject("Prueba1");
	    helper.setText("Hola mundo!!!!");
	    
	  
	    emisorCorreo.send(message);
	}
	
	
	//Bitacora_Cambios
	@Test
	public void findByTipoMov() {
		List<ReBitacoraCambiosEntity> lista = bitacoraDAO.findByTipoMov("A");
		log.info("Lista: " + lista.size());
	}
	
	@Test
	public void findBySolicitante() {
		List<ReBitacoraCambiosEntity> lista = bitacoraDAO.findBySolicitante("solicitante");
		log.info("Lista: " + lista.size());
	}
	
	//Control_Cambios
	@Test
	public void findByAtendio() {
		List<ReControlCambiosEntity> lista = controlDAO.findByAtendio("atendio");
		log.info("Lista: " + lista.size());
	}

	@Test
	public void findByEstatus() {
		List<ReControlCambiosEntity> lista = controlDAO.findByEstatus(false);
		log.info("Lista: " + lista.size());
	}

	//Cuentas_Usuario
	@Test
	public void findbyidCuenta_UsuarioEntitiesIdUsuario() {
//		List<ReCuentasUsuarioEntity> lista = cuentaDAO.findByIdCuentasUsuarioIdUsuario("usuario");
//		log.info("Lista: " + lista.size());
	}
	
	@Test
	public void findByCuentaSistema() {
		ReCuentasUsuarioEntity lista = cuentaDAO.findByCuentaSistema("mgmolinae");
		log.info("Lista: " + lista);
	}
	
	//Departamento
	@Test
	public void findByDepartamento() {
		ReDepartamentoEntity lista = departamentoDAO.findByDepartamento("Departamento");
		log.info("Lista: " + lista);
	}
	
	//Detalle_Jefe
	@Test
	public void findByIdDepartamento() {
		ReDetalleJefeEntity lista = detalleDAO.findByIdDepartamento(1);
		log.info("Lista: " + lista);
	}

	@Test
	public void findByIdJefe() {
		List<ReDetalleJefeEntity> lista = detalleDAO.findByIdJefe("jefe");
		log.info("Lista: " + lista.size());
	}
	
	//Jerarquia
	@Test
	public void findByIdEmpleadoJefeIdJefe() {
//		List<ReJerarquiaEntity> lista = jerarquiaDAO.findByIdJerarquiaIdJefe("jefe");
//		log.info("Lista: " + lista.size());
	}

	@Test
	public void findByIdEmpleadoJefeIdUsuario() {
//		ReJerarquiaEntity lista = jerarquiaDAO.findByIdJerarquiaIdUsuario("usuario");
//		log.info("Lista: " + lista);
	}
	
	//Perfil_Sistema
	@Test
	public void findByIdPerfilSistemaIdSistemaPerfil() {
		List<RePerfilSistemaEntity> lista = perfilDAO.findByIdPerfilSistemaIdSistema("S001");
		log.info("Lista: " + lista.size());
	}
	
	@Test
	public void findByIdEmpleadoJefeIdJefePerfil() {
		List<RePerfilSistemaEntity> lista = perfilDAO.findByPerfil("perfil");
		log.info("Lista: " + lista.size());
	}
	
	//Recertificacion
	@Test
	public void findByEstatusRecertificacion() {
		List<ReRecertificacionEntity> lista = recertificacionDAO.findByEstatus(true);
		log.info("Lista: " + lista.size());
	}
	
	@Test
	public void findByPeriodo() {
		PKRecertificacion idRecertificacion = new PKRecertificacion("jefe", "0119");
		List<ReRecertificacionEntity> lista = recertificacionDAO.findByIdRecertificacion(idRecertificacion);
		log.info("Lista: " + lista.size());
	}
	
	@Test
	public void findByIdJefeandIdPeriodo() {
		List<ReRecertificacionEntity> lista = recertificacionDAO.findByIdRecertificacionPeriodo("0219");
		log.info("Lista: " + lista);
	}
	
	//Sistema
	@Test
	public void findBySistema() {
		ReSistemaEntity lista = sistemaDAO.findBySistema("CIAT");
		log.info("Lista: " + lista);
	}
	
	//Token
	@Test
	public void findByUltimaSesion() {
		ReTokenEntity lista = tokenDAO.findByUltimaSesion(new Timestamp(2019, 11, 11, 11, 11, 11, 11));
		log.info("Lista: " + lista);
	}
	
	//Usuario
	@Test
	public void findByNombre() {
		ReUsuarioEntity lista = usuarioDAO.findByNombre("nombre");
		log.info("Lista: " + lista);
	}
	
	@Test
	public void findByNoEmpleado() {
		ReUsuarioEntity lista = usuarioDAO.findByNoEmpleado(1);
		log.info("Lista: " + lista);
	}
	
	@Test
	public void findByEstatusUsuario() {
		List<ReUsuarioEntity> lista = usuarioDAO.findByEstatus(true);
		log.info("Lista: " + lista.size());
	}
}

package com.truper.recertification.excel;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReadExcel {
	
	public void leerFicheros() {
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
//				Cell cell;
//				//se recorre cada celda
//				while (cellIterator.hasNext()) {
//					// se obtiene la celda en específico y se la imprime
//					cell = cellIterator.next();
//					
//					log.info(cell.getStringCellValue()+" | ");
//				}
				while (cellIterator.hasNext()) 
                {
                    Cell cell = cellIterator.next();
                    //Check the cell type and format accordingly
/*                    switch (cell.getCellType()) 
                    {
                    	
                            System.out.print(cell.getNumericCellValue() + "t");
                            System.out.print(cell.getStringCellValue() + "t");
                      
                    }*/
                }
				System.out.println();
			}
		} catch (Exception e) {
			e.getMessage();
		}
	}
}

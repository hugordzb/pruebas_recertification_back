package com.truper.recertification.common.template;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.springframework.stereotype.Component;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.pdf.PdfWriter;
import com.truper.recertification.model.ReDetalleJefeEntity;

@Component
public class PDF {

	public void makePDF(ReDetalleJefeEntity detailBoss) throws FileNotFoundException, DocumentException {
		
		Document document = new Document();
		PdfWriter.getInstance(document, new FileOutputStream(detailBoss.getNombre() + ".pdf"));
		 
		document.open();
		Font font = FontFactory.getFont(FontFactory.COURIER, 16);
		Chunk chunk = new Chunk("Hello World", font);
		 
		document.add(chunk);
		document.close();
	}
	
}

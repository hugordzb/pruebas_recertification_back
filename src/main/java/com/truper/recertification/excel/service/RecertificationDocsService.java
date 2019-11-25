package com.truper.recertification.excel.service;

import java.io.InputStream;

public interface RecertificationDocsService {

	/**
	 * Read the first Charge and insert data on tables with the last excel format
	 */
	public void selectRecertificationDoc(String string, InputStream inputStream);
	
}

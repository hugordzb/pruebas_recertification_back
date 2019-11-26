package com.truper.recertification.excel.service;

import java.io.InputStream;

import com.truper.recertification.exception.RecertificationException;

public interface RecertificationDocsService {

	/**
	 * Read the first Charge and insert data on tables with the last excel format
	 */
	public void loadRecert(String string, InputStream inputStream) throws RecertificationException;
	
}

package com.truper.recertification.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

import com.truper.recertification.utils.constants.SistemaCatalogs;

public class FiltersUtils {
	
	public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
		Map<Object, Boolean> map = new ConcurrentHashMap<>();
		return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
	}
	
	public static SistemaCatalogs getCatalog(String code) {
		switch(code) {
			case "S001":
				return SistemaCatalogs.CIAT;
			case "S002":
				return SistemaCatalogs.SAP;
			case "S003":
				return SistemaCatalogs.TEL;
			default:
				return SistemaCatalogs.UNDEFINED;
		}
	}
	
}

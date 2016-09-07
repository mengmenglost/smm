package org.mm.excel;

import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelUtil {
		public static List<Map<String, Object>> getDataList(String path) {
			Workbook wb=WorkbookFactory.create(path);
		}
}

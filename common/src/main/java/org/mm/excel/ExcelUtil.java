package org.mm.excel;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelUtil {
	public static List<Map<String, Object>> getDataList(String path, String sheetName) throws InvalidFormatException, IOException {
		InputStream inputStream = null;
		Workbook wb = null;
		try {
			inputStream = new FileInputStream(path);
			wb = WorkbookFactory.create(inputStream);
			Sheet sheet = wb.getSheet(sheetName);
			Row headerRow = sheet.getRow(0);
			int columnNum = headerRow.getLastCellNum();
			int rowNum = sheet.getLastRowNum();
			Map<Integer, String> headerMap = new HashMap<Integer, String>();
			for (int columnIndex = 0; columnIndex < columnNum; columnIndex++) {
				headerMap.put(columnIndex, headerRow.getCell(columnIndex).getStringCellValue());
			}
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			for (int rowIndex = 1; rowIndex <= rowNum; rowIndex++) {
				Row row = sheet.getRow(rowIndex);
				Map<String, Object> map = new HashMap<String, Object>();
				for (int columnIndex = 0; columnIndex < columnNum; columnIndex++) {
					String key = headerMap.get(columnIndex);
					Cell cell = row.getCell(columnIndex);
					Object value = null;
					if (cell != null) {
						value = getCellValue(row.getCell(columnIndex));
					}
					map.put(key, value);
				}
				list.add(map);
			}
			return list;
		} finally {
			if (wb != null) {
				wb.close();
			}
			if (inputStream != null) {
				inputStream.close();
			}
		}
	}

	private static Object getCellValue(Cell cell) {
		int realCellType = cell.getCellType();
		if (realCellType == Cell.CELL_TYPE_FORMULA) {
			realCellType = cell.getCachedFormulaResultType();
		}
		switch (realCellType) {
		case Cell.CELL_TYPE_BOOLEAN:
			return cell.getBooleanCellValue();
		case Cell.CELL_TYPE_NUMERIC:
			if (DateUtil.isCellDateFormatted(cell)) {
				return cell.getDateCellValue();
			} else {
				return cell.getNumericCellValue();
			}
		case Cell.CELL_TYPE_STRING:
			return cell.getStringCellValue();
		case Cell.CELL_TYPE_BLANK:
			return "";
		default:
			return "";
		}
	}

	public static void main(String[] args) throws InvalidFormatException, IOException {
		String path = "C:\\Users\\mm\\Desktop\\ËïÃÉÃÉ.xlsx";
		List<Map<String, Object>> list = getDataList(path, "la");
		for (Map<String, Object> map : list) {
			System.out.println(map.toString());
		}
	}
}

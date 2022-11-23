package com.fix.mobile.helper;

import org.apache.poi.ss.usermodel.*;

public class ExcellConfig {
	// cấu hình file excell
	public CellStyle getCellStyle(Workbook wb, String type) {
		Font font = wb.createFont();
		font.setFontHeightInPoints((short) 12);
		font.setFontName("Times New Roman");
		font.setItalic(false);

		CellStyle style = wb.createCellStyle();
		style.setLocked(false);

		// Tao Style Header
		if (type.equalsIgnoreCase("Header")) {
			style.setFont(font);
			style.setAlignment(HorizontalAlignment.CENTER);
			style.setVerticalAlignment(VerticalAlignment.CENTER);
		}
		if (type.equalsIgnoreCase("Content")) {
			style.setFont(font);
			style.setBorderBottom(BorderStyle.THIN);
			style.setBorderLeft(BorderStyle.THIN);
			style.setBorderRight(BorderStyle.THIN);
			style.setBorderTop(BorderStyle.THIN);
		}
		if (type.equalsIgnoreCase("Content_TextRed")) {
			style.setBorderBottom(BorderStyle.THIN);
			style.setBorderLeft(BorderStyle.THIN);
			style.setBorderRight(BorderStyle.THIN);
			style.setBorderTop(BorderStyle.THIN);
			font.setColor(IndexedColors.RED.getIndex());
			style.setFont(font);
		}
		if (type.equalsIgnoreCase("Content_text_right")) {
			style.setFont(font);
			style.setBorderBottom(BorderStyle.THIN);
			style.setBorderLeft(BorderStyle.THIN);
			style.setBorderRight(BorderStyle.THIN);
			style.setBorderTop(BorderStyle.THIN);
			style.setAlignment(HorizontalAlignment.RIGHT);
			style.setVerticalAlignment(VerticalAlignment.BOTTOM);
		}
		if (type.equalsIgnoreCase("Content_Yellow")) {
			style.setFont(font);
			style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
			style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			style.setBorderBottom(BorderStyle.THIN);
			style.setBorderLeft(BorderStyle.THIN);
			style.setBorderRight(BorderStyle.THIN);
			style.setBorderTop(BorderStyle.THIN);
		}
		if (type.equalsIgnoreCase("BackgroundAqua")) {
			style.setFont(font);
			style.setFillForegroundColor(IndexedColors.AQUA.getIndex());
			style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			style.setBorderBottom(BorderStyle.THIN);
			style.setBorderLeft(BorderStyle.THIN);
			style.setBorderRight(BorderStyle.THIN);
			style.setBorderTop(BorderStyle.THIN);
		}
		if (type.equalsIgnoreCase("BackgroundAqua_TextRed")) {
			font.setColor(IndexedColors.RED.getIndex());
			style.setFont(font);
			style.setFillForegroundColor(IndexedColors.AQUA.getIndex());
			style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			style.setBorderBottom(BorderStyle.THIN);
			style.setBorderLeft(BorderStyle.THIN);
			style.setBorderRight(BorderStyle.THIN);
			style.setBorderTop(BorderStyle.THIN);
		}
		return style;
	}


}

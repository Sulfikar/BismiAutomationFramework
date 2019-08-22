package org.bismi.excel;
/**
 * @author Sulfikar Ali Nazar -
 * sulfikar@outlook.com
 *  BismiAutomationFramework - https://bismi.solutions
 */
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import org.apache.commons.lang3.StringUtils;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelRow {
	private Logger log = LogManager.getLogger(ExcelRow.class);
	Sheet sheet;
	Workbook wb;
	int row;
	
	FileInputStream inputStream = null;
	FileOutputStream outputStream = null;
	String sCompleteFileName = null;
	Row ROW=null;
	
	protected ExcelRow(Sheet sheet, Workbook wb, int row, FileInputStream inputStream,
			FileOutputStream outputStream, String sCompleteFileName) {

		this.sheet = sheet;
		this.wb = wb;
		this.row = row;
		this.inputStream = inputStream;
		this.outputStream = outputStream;
		this.sCompleteFileName = sCompleteFileName;
		try {
			
			
			
			
			
			//SaveWorkBook(inputStream, outputStream, sCompleteFileName);
		} catch (Exception e) {
			log.info("Error in Cells constructor creation " + e.toString());
		}
		
		
		
	}
	
	public void SetTextFieldValues(String[] sValues) {
		String val="";
		try {
			Row cRow = null;
			try {
				cRow = sheet.getRow(this.row - 1);

			} catch (Exception e) {

			}
			Row row1 = null;
			if (checkIfRowIsEmpty(cRow)) {
				row1 = sheet.createRow(this.row - 1);
			} else {
				row1 = cRow;
			}
			int i=0;
			
			for (String sValu : sValues) {
			
				Cell cell1 = row1.createCell(i);
				cell1.setCellValue(sValu);
				++i;
				
			}	
			SaveWorkBook(inputStream, outputStream, sCompleteFileName);
			
			
		} catch (Exception e) {
			log.info("Error in writing record to excel" + e.toString());
		}
		
	}
	
	public void SetTextFieldValues(String[] sValues,int startColNumber) {
		String val="";
		try {
			Row cRow = null;
			try {
				cRow = sheet.getRow(this.row - 1);

			} catch (Exception e) {

			}
			Row row1 = null;
			if (checkIfRowIsEmpty(cRow)) {
				row1 = sheet.createRow(this.row - 1);
			} else {
				row1 = cRow;
			}
			int i=startColNumber;
			
			for (String sValu : sValues) {
			
				Cell cell1 = row1.createCell(i);
				cell1.setCellValue(sValu);
				++i;
				
			}	
			SaveWorkBook(inputStream, outputStream, sCompleteFileName);
			
			
		} catch (Exception e) {
			log.info("Error in writing record to excel" + e.toString());
		}
		
	}
	public void SetTextFieldValues(String[] sValues, boolean autoSizeColoumns) {
		String val="";
		try {
			Row cRow = null;
			try {
				cRow = sheet.getRow(this.row - 1);

			} catch (Exception e) {

			}
			Row row1 = null;
			if (checkIfRowIsEmpty(cRow)) {
				row1 = sheet.createRow(this.row - 1);
			} else {
				row1 = cRow;
			}
			int i=0;
			
			for (String sValu : sValues) {
			
				Cell cell1 = row1.createCell(i);
				cell1.setCellValue(sValu);
				if(autoSizeColoumns) {
					sheet.autoSizeColumn(i);
				}
				++i;
				
			}	
			SaveWorkBook(inputStream, outputStream, sCompleteFileName);
			
			
		} catch (Exception e) {
			log.info("Error in writing record to excel" + e.toString());
		}
		
	}
	
	private void SaveWorkBook(FileInputStream inputStream, FileOutputStream outputStream, String sCompleteFileName) {
		try {

			if (inputStream != null)
				inputStream.close();
			OutputStream fileOut = new FileOutputStream(sCompleteFileName);
			wb.write(fileOut);
			// wb.close();
			fileOut.close();

		} catch (Exception e) {
			log.info("Error in saving record " + e.toString());
		}

	}

	@SuppressWarnings("deprecation")
	private boolean checkIfRowIsEmpty(Row ROW) {
		if (ROW == null) {
			return true;
		}
		if (ROW.getLastCellNum() <= 0) {
			return true;
		}
		for (int cellNum = ROW.getFirstCellNum(); cellNum < ROW.getLastCellNum(); cellNum++) {
			Cell cell = ROW.getCell(cellNum);
			if (cell != null && cell.getCellTypeEnum() != CellType.BLANK && StringUtils.isNotBlank(cell.toString())) {
				return false;
			}
		}
		return true;
	}
	

	
	
	
}

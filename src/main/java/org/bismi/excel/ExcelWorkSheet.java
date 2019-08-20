package org.bismi.excel;
/**
 * @author Sulfikar Ali Nazar
 *
 */
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class ExcelWorkSheet {
	int colNumber;


	FileInputStream inputStream=null;

	private Logger log = LogManager.getLogger(ExcelWorkSheet.class);
	FileOutputStream outputStream=null;
	int rowNumber;
	String sCompleteFileName=null;
	Sheet sheet; 
	String sheetName = null;
	Workbook wb;
	ExcelWorkSheet() {

	}

	ExcelWorkSheet(Sheet sheet, Logger log, String sheetName, Workbook wb) {

		this.sheet = sheet;
		this.log = log;
		this.sheetName = sheetName;
		this.wb = wb;
	
	}
	protected ExcelWorkSheet(Sheet sheet, String sheetName, Workbook wb,FileInputStream inputStream,FileOutputStream outputStream, String sCompleteFileName) {

		this.sheet = sheet;
		
		this.sheetName = sheetName;
		this.wb = wb;
		this.inputStream=inputStream;
		this.outputStream=outputStream;
		this.sCompleteFileName=sCompleteFileName;
		
	}

	public void Activate() {
				
		this.wb.setActiveSheet(this.wb.getSheetIndex(this.sheet.getSheetName()));
		SaveWorkBook( inputStream, outputStream, sCompleteFileName);
		
	}
	protected ExcelWorkSheet AddSheet(String sSheetName) {
		ExcelWorkSheet shExcel = null;
		try {
			sheet = this.wb.createSheet(sSheetName);
		
			this.sheetName = sSheetName;
			log.info("Created sheet " + sSheetName);
			shExcel = new ExcelWorkSheet(this.sheet, this.log, this.sheetName, this.wb);

		} catch (Exception e) {
			log.info("Error in creating sheet " + sSheetName + e.toString());

		}

		return shExcel;
	}

	protected List<ExcelWorkSheet> AddSheets(String[] strArrSheets) {
		List<ExcelWorkSheet> createdExcelWorkSheets = new ArrayList<>();
		String eShName=null;
		ExcelWorkSheet shExcel = null;
		try {
			
			for (String sSheetName : strArrSheets) {
				sheet = this.wb.createSheet(sSheetName);
				this.sheetName = sSheetName;
				log.info("Created sheet " + sSheetName);
				shExcel = new ExcelWorkSheet(this.sheet, this.log, this.sheetName, this.wb);
				eShName=sSheetName;
				createdExcelWorkSheets.add(shExcel);
			}
			

		} catch (Exception e) {
			log.info("Error in creating sheet " + eShName + e.toString());

		}

		return createdExcelWorkSheets;
	}

	public ExcelCell Cells(int row,int col) {
		ExcelCell cells =null;
		try {
			cells=new ExcelCell(this.sheet,this.wb,row,col,this.inputStream,this.outputStream,this.sCompleteFileName);
			
		} catch (Exception e) {
			log.info("Error Excel cells operation " + e.toString());
		}
		
		return cells;
		
	}

	public boolean ClearContents() {
		
		try {
			
			for (int i = this.sheet.getLastRowNum(); i >= 0; i--) {
				  
				  try {
					  sheet.removeRow(sheet.getRow(i));
					  
					} catch (Exception e) {
						log.info("Empty row removal error " + i);
					}
				  
				}
			
			SaveWorkBook( inputStream, outputStream, sCompleteFileName);
			log.info("Cleared sheet contents successfully " );
			return true;

		} catch (Exception e) {
			log.info("Error in clearing sheet contents " + e.toString());
			return false;
		}
		
		
		
	}
	
	public int getColNumber() {
		return this.sheet.getRow(0).getLastCellNum();
	}
	
	
	public int getRowNumber() {
		return this.sheet.getLastRowNum()+1;
	}
	
	public String getSheetName() {
		return sheetName;
	}
	
	public ExcelRow Rows(int row) {
		ExcelRow rows =null;
		try {
			rows=new ExcelRow(this.sheet,this.wb,row,this.inputStream,this.outputStream,this.sCompleteFileName);
			
		} catch (Exception e) {
			log.info("Error Excel rows operation " + e.toString());
		}
		
		return rows;
		
	}
	
	private void SaveWorkBook(FileInputStream inputStream,FileOutputStream outputStream, String sCompleteFileName ) {
		try {
			
			if (inputStream != null)
				inputStream.close();
			OutputStream fileOut = new FileOutputStream(sCompleteFileName);
			wb.write(fileOut);
			//wb.close();
			fileOut.close();
			
			
		} catch (Exception e) {
			log.info("Error in saving record " + e.toString());
		}
		
		
	}
	
	
	

}

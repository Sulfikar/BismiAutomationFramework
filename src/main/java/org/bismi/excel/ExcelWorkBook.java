package org.bismi.excel;
/**
 * @author Sulfikar Ali Nazar -
 * sulfikar@outlook.com
 *  BismiAutomationFramework - https://bismi.solutions
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;




/**
 * The Class ExcelWorkBook.
 */
public class ExcelWorkBook {
	Workbook wb;
	String excelBookName = "";
	String excelBookPath = "";
	FileInputStream inputStream = null;
	FileOutputStream outputStream = null;
	File xlFile = null;

	List<ExcelWorkSheet> excelWorkSheets = new ArrayList<>();

	/** The log. */
	private Logger log = LogManager.getLogger(ExcelWorkBook.class);

	/** The excel sheets. */
	List<ExcelWorkSheet> excelSheets = new ArrayList<>();

	/**
	 * Instantiates a new excel work book.
	 */
	ExcelWorkBook() {

	}

	ExcelWorkBook(Workbook wb, String excelBookName, String excelBookPath) {
		this.wb = wb;
		this.excelBookName = excelBookName;
		this.excelBookPath = excelBookPath;

	}

	protected boolean CloseWorkBook() {
		try {

			this.wb.close();
			this.wb = null;
			log.info("Closed work book " + this.excelBookName);
			return true;
		} catch (IOException e) {
			log.info("Error in closing work book " + e.toString());
			return false;
		}

	}

	/**
	 * Creates the Excel work book.
	 *
	 * @param sCompleteFileName the s complete file name
	 * @return the excel work book
	 */
	protected ExcelWorkBook CreateWorkBook(String sCompleteFileName) {
		ExcelWorkBook xlWbook = new ExcelWorkBook();// WorkbookFactory
		try {
			if (isValidPath(sCompleteFileName)) {
				this.xlFile = new File(sCompleteFileName);
				this.excelBookName = GetFileName(sCompleteFileName);
				String[] fileExtn = this.excelBookName.split("[.]");
				if (fileExtn[1].equalsIgnoreCase("xlsx")) {
					wb = new XSSFWorkbook();

					OutputStream fileOut = new FileOutputStream(sCompleteFileName);
					Sheet sheet1 = wb.createSheet("Sheet1");
					wb.write(fileOut);
					wb.close();
					fileOut.close();
					log.info("Created file " + this.excelBookName);

				} else if (fileExtn[1].equalsIgnoreCase("xls")) {
					wb = new HSSFWorkbook();
					OutputStream fileOut = new FileOutputStream(sCompleteFileName);
					Sheet sheet1 = wb.createSheet("Sheet1");
					
					wb.write(fileOut);
					wb.close();
					fileOut.close();

					log.info("Created file " + this.excelBookName);
				}
			}

		} catch (Exception e) {
			log.info("Exception occured while creating work book " + e.toString());
		}
		xlWbook = new ExcelWorkBook(this.wb, this.excelBookName, this.excelBookPath);

		return xlWbook;

	}

	public String getExcelBookName() {
		return excelBookName;
	}

	public String getExcelBookPath() {
		return excelBookPath;
	}

	private String GetFileName(String sCompleteFilePath) {
		try {
			if (isValidPath(sCompleteFilePath)) {
				File file = new File(sCompleteFilePath);
				log.info("Returning file name " + file.getName());
				this.excelBookName = file.getName();
				return file.getName();

			}
		} catch (Exception e) {
			log.info("Error in getting file name " + e.toString());

		}

		return "";// return null on error
	}

	/**
	 * Checks if is valid path.
	 *
	 * @param path the path
	 * @return true, if is valid path
	 */
	private boolean isValidPath(String path) {
		try {
			Paths.get(path);
			log.info("Verified path " + path + " is valid");
			File file = new File(path);

			if (!file.isDirectory()) {
				file = file.getParentFile();
				new File(file.getPath()).mkdirs();
				log.info("Created missing directories");
				this.excelBookPath = file.getPath();
			} else {
				new File(file.getPath()).mkdirs();
				this.excelBookPath = file.getPath();
			}
		} catch (InvalidPathException | NullPointerException ex) {
			log.info("Returned error message during -- " + ex.toString());
			return false;
		}
		return true;
	}

	/**
	 * Open workbook.
	 *
	 * @param sCompleteFileName the s complete file name
	 * @return the excel work book
	 */
	protected ExcelWorkBook OpenWorkbook(String sCompleteFileName) {

		ExcelWorkBook xlWbook = null;
		log.info("Opening Excel work book");

		try {

			if (isValidPath(sCompleteFileName)) {
				this.xlFile = new File(sCompleteFileName);
				this.excelBookName = GetFileName(sCompleteFileName);
				inputStream = new FileInputStream(this.xlFile);
				this.wb = WorkbookFactory.create(inputStream);

				xlWbook = new ExcelWorkBook(this.wb, this.excelBookName, this.excelBookPath);

			}

		} catch (Exception e) {
			log.info("Error in opening excel work book" + e.toString());
		}

		return xlWbook;

	}

	public ExcelWorkSheet AddSheet(String sSheetName) {
		ExcelWorkSheet wSheet = new ExcelWorkSheet();
		wSheet.wb = this.wb;
		ExcelWorkSheet tempSheet = wSheet.AddSheet(sSheetName);

		try {
			if (inputStream != null)
				inputStream.close();
			outputStream = new FileOutputStream(this.xlFile);
			this.wb.write(outputStream);
			outputStream.close();

		} catch (Exception e) {
			log.info("Error in creating excel sheet" + e.toString());

		}

		return tempSheet;

	}

	protected Workbook getWb() {
		return wb;
	}

	protected void setWb(Workbook wb) {
		this.wb = wb;
	}

	protected FileInputStream getInputStream() {
		return inputStream;
	}

	protected void setInputStream(FileInputStream inputStream) {
		this.inputStream = inputStream;
	}

	protected FileOutputStream getOutputStream() {
		return outputStream;
	}

	protected void setOutputStream(FileOutputStream outputStream) {
		this.outputStream = outputStream;
	}

	protected File getXlFile() {
		return xlFile;
	}

	protected void setXlFile(File xlFile) {
		this.xlFile = xlFile;
	}

	protected List<ExcelWorkSheet> getExcelWorkSheets() {
		return excelWorkSheets;
	}

	protected void setExcelWorkSheets(List<ExcelWorkSheet> excelWorkSheets) {
		this.excelWorkSheets = excelWorkSheets;
	}

	protected Logger getLog() {
		return log;
	}

	protected void setLog(Logger log) {
		this.log = log;
	}

	protected List<ExcelWorkSheet> getExcelSheets() {
		return excelSheets;
	}

	protected void setExcelSheets(List<ExcelWorkSheet> excelSheets) {
		this.excelSheets = excelSheets;
	}

	protected void setExcelBookName(String excelBookName) {
		this.excelBookName = excelBookName;
	}

	protected void setExcelBookPath(String excelBookPath) {
		this.excelBookPath = excelBookPath;
	}

	public List<ExcelWorkSheet> AddSheets(String[] strArrSheets) {
		List<ExcelWorkSheet> createdExcelWorkSheets = new ArrayList<>();
		
		ExcelWorkSheet wSheet = new ExcelWorkSheet();
		wSheet.wb = this.wb;
		createdExcelWorkSheets = wSheet.AddSheets(strArrSheets);

		try {
			if (inputStream != null)
				inputStream.close();
			outputStream = new FileOutputStream(this.xlFile);
			this.wb.write(outputStream);
			outputStream.close();

		} catch (Exception e) {
			log.info("Error in creating excel sheet" + e.toString());

		}

		return createdExcelWorkSheets;
	}

	public List<ExcelWorkSheet> GetExcelSheets() {
		this.excelSheets.clear();

		int iNumberOfSheets = this.wb.getNumberOfSheets();

		for (int i = 0; i < iNumberOfSheets; i++) {
			String pth= excelBookPath+"\\"+excelBookName;
			ExcelWorkSheet temp = new ExcelWorkSheet(this.wb.getSheetAt(i), this.wb.getSheetAt(i).getSheetName(),
					this.wb,this.inputStream,this.outputStream,pth);
			this.excelSheets.add(temp);
		}
		return excelSheets;
	}

	public ExcelWorkSheet GetExcelSheet(String sSheetName) {
		
		Sheet sheet1=null;
		ExcelWorkSheet shTemp=null;
		try {
			sheet1=this.wb.getSheet(sSheetName);

			if(sheet1 != null)   {
			    int index = this.wb.getSheetIndex(sheet1);
			   // this.wb.removeSheetAt(index);
			    
				String pth= excelBookPath+"\\"+excelBookName;
			    shTemp=new ExcelWorkSheet(this.wb.getSheet(sSheetName), this.wb.getSheet(sSheetName).getSheetName(), this.wb,this.inputStream,this.outputStream,pth);
			    }
			
		} catch (Exception e) {
			log.info("Error in getting sheet " + sSheetName);
		}
		
		
		return shTemp;
	}

	public ExcelWorkSheet GetExcelSheet(int iIndex) {

		Sheet sheet1=null;
		ExcelWorkSheet shTemp=null;
		try {
		
			sheet1=this.wb.getSheetAt(iIndex);
			if(sheet1 != null)   {
			    int index = this.wb.getSheetIndex(sheet1);
			   // this.wb.removeSheetAt(index);
			    String pth= excelBookPath+"\\"+excelBookName;
			    shTemp=new ExcelWorkSheet(this.wb.getSheetAt(iIndex), this.wb.getSheetAt(iIndex).getSheetName(), this.wb,this.inputStream,this.outputStream,pth);
			    }
		} catch (Exception e) {
			log.info("Error in getting sheet index" + iIndex);
		}
		
		return shTemp;//new ExcelWorkSheet(this.wb.getSheetAt(iIndex), this.wb.getSheetAt(iIndex).getSheetName(), this.wb);
	}
	
	
	

}

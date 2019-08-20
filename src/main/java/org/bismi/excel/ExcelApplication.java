package org.bismi.excel;
/**
 * @author Sulfikar Ali Nazar
 *
 */
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



/**
 * The Class ExcelApplication contains All operation related to application
 * excel
 */

public class ExcelApplication {
	
	private Logger log=LogManager.getLogger(ExcelApplication.class);
	
	List<ExcelWorkBook> exlWorkBooks = new ArrayList<>();

	public ExcelApplication() {
		
		
	}

	public List<ExcelWorkBook> getWorkbooks() {

		return exlWorkBooks;

	}

	public int getOpenWorkbookCount() {

		return exlWorkBooks.size();
	}

	public ExcelWorkBook CreateWorkBook(String sCompleteFileName) {

		ExcelWorkBook exlWorkBook = new ExcelWorkBook();
		exlWorkBook.CreateWorkBook(sCompleteFileName);
		return exlWorkBook;

	}

	public ExcelWorkBook OpenWorkbook(String sCompleteFileName) {
		ExcelWorkBook exlWorkBook = new ExcelWorkBook();
		exlWorkBook.OpenWorkbook(sCompleteFileName);
		exlWorkBooks.add(exlWorkBook);
		return exlWorkBook;
	}

	public ExcelWorkBook getWorkbook(int iIndex) {
		ExcelWorkBook tempBook = null;

		try {
			tempBook = this.exlWorkBooks.get(iIndex);
		} catch (Exception e) {
			log.info("Error during work book retrieval " + " - - " + e.toString());
		}

		return tempBook;
	}

	public ExcelWorkBook getWorkbook(String sWorkBookName) {
		ExcelWorkBook tempBook = null;
		boolean fFound = false;
		for (ExcelWorkBook excelWorkBook : this.exlWorkBooks) {

			try {

				if (excelWorkBook.getExcelBookName().equalsIgnoreCase(sWorkBookName)) {
					fFound = true;
					tempBook = excelWorkBook;
					break;
				}

			} catch (Exception e) {
				log.info("Error during work book retrieval " + sWorkBookName + " - - " + e.toString());
			}

		}

		if (!fFound)
			log.info("Work book not opened. Please open work book -- " + sWorkBookName);

		return tempBook;
	}

	public void CloseWorkBook(int iIndex) {
		ExcelWorkBook tempBook = null;
		try {
			tempBook = this.exlWorkBooks.get(iIndex);
			tempBook.CloseWorkBook();
			this.exlWorkBooks.remove(iIndex);
		} catch (Exception e) {
			log.info("Error during work book retrieval " + " - - " + e.toString());
		}

	}

	public void CloseWorkBook(String sWorkBookName) {
		int iIndex = -1;
		boolean fFound = false;
		for (ExcelWorkBook excelWorkBook : this.exlWorkBooks) {
			++iIndex;
			try {

				if (excelWorkBook.getExcelBookName().equalsIgnoreCase(sWorkBookName)) {
					excelWorkBook.CloseWorkBook();

					fFound = true;
					break;
				}

			} catch (Exception e) {

			}

		}

		if (iIndex >= 0 && fFound == true) {
			this.exlWorkBooks.remove(iIndex);

		} else {
			log.info("Work book " + sWorkBookName + " not available for close operation");
		}

	}

	/**
	 * Close all work books.
	 */
	public void CloseAllWorkBooks() {

		for (ExcelWorkBook excelWorkBook : this.exlWorkBooks) {

			try {
				excelWorkBook.CloseWorkBook();

			} catch (Exception e) {
				log.info("Work Book " + excelWorkBook.excelBookName + " is already closed");
			}

		}

		this.exlWorkBooks.clear();

	}

}

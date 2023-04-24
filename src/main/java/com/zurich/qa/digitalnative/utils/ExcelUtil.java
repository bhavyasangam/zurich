package com.zurich.qa.digitalnative.utils;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

public class ExcelUtil {
	public static Object[][] readExcelData(String excelFilePath, String sheetName) throws IOException {
		Object[][] dataTable = null;
		try {
			FileInputStream xlfile = new FileInputStream(excelFilePath);
			XSSFWorkbook xlwb = new XSSFWorkbook(xlfile);
			//XSSFSheet xlSheet = xlwb.getSheetAt(0);

			XSSFSheet xlSheet  = xlwb.getSheet(sheetName);

			// Get the number of rows and columns
			int numRows = xlSheet.getLastRowNum() + 1;
			int numCols = xlSheet.getRow(0).getLastCellNum();

			// Create double array data table - rows x cols
			// We will return this data table
			dataTable = new String[numRows][numCols];

			// For each row, create a HSSFRow, then iterate through the "columns"
			// For each "column" create an HSSFCell to grab the value at the specified cell
			// (i,j)
			for (int i = 0; i < numRows; i++) {
				XSSFRow xlRow = xlSheet.getRow(i);
				for (int j = 0; j < numCols; j++) {
					XSSFCell xlCell = xlRow.getCell(j);
					dataTable[i][j] = xlCell.toString();
				}
			}
		} catch (IOException e) {
			System.out.println("ERROR FILE HANDLING " + e.toString());
		}
		return dataTable;
	}

	@DataProvider(name = "getData")
	public static Object[][] getData(ITestContext context) {
		Object[][] excelData = null;
		try {

			 String filename = context.getCurrentXmlTest().getParameter("ReadExcelPath");
			 String sheet = context.getCurrentXmlTest().getParameter("DataSheetName"); 
			 String filePath=System.getProperty("user.dir")+"\\src\\test\\resources\\TestData\\"+filename+".xlsx";
			 //String filePath = System.getProperty("user.dir") + "\\src\\test\\resources\\TestData\\Transition_Plan.xlsx"; 
			 //String sheet = "teset";
			 excelData = readExcelData(filePath, sheet);
		} catch (Exception exception) {
		}
		return excelData;
	}
}

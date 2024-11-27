package testComponents;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;


public class ExcelSheetHandler {
	public ExcelSheetHandler() throws IOException {
		super();
		// TODO Auto-generated constructor stub
	}
	static FileInputStream fis;
	private static XSSFWorkbook ExcelWBook;

	public static Map<String, String> getDataInMap(String SheetName, String testcaseId) throws Exception {

		Map<String, String> TestDatainMap = new TreeMap<String, String>();

		// String testDataFile=TestDataPath;
		String query = null;

		if (SheetName.equals("Login"))
			query = String.format("SELECT * from %s where ENVIRONMENT = '%s'", SheetName, testcaseId);
		else
			query = String.format("SELECT * from %s where TCID = '%s'", SheetName, testcaseId);

		Fillo fillo = new Fillo();
		Connection conn = null;
		Recordset recodset = null;
		try {
			conn = fillo.getConnection(System.getProperty("user.dir") + "/src/test/java/testData/TestData.xlsx");
			recodset = conn.executeQuery(query);
			while (recodset.next()) {
				for (String field : recodset.getFieldNames()) {
					TestDatainMap.put(field, recodset.getField(field));

				}
			}
		} catch (FilloException e) {
			e.printStackTrace();
			throw new Exception("Test Data can't found");
		}
		conn.close();

		return TestDatainMap;

	}

	//Writing to excel Using fillo

	public static void writeExcel(String excelFilePath, String sheetName, String columnNameToWrite, String rowIdColumn, String rowIdValue, String valueToWrite) {
		Fillo fillo = new Fillo();
		Connection connection = null;

		try {
			connection = fillo.getConnection(excelFilePath);

			// Update the specific cell
			String strQuery = String.format("UPDATE %s SET %s='%s' WHERE %s='%s'",
					sheetName, columnNameToWrite, valueToWrite, rowIdColumn, rowIdValue);
			connection.executeUpdate(strQuery);

			System.out.println("Successfully updated the Excel file.");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
	}
}

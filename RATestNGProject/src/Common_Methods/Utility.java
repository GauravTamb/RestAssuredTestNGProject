package Common_Methods;

import java.io.File;
import java.io.FileWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Utility {

	public static ArrayList<String> readExcelData(String SheetName, String TestCase) throws IOException {

		ArrayList<String> arrayData = new ArrayList<String>();

		// Step 1 : Fetch the Java project name and location

		String projectDir = System.getProperty("user.dir");
		// System.out.println("Current project Directory Is :" + projectDir);

		// Step 2 : Create an object of File Input Stream to locate the excel file

		FileInputStream fis = new FileInputStream(projectDir + "\\DataFiles\\Input_Data.xlsx");

		// Step 3 : Create an object of XSSFWorkbook to open the excel file

		// Step 3.1 : Fetch the count of sheets
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		int count = wb.getNumberOfSheets();
		// System.out.println("Count of sheets is : " + count);

		// Step 4 : Access the desired sheet
		for (int i = 0; i < count; i++) {
			if (wb.getSheetName(i).equals(SheetName)) {
				// System.out.println("Sheet at index " + i + " : " + wb.getSheetName(i));
				// Step 4.1 : Access the row data from sheet
				XSSFSheet datasheet = wb.getSheetAt(i);
				Iterator<Row> rows = datasheet.iterator();
				String testcasefound = "False";
				while (rows.hasNext()) {

					Row datarows = rows.next();

					String tcname = datarows.getCell(0).getStringCellValue();

					if (tcname.equals(TestCase)) {
						testcasefound = "True";
						// Step 4.2 : Fetch the cell data from row

						Iterator<Cell> cellvalues = datarows.cellIterator();
						while (cellvalues.hasNext()) {
							Cell Cell = cellvalues.next();
							String testdata = "";

							// Handle multiple data type method number 1
							/*
							 * try { testdata = Cell.getStringCellValue(); } catch(IllegalStateException e)
							 * { double inttestData = Cell.getNumericCellValue(); testdata =
							 * String.valueOf(inttestData); }
							 */

							// Handle multiple data type method number 2

							CellType dataType = Cell.getCellType();
							if (dataType.toString().equals("STRING")) {
								testdata = Cell.getStringCellValue();
							} else if (dataType.toString().equals("NUMERIC")) {
								double inttestData = Cell.getNumericCellValue();
								testdata = String.valueOf(inttestData);
							}

							System.out.println(testdata);
							// Step 4.3 : Add data in array list
							arrayData.add(testdata);
						}
						break;
					}
				}
				if (testcasefound.equals("False")) {
					System.out.println(TestCase + " test case not found in sheet:" + wb.getSheetName(i));
				}
				break;
			} else {
				System.out.println(SheetName + " sheet not found in file Input_Data.xlsx at index : " + i);
			}

		}
		wb.close();
		return arrayData;
	}

	public static void evidenceFileCreator(String Filename, File FileLocation, String endpoint, String RequestBody,
			String ResHeader, String ResponseBody) throws IOException {
		// Step 1 : Create and Open the file
		File newTextFile = new File(FileLocation + "\\" + Filename + ".txt");
		System.out.println("File create with name: " + newTextFile.getName());

		// Step 2 : Write data
		FileWriter writedata = new FileWriter(newTextFile);
		writedata.write("Endpoint is :\n" + endpoint + "\n\n");
		writedata.write("Request body is :\n" + RequestBody + "\n\n");
		writedata.write("Response header date is : \n" + ResHeader + "\n\n");
		writedata.write("Response body is : \n" + ResponseBody);

		// Step 3 : Save and close
		writedata.close();

	}

	public static File CreateLogDirectory(String dirName) {

		// Step 1 : Fetch the Java project name and location

		String projectDir = System.getProperty("user.dir");
		// System.out.println("Current project Directory Is :" + projectDir);

		// Step 2 : Verify weather the directory in variable dirName exists inside the
		// projectDir and act accordingly

		File directory = new File(projectDir + "\\" + dirName);

		if (directory.exists()) {
			System.out.println(directory + " , already exists");
		} else {
			System.out.println(directory + " , doesnt exists , hence creating it");
			directory.mkdir();
			System.out.println(directory + " , created");
		}

		return directory;
	}

	public static String testLogName(String Name) {
		LocalTime currentTime = LocalTime.now();
		String currentstringTime = currentTime.toString().replaceAll(":", "");
		String TestLogName = "Test_Case_1" + currentstringTime;
		return TestLogName;
	}

}

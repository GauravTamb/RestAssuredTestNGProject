package Common_Methods;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;

public class Utility1 {

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

	public static String testLogName(String Name) {
		LocalTime currentTime = LocalTime.now();
		String currentstringTime = currentTime.toString().replaceAll(":", "");
		String TestLogName = "Test_Case_1" + currentstringTime;
		return TestLogName;
	}

	public static File createFileDir(String dirName) {

		File folder = new File(dirName);

		if (!folder.exists()) {
			// Create the folder
			boolean success = folder.mkdir();

			// Check if folder creation was successful
			if (success) {
				System.out.println("Folder created successfully!");
			} else {
				System.err.println("Failed to create folder!");
			}
		} else {
			System.out.println("Folder already exists!");
		}

		return folder;

	}

}

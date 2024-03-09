package Repository;

import java.io.IOException;
import java.util.ArrayList;

import Common_Methods.Utility;

public class RequestBody extends Environment {

	public static String req_post_tc(String TestCaseName) throws IOException {
		ArrayList<String> Data = Utility.readExcelData("Post_API", TestCaseName);
		String key_name = Data.get(1);
		String value_name = Data.get(2);
		String key_job = Data.get(3);
		String value_job = Data.get(4);
		String req_body = "{\r\n" + "    \"" + key_name + "\": \"" + value_name + "\",\r\n" + "    \"" + key_job
				+ "\": \"" + value_job + "\"\r\n" + "}";

		return req_body;
	}

}

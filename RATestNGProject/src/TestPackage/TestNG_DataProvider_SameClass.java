package TestPackage;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import Common_Methods.API_Trigger;
import Common_Methods.Utility;
import Repository.RequestBody;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;

public class TestNG_DataProvider_SameClass {

	String requestBody;
	String Endpoint;
	File dir_name;
	Response response;

	@DataProvider()
	public Object[][] requestBody(){
		return new Object[][]
				{
			         {"morpheus","leader"},
			         {"jayesh","qa"}
				};
	}

	@BeforeTest
	public void testSetUp() throws IOException {
		System.out.println("Before Test Method Called");
		dir_name = Utility.CreateLogDirectory("Post_API_Logs");
		Endpoint = RequestBody.Hostname() + RequestBody.Resource();
	}

	@Test(dataProvider = "requestBody" , description = "Data_Provider_Same_Class_Test")
	public void validator(String Req_name , String Req_job) throws IOException {
		
		requestBody = "{\r\n"
				+ "    \"name\": \""+Req_name+"\",\r\n"
				+ "    \"job\": \""+Req_job+"\"\r\n"
				+ "}";

		System.out.println("Test Method Called");
		response = API_Trigger.Post_trigger(RequestBody.HeaderName(), RequestBody.HeaderValue(), requestBody, Endpoint);

		int statuscode = response.statusCode();

		ResponseBody res_body = response.getBody();
		String res_name = res_body.jsonPath().getString("name");
		String res_job = res_body.jsonPath().getString("job");
		String res_id = res_body.jsonPath().getString("id");
		String res_createdAt = res_body.jsonPath().getString("createdAt");
		res_createdAt = res_createdAt.substring(0, 11);

		// Set the expected results
		JsonPath jsp_req = new JsonPath(requestBody);
		String req_name = jsp_req.getString("name");
		String req_job = jsp_req.getString("job");

		LocalDateTime currentdate = LocalDateTime.now();
		String expecteddate = currentdate.toString().substring(0, 11);

		// Validate the response parameters
		Assert.assertEquals(response.statusCode(), 201);
		Assert.assertEquals(res_name, req_name);
		Assert.assertEquals(res_job, req_job);
		Assert.assertNotNull(res_id);
		Assert.assertEquals(res_createdAt, expecteddate);
		
		//Evidence Creation
		Utility.evidenceFileCreator(Utility.testLogName("Data_Provider_Same_Class_Evidence"), dir_name, Endpoint, requestBody,
				response.getHeader("Date"), response.getBody().asString());
	}

	@AfterTest
	public void evidenceCreator()  {
		System.out.println("Test Execution Completed");
	
	}

}


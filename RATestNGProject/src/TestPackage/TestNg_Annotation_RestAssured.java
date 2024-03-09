package TestPackage;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Common_Methods.API_Trigger;
import Common_Methods.Utility;
import Repository.RequestBody;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;

public class TestNg_Annotation_RestAssured {

	String requestBody;
	String Endpoint;
	File dir_name;
	Response response;

	@BeforeTest
	public void testSetUp() throws IOException {
		System.out.println("Before Test Method Called");
		dir_name = Utility.CreateLogDirectory("Post_API_Logs");
		requestBody = RequestBody.req_post_tc("Post_TC1");
		Endpoint = RequestBody.Hostname() + RequestBody.Resource();
	}

	@Test(description = "Validate the response body parameters of Post API Test Case 1")
	public void validator() {

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
	}

	@AfterTest
	public void evidenceCreator() throws IOException {
		System.out.println("After Test Method Called");
		Utility.evidenceFileCreator(Utility.testLogName("Test_Case_1"), dir_name, Endpoint, requestBody,
				response.getHeader("Date"), response.getBody().asString());
	}

}

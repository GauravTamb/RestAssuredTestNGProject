package TestPackage;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.testng.Assert;
import org.testng.annotations.Test;

import Common_Methods.API_Trigger;
import Common_Methods.Utility;
import Repository.RequestBody;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;

public class Test_Case_6 extends RequestBody {
 @Test
	public static void executor() throws ClassNotFoundException, IOException {

		File dir_name = Utility.CreateLogDirectory("Post_API_Logs");

		String requestBody = RequestBody.req_post_tc("Post_TC6");
		String Endpoint = RequestBody.Hostname() + RequestBody.Resource();
		int statuscode=0;

		for (int i = 0; i < 5; i++) {
			Response response = API_Trigger.Post_trigger(RequestBody.HeaderName(), RequestBody.HeaderValue(),
					requestBody, Endpoint);

			statuscode = response.statusCode();

			if (statuscode == 201) {
				Utility.evidenceFileCreator(Utility.testLogName("Test_Case_6"), dir_name, Endpoint, requestBody,
						response.getHeader("Date"), response.getBody().asString());
				validator(response, requestBody);
				break;
			}
			else {
				System.out.println("Expected status code is not found in current iteration :" +i+ " hence retrying");
			}

		}
		
		if (statuscode!=201) {
			System.out.println("Expected status code not found even after 5 retries hence failing the test case");
			Assert.assertEquals(statuscode, 200);
		}

	}

	public static void validator(Response response, String requestBody) {
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

}
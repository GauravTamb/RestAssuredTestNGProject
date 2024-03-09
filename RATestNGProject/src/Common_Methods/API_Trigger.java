package Common_Methods;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class API_Trigger {

	public static Response Post_trigger(String HeaderName, String HeaderValue, String reqbody, String Endpoint) {

		RequestSpecification requestSpec = RestAssured.given();

		requestSpec.header(HeaderName, HeaderValue);

		requestSpec.body(reqbody);

		Response response = requestSpec.post(Endpoint);

		return response;

	}

}

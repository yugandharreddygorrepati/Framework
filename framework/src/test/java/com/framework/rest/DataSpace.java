package com.framework.rest;
import java.util.HashMap;
import java.util.LinkedHashMap;
import com.framework.lib.RestAPI;
import io.restassured.response.Response;
public class DataSpace
{
	public static Response unPublish(String endpoint, String reqBody, String cookie, String token)
	{
		HashMap<String, String> headers = new LinkedHashMap<String, String>();
		headers.put("X-CSRF-TOKEN", token);
		headers.put("cookie", cookie);
		headers.put("Content-Type", "application/xml");
		Response response = RestAPI.post(endpoint, reqBody, null, headers);
		System.out.println("DataSpace unPublish --Response Code" + response.getStatusCode());
		return response;
	}
	public static Response publish(String endpoint, String reqBody, String cookie, String token)
	{
		HashMap<String, String> headers = new LinkedHashMap<String, String>();
		headers.put("X-CSRF-TOKEN", token);
		headers.put("cookie", cookie);
		headers.put("Content-Type", "application/xml");
		Response response = RestAPI.post(endpoint, reqBody, null, headers);
		System.out.println("DataSpace publish --Response Code" + response.getStatusCode());
		return response;
	}
}

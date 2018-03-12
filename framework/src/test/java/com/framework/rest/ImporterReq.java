package com.framework.rest;
import java.util.HashMap;
import java.util.LinkedHashMap;
import com.framework.lib.RestAPI;
import io.restassured.response.Response;
public class ImporterReq
{
	public static Response executeImport(String endpoint, String cookie, String token, String reqBody)
	{
		HashMap<String, String> headers = new LinkedHashMap<String, String>();
		headers.put("X-CSRF-TOKEN", token);
		headers.put("cookie", cookie);
		headers.put("Content-Type", "application/xml");
		headers.put("Accept", "application/xml");
		headers.put("Connection", "keep-alive");
		Response response = RestAPI.post(endpoint, reqBody, null, headers);
		System.out.println("ExecuteImport--Response " + response.getBody().asString());
		return response;
	}
}

package com.framework.rest;
import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;
import com.framework.lib.RestAPI;
import io.restassured.response.Response;
public class AnalyticReq
{
	public static int createAnalytic(String endpoint, String cookie, String token, String analyticFilePath)
	{
		File analyticBodyxml = new File(analyticFilePath);
		HashMap<String, String> headers = new LinkedHashMap<String, String>();
		HashMap<String, String> parameters = new LinkedHashMap<String, String>();
		parameters.put("view", "xml");
		parameters.put("new", "true");
		headers.put("X-CSRF-TOKEN", token);
		headers.put("Cookie", cookie);
		headers.put("Content-Type", "application/xml");
		int code = RestAPI.put(endpoint, analyticBodyxml, parameters, headers);
		System.out.println("Analytic Creation --Response Code" + code);
		return code;
	}
	public static Response buildAnalytic(String endpoint, String analyticName, String cookie, String token)
	{
		String analyticBuildOp = "<ANALYTIC name=\"" + analyticName + "\" op=\"build\"/>";
		System.out.println("build op " + analyticBuildOp);
		HashMap<String, String> headers = new LinkedHashMap<String, String>();
		headers.put("X-CSRF-TOKEN", token);
		headers.put("Cookie", cookie);
		headers.put("Accept", "application/xml");
		headers.put("Content-Type", "application/xml");
		headers.put("Connection", "keep-alive");
		// headers.put("Referer", "https://10.105.56.38:8443/ims/");
		// headers.put("Accept-Language", "en-US,en;q=0.5");
		Response response = RestAPI.postWithResponse(endpoint, analyticBuildOp, null, headers);
		return response;
	}
	public static int deleteAnalytic(String endpoint, String cookie, String token)
	{
		HashMap<String, String> headers = new LinkedHashMap<String, String>();
		headers.put("X-CSRF-TOKEN", token);
		headers.put("Cookie", cookie);
		headers.put("Content-Type", "application/xml");
		int code = RestAPI.delete(endpoint, null, headers);
		System.out.println("Analytic Delete --Response Code" + code);
		return code;
	}
}

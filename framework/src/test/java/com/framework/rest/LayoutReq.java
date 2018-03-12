package com.framework.rest;
import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;
import com.framework.lib.RestAPI;
public class LayoutReq
{
	public static int createLayout(String endpoint, String cookie, String token, String layoutFilePath)
	{
		File layoutxmlPath = new File(layoutFilePath);
		HashMap<String, String> parameters = new LinkedHashMap<String, String>();
		HashMap<String, String> headers = new LinkedHashMap<String, String>();
		parameters.put("view", "xml");
		parameters.put("new", "true");
		headers.put("X-CSRF-TOKEN", token);
		headers.put("Cookie", cookie);
		headers.put("Content-Type", "application/xml");
		int code = RestAPI.put(endpoint, layoutxmlPath, parameters, headers);
		System.out.println("Create Layout --Response Code" + code);
		return code;
	}
	public static int deleteLayout(String endpoint, String cookie, String token)
	{
		HashMap<String, String> headers = new LinkedHashMap<String, String>();
		headers.put("X-CSRF-TOKEN", token);
		headers.put("Cookie", cookie);
		headers.put("Content-Type", "application/xml");
		int code = RestAPI.delete(endpoint, null, headers);
		System.out.println("Delete Layout  --Response Code" + code);
		return code;
	}
}

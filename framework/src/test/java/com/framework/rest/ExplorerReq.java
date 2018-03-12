package com.framework.rest;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import com.framework.lib.RestAPI;
public class ExplorerReq
{
	public static int createExplorer(String endpoint, String cookie, String token, String explorerFilePath)
	{
		File explorerBodyxml = new File(explorerFilePath);
		HashMap<String, String> parameters = new LinkedHashMap<String, String>();
		HashMap<String, String> headers = new LinkedHashMap<String, String>();
		parameters.put("view", "xml");
		parameters.put("new", "true");
		headers.put("X-CSRF-TOKEN", token);
		headers.put("Cookie", cookie);
		headers.put("Content-Type", "application/xml");
		int code = RestAPI.put(endpoint, explorerBodyxml, parameters, headers);
		System.out.println("CreateExplorer --Response Code" + code);
		return code;
	}
	public static int deleteExplorer(String endpoint, String cookie, String token)
	{
		HashMap<String, String> headers = new LinkedHashMap<String, String>();
		headers.put("X-CSRF-TOKEN", token);
		headers.put("Cookie", cookie);
		headers.put("Content-Type", "application/xml");
		List<HashMap<String, String>> paramsAndHeaders = new ArrayList<HashMap<String, String>>();
		paramsAndHeaders.add(headers);
		int code = RestAPI.delete(endpoint, null, headers);
		System.out.println("Delete Explorer --Response Code" + code);
		return code;
	}
}

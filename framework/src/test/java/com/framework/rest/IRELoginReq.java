package com.framework.rest;
import java.util.HashMap;
import java.util.LinkedHashMap;
import com.framework.lib.RestAPI;
import io.restassured.response.Response;
/**
 * @author YugandharReddyGorrep
 *
 */
public class IRELoginReq
{
	public static String token;
	public static String cookie;
	/**
	 * @param eNDPOINT2
	 * @param uSERNAME2
	 * @param pASSWORD2
	 */
	public static void initSession(String endpoint, String userName, String password)
	{
		HashMap<String, String> parameters = new LinkedHashMap<String, String>();
		parameters.put("j_username", userName);
		parameters.put("j_password", password);
		Response response = RestAPI.postWithResponse(endpoint, "", parameters, null);
		token = response.header("X-CSRF-TOKEN");
		String cookies[] = response.header("Set-Cookie").split(";");
		cookie = cookies[0];
		System.out.println();
		System.out.println();
	}
}
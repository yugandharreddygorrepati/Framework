/**
 * 
 */
package com.framework.rest;
import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;
import com.framework.lib.RestAPI;
import io.restassured.response.Response;
/**
 * @author YugandharReddyGorrep
 *
 */
public class KibanaReq
{
	public static int getExpNoOfRec(String endpoint_Kibana, String kibanaFilePath, String analyticName)
	{
		File kibanaFilReqBody = new File(kibanaFilePath);
		analyticName = analyticName.toLowerCase();
		HashMap<String, String> headers = new LinkedHashMap<String, String>();
		headers.put("Content-Type", "application/json");
		headers.put("kbn-version", "6.0.0");
		HashMap<String, String> queryParamas = new LinkedHashMap<String, String>();
		queryParamas.put("path", "ire-ire-" + analyticName + "-analytic/_search");
		queryParamas.put("method", "POST");
		Response response = RestAPI.postWithResponse(endpoint_Kibana, kibanaFilReqBody, queryParamas, headers);
		String resposneStr = response.getBody().asString();
		String patt = ",\"max_score\"";
		int indexFrCnt = resposneStr.indexOf(patt);
		System.out.println("count " + resposneStr.substring(indexFrCnt - 1, indexFrCnt));
		return Integer.parseInt(resposneStr.substring(indexFrCnt - 1, indexFrCnt));
	}
	/**
	 * @param eNDPOINT_KIBANA
	 * @param analyticNAME
	 */
	public static Response deleteAnalytic(String endpoint_Kibana, String analyticNAME)
	{
		analyticNAME = analyticNAME.toLowerCase();
		HashMap<String, String> headers = new LinkedHashMap<String, String>();
		// headers.put("Content-Type", "application/json");
		headers.put("kbn-version", "6.0.0");
		HashMap<String, String> queryParamas = new LinkedHashMap<String, String>();
		queryParamas.put("path", "ire-ire-" + analyticNAME + "-analytic");
		queryParamas.put("method", "DELETE");
		Response response = RestAPI.post(endpoint_Kibana, "", queryParamas, headers);
		System.out.println("Kibana Delete Analytic Index  --Response Code" + response.getStatusCode());
		return response;
	}
	/**
	 * @return
	 */
	public static Response getIndexCreationCode(String endpoint_Kibana, String analyticNAME)
	{
		try
		{
			Thread.sleep(8000);// sync time for index to reflect in kibana
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		analyticNAME = analyticNAME.toLowerCase();
		HashMap<String, String> headers = new LinkedHashMap<String, String>();
		headers.put("kbn-version", "6.0.0");
		HashMap<String, String> queryParamas = new LinkedHashMap<String, String>();
		queryParamas.put("path", "ire-ire-" + analyticNAME + "-analytic");
		queryParamas.put("method", "GET");
		Response response = RestAPI.post(endpoint_Kibana, "", queryParamas, headers);
		System.out.println("Kibana Create Analytic Index  --Response Code" + response.getStatusCode());
		return response;
	}
}

/**
 * 
 */
package com.framework.lib;
import java.io.File;
import java.util.HashMap;
import io.restassured.RestAssured;
import io.restassured.response.Response;
/**
 * @author YugandharReddyGorrep
 *
 */
public class RestAPI
{
	/**
	 * @param baseurl
	 * @param resourcePath
	 * @param reqBody
	 * @param headers
	 * @param parameters
	 * @return responseCode
	 */
	/*
	 * put request accepts body type file
	 */
	public static int put(String endpoint, File reqBody, HashMap<String, String> parameters,
			HashMap<String, String> headers)
	{
		RestAssured.useRelaxedHTTPSValidation();
		RestAssured.baseURI = endpoint;
		if (reqBody.exists())
		{
			if (parameters == null)
			{
				Response response = RestAssured.given().headers(headers).body(reqBody).when().put();
				return response.getStatusCode();
			} else
			{
				Response response = RestAssured.given().params(parameters).headers(headers).body(reqBody).when().put();
				return response.getStatusCode();
			}
		} else
		{
			System.out.println("request body not existed");
			return 0;
		}
	}
	/**
	 * @param endpoint
	 * @param reqBody
	 * @param headers
	 * @param headers
	 * @return responseCode
	 */
	/*
	 * put request accepts body type String
	 */
	public static int put(String endpoint, String reqBody, HashMap<String, String> params,
			HashMap<String, String> headers)
	{
		RestAssured.useRelaxedHTTPSValidation();
		RestAssured.baseURI = endpoint;
		if (!reqBody.isEmpty())
		{
			if (params == null)
			{
				Response response = RestAssured.given().headers(headers).body(reqBody).when().put();
				return response.getStatusCode();
			} else
			{
				Response response = RestAssured.given().params(params).headers(headers).body(reqBody).when().put();
				return response.getStatusCode();
			}
		} else
		{
			System.out.println("request body is empty");
		}
		return 0;
	}
	/*
	 * post request accepts body type String
	 */
	/**
	 * @param endpoint
	 * @param reqBody
	 * @param headers
	 * @param queryparams
	 * @return responseCode
	 */
	public static Response post(String endpoint, String reqBody, HashMap<String, String> queryparams,
			HashMap<String, String> headers)
	{
		System.out.println("-----");
		System.out.println("endpoint " + endpoint);
		RestAssured.useRelaxedHTTPSValidation();
		RestAssured.baseURI = endpoint;
		if (!reqBody.isEmpty())
		{
			if (queryparams == null)
			{
				Response response = RestAssured.given().headers(headers).body(reqBody).when().post();
				System.out.println("message body" + response.getBody().asString());
				return response;
			} else
			{
				Response response = RestAssured.given().queryParams(queryparams).headers(headers).body(reqBody).when()
						.post();
				System.out.println("message body" + response.getBody().asString());
				return response;
			}
		} else
		{
			if (queryparams == null)
			{
				Response response = RestAssured.given().headers(headers).when().post();
				System.out.println("message body" + response.getBody().asString());
				return response;
			} else
			{
				// System.out.println(endpoint + headers.get("kbn-version"));
				Response response = RestAssured.given().queryParams(queryparams).headers(headers).when().post();
				System.out.println("message body" + response.getBody().asString());
				return response;
			}
		}
	}
	/*
	 * post request accepts body type String
	 */
	/**
	 * @param endpoint
	 * @param reqBody
	 * @param headers
	 * @param queryparams
	 * @return response
	 */
	public static Response postWithResponse(String endpoint, String reqBody, HashMap<String, String> queryparams,
			HashMap<String, String> headers)
	{
		RestAssured.useRelaxedHTTPSValidation();
		RestAssured.baseURI = endpoint;
		if (!reqBody.isEmpty())
		{
			if (queryparams == null)
			{
				Response response = RestAssured.given().headers(headers).body(reqBody).when().post();
				System.out.println("message body" + response.getBody().asString());
				return response;
			} else
			{
				Response response = RestAssured.given().queryParams(queryparams).headers(headers).body(reqBody).when()
						.post();
				System.out.println("message body" + response.getBody().asString());
				return response;
			}
		} else
		{
			if (queryparams == null)
			{
				Response response = RestAssured.given().headers(headers).when().post();
				System.out.println("message body" + response.getBody().asString());
				return response;
			}
			if (headers == null)
			{
				Response response = RestAssured.given().queryParams(queryparams).when().post();
				System.out.println("message body" + response.getBody().asString());
				return response;
			} else
			{
				System.out.println(endpoint + headers.get("kbn-version"));
				Response response = RestAssured.given().queryParams(queryparams).headers(headers).when().post();
				System.out.println("message body" + response.getBody().asString());
				return response;
			}
		}
	}
	/*
	 * post request returns the response
	 */
	/**
	 * @param endpoint
	 * @param queryParams
	 * @param headers
	 * @param reqBody
	 * @return response
	 */
	public static Response postWithResponse(String endpoint, File reqBody, HashMap<String, String> queryParams,
			HashMap<String, String> headers)
	{
		RestAssured.useRelaxedHTTPSValidation();
		Response response = null;
		RestAssured.baseURI = endpoint;
		if (reqBody == null)
		{
			if (queryParams == null)
			{
				response = RestAssured.given().headers(headers).when().post();
			} else if (headers == null)
			{
				response = RestAssured.given().queryParams(queryParams).when().post();
			} else
				response = RestAssured.given().queryParams(queryParams).headers(headers).when().post();
			return response;
		} else
		{
			if (queryParams == null)
			{
				response = RestAssured.given().headers(headers).when().body(reqBody).post();
			}
			{
				response = RestAssured.given().queryParams(queryParams).headers(headers).when().body(reqBody).post();
			}
			return response;
		}
	}
	/*
	 * Delete the resource
	 */
	/**
	 * @param endpoint
	 * @param parameters
	 * @param headers
	 * @return responseCode
	 */
	public static int delete(String endpoint, HashMap<String, String> parameters, HashMap<String, String> headers)
	{
		RestAssured.useRelaxedHTTPSValidation();
		RestAssured.baseURI = endpoint;
		if (parameters == null)
		{
			Response response = RestAssured.given().headers(headers).when().delete();
			return response.getStatusCode();
		} else
		{
			Response response = RestAssured.given().params(parameters).headers(headers).when().delete();
			return response.getStatusCode();
		}
	}
	/*
	 * Get the resource details
	 */
	/**
	 * @param endpoint
	 * @param parameters
	 * @param headers
	 * @return responseCode
	 */
	public static int get(String endpoint, HashMap<String, String> queryParameters, HashMap<String, String> parameters,
			HashMap<String, String> headers)
	{
		RestAssured.useRelaxedHTTPSValidation();
		RestAssured.baseURI = endpoint;
		if (queryParameters == null && parameters == null)
		{
			Response response = RestAssured.given().headers(headers).when().get();
			return response.getStatusCode();
		} else if (queryParameters == null)
		{
			Response response = RestAssured.given().params(parameters).headers(headers).when().get();
			return response.getStatusCode();
		} else if (parameters == null)
		{
			Response response = RestAssured.given().queryParams(queryParameters).headers(headers).when().get();
			return response.getStatusCode();
		} else
		{
			System.out.println("headers cannot be null");
			return 0;
		}
	}
}

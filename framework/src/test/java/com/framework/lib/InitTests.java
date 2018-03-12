package com.framework.lib;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
/**
 * @author YugandharReddy
 */
public class InitTests
{
	public static Properties props = new Properties();
	/**
	 * @description:Initialization OS,version,browser and url details.
	 * 
	 * @throws Exception
	 */
	public static String OS_VERSION = "";
	public static String ENDPOINT_LOGIN = "";
	public static String CaptureScreenshotOnFail = "";
	public static String OS_NAME = "";
	public static String BROWSER_TYPE = "";
	public static String BASEURL = "";
	
	public static String USERNAME = "";
	public static String PASSWORD = "";
	public static String SEARCH_TEXT = "";
	public InitTests()
	{
		try
		{
			FileReader file = new FileReader(
					Thread.currentThread().getContextClassLoader().getResource("testdata.properties").getPath());
			props.load(file);
			BASEURL = props.getProperty("baseurl");
		
			USERNAME = props.getProperty("username");
			PASSWORD = props.getProperty("password");
			BROWSER_TYPE = props.getProperty("browser");
			OS_VERSION = props.getProperty("os_version");
			OS_NAME = props.getProperty("os_name");
			
			CaptureScreenshotOnFail = props.getProperty("CaptureScreenshotOnFail");
			ENDPOINT_LOGIN = props.getProperty("endpoint_Login");
			SEARCH_TEXT= props.getProperty("search_Txt");
			
		} catch (IOException ex)
		{
			ex.printStackTrace();
		}
	}
	public static String getPropValue(String key)
	{
		return props.getProperty(key);
	}
}

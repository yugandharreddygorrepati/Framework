package com.framework.lib;
import static com.framework.lib.DateUtils.getCurrTimeStamp;
import java.io.File;
import java.io.IOException;
import org.openqa.selenium.Platform;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
/**
 * @author YugandharReddyGorrep
 *
 */
public class MyExtentReports
{
	/**
	 * Setting up the platform details
	 * 
	 * @param browserType
	 * @param url
	 * @param platform
	 * @param version
	 * @throws Exception
	 */
	public static ExtentReports reports;
	public static ExtentTest test;
	public static void setPlatformDetails(String browserType, Platform platform, String version, String url)
			throws IOException
	{
		reports.addSystemInfo("Browser", browserType);
		reports.addSystemInfo("Browser Version", version);
		// reports.addSystemInfo("platform", platform.toString());
		reports.addSystemInfo("URL", url);
	}
	/**
	 * Initialization of extent reports with time stamp
	 * 
	 */
	public MyExtentReports() throws IOException
	{
		String timeStamp;
		if (System.getProperty("user.name").contains("CYBERWINDEV"))
			timeStamp = "extentReports";
		else
			timeStamp = getCurrTimeStamp();
		File f = new File(System.getProperty("user.dir") + "/advReports");
		if (!f.exists())
		{
			f.mkdir();
		}
		f = new File(System.getProperty("user.dir") + "/advReports/" + timeStamp + ".html");
		f.createNewFile();
		String reportFilePath = System.getProperty("user.dir") + "/advReports/" + timeStamp + ".html";
		reports = new ExtentReports(reportFilePath, false);
		// reports.addSystemInfo("Selenium Version", "2.53.1");
		reports.addSystemInfo("Environment", "QA");
	}
}

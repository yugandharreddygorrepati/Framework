package com.framework.tests;
import static com.framework.lib.Driver.closeBrowser;
import static com.framework.lib.Driver.getScreenPath;
import static com.framework.lib.Driver.initTestNgSoftAssert;
import static com.framework.lib.Driver.initWebDriver;
import static com.framework.lib.Driver.softAssert;
import static com.framework.lib.MyExtentReports.reports;
import static com.framework.lib.MyExtentReports.test;
import static com.framework.lib.SoftAssertions.verifyEquals;
import static com.framework.lib.SoftAssertions.verifyContains;
import java.io.IOException;
import java.util.Properties;
import org.testng.annotations.Test;
import com.framework.lib.InitTests;
import com.framework.lib.RetryAnalyzer;
import com.framework.lib.SoftAssertions;
import com.framework.pages.GoogleHomePage;
import com.framework.rest.AnalyticReq;
import com.framework.rest.DataSpace;
import com.framework.rest.ExplorerReq;
import com.framework.rest.IRELoginReq;
import com.framework.rest.KibanaReq;
import com.framework.rest.LayoutReq;
import io.restassured.response.Response;
/**
 * @author YugandharReddyGorrep
 * @category regression
 * @Precondition
 * @Description DateFilterBetween
 *
 */
public class TestGoogle extends InitTests
{
	Properties p = new Properties();
	@Test(priority = 1, enabled = true, groups =
	{ "smoke" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGoogle() throws IOException
	{
		
		try
		{
			test = reports.startTest("testGoogle", "to test testGoogle");
			test.assignCategory("smoke");
			initTestNgSoftAssert(); // to initialize testng soft assertions
			initWebDriver(BROWSER_TYPE, BASEURL);
			GoogleHomePage ghome=new GoogleHomePage();
			ghome.search(SEARCH_TEXT);
			Thread.sleep(5000);
			verifyContains(ghome.getFirstLnkDisplayed(), "Selenium");
		} catch (Exception e)
		{
			e.printStackTrace();
			SoftAssertions.fail(e, getScreenPath(new Exception().getStackTrace()[0].getMethodName()));
		} finally
		{
			
			reports.endTest(test);
			reports.flush();
			closeBrowser(BROWSER_TYPE);
			softAssert.assertAll();// to assert all testng soft assertions
		}
	}
}

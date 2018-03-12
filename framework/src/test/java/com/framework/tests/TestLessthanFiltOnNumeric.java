/*package com.framework.tests;
import static com.fico.rest.IRELoginReq.cookie;
import static com.fico.rest.IRELoginReq.token;
import static com.framework.lib.Driver.closeBrowser;
import static com.framework.lib.Driver.getScreenPath;
import static com.framework.lib.Driver.initTestNgSoftAssert;
import static com.framework.lib.Driver.initWebDriver;
import static com.framework.lib.Driver.softAssert;
import static com.framework.lib.MyExtentReports.reports;
import static com.framework.lib.MyExtentReports.test;
import static com.framework.lib.SoftAssertions.verifyContains;
import static com.framework.lib.SoftAssertions.verifyEquals;
import java.io.IOException;
import java.util.Properties;
import org.testng.annotations.Test;
import com.fico.rest.AnalyticReq;
import com.fico.rest.DataSpace;
import com.fico.rest.ExplorerReq;
import com.fico.rest.IRELoginReq;
import com.fico.rest.KibanaReq;
import com.fico.rest.LayoutReq;
import com.framework.lib.InitTests;
import com.framework.lib.RetryAnalyzer;
import com.framework.lib.SoftAssertions;
import com.framework.pages.GoogleHomePage;

import io.restassured.response.Response;
*//**
 * @author YugandharReddyGorrep
 * @category regression
 * @Precondition
 * @Description DateFilterBetween
 *
 *//*
public class TestLessthanFiltOnNumeric extends InitTests
{
	Properties p = new Properties();
	@Test(priority = 1, enabled = true, groups =
	{ "smoke" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLessThanFiltOnNumeric() throws IOException
	{
		String ANALYTIC_FILE = Thread.currentThread().getContextClassLoader()
				.getResource("testdata/lessThanFiltOnNumeric/Test_LessThan_Numeric_Analytic.xml").getPath();
		String EXPLORER_FILE = Thread.currentThread().getContextClassLoader()
				.getResource("testdata/lessThanFiltOnNumeric/Test_LessThan_Numeric_Explorer.xml").getPath();
		String LAYOUT_FILE = Thread.currentThread().getContextClassLoader()
				.getResource("testdata/lessThanFiltOnNumeric/Test_LessThan_Numeric_Layout.xml").getPath();
		String KIBANA_FILE = Thread.currentThread().getContextClassLoader()
				.getResource("testdata/lessThanFiltOnNumeric/kibanaQuery.json").getPath();
		String analyticName = ANALYTIC_FILE.substring(ANALYTIC_FILE.lastIndexOf("/") + 1,
				ANALYTIC_FILE.lastIndexOf("."));
		String explorerName = EXPLORER_FILE.substring(EXPLORER_FILE.lastIndexOf("/") + 1,
				EXPLORER_FILE.lastIndexOf("."));
		String layoutName = LAYOUT_FILE.substring(LAYOUT_FILE.lastIndexOf("/") + 1, LAYOUT_FILE.lastIndexOf("."));
		try
		{
			test = reports.startTest("testLessThanFiltOnNumeric-RestAPI",
					"to test Less than Filter OnNumeric using REST API");
			test.assignCategory("RestAPI");
			initTestNgSoftAssert(); // to initialize testng soft assertions
			IRELoginReq.initSession(ENDPOINT_LOGIN, USERNAME, PASSWORD);
			int actRespCode = AnalyticReq.createAnalytic(ANALYTICPATH + analyticName, cookie, token, ANALYTIC_FILE);
			verifyEquals(actRespCode, 200, "--Analytic with name " + analyticName + " created sucessfully ");
			Response response = DataSpace.unPublish(XCFBUILD, DATASPACE_UNPUBLISH_BODY, cookie, token);
			verifyEquals(response.getStatusCode(), 200, "--Dataspace un publish success");
			response = DataSpace.publish(XCFBUILD, DATASPACE_PUBLISH_BODY, cookie, token);
			verifyEquals(response.getStatusCode(), 200, "--Dataspace publish success");
			response = AnalyticReq.buildAnalytic(XCFBUILD, analyticName, cookie, token);
			verifyContains(response.getBody().asString(), "success",
					"--Analytic with name " + analyticName + " build sucessfully ");
			verifyEquals(response.getStatusCode(), 200, "--Analytic with name " + analyticName + " build sucessfully ");
			response = KibanaReq.getIndexCreationCode(ENDPOINT_KIBANA, analyticName);
			verifyEquals(response.getStatusCode(), 200, "--Idex for analytic " + analyticName + " created succefully ");
			actRespCode = ExplorerReq.createExplorer(EXPLORERPATH + explorerName, cookie, token, EXPLORER_FILE);
			verifyEquals(actRespCode, 200, "--Explorer with  " + explorerName + " created sucessfully ");
			actRespCode = LayoutReq.createLayout(LAYOUTPATH + layoutName, cookie, token, LAYOUT_FILE);
			verifyEquals(actRespCode, 200, "--Layout with  " + layoutName + " created sucessfully ");
			// selenium to verify date filter in UI
			test = reports.startTest("testLessThanFiltOnNumeric-UI", "--to test LessThanuals FiltOnNumeric in UI");
			test.assignCategory("smoke");
			initWebDriver(BROWSER_TYPE, BASEURL + "/ire/");
			initTestNgSoftAssert(); // to initialize testng soft assertions
			GoogleHomePage loginPage = new GoogleHomePage();
			loginPage.ireLogin(USERNAME, PASSWORD);
			//IREUserHomepage irehome = new IREUserHomepage();
		
					//KibanaReq.getExpNoOfRec(ENDPOINT_KIBANA, KIBANA_FILE, analyticName),
				//	"--Records displayed in UI and Kibana are same--");
			// verifyEquals(irehome.getActDateinUI(), "1927-08-15");
		} catch (Exception e)
		{
			e.printStackTrace();
			SoftAssertions.fail(e, getScreenPath(new Exception().getStackTrace()[0].getMethodName()));
		} finally
		{
			IRELoginReq.initSession(ENDPOINT_LOGIN, USERNAME, PASSWORD);
			int actRespCode = AnalyticReq.deleteAnalytic(ANALYTICPATH + analyticName, cookie, token);
			verifyEquals(actRespCode, 200, "--Analytic with " + ANALYTICPATH + " deleted sucessfully ");
			actRespCode = ExplorerReq.deleteExplorer(EXPLORERPATH + explorerName, cookie, token);
			verifyEquals(actRespCode, 200, "--Explorer with " + EXPLORERPATH + " deleted sucessfully ");
			actRespCode = LayoutReq.deleteLayout(LAYOUTPATH + layoutName, cookie, token);
			verifyEquals(actRespCode, 200, "--Layout with " + LAYOUTPATH + " deleted sucessfully ");
			Response response = KibanaReq.deleteAnalytic(ENDPOINT_KIBANA, analyticName);
			verifyEquals(response.getStatusCode(), 200,
					"--Kibana analytic with name " + analyticName + " deleted sucessfully ");
			reports.endTest(test);
			reports.flush();
			closeBrowser(BROWSER_TYPE);
			softAssert.assertAll();// to assert all testng soft assertions
		}
	}
}
*/
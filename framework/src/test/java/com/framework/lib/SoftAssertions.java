package com.framework.lib;
import static com.framework.lib.Driver.softAssert;
import static com.framework.lib.MyExtentReports.test;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.openqa.selenium.WebElement;
import org.testng.asserts.SoftAssert;
import com.relevantcodes.extentreports.LogStatus;
/**
 * @author YugandharReddyGorrep
 *
 */
public class SoftAssertions
{
	// publpublic static SoftAssert softAssert;ic static SoftAssert softAssert;
	public static void verifyEquals(String... values)
	{
		String actual = values[0];
		String expected = values[1];
		String message = "";
		if (values.length == 3)
			message = values[2];
		if (actual.equals(expected))
			test.log(LogStatus.PASS, "verifyEquals() ", "actual " + actual + " expected " + expected + "--" + message);
		else
			test.log(LogStatus.FAIL, "verifyEquals() ", "actual " + actual + " but " + " expected " + expected);
		softAssert.assertEquals(actual, expected);
	}
	public static void verifyNotEquals(String... values)
	{
		String actual = values[0];
		String expected = values[1];
		String message = "";
		if (values.length == 3)
			message = values[2];
		if (!actual.equals(expected))
			test.log(LogStatus.PASS, "verifyNotEquals() ",
					"actual " + actual + " expected " + expected + "--" + message);
		else
			test.log(LogStatus.FAIL, "verifyNotEquals() ", "actual " + actual + "but" + " expected " + expected);
		softAssert.assertNotEquals(actual, expected);
	}
	public static void verifyEquals(int actual, int expected, String message)
	{
		if (actual == expected)
			test.log(LogStatus.PASS, "verifyEquals() ", "actual " + actual + " expected " + expected + message);
		else
			test.log(LogStatus.FAIL, "verifyEquals() ", "actual " + actual + "but " + " expected " + expected);
		softAssert.assertEquals(actual, expected);
	}
	public static void verifyEquals(boolean actual, boolean expected)
	{
		if (actual == expected)
			test.log(LogStatus.PASS, "verifyEquals() ", "actual " + actual + " expected " + expected);
		else
			test.log(LogStatus.FAIL, "verifyEquals() ", "actual " + actual + "but " + " expected " + expected);
		softAssert.assertEquals(actual, expected);
	}
	public static void verifyEquals(boolean actual, boolean expected, String message)
	{
		if (actual == expected)
			test.log(LogStatus.PASS, "verifyEquals() ", "actual " + actual + " expected " + expected + "--" + message);
		else
			test.log(LogStatus.FAIL, "verifyEquals() ", "actual " + actual + " but " + " expected " + expected);
		softAssert.assertEquals(actual, expected);
	}
	private static void initTestngSoftAssert()
	{
		softAssert = new SoftAssert();
	}
	public static void verifyNotEquals(Object... values)
	{
		Object actual = values[0];
		Object expected = values[1];
		Object message = "";
		if (values.length == 3)
			message = values[2];
		if (!actual.equals(expected))
			test.log(LogStatus.PASS, "verifyNotEquals() ",
					"actual " + actual + " expected " + expected + "--" + message);
		else
			test.log(LogStatus.FAIL, "verifyNotEquals() ", "actual " + actual + "but" + " expected " + expected);
		softAssert.assertNotEquals(actual, expected);
	}
	public static void verifyContains(String... values)
	{
		String actual = values[0];
		String expected = values[1];
		String message = "";
		if (values.length == 3)
			message = values[2];
		if (actual.contains(expected))
		{
			test.log(LogStatus.PASS, "verifyContains() ",
					"actual " + actual + " expected " + expected + "--" + message);
		} else
		{
			test.log(LogStatus.FAIL, "verifyContains() ", "actual " + actual + "but" + " expected " + expected);
		}
	}
	public static void verifyElementTextContains(WebElement e, String expected, String message)
	{
		String actual;
		if (e.getText().isEmpty())
			actual = e.getAttribute("value").trim();
		else
			actual = e.getText().trim();
		if (actual.toUpperCase().contains(expected.trim().toUpperCase()))
		{
			test.log(LogStatus.PASS, "verifyElementContains() ",
					"Webelement " + e.getAttribute("outerHTML") + " contains text as " + expected + "--" + message);
		} else
		{
			test.log(LogStatus.FAIL, "verifyElementContains() ",
					e.getAttribute("outerHTML") + " has actual value " + actual + " but" + " expected " + expected);
		}
	}
	public static void verifyElementTextContains(WebElement e, String expected)
	{
		String actual;
		if (e.getText().isEmpty())
			actual = e.getAttribute("value").trim();
		else
			actual = e.getText().trim();
		if (actual.toUpperCase().contains(expected.trim().toUpperCase()))
		{
			test.log(LogStatus.PASS, "verifyElementContains() ",
					"Webelement " + e.getAttribute("outerHTML") + " contains text as " + expected);
		} else
		{
			test.log(LogStatus.FAIL, "verifyElementContains() ",
					e.getAttribute("outerHTML") + " has actual value " + actual + " but" + " expected " + expected);
		}
	}
	public static void verifyElementText(WebElement e, String expected)
	{
		String actual;
		if (e.getText().isEmpty())
			actual = e.getAttribute("value");
		else
			actual = e.getText();
		if (actual.equals(expected))
		{
			test.log(LogStatus.PASS, "verifyElementText() ",
					"Webelement " + e.getAttribute("outerHTML") + " contains text as " + expected);
		} else
		{
			test.log(LogStatus.FAIL, "verifyElementText() ",
					e.getAttribute("outerHTML") + " has actual " + actual + " but" + " expected " + expected);
		}
		softAssert.assertEquals(actual, expected);
	}
	public static void verifyElementTextIgnoreCase(WebElement e, String expected)
	{
		String actual;
		if (e.getText().isEmpty())
			actual = e.getAttribute("value");
		else
			actual = e.getText();
		if (actual.toUpperCase().equals(expected.toUpperCase()))
		{
			test.log(LogStatus.PASS, "verifyElementTextIgnoreCase() ",
					"Webelement " + e.getAttribute("outerHTML") + " contains text as " + expected);
		} else
		{
			test.log(LogStatus.FAIL, "verifyElementTextIgnoreCase() ",
					e.getAttribute("outerHTML") + " has actual " + actual + " but" + " expected " + expected);
		}
		softAssert.assertEquals(actual.toUpperCase(), expected.toUpperCase());
	}
	public static void verifyElementHyperLink(WebElement e)
	{
		if (e.getTagName().contains("a"))
		{
			test.log(LogStatus.PASS, "verifyElementHyperLink()",
					"Webelement " + e.getAttribute("outerHTML") + " contains hyperlink");
		} else
		{
			test.log(LogStatus.FAIL, "verifyElementHyperLink() ", "Webelement " + e.getAttribute("outerHTML")
					+ " have actual " + e.getTagName() + " but " + " expected " + "a");
		}
		softAssert.assertEquals(e.getTagName(), "a");
	}
	public static void verifyElementContains(WebElement e, String expected, String message)
	{
		String actual;
		if (e.getText().isEmpty())
			actual = e.getAttribute("value");
		else
			actual = e.getText();
		if (actual.toUpperCase().contains(expected.toUpperCase()))
		{
			test.log(LogStatus.PASS, "verifyElementContains() ",
					"Webelement " + e.getAttribute("outerHTML") + " contains text as " + expected + message);
		} else
		{
			test.log(LogStatus.FAIL, "verifyElementContains() ",
					e.getAttribute("outerHTML") + "has actual " + actual + " but" + " expected " + expected);
		}
	}
	public static void verifyElementText(WebElement e, String expected, String message)
	{
		String actual;
		if (e.getText().isEmpty())
			actual = e.getAttribute("value");
		else
			actual = e.getText();
		if (actual.equals(expected))
		{
			test.log(LogStatus.PASS, "verifyElementText() ",
					"Webelement " + e.getAttribute("outerHTML") + " contains text as " + expected + message);
		} else
		{
			test.log(LogStatus.FAIL, "verifyElementText() ", "actual " + actual + "but" + " expected " + expected);
		}
		softAssert.assertEquals(actual, expected, message);
	}
	public static void verifyElementTextIgnoreCase(WebElement e, String expected, String message)
	{
		String actual;
		if (e.getText().isEmpty())
			actual = e.getAttribute("value");
		else
			actual = e.getText();
		if (actual.toUpperCase().equals(expected.toUpperCase()))
		{
			test.log(LogStatus.PASS, "verifyElementTextIgnoreCase() ",
					"Webelement " + e.getAttribute("outerHTML") + " contains text as " + expected + message);
		} else
		{
			test.log(LogStatus.FAIL, "verifyElementTextIgnoreCase() ",
					"actual " + actual + "but" + " expected " + expected);
		}
		softAssert.assertEquals(actual.toUpperCase(), expected.toUpperCase(), message);
	}
	public static void verifyElementLink(WebElement e, String message)
	{
		if (e.getTagName().contains("a"))
		{
			test.log(LogStatus.PASS, "verifyElementLink()",
					"Webelement " + e.getAttribute("outerHTML") + " contains hyperlink" + message);
		} else
		{
			test.log(LogStatus.FAIL, "verifyElementLink() ", "actual " + e.getTagName() + "but" + " expected " + "a");
		}
		softAssert.assertEquals(e.getTagName(), "a", message);
	}
	public static void assertTrue(boolean condition, String message)
	{
		if (condition == true)
		{
			test.log(LogStatus.PASS, "assertTrue()", "true");
		} else
		{
			test.log(LogStatus.FAIL, "assertTrue()", message);
		}
		softAssert.assertTrue(condition, message);
	}
	public static void assertFalse(boolean condition, String message)
	{
		if (condition == false)
		{
			test.log(LogStatus.PASS, "assertFalse()", "true" + message);
		} else
		{
			test.log(LogStatus.FAIL, "assertFalse()");
		}
		softAssert.assertFalse(condition, message);
	}
	public static void assertNull(Object obj, String message)
	{
		if (obj == null)
		{
			test.log(LogStatus.PASS, "assertNull()", "null" + message);
		} else
		{
			test.log(LogStatus.FAIL, "assertNull()");
		}
		softAssert.assertNull(obj, message);
	}
	public static void assertNotNull(Object obj, String message)
	{
		if (obj != null)
		{
			test.log(LogStatus.PASS, "assertNotNull()", obj.toString() + message);
		} else
		{
			test.log(LogStatus.FAIL, "assertNotNull()");
		}
		softAssert.assertNotNull(obj, message);
	}
	public static void fail(Throwable e, String pathFrScreenshot) throws IOException
	{
		createExceptionFile(e);
		String content = new String(
				Files.readAllBytes(Paths.get(System.getProperty("user.dir") + "\\advReports\\exception.txt")));
		test.log(LogStatus.FAIL, "fail()-Exception", content);
		if (InitTests.CaptureScreenshotOnFail.equalsIgnoreCase("true"))
		{
			test.log(LogStatus.INFO, "fail()-Exception", test.addScreenCapture(pathFrScreenshot));
		}
		softAssert.fail(content);
	}
	private static void createExceptionFile(Throwable e) throws IOException
	{
		File f = new File(System.getProperty("user.dir") + "\\advReports\\exception.txt");
		if (!f.exists())
			f.createNewFile();
		PrintWriter p = new PrintWriter(System.getProperty("user.dir") + "\\advReports\\exception.txt");
		e.printStackTrace(p);
		p.close();
	}
}

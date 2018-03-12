package com.framework.lib;
import static com.framework.lib.DateUtils.getCurrTimeStamp;
import static com.framework.lib.MyExtentReports.setPlatformDetails;
import static com.framework.lib.MyExtentReports.test;
/**
 * @author Yugandhar Reddy 
 *         Utility Class where all common libraries can be defined which can be used
 *         across the test cases.
 * 
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerDriverLogLevel;
import org.openqa.selenium.ie.InternetExplorerDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.Clock;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Sleeper;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import com.google.common.base.Function;
import com.relevantcodes.extentreports.LogStatus;
public class Driver
{
	private static final Logger logger = Logger.getLogger("selenium");
	public static String driverpath;
	public static WebDriver webdriver;
	public static EventFiringWebDriver driver;
	public static WebDriverWait wait;
	static EventListner event;
	public static SoftAssert softAssert;
	public static int defaultTimeOut = 20;
	/**
	 * @author Yugandhar Reddy
	 * 
	 * @description:Instantiate the webdriver based on the browser type
	 * @param browserType
	 * @param url
	 * @return WebDriver
	 * @throws Exception
	 */
	public static void initWebDriver(String browserType, String url) throws Exception
	{
		System.out.println("Inside initWebDriver : Browser type is :" + browserType);
		switch (browserType)
		{
		case "IE":
			try
			{
				if (InitTests.OS_VERSION.contains("64"))
				{
					driverpath = Thread.currentThread().getContextClassLoader()
							.getResource("drivers/IEDriverServer.exe").getPath();
				} else
				{
					driverpath = Thread.currentThread().getContextClassLoader()
							.getResource("drivers/IEDriverServer32.exe").getPath();
				}
			} catch (Exception e)
			{
				if (driverpath == null)
				{
					driverpath = "c:\\iedriver\\IEDriverServer.exe";
					File file = new File(driverpath);
					if (!file.exists())
						throw new FileNotFoundException(
								"IE Driver executable not found in resources and " + driverpath);
				}
			}
			System.out.println("driverpath =" + driverpath);
			setExecPermsWin(driverpath);
			System.setProperty("webdriver.ie.driver", driverpath);
			logger.info("----- IE webdriver -----");
			// new code
			InternetExplorerDriverService service = new InternetExplorerDriverService.Builder().usingAnyFreePort()
					.withLogFile(new File("IE_Driver.log")).withLogLevel(InternetExplorerDriverLogLevel.TRACE).build();
			DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();
			// ieCapabilities.setCapability("EnableNativeEvents", false);
			// ieCapabilities.setCapability("ignoreZoomSetting", true);
			// ieCapabilities.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION,
			// true);
			// ieCapabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS,
			// true);
			ieCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			// ieCapabilities.setCapability(InternetExplorerDriver.INITIAL_BROWSER_URL,
			// "about:blank");
			webdriver = new InternetExplorerDriver(service, ieCapabilities);
			if (webdriver == null)
			{
				System.out.println("Failed to initialize IE webdriver in Utils.initWebDriver() ");
				throw new Exception("Failed to initialize IE webdriver in Utils.initWebDriver() ");
			}
			Capabilities cap = ((RemoteWebDriver) webdriver).getCapabilities();
			String browserName = cap.getBrowserName().toLowerCase();
			System.out.println(browserName);
			Platform os = cap.getPlatform();
			System.out.println(os);
			String version = cap.getVersion().toString();
			setPlatformDetails(browserName, ieCapabilities.getPlatform(), version, url);
			webdriver.manage().window().maximize();
			driver = new EventFiringWebDriver(webdriver);
			event = new EventListner();
			driver.register(event);
			driver.manage().timeouts().setScriptTimeout(defaultTimeOut, TimeUnit.SECONDS);
			waitForPageLoad();
			driver.get(url);
			By loc = By.id("IDToken1");
			driver.get("javascript:document.getElementById('overridelink').click();");
			driver.get("javascript:document.getElementById('overridelink').click();");
			// for (;;)
			// {
			// try
			// {
			// waitForElementToDisplay(loc);
			// break;
			// } catch (Exception e)
			// {
			// continue;
			// }
			// }
			// waitForPageLoad();
			break;
		case "CHROME":
			if (InitTests.OS_NAME.toLowerCase().contains("linux"))
			{
				if (InitTests.OS_VERSION.contains("32"))
				{
					driverpath = Driver.class.getClassLoader().getResource("drivers/chromedriver_linux32").getPath();
					System.out.println("Linux 32 bit chrome driver:" + driverpath);
				} else
				{
					driverpath = Driver.class.getClassLoader().getResource("drivers/chromedriver_linux64").getPath();
					System.out.println("Linux 64 bit chrome driver:" + driverpath);
				}
				try
				{
					setExecPermsPosix(driverpath);
				} catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (InitTests.OS_NAME.toLowerCase().contains("windows"))
			{
				driverpath = Driver.class.getClassLoader().getResource("drivers/chromedriver.exe").getPath();
				System.out.println("Windows chrome driver:" + driverpath);
			} else if (InitTests.OS_NAME.toLowerCase().contains("mac"))
			{
				driverpath = Driver.class.getClassLoader().getResource("drivers/chromedriver").getPath();
				try
				{
					setExecPermsPosix(driverpath);
					System.out.println(driverpath);
				} catch (Exception e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			logger.info("-------Chrome webdriver driverpath --------- " + driverpath + "\n");
			System.out.println("Init chrome driver :: \n\t" + "OS_NAME ->" + InitTests.OS_NAME);
			System.out.println("\n\t" + "OS_VERSION ->" + InitTests.OS_VERSION);
			System.out.println("\n\t" + "BROWSER_TYPE ->" + InitTests.BROWSER_TYPE);
			System.out.println("\n\t" + "BASEURL ->" + InitTests.BASEURL);
			System.out.println("\n\t" + "driverpath for windows->" + driverpath);
			System.setProperty("webdriver.chrome.driver", driverpath);
			ChromeOptions options = new ChromeOptions();
			options.addArguments("disable-extensions");
			options.addArguments("test-type");
			options.addArguments("start-maximized");
			options.addArguments("--js-flags=--expose-gc");
			options.addArguments("--enable-precise-memory-info");
			options.addArguments("--disable-popup-blocking");
			options.addArguments("--disable-default-apps");
			options.addArguments("test-type=browser");
			options.addArguments("disable-infobars");
			webdriver = new ChromeDriver(options);
			if (webdriver == null)
			{
				System.out.println("Failed to initialize CHROME webdriver in Utils.initWebDriver() ");
				throw new Exception("Failed to initialize CHROME webdriver in Utils.initWebDriver() ");
			}
			cap = ((RemoteWebDriver) webdriver).getCapabilities();
			browserName = cap.getBrowserName().toLowerCase();
			System.out.println(browserName);
			os = cap.getPlatform();
			System.out.println(os);
			version = cap.getVersion().toString();
			logger.info("Browser Name: " + cap.getBrowserName().toLowerCase() + " OS name: "
					+ cap.getPlatform().toString() + " Browser version: " + cap.getVersion().toString());
			System.out.println("Browser Name: " + cap.getBrowserName().toLowerCase() + " OS name: "
					+ cap.getPlatform().toString() + " Browser version: " + cap.getVersion().toString());
			setPlatformDetails(browserName, os, version, InitTests.BASEURL);
			webdriver.manage().window().maximize();
			driver = new EventFiringWebDriver(webdriver);
			EventListner event = new EventListner();
			driver.register(event);
			waitForPageLoad();
			driver.manage().timeouts().setScriptTimeout(defaultTimeOut, TimeUnit.SECONDS);
			driver.get(url);
			break;
		case "FF":
			if (InitTests.OS_NAME.toLowerCase().contains("linux"))
			{
			} else if (InitTests.OS_NAME.toLowerCase().contains("windows"))
			{
			} else if (InitTests.OS_NAME.toLowerCase().contains("mac"))
			{
			}
			logger.info("----- Firefox webdirver -----");
			System.out.println("Checking firefox ");
			webdriver = new FirefoxDriver();
			if (webdriver == null)
			{
				System.out.println("Failed to initialize FF webdriver in Utils.initWebDriver() ");
				throw new Exception("Failed to initialize FF webdriver in Utils.initWebDriver() ");
			}
			Capabilities ffCapabilities = ((RemoteWebDriver) webdriver).getCapabilities();
			setPlatformDetails(ffCapabilities.getBrowserName(), ffCapabilities.getPlatform(),
					ffCapabilities.getVersion().toString(), url);
			webdriver.manage().window().maximize();
			driver = new EventFiringWebDriver(webdriver);
			event = new EventListner();
			driver.register(event);
			driver.get(url);
			waitForPageLoad();
			break;
		default:
			System.out.println("Not valid Browser");
			throw new RuntimeException("Browser type unsupported");
		}
	}
	public static WebDriver getDriver()
	{
		return driver;
	}
	/**
	 * @author KhaderKhan
	 * @param Path
	 *            - File to make it executable
	 * @throws IOException
	 */
	public static void setExecPermsWin(String Path) throws Exception
	{
		File file = new File(Path);
		System.out.println("IE Driver path: " + Path);
		System.out.println("Before set - Is Execute Permission set : " + file.canExecute());
		if (file.exists())
		{
			System.out.println("Is Execute Permission set : " + file.canExecute());
			file.setExecutable(true, false);
			file.setReadable(true, false);
			file.setWritable(true, false);
		}
		System.out.println("After - Is Execute allow : " + file.canExecute());
		System.out.println("Afetr - Is Write allow : " + file.canWrite());
		System.out.println("After - Is Read allow : " + file.canRead());
	}
	/**
	 * @author KhaderKhan
	 * @param Path
	 *            - File to make it executable
	 * @throws IOException
	 */
	public static void setExecPermsPosix(String Path) throws IOException
	{
		File file = new File(Path);
		/*
		 * if(file.exists()){
		 * System.out.println("Before setting perms - Execute allowed : " +
		 * file.canExecute()); file.setExecutable(true, false);
		 * file.setReadable(true, false); file.setWritable(true, false); }
		 */
		/*
		 * System.out.println("Is Execute allow : " + file.canExecute());
		 * System.out.println("Is Write allow : " + file.canWrite());
		 * System.out.println("Is Read allow : " + file.canRead());
		 */
		// using PosixFilePermission to set file permissions 777
		Set<PosixFilePermission> perms = new HashSet<PosixFilePermission>();
		// add owners permission
		perms.add(PosixFilePermission.OWNER_READ);
		perms.add(PosixFilePermission.OWNER_WRITE);
		perms.add(PosixFilePermission.OWNER_EXECUTE);
		// add group permissions
		perms.add(PosixFilePermission.GROUP_READ);
		perms.add(PosixFilePermission.GROUP_WRITE);
		perms.add(PosixFilePermission.GROUP_EXECUTE);
		// add others permissions
		perms.add(PosixFilePermission.OTHERS_READ);
		perms.add(PosixFilePermission.OTHERS_WRITE);
		perms.add(PosixFilePermission.OTHERS_EXECUTE);
		Files.setPosixFilePermissions(Paths.get(Path), perms);
	}
	/**
	 * Delete cookies of IE
	 * 
	 */
	public static void deleteCookies()
	{
		String command = "RunDll32.exe InetCpl.cpl,ClearMyTracksByProcess 2";
		logger.info("----- deleteCookies(): method -----");
		try
		{
			Process process = Runtime.getRuntime().exec(command);
			logger.info("Delete cookies output - " + process.getOutputStream());
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	/**
	 * Kill IE browser forcefully In IE Broswer disable the automatic crash
	 * recovery in advanced settings
	 */
	public static void killIE()
	{
		String command = "taskkill /f /t /im iexplore.exe";
		logger.info("----- killIE(): method -----");
		try
		{
			Process process = Runtime.getRuntime().exec(command);
			logger.info("taskkill iexplore output - " + process.getOutputStream());
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	/**
	 * Kill IE Browser and Driver
	 */
	public static void killIEDriver()
	{
		String command = "taskkill /f /t /im IEDriverServer.exe";
		logger.info("----- killIEDriver() : closing IE webdriver -----");
		try
		{
			Process process = Runtime.getRuntime().exec(command);
			logger.info("taskkill IEdriver output - " + process.getOutputStream());
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	/**
	 * Kill Chrome Driver
	 */
	public static void killChromeDriver()
	{
		// String cmd = "pgrep chrome | xargs kill -9";
		// taskkill /im chromedriver.exe /f
		try
		{
			Runtime rt = Runtime.getRuntime();
			if (InitTests.OS_NAME.toLowerCase().contains("windows"))
			{
				rt.exec("taskkill /f /t /im chromedriver.exe");
				rt.exec("taskkill /f /t /im chrome.exe");
			} else
			{
				rt.exec("pgrep -i chrome | xargs kill -9");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
	/**
	 * Kill FF Driver
	 */
	public static void killFireFoxDriver()
	{
		// String cmd = "pgrep chrome | xargs kill -9";
		// taskkill /im chromedriver.exe /f
		try
		{
			Runtime rt = Runtime.getRuntime();
			if (InitTests.OS_NAME.toLowerCase().contains("windows"))
			{
				rt.exec("taskkill /f /t /im firefox.exe");
			} else
			{
				rt.exec("pgrep -i firefox | xargs kill -9");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
	/**
	 * Close All Browsers
	 */
	public static void closeBrowser(String brType)
	{
		logger.info("----- closeBrowser() : Closing the currently opened browser and killing driver " + brType);
		System.out.println("----- closeBrowser() : Closing the currently opened browser and killing driver " + brType);
		if (brType.toLowerCase().contains("ie"))
		{
			if (getDriver() != null)
			{
				getDriver().manage().deleteAllCookies();
				getDriver().quit();
			}
			killIE();
			killIEDriver();
			deleteCookies();
			delay(2000);
		} else if (brType.toLowerCase().contains("chrome"))
		{
			if (getDriver() != null)
			{
				getDriver().quit();
			}
			killChromeDriver();
			delay(2000);
		} else if (brType.toLowerCase().contains("ff"))
		{
			// code for FF
			System.out.println("close broser: kill ff browser");
			if (getDriver() != null)
			{
				getDriver().quit();
			}
			killFireFoxDriver();
			delay(2000);
		}
	}
	public static void delay(long milliseconds)
	{
		try
		{
			Thread.sleep(milliseconds);
		} catch (Exception e)
		{
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
	}
	/**
	 * @author KhaderKhan
	 * @param fileName
	 * @return String Array
	 * @throws IOException
	 */
	public static List<String> readTestData(String fileName) throws IOException
	{
		String inputFileName = System.getProperty("user.dir") + "/src/test/resources/" + fileName;
		List<String> testData = new ArrayList<String>();
		List<String> contents = FileUtils.readLines(new File(inputFileName));
		for (String lines : contents)
		{
			// System.out.println(lines);
			testData.add(lines);
		}
		return contents;
	}
	/**
	 * @author KhaderKhan
	 * @param key
	 *            - Key name in properties file
	 * @return - Value of key from prop file
	 * @throws Exception
	 */
	public static String getTestData(String filename, String key) throws Exception
	{
		String inputFileName = Driver.class.getClassLoader().getResource("testdata.properties").getPath();
		// String inputFileName =
		// getClass().getResource("/testdata.properties").getFile();
		// String inputFileName = System.getProperty("user.dir") +
		// "/src/test/resources/testdata/" + filename;
		Properties props = new Properties();
		props.load(new FileReader(inputFileName));
		String value = props.getProperty(key);
		return value == null ? value : new String(value.getBytes(), "UTF-8");
	}
	/*
	 * public String getTestDataUTF(String filename, String key) throws
	 * Exception {
	 * 
	 * //String inputFileName =
	 * getClass().getResource("/testdata.properties").getFile(); String
	 * inputFileName = System.getProperty("user.dir")+
	 * "/src/test/resources/testdata/"+filename;
	 * 
	 * System.out.println("inputFileName :"+inputFileName); Properties props =
	 * new Properties(); props.load(new BufferedReader reader = new
	 * BufferedReader(new InputStreamReader(new FileInputStream("myfile.txt"),
	 * "UTF-16"))); String value = props.getProperty(key); return value; }
	 */
	/**
	 * This Method converts current system date into server date in different
	 * time Zone (CDT).
	 * 
	 * @return actDate
	 * @throws ParseException
	 */
	public static String[] getCurrentServerDate() throws ParseException
	{
		List<String> lsdate = new ArrayList<String>();
		DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss zzz");
		Calendar currentdate = Calendar.getInstance();
		String strdate = formatter.format(currentdate.getTime());
		logger.info("Current system date from util:" + strdate);
		Date fromDate = (Date) formatter.parse(strdate);
		TimeZone central = TimeZone.getTimeZone("America/Mexico_City");
		formatter.setTimeZone(central);
		logger.info("server date from utils:" + formatter.format(fromDate));
		String[] actDate = formatter.format(fromDate).toString().split(" ");
		return actDate[0].split("/");
	}
	public static Long getTimeStamp()
	{
		Long timestamp = System.currentTimeMillis();
		return timestamp;
	}
	public static String uniqueTime()
	{
		Date myDate = new Date();
		logger.info("----- uniqueTime() " + myDate + " -----");
		logger.info("----- simpledateFormat " + new SimpleDateFormat("MM-dd-yyyy").format(myDate) + " -----");
		String uniqueTime = new SimpleDateFormat("MMddyy").format(myDate);
		logger.info("tmp is " + uniqueTime);
		uniqueTime += System.currentTimeMillis();
		logger.info("----- converted timestamp " + uniqueTime + " -----");
		return uniqueTime;
	}
	/**
	 * Method to send email with attachment
	 * 
	 * @param filename
	 *            filename is HTML report created
	 * @throws MessagingException
	 */
	@SuppressWarnings("unused")
	public static void sendEmail(String filename) throws MessagingException
	{
		String host = "localhost";
		String from = "khaderkhan@fico.com";
		// String to = "";
		String to = "khaderkhan@fico.com";
		// Get system properties
		Properties props = System.getProperties();
		// Setup mail server
		// props.put("smtp.fico.com", host); //MSPMSGCCR000.corp.fairisaac.com
		// props.put(host, "smtp.fico.com");
		props.put("mail.smtp.host", "smtp.fico.com");
		props.put("mail.smtp.port", "25");
		// Get session
		Session session = Session.getDefaultInstance(props);
		// Define message
		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(from));
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
		message.setSubject("OM45 Automation Report");
		// Create the message part
		BodyPart messageBodyPart = new MimeBodyPart();
		// Fill the message
		messageBodyPart.setText("Hi, This is automated email, please do not reply. kindly check the attached report");
		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(messageBodyPart);
		// Part two is attachment
		// messageBodyPart = new MimeBodyPart();
		// DataSource source = new FileDataSource(filename);
		// messageBodyPart.setDataHandler(new DataHandler(source));
		// messageBodyPart.setFileName(filename);
		// multipart.addBodyPart(messageBodyPart);
		// Put parts in message
		message.setContent(multipart);
		// Send message
		Transport.send(message);
	}
	@SuppressWarnings("unused")
	private static void copyFileUsingFileStreams(File source, File dest) throws IOException
	{
		InputStream input = null;
		OutputStream output = null;
		try
		{
			input = new FileInputStream(source);
			output = new FileOutputStream(dest);
			byte[] buf = new byte[1024];
			int bytesRead;
			while ((bytesRead = input.read(buf)) > 0)
			{
				output.write(buf, 0, bytesRead);
			}
		} finally
		{
			input.close();
			output.close();
		}
	}
	/**
	 * Creates a new {@link FluentWait} which ignores
	 * {@link NoSuchElementException} by default.
	 *
	 * @return a new {@link FluentWait} which ignores
	 *         {@link NoSuchElementException}.
	 */
	public static FluentWait<WebDriver> newTolerantWait(WebDriver Driver)
	{
		return newWait(Driver).ignoring(NoSuchElementException.class).ignoring(StaleElementReferenceException.class);
	}
	// , StaleElementReferenceException.class
	/**
	 * Creates a new {@link FluentWait} which ignores
	 * {@link NoSuchElementException} by default.
	 *
	 * @param timeoutSeconds
	 *            The timeout in seconds when an expectation is called
	 * @return a new {@link FluentWait} which ignores
	 *         {@link NoSuchElementException}.
	 */
	public static FluentWait<WebDriver> newTolerantWait(long timeoutSeconds, WebDriver Driver)
	{
		return new WebDriverWait(Driver, timeoutSeconds);
	}
	/**
	 * Creates a new {@link FluentWait} which ignores
	 * {@link NoSuchElementException} by default.
	 *
	 * @param timeoutSeconds
	 *            The timeout in seconds when an expectation is called
	 * @param sleepMillis
	 *            The duration in milliseconds to sleep between polls.
	 * @return a new {@link FluentWait} which ignores
	 *         {@link NoSuchElementException}.
	 */
	public static FluentWait<WebDriver> newTolerantWait(long timeoutSeconds, long sleepMillis, WebDriver Driver)
	{
		return new WebDriverWait(Driver, timeoutSeconds, sleepMillis);
	}
	/**
	 * Creates a new {@link FluentWait} which ignores
	 * {@link NoSuchElementException} by default.
	 *
	 * @param clock
	 *            The clock to use when measuring the timeout.
	 * @param sleeper
	 *            Used to put the thread to sleep between evaluation loops.
	 * @return a new {@link FluentWait} which ignores
	 *         {@link NoSuchElementException}.
	 */
	public static FluentWait<WebDriver> newTolerantWait(Clock clock, Sleeper sleeper)
	{
		return newWait(clock, sleeper).ignoring(NoSuchElementException.class);
	}
	/**
	 * Creates a new {@link FluentWait} which ignores
	 * {@link NoSuchElementException} by default and will return {@code null}
	 * instead of throwing {@link TimeoutException}.
	 *
	 * @return a new {@link FluentWait} which ignores
	 *         {@link NoSuchElementException}.
	 */
	public static FluentWait<WebDriver> newTolerantWaitIgnoringTimeout()
	{
		return newWaitIgnoringTimeout().ignoring(NoSuchElementException.class);
	}
	/**
	 * Creates a new {@link FluentWait} which ignores
	 * {@link NoSuchElementException} by default and will return {@code null}
	 * instead of throwing {@link TimeoutException}.
	 *
	 * @param clock
	 *            The clock to use when measuring the timeout.
	 * @param sleeper
	 *            Used to put the thread to sleep between evaluation loops.
	 *
	 * @return a new {@link FluentWait} which ignores
	 *         {@link NoSuchElementException}.
	 */
	public static FluentWait<WebDriver> newTolerantWaitIgnoringTimeout(Clock clock, Sleeper sleeper, WebDriver Driver)
	{
		return newWaitIgnoringTimeout(clock, sleeper, Driver).ignoring(NoSuchElementException.class);
	}
	/**
	 * Shorthand for the commonly used
	 * {@code new FluentWait<WebDriver>(GlobalVariables.driver.get())}.
	 *
	 * @return a new {@link FluentWait}
	 */
	public static FluentWait<WebDriver> newWait(WebDriver Driver)
	{
		return new FluentWait<WebDriver>(Driver);
	}
	/**
	 * Shorthand for the commonly used
	 * {@code new FluentWait<WebDriver>(GlobalVariables.driver.get(), clock,
	 * sleeper)}.
	 *
	 * @param clock
	 *            The clock to use when measuring the timeout.
	 * @param sleeper
	 *            Used to put the thread to sleep between evaluation loops.
	 * @return a new {@link FluentWait}
	 */
	public static FluentWait<WebDriver> newWait(Clock clock, Sleeper sleeper)
	{
		return new FluentWait<WebDriver>(driver, clock, sleeper);
	}
	/**
	 * Creates a {@link FluentWait} whose {@code until} methods do not throw
	 * {@link TimeoutException} but otherwise behaves like a normal
	 * {@link FluentWait}. For example, it will throw
	 * {@link NoSuchElementException} by default.
	 *
	 * @return a new {@link FluentWait} whose {@code until} methods return
	 *         {@code null} instead of timing out
	 */
	public static FluentWait<WebDriver> newWaitIgnoringTimeout()
	{
		return new IgnoreTimeoutWait<WebDriver>(driver);
	}
	/**
	 * Creates a {@link FluentWait} whose {@code until} methods do not throw
	 * {@link TimeoutException} but otherwise behaves like a normal
	 * {@link FluentWait}. For example, it will throw
	 * {@link NoSuchElementException} by default.
	 *
	 * @param clock
	 *            The clock to use when measuring the timeout.
	 * @param sleeper
	 *            Used to put the thread to sleep between evaluation loops.
	 * @return a new {@link FluentWait} whose {@code until} methods return
	 *         {@code null} instead of timing out
	 */
	public static FluentWait<WebDriver> newWaitIgnoringTimeout(Clock clock, Sleeper sleeper, WebDriver Driver)
	{
		return new IgnoreTimeoutWait<WebDriver>(Driver);
	}
	private static class IgnoreTimeoutWait<T> extends FluentWait<T>
	{
		public IgnoreTimeoutWait(T input, Clock clock, Sleeper sleeper)
		{
			super(input, clock, sleeper);
		}
		public IgnoreTimeoutWait(T input)
		{
			super(input);
		}
		@Override
		public <V> V until(Function<? super T, V> isTrue)
		{
			try
			{
				return super.until(isTrue);
			} catch (TimeoutException e)
			{
				return null;
			}
		}
	}
	/**
	 * Creates a new {@link FluentWait} which ignores
	 * {@link NoSuchElementException} by default.
	 *
	 * @return a new {@link FluentWait} which ignores
	 *         {@link NoSuchElementException}.
	 */
	public static void implicitWait(long time)
	{
		driver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
	}
	public static boolean isElementExisting(WebElement we, int time)
	{
		try
		{
			implicitWait(time);
			// Utils.getDriver().findElement(By.xpath(we.getText()));
			if (we.isDisplayed())
				return true;
			else
				return false;
		} catch (NoSuchElementException e)
		{
			return false;
		}
	}
	public static void waitForElementToDisplay(WebElement element)
	{
		newTolerantWait(driver).withTimeout(defaultTimeOut, TimeUnit.SECONDS).pollingEvery(1, TimeUnit.SECONDS)
				.ignoring(ElementNotVisibleException.class).ignoring(StaleElementReferenceException.class)
				.until(ExpectedConditions.visibilityOf(element));
	}
	// this is to check the presence of element if the locator returns more than
	// one web element
	public static void waitForElementToDisplay(List<WebElement> element)
	{
		newTolerantWait(driver).withTimeout(defaultTimeOut, TimeUnit.SECONDS).pollingEvery(1, TimeUnit.SECONDS)
				.ignoring(ElementNotVisibleException.class).ignoring(StaleElementReferenceException.class)
				.until(ExpectedConditions.visibilityOf(element.get(0)));
	}
	public static void waitForElementToDisplay(WebElement element, int secs)
	{
		newTolerantWait(driver).withTimeout(secs, TimeUnit.SECONDS).pollingEvery(1, TimeUnit.SECONDS)
				.ignoring(ElementNotVisibleException.class).ignoring(StaleElementReferenceException.class)
				.until(ExpectedConditions.visibilityOf(element));
	}
	/**
	 * @description: Wait for element to enable
	 * @param element
	 */
	public static void waitForElementToEnable(WebElement element)
	{
		newTolerantWait(driver).withTimeout(defaultTimeOut, TimeUnit.SECONDS).pollingEvery(1, TimeUnit.SECONDS)
				.ignoring(ElementNotVisibleException.class).until(ExpectedConditions.elementToBeClickable(element));
	}
	/**
	 * @description: WebDriver Wait for element to display by locator
	 * @param by
	 * 
	 */
	public static void waitForElementToDisplay(By by)
	{
		newTolerantWait(driver).withTimeout(defaultTimeOut, TimeUnit.SECONDS).pollingEvery(1, TimeUnit.SECONDS)
				.ignoring(ElementNotVisibleException.class).ignoring(StaleElementReferenceException.class)
				.until(ExpectedConditions.visibilityOfElementLocated(by));
	}
	/**
	 * @description: WebDriver Wait for element to display by locator
	 * @param by
	 * @param secs
	 */
	public static void waitForElementToDisplay(By by, int secs)
	{
		wait = new WebDriverWait(driver, secs);
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
	}
	/**
	 * @description : Waits for the page to load for about 30 seconds.
	 */
	public static void waitForPageLoad()
	{
		ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>()
		{
			public Boolean apply(WebDriver driver)
			{
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
			}
		};
		WebDriverWait wait = new WebDriverWait(driver, defaultTimeOut);
		try
		{
			wait.until(expectation);
		} catch (Throwable error)
		{
			logger.error(" Page load is timing out :" + error.getMessage());
			Assert.assertFalse(false, "Timeout waiting for Page Load Request to complete.");
		}
	}
	public static String getScreenPath(String testScriptName) throws IOException
	{
		String timeStamp = getCurrTimeStamp();
		
			File ff = new File(System.getProperty("user.dir") + "/Screens");
			if (!ff.exists())
			{
				ff.mkdir();
			}
			if (InitTests.CaptureScreenshotOnFail.equalsIgnoreCase("true"))
			{
				// TODO Auto-generated method stub
				File f = ((TakesScreenshot) (driver)).getScreenshotAs(OutputType.FILE);
				FileUtils.copyFile(f, new File(
						System.getProperty("user.dir") + "/Screens/" + testScriptName + "_" + timeStamp + ".png"));
				return "../Screens/" + testScriptName + "_" + timeStamp + ".png";
			} else
			{
				return "../Screens";
			}
		
		
	}
	public static void initTestNgSoftAssert()
	{
		softAssert = new SoftAssert();
	}
	public static void switchToFrame(WebElement frameLocator)
	{
		driver.switchTo().frame(frameLocator);
		test.log(LogStatus.INFO, "switchToFrame()", "navigated to inside frame " + frameLocator);
		System.out.println("navigated inside frame " + driver.getTitle());
	}
	public static void switchToDefaultContent()
	{
		driver.switchTo().defaultContent();
		test.log(LogStatus.INFO, "switchToDefaultContent()", "navigated to default content");
		System.out.println("navigated to default content " + driver.getTitle());
	}
	public static void waitForElementToDisappear(List<WebElement> elements)
	{
		newTolerantWait(driver).withTimeout(defaultTimeOut, TimeUnit.SECONDS).pollingEvery(1, TimeUnit.SECONDS)
				.ignoring(ElementNotVisibleException.class).ignoring(StaleElementReferenceException.class)
				.until(ExpectedConditions.invisibilityOfAllElements(elements));
	}
	public static void waitForElementToDisappear(List<WebElement> elements, int secs)
	{
		newTolerantWait(driver).withTimeout(secs, TimeUnit.SECONDS).pollingEvery(1, TimeUnit.SECONDS)
				.ignoring(ElementNotVisibleException.class).ignoring(StaleElementReferenceException.class)
				.until(ExpectedConditions.invisibilityOfAllElements(elements));
	}
	public static void navigateBack()
	{
		driver.navigate().back();
	}
}

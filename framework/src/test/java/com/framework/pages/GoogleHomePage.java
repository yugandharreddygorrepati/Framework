package com.framework.pages;
import static com.framework.lib.WebElementUtil.setInput;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.framework.lib.Driver;
/**
 * @author YugandharReddyGorrep
 *
 */
public class GoogleHomePage extends Driver
{
	@FindBy(name = "q")
	public static WebElement searchInput;
	@FindBy(css = "h3.r a")
	public static WebElement firstLnk;
	public GoogleHomePage()
	{
		PageFactory.initElements(driver, this);
	}
	public void search(String searchTxt)
	{
		// TODO Auto-generated method stub
		waitForElementToDisplay(searchInput);
		setInput(searchInput, searchTxt);
		searchInput.sendKeys(Keys.ENTER);
		
		
	}
	public String getFirstLnkDisplayed()
	{
		waitForElementToDisplay(firstLnk);
		// TODO Auto-generated method stub
		return firstLnk.getText();
	}
}
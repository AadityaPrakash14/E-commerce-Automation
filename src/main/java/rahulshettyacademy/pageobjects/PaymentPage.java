package rahulshettyacademy.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import rahulshetthyacademy.abstractcomponents.AbstractComponets;

public class PaymentPage extends AbstractComponets {
	
	WebDriver driver;
	JavascriptExecutor js; 
	
	@FindBy(css="input[placeholder='Select Country']")
	WebElement country;
	
	@FindBy(css=".action__submit")
	WebElement placeOrderEle;
	
	@FindBy(xpath="(//span[contains(@class, 'ng-star-inserted')])[2]")
	WebElement clickCountry;
	
	By shippingInfo = By.cssSelector("input[placeholder='Select Country']");
	By countryNames = By.cssSelector("section[class*=\'ta-results\']");
	By placeOrder = By.cssSelector(".action__submit");
	
	public PaymentPage(WebDriver driver)
	{
		super(driver);
		this.driver =driver;
		PageFactory.initElements(driver, this);
		this.a =new Actions(driver);
		js= (JavascriptExecutor)driver;
	}
	
	Actions a ;
	public void scrollDown()
	{
		// Step 1: Wait and type "India" in the input field
	    waitForElementToAppear(shippingInfo);
		js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
	}
	
	public void placeOrder(String countryName)
	{
	    
	    country.click();
	    a.sendKeys(country, countryName).build().perform();

	    // Step 2: Wait for suggestions to load
	    waitForElementToAppear(countryNames);

	    // Step 3: Now click the appropriate option
	    clickCountry.click();  // only now it's safe to click

	}
	public ConfirmationPage submitOrder()
	{
		 // Step 4: Click the 'Place Order' button
		waitForElementToAppear(placeOrder);
	    placeOrderEle.click();
	    return new ConfirmationPage(driver);
	}
}

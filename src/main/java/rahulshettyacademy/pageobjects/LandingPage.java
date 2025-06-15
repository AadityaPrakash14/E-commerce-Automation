package rahulshettyacademy.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import rahulshetthyacademy.abstractcomponents.AbstractComponets;

public class LandingPage extends AbstractComponets {
	
	WebDriver driver;
	public LandingPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	//WebElement userEmail = driver.findElement(By.id("usermail"));
	
	@FindBy(id="userEmail")
	WebElement userEmail;
	
	@FindBy(id = "userPassword")
	WebElement passwordEle;
	
	@FindBy(id = "login")
	WebElement submit;
	
	@FindBy(css="[class*='ng-trigger-flyInOut']")
	WebElement errorMessage;
	
	By errorMessageLocator = By.cssSelector("[class*='ng-trigger-flyInOut']");

	public ProductCatalogue fillform(String email , String password)
	{
		userEmail.sendKeys(email);
		passwordEle.sendKeys(password);
		submit.click();
		ProductCatalogue productCatalogue = new ProductCatalogue(driver);
		return productCatalogue;
	}
	
	public void goTo()
	{
		driver.get("https://rahulshettyacademy.com/client");
	}
	 public String getErrorMessage()
	 {
		 waitForElementToAppear(errorMessageLocator);
		  return errorMessage.getText();
	 }

}

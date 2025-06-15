package rahulshettyacademy.pageobjects;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import rahulshetthyacademy.abstractcomponents.AbstractComponets;

public class ConfirmationPage extends AbstractComponets {
	WebDriver driver;
	public ConfirmationPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
		
	}
	
	@FindBy(css=".hero-primary")
	WebElement confirmationMessage;

	
	public  String getConfirmationMessage()
	{
		return confirmationMessage.getText();
	}
	
}

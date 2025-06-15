package rahulshettyacademy.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


import rahulshetthyacademy.abstractcomponents.AbstractComponets;

public class CartPage extends AbstractComponets {
	
	WebDriver driver;
	
	public CartPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver,this);
	}
	
	@FindBy(xpath="//div/h3")
	List<WebElement> cartProducts;
	
	@FindBy(xpath="//ul[@style=\"list-style-type: none;\"]/li//button[@class='btn btn-primary']")
	WebElement checkoutButton;
	
	
	public Boolean verifyProductDisplay(String productName) {
		waitForElementToAppear(productOrder);
	    Boolean match = cartProducts.stream()
	        .anyMatch(cartProduct -> cartProduct.getText().equalsIgnoreCase(productName));
	    return match;
	}

	
	By productOrder = By.cssSelector(".cartWrap.ng-star-inserted");
	
	public PaymentPage checkoutProduct(String productName)
	{
		
		checkoutButton.click();
		return new PaymentPage(driver);
	}
	
	}
	

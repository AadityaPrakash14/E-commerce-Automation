package rahulshettyacademy.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


import rahulshetthyacademy.abstractcomponents.AbstractComponets;

public class OrderPage extends AbstractComponets {
	
	WebDriver driver;
	public OrderPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver,this);
	}
	
	public List<WebElement> getProductRowsByName(String productName) {
	    return driver.findElements(By.xpath("//tr/td[text()='" + productName + "']"));
	}

	
	public Boolean verifyOrderDisplay(String productName) {
	    Boolean match = getProductRowsByName(productName).stream()
	        .anyMatch(product -> product.getText().equalsIgnoreCase(productName));
	    return match;
	}

	

	
	}
	

package rahulshetthyacademy.abstractcomponents;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import rahulshettyacademy.pageobjects.CartPage;
import rahulshettyacademy.pageobjects.OrderPage;

public class AbstractComponets {
	
	WebDriver driver;
	
	public AbstractComponets(WebDriver driver) {
		this.driver = driver;
	}
	
//	public CartPage goToCartPage()
//	{
//		WebElement cartIcon = driver.findElement(By.xpath("(//button[@class='btn btn-custom'])[3]"));
//		cartIcon.click();
//		CartPage cartpage = new CartPage(driver);
//		return cartpage;
//		
//	}
//	public OrderPage goToOrderpage()
//	{
//		WebElement orderIcon = driver.findElement(By.xpath("(//button[@class='btn btn-custom'])[2]"));
//		orderIcon.click();
//		OrderPage orderpage = new OrderPage(driver);
//		return orderpage;
//	}
	public CartPage goToCartPage() {
	    clickElement(By.xpath("(//button[@class='btn btn-custom'])[3]"));
	    return new CartPage(driver);
	}

	public OrderPage goToOrderpage() {
	    clickElement(By.xpath("(//button[@class='btn btn-custom'])[2]"));
	    return new OrderPage(driver);
	}

	public void waitForElementToAppear(By findBy)
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOfElementLocated(findBy));
	}
	
	public void waitForElementToDisappear(By findBy)
	{
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(7));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(findBy));
	}
	public void clickElement(By locator) {
	    waitForElementToDisappear(By.cssSelector(".ngx-spinner-overlay")); // wait for spinner
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
	    element.click();
	}

 	


}

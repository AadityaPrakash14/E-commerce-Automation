package rahulshettyacademy.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import rahulshetthyacademy.abstractcomponents.AbstractComponets;

public class ProductCatalogue extends AbstractComponets{

		WebDriver driver;
		
		public ProductCatalogue(WebDriver driver)
		{
			super(driver);
			this.driver = driver;
			PageFactory.initElements(driver, this);
		}
		
		@FindBy(css=".mb-3")
	    List<WebElement> products;
		
		By toast = By.cssSelector(".toast-container");
		By productsBY = By.cssSelector(".mb-3");
		
		public List<WebElement> getProductList()
		{
			waitForElementToAppear(productsBY);
			return products;
			
		}
		
		public WebElement getProductByName(String productName)
		{
			WebElement prod2 = getProductList().stream()
					.filter(product -> product.findElement(By.xpath(".//div[@class=\'card-body\']/h5/b")).getText().equals(productName)).findFirst()
					.orElse(null);
			return prod2;
		}
		
		public void addProductToCart(String productName)
		{
			WebElement prod = getProductByName(productName);
			prod.findElement(By.xpath( ".//b[text()='"+ productName +"']/ancestor::div[@class='card-body']//button[2]")).click();
			System.out.println("hii debug");
			waitForElementToDisappear(toast);
			
		}
}

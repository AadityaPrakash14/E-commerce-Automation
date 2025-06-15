package rahulshettyacademy.Tests;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.github.bonigarcia.wdm.WebDriverManager;
import rahulshettyacademy.pageobjects.LandingPage;

public class StandAloneTest {
	public static void main(String[] args) throws InterruptedException {
		String productName = "ZARA COAT 3";
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		driver.get("https://rahulshettyacademy.com/client");
		
		driver.findElement(By.id("userEmail")).sendKeys("princeaditya2001@gmail.com");
		driver.findElement(By.id("userPassword")).sendKeys("Aa@12345678");
		driver.findElement(By.id("login")).click();
		
		Actions a = new Actions(driver);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3")));

		List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));
		WebElement prod = products.stream()
				.filter(product -> product.findElement(By.cssSelector("b")).getText().equals("ZARA COAT 3")).findFirst()
				.orElse(null);
		prod.findElement(By.cssSelector(".btn.w-10.rounded")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("toast-container")));
	
		//ng-animating
//		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));
		driver.findElement(By.xpath("(//i[@class='fa fa-shopping-cart'])[1]")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".cartWrap.ng-star-inserted")));
		List<WebElement> cartProducts = driver.findElements(By.xpath("//div/h3"));
		Boolean match = cartProducts.stream().anyMatch(cartProduct->cartProduct.getText().equalsIgnoreCase(productName));
		Assert.assertTrue(match);
		driver.findElement(By.xpath("//ul[@style=\"list-style-type: none;\"]/li//button[@class='btn btn-primary']")).click();
		
		//driver.findElement(By.xpath("//li[@class='totalRow']//button[contains(@class, 'btn-primary')]")).click();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(("input[placeholder='Select Country']"))));
		//a.sendKeys(driver.findElement(By.cssSelector("input[class*=\'ng-valid\'][class*=\'ng-dirty\']")),"princeaditya2001@gmail.com").build().perform();
		a.sendKeys(driver.findElement(By.cssSelector("input[placeholder='Select Country']")),"India").build().perform();
		
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("section[class*=\'ta-results\']")));
		
		driver.findElement(By.xpath("(//span[contains(@class, 'ng-star-inserted')])[2]")).click();
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
		//js.executeScript("window.scrollBy(0,500);");
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".action__submit")));
		driver.findElement(By.cssSelector(".action__submit")).click();
		
		String message = driver.findElement(By.cssSelector(".hero-primary")).getText();
		System.out.println(message);
		Assert.assertTrue(message.equalsIgnoreCase("Thankyou for the order."));
		driver.close();
		
	}
}

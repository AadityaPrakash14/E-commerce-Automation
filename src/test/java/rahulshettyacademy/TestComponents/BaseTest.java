package rahulshettyacademy.TestComponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.bonigarcia.wdm.WebDriverManager;
import rahulshettyacademy.pageobjects.LandingPage;

public class BaseTest {
	protected WebDriver driver;
	protected LandingPage landingpage;
	public WebDriver initializeDriver() throws IOException
	{
		Properties prop = new Properties();
		  // Load the file from classpath (src/main/resources)
	    try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("GlobalData.properties")) {
	        if (inputStream == null) {
	            throw new RuntimeException("File not found: GlobalData.properties");
	        }
	        prop.load(inputStream);
	    }

	    
//		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\main\\java\\rahulshettyacademy\\resources\\GlobalData.properties");
//		prop.load(fis);
		String browserName = System.getProperty("browser")!=null ? System.getProperty("browser") : prop.getProperty("browser");
		
		
		if(browserName.toLowerCase().contains("chrome"))
		{
			ChromeOptions options = new ChromeOptions();
			if(browserName.toLowerCase().contains("headless"))
			{
				options.addArguments("headless");
			}
			WebDriverManager.chromedriver().avoidResolutionCache().setup();
			driver = new ChromeDriver(options);
			driver.manage().window().setSize(new Dimension(1440,900));
		}
		else if(browserName.toLowerCase().contains("firefox"))
		{
			FirefoxOptions options = new FirefoxOptions();

			if (browserName.toLowerCase().contains("headless")) {
			    options.addArguments("headless");
			}

			// Setup Firefox driver
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver(options);

			// Set window size (needed in headless mode to avoid 0x0 resolution)
			driver.manage().window().setSize(new Dimension(1440, 900));
		}
		else if(browserName.toLowerCase().contains("edge"))
		{
			EdgeOptions options = new EdgeOptions();
			if(browserName.toLowerCase().contains("headess"))
			{
				options.addArguments("headless");
			}
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver(options);
			driver.manage().window().setSize(new Dimension(1440,900));
		}
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		return driver;
	}
	public List<HashMap<String,String>> getJsonDataToMap(String filePath)  throws IOException{
		
	//read json to string
	String jsonContent = FileUtils.readFileToString(new File(filePath),StandardCharsets.UTF_8);
	
	// string to hashmap jackson Databind
	ObjectMapper mapper = new ObjectMapper();
	List<HashMap<String,String>> data = mapper.readValue(jsonContent, new TypeReference<List<HashMap<String,String>>>(){});
	return data;
}
	public String getScreenshot(String testCaseName,WebDriver driver) throws IOException
    {
    	TakesScreenshot ts = (TakesScreenshot)driver;
    	File source = ts.getScreenshotAs(OutputType.FILE);
		File file = new File(System.getProperty("user.dir")+"//reports//"+testCaseName+".png");
		FileUtils.copyFile(source, file);
		return System.getProperty("user.dir")+File.separator+"reports"+File.separator+testCaseName+".png";
    }  
	
	@BeforeMethod(alwaysRun=true)
	public LandingPage launchApplication() throws IOException
	{
		driver = initializeDriver();
		landingpage = new LandingPage(driver);
		landingpage.goTo();
		return landingpage;
	}
	
	@AfterMethod(alwaysRun=true)
	public void tearDown() {
	    if (driver != null) {
	        try {
	            driver.quit(); // prefer quit() to close()
	        } catch (Exception e) {
	            System.out.println("Exception during teardown: " + e.getMessage());
	        }
	    }
	}

	
}

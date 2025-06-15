package rahulshettyacademy.Tests;

import java.io.IOException;

import java.util.List;

import rahulshettyacademy.TestComponents.Retry;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


import rahulshettyacademy.TestComponents.BaseTest;
import rahulshettyacademy.pageobjects.CartPage;
import rahulshettyacademy.pageobjects.ConfirmationPage;
import rahulshettyacademy.pageobjects.LandingPage;
import rahulshettyacademy.pageobjects.PaymentPage;
import rahulshettyacademy.pageobjects.ProductCatalogue;

public class ErrorValidationsTest extends BaseTest {
    @Test(groups="ErrorHandling",retryAnalyzer=Retry.class)
    public void loginErrorValidation() throws IOException {
     
    
        landingpage.fillform("princeaditya2001@gmail.com", "Aa@123478");
        Assert.assertEquals("Incorrect email or password.", landingpage.getErrorMessage());
     
    }
    @Test
    public void productErrorValiationr() throws IOException {
        String productName = "ZARA COAT 3";
    
  
        ProductCatalogue productCatalogue = landingpage.fillform("princeaditya2001@gmail.com", "Aa@12345678");

        List<WebElement> products = productCatalogue.getProductList();
        productCatalogue.addProductToCart(productName);
        
        CartPage cartPage = productCatalogue.goToCartPage();
        Boolean match = cartPage.verifyProductDisplay(productName);
        Assert.assertTrue(match);

     
    }
}

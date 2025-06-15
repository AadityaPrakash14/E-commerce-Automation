package rahulshettyacademy.Tests;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import rahulshettyacademy.TestComponents.BaseTest;
import rahulshettyacademy.pageobjects.CartPage;
import rahulshettyacademy.pageobjects.ConfirmationPage;
import rahulshettyacademy.pageobjects.OrderPage;
import rahulshettyacademy.pageobjects.PaymentPage;
import rahulshettyacademy.pageobjects.ProductCatalogue;

public class SubmitOrderTest extends BaseTest {
	
    @Test(dataProvider="getData")
    public void submitOrder(HashMap<String,String> input) throws IOException, InterruptedException {
  
        ProductCatalogue productCatalogue = landingpage.fillform(input.get("email"), input.get("password"));
        List<WebElement> products = productCatalogue.getProductList();
        productCatalogue.addProductToCart(input.get("productName"));
        CartPage cartPage = productCatalogue.goToCartPage();
        Boolean match = cartPage.verifyProductDisplay(input.get("productName"));
        Assert.assertTrue(match);

        PaymentPage paymentPage = cartPage.checkoutProduct(input.get("productName"));
        paymentPage.scrollDown();
        Thread.sleep(4000);
        paymentPage.placeOrder("India");

        ConfirmationPage confirmationPage = paymentPage.submitOrder();
        String message = confirmationPage.getConfirmationMessage();
        Assert.assertTrue(message.equalsIgnoreCase("Thankyou for the order."));
     
    }
    
    @Test(dependsOnMethods="submitOrder",groups="unitTesting")
    public void OrderHistoryPage() throws IOException
    {
    	ProductCatalogue productCatalogue = landingpage.fillform("princeaditya2001@gmail.com","Aa@12345678");
    	OrderPage orderPage = productCatalogue.goToOrderpage();
    	orderPage.getProductRowsByName("ZARA COAT 3");
    	Assert.assertTrue(orderPage.verifyOrderDisplay("ZARA COAT 3"));
    	
    }
    
    @DataProvider
    public Object[][] getData() throws IOException
    {

    	List<HashMap<String,String>> data = getJsonDataToMap(System.getProperty("user.dir")+"\\src\\test\\java\\rahulshettyacademy\\data\\PurchaseOrder.json");
    	return  new Object[][] {{data.get(0)},{data.get(1)}};
    }
}

//@DataProvider
//public Object[][] getData() throws IOException {
//    DataReader dataReader = new DataReader();
//    List<HashMap<String, String>> dataList = dataReader.getJsonDataToMap();
//
//    Object[][] returnData = new Object[dataList.size()][1];
//
//    for (int i = 0; i < dataList.size(); i++) {
//        returnData[i][0] = dataList.get(i);
//    }
//
//    return returnData;
//}

//HashMap<String,String> map = new HashMap<String,String>();
//map.put("email","princeaditya2001@gmail.com");
//map.put("password", "Aa@12345678");
//map.put("productName", "ZARA COAT 3");
//HashMap<String,String> map1 = new HashMap<String,String>();
//map.put("email","princeaditya2001@gmail.com");
//map.put("password", "Aa@12345678");
//map.put("productName", "ZARA COAT 3");
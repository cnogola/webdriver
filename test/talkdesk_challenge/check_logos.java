package talkdesk_challenge;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class check_logos {

	@Test
	public void webdriver_test() {
		// Create a new instance of the Firefox driver
		// Firefox's geckodriver requires its location to be specified.
		System.setProperty("webdriver.gecko.driver","C:\\Users\\Goncalo\\utils\\geckodriver.exe"); 
        WebDriver driver = new FirefoxDriver();

        // And now use this to visit Talkdesk homepage
        driver.get("http://www.talkdesk.com");
        
        // iterate through all customer logo present at homepage
        By homeLocator = By.className("logo");
        int homeCount = driver.findElements(homeLocator).size();

        int aa = 0;
        while (aa < homeCount) {
        	List<WebElement> homeCustomers = driver.findElements(homeLocator);
        	WebElement i = homeCustomers.get(aa);
        	aa++;
        	
        	// skip non img objects with class = "logo"
        	if (!i.getTagName().equals("img"))
        		continue;
        	String homeCustomerName = i.getAttribute("alt");
        	System.out.println(homeCustomerName + " customer logo at homepage.");
        	
            // Navigate to customers page
            driver.navigate().to("http://www.talkdesk.com/customers");
            
            // at this point homeCustomer WebElement is gone stale!
            // scroll down and wait until logos are present 
            JavascriptExecutor jsx = (JavascriptExecutor)driver;
            jsx.executeScript("window.scrollBy(0,3000)", "");//{top: 3750,behavior: 'smooth'}
            
            WebDriverWait wait = new WebDriverWait(driver, 3);
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("button__text")));
            
            // iterate through all customer logo present at customers page
            By customersLocator = By.className("logo");
            int customersCount = driver.findElements(customersLocator).size();
            
        	int bb = 0;
        	while ( bb < customersCount) {
            	List<WebElement> customers = driver.findElements(customersLocator);
        		WebElement j = customers.get(bb);
            	bb++;
            	// skip non img objects with class = "logo"
            	if (!j.getTagName().equals("img"))
            		continue;
            	
            	// once customer logo from homepage is found at customers page terminate loop
            	else {
                	String customerName = j.getAttribute("alt");
            		if (homeCustomerName.equals(customerName)) {
                		System.out.println(customerName + " customer logo at customers page.");
            			break;
            		}
            	}
            }
        	driver.navigate().back();
        }
        driver.quit();
	}

}

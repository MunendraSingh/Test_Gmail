package hello;		
import org.openqa.selenium.WebDriver;			
import org.openqa.selenium.firefox.FirefoxDriver;					

public class NewTest {	
	
	public static void main (String[] arg){
		
		System.setProperty("webdriver.gecko.driver", "C:\\Users\\ASUS\\Desktop\\selenium_set up files\\geckodriver.exe");
	
	WebDriver driver = new FirefoxDriver();
	
	driver.get("https://www.google.com");
	driver.quit();
	}
	}		

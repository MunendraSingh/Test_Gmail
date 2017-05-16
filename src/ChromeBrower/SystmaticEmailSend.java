package ChromeBrower;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;


public class SystmaticEmailSend {
	WebDriver driver = new ChromeDriver();
	
	public void browserOpen(){
		driver.get("https://mail.google.com");
		driver.manage().window().maximize();
	}
	
	public void login () throws InterruptedException, IOException{
		  FileInputStream fis = new FileInputStream("C:\\Users\\ASUS\\Desktop\\Book1.xls");
		  HSSFWorkbook wb = new HSSFWorkbook(fis);
		  HSSFSheet Test=wb.getSheetAt(0);
		  String data2 = Test.getRow(1).getCell(0).getStringCellValue();
		  String data3 = Test.getRow(1).getCell(1).getStringCellValue();		  
		  WebElement emailBox = driver.findElement(By.id("identifierId"));
		  emailBox.sendKeys(data2);
		  emailBox.sendKeys(Keys.RETURN);
		  
		  Thread.sleep(5000); 
		  
		  WebElement pswdBox = driver.findElement(By.name("password"));
		  pswdBox.sendKeys(data3);
		  pswdBox.sendKeys(Keys.RETURN);
		    
		
	}
	
	public ArrayList<String> excelReadITR(int colNo) throws IOException{
		  FileInputStream fis = new FileInputStream("C:\\Users\\ASUS\\Desktop\\Book1.xls");
		  HSSFWorkbook wb = new HSSFWorkbook(fis);
		  HSSFSheet Test=wb.getSheetAt(0);
		  Iterator<Row> rowIterator = Test.iterator();
		  rowIterator.next();
		  ArrayList<String> list = new ArrayList<String>();
		  while (rowIterator.hasNext()){
			  list.add(rowIterator.next().getCell(colNo).getStringCellValue());
			
		  }
		return list;
	}
	
	public void mailSend() throws InterruptedException, IOException{
		driver.switchTo().defaultContent();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	    Thread.sleep(5000);
	    
	    ArrayList<String> emailID = excelReadITR(2);
	    ArrayList<String> mailBody = excelReadITR(3);
	    ArrayList<String> sss = excelReadITR(4);
	    
	    for (int i=0; i<=emailID.size(); i++){
	    	driver.findElement(By.xpath("//div[contains(text(),'COMPOSE')]")).click();

	    	driver.findElement(By.className("vO")).sendKeys(emailID.get(i));

		    driver.findElement(By.className("aoT")).sendKeys("Email from webdriver");

		    driver.findElement(By.cssSelector("div.Am.Al.editable.LW-avf")).sendKeys(mailBody.get(i));
		    
			File ss = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(ss, new File("D:\\J2EE\\workspace\\Screenshots\\"+sss.get(i)+".png"));
		    
		    driver.findElement(By.xpath("//div[text()='Send']")).click();
		    Thread.sleep(5000);
	    	
	    }
	    
	}
	
	public static void main(String[] args) throws InterruptedException, IOException {
		System.setProperty("webdriver.chrome.driver", "D:\\J2EE\\workspace\\SeleniumWebDrivers\\chromedriver_2.29_win32\\chromedriver.exe");
		SystmaticEmailSend open = new SystmaticEmailSend();
		open.browserOpen();
		open.login();
		open.excelReadITR(2);
		open.excelReadITR(3);
		open.excelReadITR(4);
		open.mailSend();
		open.driver.close();
	}

}

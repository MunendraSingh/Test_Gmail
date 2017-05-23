package randomNameMail;

	import java.io.File;
	import java.io.FileInputStream;
	import java.io.FileNotFoundException;
	import java.io.IOException;
	import java.util.ArrayList;
	import java.util.Iterator;
import java.util.Properties;
import java.util.Random;
	import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFSheet;
	import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
	import org.openqa.selenium.By;
	import org.openqa.selenium.Keys;
	import org.openqa.selenium.OutputType;
	import org.openqa.selenium.TakesScreenshot;
	import org.openqa.selenium.WebDriver;
	import org.openqa.selenium.WebElement;
	import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;


	public class MailsendRandomEmail {
		static int NumofMail = Integer.parseInt(JOptionPane.showInputDialog(null,"Enter number of mails"));
		//DesiredCapabilities dc = null;
		WebDriver driver = null;
		String projDir = System.getProperty("user.dir");
		
		
		public void browserOpen(){
			try{
				//dc = DesiredCapabilities.chrome();
				driver = new ChromeDriver();
				//dc = DesiredCapabilities.internetExplorer();
				//dc.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
				//driver = new InternetExplorerDriver();
				Properties prop = new Properties();
				prop.load(new FileInputStream(projDir + "\\src\\login.properties"));
				String link = prop.get("link").toString();			
				driver.get(link);
				driver.manage().window().maximize();
			}
			
			catch(Exception e){
				
				e.printStackTrace();
				
			}
			}
		
		public void login () throws InterruptedException, IOException{
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			try{
				Properties prop = new Properties();
				prop.load(new FileInputStream(projDir + "\\src\\login.properties"));
				String emailID = prop.get("emailID").toString();
				String pswd = prop.get("password").toString();
				WebElement emailBox = driver.findElement(By.id("identifierId"));
				emailBox.sendKeys(emailID);
				emailBox.sendKeys(Keys.RETURN);
			  
				Thread.sleep(5000); 
			  
				WebElement pswdBox = driver.findElement(By.name("password"));
				pswdBox.sendKeys(pswd);
				pswdBox.sendKeys(Keys.RETURN);
			    
			
			}
		
		catch(Exception e) {
			
			e.printStackTrace();
			
			}
			}

		public ArrayList<String> excelReadITR(int colNo) throws IOException{
			  FileInputStream fis = new FileInputStream(projDir+"\\src\\Random_data_repository.xls");
			  HSSFWorkbook wb = new HSSFWorkbook(fis);
			  HSSFSheet Test=wb.getSheetAt(0);
			  Iterator<Row> rowIterator = Test.iterator();
			  rowIterator.next();
			  ArrayList<String> list = new ArrayList<String>();
			  while (rowIterator.hasNext()){
				  Row r = rowIterator.next();
				  try{
				  list.add(r.getCell(colNo).getStringCellValue());
				  }catch(Exception e){
					  double d  = r.getCell(colNo).getNumericCellValue();
					  int i = (int)d;
					  String s = i + "";
					  list.add(s);
				  }
				
			  }
			return list;
		}
		
		public void mailSend() throws InterruptedException, IOException{
			driver.switchTo().defaultContent();
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		    Thread.sleep(5000);
		    	
/*			  FileInputStream fis = new FileInputStream(projDir+"\\src\\Random_data_repository.xls");
			  HSSFWorkbook wb = new HSSFWorkbook(fis);
			  HSSFSheet Test=wb.getSheetAt(0);*/
			  ArrayList<String> emailID = excelReadITR(2);
			  ArrayList<String> mailBody = excelReadITR(3);
		    	
			  	for (int j=1; j<=NumofMail;j++){
			  		/*if (j==NumofMail){
			  			driver.close();
			  		}*/
			  		Random randomGenerator = new Random();
			  		int index = randomGenerator.nextInt(emailID.size());
			  		String var1 = emailID.get(index);
			  		System.out.println(var1.toString());
			  		
			  		Random randomGenerator1 = new Random();
			  		int index1 = randomGenerator1.nextInt(mailBody.size());
			  		String var2  = mailBody.get(index1);
			  		System.out.println(var2.toString());

		    	driver.findElement(By.xpath("//div[contains(text(),'COMPOSE')]")).click();

		    	driver.findElement(By.className("vO")).sendKeys(var1);

			    driver.findElement(By.className("aoT")).sendKeys("Email from webdriver");

			    driver.findElement(By.cssSelector("div.Am.Al.editable.LW-avf")).sendKeys(var2);
			    
				File ss = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
				FileUtils.copyFile(ss, new File(projDir+"\\Screenshots\\Screenshot"+j+".png"));
			    
			    driver.findElement(By.xpath("//div[text()='Send']")).click();
			    Thread.sleep(5000);
		    	
		    }
		    
		}
		
		public static void main(String[] args) throws InterruptedException, IOException {
			String projDir = System.getProperty("user.dir");
			System.setProperty("webdriver.chrome.driver", projDir+"\\src\\chromedriver.exe");
			//System.setProperty("webdriver.ie.driver", projDir+"\\src\\IEDriverServer.exe");
			MailsendRandomEmail open = new MailsendRandomEmail();
			MailSend newmail = new MailSend();
			ChromeKill killsession = new ChromeKill();
			open.browserOpen();
			open.login();
			open.excelReadITR(2);
			open.excelReadITR(3);
			open.mailSend();
			newmail.mailOutlook();
			killsession.chromeKill();
			//open.driver.close();
		}

	}


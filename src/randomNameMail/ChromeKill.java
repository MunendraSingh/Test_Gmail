package randomNameMail;

public class ChromeKill {
	public void chromeKill(){
		try{
			Runtime.getRuntime().exec("taskKill /F /IM chrome.exe");
			Runtime.getRuntime().exec("taskKill /F /IM chromedriver.exe");
		}
		catch(Exception e){
			e.printStackTrace();
		}

}
}

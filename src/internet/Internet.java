package internet;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import util.Config;

public class Internet {

	private RemoteWebDriver driver;
	private JavascriptExecutor jsexcute;
	private Config config;
	
	public Internet() throws IOException{
		config = new Config();
	}
	public boolean connect(){

		try {
			createChromeDriver();
			jsexcute = (JavascriptExecutor) driver;
			driver.get(config.getProperty("url"));
			if (!driver.getTitle().equals( config.getProperty("logoutPaperTitle"))) {
				jsexcute.executeScript(config.getProperty("loginScript"));
			}
		} catch (Exception e) {
			System.err.println( new Date() );
			System.out.println("Internet.Connect error");
			e.printStackTrace();
			disConnect();
			return false;
		}
		return true;
	}

	public void disConnect() {
		createChromeDriver();
		try {
			jsexcute = (JavascriptExecutor) driver;
			driver.get(config.getProperty("url"));
			if (driver.getTitle().equals(config.getProperty("logoutPaperTitle"))) {
			
				jsexcute.executeScript(config.getProperty("logoutScript"));
				jsexcute.executeScript(config.getProperty("alertScript"));	
			}
		} catch (Exception e) {
			System.out.println( new Date() );
			System.err.println("Internet.disConnect error");
			e.printStackTrace();
		} finally {
			closeBrowser();
		}
	}

	private void closeBrowser() {
		try {
			if( null != driver ){
				 driver.close();
			}
		} catch (Exception e) {
			System.err.println(new Date() );
			System.out.println("Internet.closeBrowser error");
			e.printStackTrace();
		}
	}
	
	private void createChromeDriver(){
		if( null == driver ){
			String driverPath = config.getProperty("chromeDriverPath");
			driverPath.replace("/", File.separator );
			String current = System.getProperty("user.dir");
			System.setProperty(config.getProperty("chromeDriverName"), current + driverPath );
			driver = new ChromeDriver();
		}
	}
}

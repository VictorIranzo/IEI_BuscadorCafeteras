package automationFramework;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class FirstTestCase {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Chrome();
	}
	public static void Chrome() {
		String exePath = "C:\\Selenium\\chromedriver_win32\\chromedriver.exe";
		System.setProperty("webdriver.chrome.driver", exePath);
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--start-maximized");
		WebDriver driver = new ChromeDriver(options);
		driver.get("http://tiendas.mediamarkt.es/cafeteras-cafe");
		ArrayList<WebElement> categorias = (ArrayList<WebElement>) driver.findElements(By.className("categoryTree"));
		for(int i=0; i< categorias.size();i++) {
			System.out.println(categorias.get(i).getText());
		}
		driver.quit();
	}

}

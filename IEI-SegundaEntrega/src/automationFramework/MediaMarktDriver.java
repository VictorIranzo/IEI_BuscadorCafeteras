package automationFramework;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class MediaMarktDriver {
	private static String urlConnection = "http://tiendas.mediamarkt.es/cafeteras-cafe";
	
	public static void Search(String articulo, List<String> marcas) {
		WebDriver driver = ChromeConnection.initChromeConnection(urlConnection);
		

	}

	public static Set<String> getCategorias() {
		WebDriver driver = ChromeConnection.initChromeConnection(urlConnection);		
		ArrayList<WebElement> elementosCategoryClass = (ArrayList<WebElement>) driver.findElements(By.className("categoryTree"));
		Set<String> categorias = new HashSet<String>();
		
		// Usamos un Set para eliminar duplicados.
		for(int i=0; i< elementosCategoryClass.size();i++) 
		{
			categorias.add(elementosCategoryClass.get(i).getText());
		}
		
		driver.quit();
		
		return categorias;
	}
}

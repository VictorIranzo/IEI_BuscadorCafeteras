package automationFramework;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import app.Cafetera;

public class MediaMarktDriver {
	private static String urlConnection = "http://tiendas.mediamarkt.es/cafeteras-cafe";
	private static HashMap<String, WebElement> categoriasWebElements;
	private static WebDriver driver;
	
	public static List<Cafetera> Search(String articulo, List<String> marcas) {		
		categoriasWebElements.get(articulo).click();
		return new ArrayList<Cafetera>();
	}

	public static Set<String> getCategorias() {
		driver = ChromeConnection.initChromeConnection(urlConnection);		
		List<WebElement> elementosCategoryClass = (List<WebElement>) driver.findElements(By.className("categoryTree"));	
		categoriasWebElements = new HashMap<String, WebElement>();
		
		for(int i=0; i< elementosCategoryClass.size();i++) 
		{
			String textoCategoria = elementosCategoryClass.get(i).getText();
			
			if(!categoriasWebElements.containsKey(textoCategoria)) { 
				categoriasWebElements.put(textoCategoria, elementosCategoryClass.get(i));
			}
		}				
		
		return categoriasWebElements.keySet();
	}
}

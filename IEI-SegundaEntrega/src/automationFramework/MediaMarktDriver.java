package automationFramework;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Predicate;

import app.Cafetera;

public class MediaMarktDriver {
	private static String urlConnection = "http://tiendas.mediamarkt.es/cafeteras-cafe";
	private static HashMap<String, WebElement> categoriasWebElements;
	private static HashMap<String, WebElement> marcasWebElements;
	private static WebDriver driver;
	
	public static List<Cafetera> Search(String articulo, List<String> marcas) {		
		categoriasWebElements.get(articulo).click();
		//obtenerMarcasArticulo();
		/*
		for(String marca : marcas) 
		{
			if(marcasWebElements.containsKey(marca)) {
				//TODO: Solucionar problema al seleccionar más de una marca. 
				//Probar a guardar el index del element para luego volver a cogerlo, porque cambiar el WebElement.
				WebDriverWait waiting = new WebDriverWait(driver, 5);
				 waiting.until(ExpectedConditions.elementToBeClickable(marcasWebElements.get(marca)));
				marcasWebElements.get(marca).click();				
			}
		}
		*/
		//Con este while miramos si quedan páginas por visitar
		waitForPageLoad();
		while(!driver.findElements(By.xpath("//a[contains(@class, 'button bPager gray left arrow')]")).isEmpty()) {
			List<WebElement> elementos = driver.findElements(By.className("product10"));
			for(WebElement element : elementos) {
				//String modelo = element.findElement(By.xpath("//h2/a/span")).getText();
				String modelo = element.findElement(By.className("product10Description"))
								.findElement(By.tagName("span")).getText();
				
				String marca = element.findElement(By.className("product10brand"))
								.findElement(By.tagName("img")).getAttribute("alt");
				
				double precioMM = Double.parseDouble(element.findElement(By.className("productPrices"))
									.findElement(By.tagName("meta")).getAttribute("content").replace(",", "."));
			}
			//TODO: Comprobar si funciona la navegación entre páginas
			scrollFinalPagina();
			driver.findElement(By.xpath("//a[contains(@class, 'button bPager gray left arrow')]")).click();
			waitForPageLoad();
		}
		return new ArrayList<Cafetera>();
	}

	private static void obtenerMarcasArticulo() {
		//Click en Ver más para obtener todas las marcas. Usamos findElements para saber si está presente.
		if(!driver.findElements(By.id("categoryFilterViewMoreBrands")).isEmpty())
		{ 
			driver.findElement(By.id("categoryFilterViewMoreBrands")).click();
		}
		
		List<WebElement> elementos = driver.findElements(By.xpath("//*[@id=\"categoryFilterBrands\"]/div/a"));
		marcasWebElements = new HashMap<String,WebElement>();
		for(WebElement element : elementos) 
		{
			marcasWebElements.put(element.getText(), element);
		}
	}

	public static Set<String> getCategorias() {
		driver = ChromeConnection.initChromeConnection(urlConnection);		
		List<WebElement> elementosCategoryClass = (List<WebElement>) driver.findElements(By.className("categoryTree"));	
		categoriasWebElements = new HashMap<String, WebElement>();
		
		for(WebElement element : elementosCategoryClass)
		{
			String textoCategoria = element.getText();
			
			if(!categoriasWebElements.containsKey(textoCategoria)) { 
				categoriasWebElements.put(textoCategoria, element);
			}
		}				
		
		return categoriasWebElements.keySet();
	}
	
	private static void waitForPageLoad() {
		driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
	}
	
	private static void scrollFinalPagina() {
		((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
	}
}

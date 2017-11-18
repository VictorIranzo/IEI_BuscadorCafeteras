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
	
	public static void Search(String articulo, List<String> marcas, List<String> categoriasPermitidas, List<Cafetera> cafeteras) {		
		
		driver = ChromeConnection.initChromeConnection(urlConnection);
		
		// Click en la categoría seleccionada en el combobox.
		getCategorias();
		categoriasWebElements.get(articulo).click();
		
		/*
		 * Fails - Código empleado para filtrar por marca y no recorrer todas las cafeteras de una categoría.
		for(String marca : marcas) 
		{
			if(marcasWebElements.containsKey(marca)) {
				WebDriverWait waiting = new WebDriverWait(driver, 5);
				 waiting.until(ExpectedConditions.elementToBeClickable(marcasWebElements.get(marca)));
				marcasWebElements.get(marca).click();				
			}
		}
		*/
		
		WebDriverWait waiting = new WebDriverWait(driver,10);
		
		boolean continuar = true;
		while(continuar)
		{
			// Esperamos hasta que la página cargue y desaparezca el mensaje de Cargando al cambiar de página.
			waitForPageLoad();
			waiting.until(ExpectedConditions.invisibilityOfElementLocated(By.className("popup_content")));
			
			// Continuamos si existe la flecha de siguiente página. 
			// Se encuentran 2 objetos filtrando así, pero sólo el último es el accesible.
			continuar = driver.findElements(By.xpath("//a[contains(@class, 'button bPager gray left arrow')]")).size() > 1;
			
			List<WebElement> elementos = driver.findElements(By.className("product10"));
			for(WebElement element : elementos) 
			{
				// En todos los elementos usamos waiting para esperar a que rendericen.
				
				WebElement modeloElement = waiting.until(ExpectedConditions.elementToBeClickable(element.findElement(By.className("product10Description"))));
				String modelo = modeloElement.findElement(By.tagName("span")).getText();
				 
				WebElement marcaElement = waiting.until(ExpectedConditions.elementToBeClickable(element.findElement(By.className("product10brand"))));
				String marca = marcaElement.findElement(By.tagName("img")).getAttribute("alt");
				
				// Cambiamos el nombre de la marca al de nuestro modelo en mayúsculas
				if(marca.equals("De Longhi")) marca = "Delongui";				
				marca = marca.toUpperCase();
				
				WebElement divPrecio = waiting.until(ExpectedConditions.elementToBeClickable(element.findElement(By.className("productPrices"))));
				double precioMM = Double.parseDouble(divPrecio
						.findElement(By.tagName("meta")).getAttribute("content").replace(",", "."));
			
				// Si hemos filtrado por todas las marcas o la de la cafetera coincide con una marcada...
				if(marcas.isEmpty() || marcas.contains(marca)) {
					Cafetera cafetera = new Cafetera(modelo,marca, precioMM, -1);
					cafeteras.add(cafetera);
				}
			}

			if(continuar) 
			{
				// Navegamos a la siguiente página.
				scrollFinalPagina();
				driver.findElements(By.xpath("//a[contains(@class, 'button bPager gray left arrow')]")).get(1).click();
			}
		}	
		
		// Una vez obtenidas todas las cafeteras, cerramos el navegador.
		driver.close();
		
		return;
	}

	//No se utiliza.
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

	//Guarda tuplas en categoriasWebElements (Nombre Categoría, WebElement)
	public static void getCategorias() {
		
		List<WebElement> elementosCategoryClass = (List<WebElement>) driver.findElements(By.className("categoryTree"));	
		categoriasWebElements = new HashMap<String, WebElement>();
		
		for(WebElement element : elementosCategoryClass)
		{
			String textoCategoria = element.getText();
			
			if(!categoriasWebElements.containsKey(textoCategoria)) { 
				categoriasWebElements.put(textoCategoria, element);
			}
		}				
	}
	
	private static void waitForPageLoad() {
		driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
	}
	
	private static void scrollFinalPagina() {
		((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
	}
}

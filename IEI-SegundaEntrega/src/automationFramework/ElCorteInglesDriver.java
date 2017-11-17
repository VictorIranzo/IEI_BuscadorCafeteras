package automationFramework;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import app.Cafetera;

public class ElCorteInglesDriver {
	private static String urlConnection = "http://www.elcorteingles.es/electrodomesticos/cafeteras/?level=6";
	private static HashMap<String, WebElement> categoriasWebElements;
	private static HashMap<String, WebElement> marcasWebElements;
	private static WebDriver driver;	
	
	public static void Search(String articulo, List<String> marcas, List<String> categoriasPermitidas, List<Cafetera> cafeteras) {
		
		driver = ChromeConnection.initChromeConnection(urlConnection);
		
		getCategorias(categoriasPermitidas);
		categoriasWebElements.get(articulo).click();
		
		waitForPageLoad();
		
		WebDriverWait waiting = new WebDriverWait(driver,10);
		
		boolean continuar = true;
		while(continuar)
		{
			waitForPageLoad();
			
			int numeroArticulos = Integer.parseInt(driver.findElement(By.id("product-list-total")).getText().split(" ")[0]);		
			
			if(numeroArticulos <= 24) {
				continuar = false;
			}
			
			List<WebElement> elementos = driver.findElements(By.className("product-preview"));
			for(WebElement element : elementos) 
			{
				//String modelo = element.findElement(By.xpath("//h2/a/span")).getText();
				WebElement modeloElement = waiting.until(ExpectedConditions.elementToBeClickable(element.findElement(By.className("info-name"))));
				String modelo = modeloElement.findElement(By.tagName("a")).getText();
				 
				WebElement marcaElement = waiting.until(ExpectedConditions.elementToBeClickable(element.findElement(By.className("brand"))));
				String marca = marcaElement.getText();
				if(marca.equals("DE'LONGHI")) marca = "DELONGUI";
				
				double precioCC = 0;
				try {
				WebElement divPrecio = waiting.until(ExpectedConditions.elementToBeClickable(element.findElement(By.className("current"))));				
				String precio = divPrecio.getText().substring(0, divPrecio.getText().length()-1);
				precioCC = Double.parseDouble(precio.replace(",", "."));
				}catch(TimeoutException e) {
					//Esta excepción se captura para productos que aparecen y no tienen precio.
				}
				
				if(marcas.isEmpty() || marcas.contains(marca)) {
					Cafetera cafetera = new Cafetera(modelo,marca, -1, precioCC);
					
					// Esto funciona porque sobreescribimos el método equals() de cafetera y las consideramos iguales 
					// cuando tienen mismo nombre de modelo
					boolean inserted = false;
					for(Cafetera caf : cafeteras) {
						if(caf.equals(cafetera)) 
						{
							//Si encontramos el mismo modelo, le cambiamos el precio de El Corte Inglés que antes era -1
							caf.setPrecioCorteIngles(precioCC);
							inserted = true;	
							break;
						}
					}
					if(!inserted) 
					{
						//Si no la hemos encontrado, insertamos con el precio de MediaMarkt a -1
						cafeteras.add(cafetera);
					}
				}
			}
			
			if(continuar) 
			{
				List<WebElement> paginasAccesibles = driver.findElement(By.className("pagination")).findElements(By.tagName("a"));
				WebElement ultimoBoton = paginasAccesibles.get(paginasAccesibles.size()-1);				
				continuar = ultimoBoton.isEnabled() && ultimoBoton.getText().equals("Siguiente");
				scrollToAnElement(ultimoBoton);
				ultimoBoton.click();
			}
		}
		
		driver.close();
		
		return;			
	}
	
	public static void getCategorias(List<String> categoriasPermitidas){		
		List<WebElement> elementosCategoryClass = (List<WebElement>) driver.findElements(By.className("facet-popup"));
		
		categoriasWebElements = new HashMap<String, WebElement>();
		
		for(WebElement element : elementosCategoryClass)
		{
			String textoCategoria = element.getText();
			
			if(textoCategoria.equals("Cafeteras Italianas eléctricas")) textoCategoria = "Cafeteras tradicionales";
			if(textoCategoria.equals("Cafetera espresso manual")) textoCategoria = "Cafeteras express";
			if(textoCategoria.equals("Superautomáticas")) textoCategoria = "Cafeteras superautomáticas";
			if(textoCategoria.equals("Cafeteras de cápsulas")) textoCategoria = "Cafeteras monodosis";
			if(textoCategoria.equals("Hervidoras")) textoCategoria = "Hervidores y teteras";
			if(textoCategoria.equals("Accesorios")) textoCategoria = "Café y accesorios";
			
			if(categoriasPermitidas.contains(textoCategoria)&& !categoriasWebElements.containsKey(textoCategoria)) { 
				categoriasWebElements.put(textoCategoria, element);
			}
		}				
	}
	
	
	private static void waitForPageLoad() {
		//TODO: Refactorizar porque no funciona.
		driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
	}
	
	private static void scrollToAnElement(WebElement webElement) {
		Actions actions = new Actions(driver);
		actions.moveToElement(webElement);
		actions.perform();
	}
}

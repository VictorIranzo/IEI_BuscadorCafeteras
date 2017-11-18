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
	private static WebDriver driver;	
	
	public static void Search(String articulo, List<String> marcas, List<String> categoriasPermitidas, List<Cafetera> cafeteras) {
		
		driver = ChromeConnection.initChromeConnection(urlConnection);
		
		// Click en la categor�a seleccionada en el combobox.
		getCategorias(categoriasPermitidas);
		categoriasWebElements.get(articulo).click();
		
		waitForPageLoad();
		
		WebDriverWait waiting = new WebDriverWait(driver,10);
		
		boolean continuar = true;
		while(continuar)
		{
			waitForPageLoad();
			
			// Extraemos el n�mero de art�culos en la categor�a seleccionada.
			int numeroArticulos = Integer.parseInt(driver.findElement(By.id("product-list-total")).getText().split(" ")[0]);		
			
			// Si s�lo hay una p�gina con cafeteras...
			if(numeroArticulos <= 24) {
				continuar = false;
			}
			
			List<WebElement> elementos = driver.findElements(By.className("product-preview"));
			for(WebElement element : elementos) 
			{				
				//En todos los elementos usamos waiting para esperar a que rendericen.
				
				WebElement modeloElement = waiting.until(ExpectedConditions.elementToBeClickable(element.findElement(By.className("info-name"))));
				String modelo = modeloElement.findElement(By.tagName("a")).getAttribute("title");
				
				WebElement marcaElement = waiting.until(ExpectedConditions.elementToBeClickable(element.findElement(By.className("brand"))));
				String marca = marcaElement.getText();
				
				// Cambiamos la marca a como est� escrita en nuestro modelo.
				if(marca.equals("DE'LONGHI")) marca = "DELONGUI";
				
				double precioCC = 0;
				try {
				WebElement divPrecio = waiting.until(ExpectedConditions.elementToBeClickable(element.findElement(By.className("current"))));				
				String precio = divPrecio.getText().substring(0, divPrecio.getText().length()-1);
				precioCC = Double.parseDouble(precio.replace(",", "."));
				}catch(TimeoutException e) {
					// Esta excepci�n se captura para productos que aparecen y no tienen precio.
				}
				
				// Si hemos filtrado por todas las marcas o la marca de la cafetera coincide con una marcada...
				if(marcas.isEmpty() || marcas.contains(marca)) 
				{
					Cafetera cafetera = new Cafetera(modelo,marca, -1, precioCC);
					
					boolean inserted = false;
					for(Cafetera cafMM : cafeteras) 
					{
						// Buscamos si el modelo de la cafetera actual estaba en MediaMarkt.
						if(compararCafeteras(cafMM,cafetera)) 
						{
							// Si encontramos el mismo modelo, le cambiamos el precio de El Corte Ingl�s que antes era -1
							cafMM.setPrecioCorteIngles(precioCC);
							inserted = true;	
							break;
						}
					}
					if(!inserted) 
					{
						// Si no la hemos encontrado, insertamos con el precio de MediaMarkt a -1
						cafeteras.add(cafetera);
					}
				}
			}
			
			if(continuar) 
			{
				// Obtenemos todos los botones para navegar entre p�ginas.
				List<WebElement> paginasAccesibles = driver.findElement(By.className("pagination")).findElements(By.tagName("a"));
				
				// Cogemos el �ltimo y miramos si est� habilitado y es el bot�n de Siguiente, ya que podr�a ser el de otra p�gina.
				WebElement ultimoBoton = paginasAccesibles.get(paginasAccesibles.size()-1);				
				continuar = ultimoBoton.isEnabled() && ultimoBoton.getText().equals("Siguiente");
				
				// Hacemos scroll hasta ese bot�n y le hacemos click.
				// Si estamos en la �ltima p�gina, continuar se habr� evaluado a falso, 
				// iremos a la p�gina anterior y el bucle se evaluar� a false, por lo que cerraremos el navegador.
				scrollToAnElement(ultimoBoton);
				ultimoBoton.click();
			}
		}
		
		// Una vez obtenidas todas las cafeteras, cerramos el navegador.
		driver.close();
		
		return;			
	}
	
	// Creamos tuplas en categoriasWebElements con (Nombre de categor�a en nuestro modelo, WebElement)
	public static void getCategorias(List<String> categoriasPermitidas){		
		List<WebElement> elementosCategoryClass = (List<WebElement>) driver.findElements(By.className("facet-popup"));
		
		categoriasWebElements = new HashMap<String, WebElement>();
		
		for(WebElement element : elementosCategoryClass)
		{
			String textoCategoria = element.getText();
			
			if(textoCategoria.equals("Cafeteras Italianas el�ctricas")) textoCategoria = "Cafeteras tradicionales";
			if(textoCategoria.equals("Cafetera espresso manual")) textoCategoria = "Cafeteras express";
			if(textoCategoria.equals("Superautom�ticas")) textoCategoria = "Cafeteras superautom�ticas";
			if(textoCategoria.equals("Cafeteras de c�psulas")) textoCategoria = "Cafeteras monodosis";
			if(textoCategoria.equals("Hervidoras")) textoCategoria = "Hervidores y teteras";
			if(textoCategoria.equals("Accesorios")) textoCategoria = "Caf� y accesorios";
			
			if(categoriasPermitidas.contains(textoCategoria)&& !categoriasWebElements.containsKey(textoCategoria)) { 
				categoriasWebElements.put(textoCategoria, element);
			}
		}				
	}
	
	
	private static void waitForPageLoad() {
		driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
	}
	
	private static void scrollToAnElement(WebElement webElement) {
		Actions actions = new Actions(driver);
		actions.moveToElement(webElement);
		actions.perform();
	}
	
	private static boolean compararCafeteras(Cafetera cafeteraMM, Cafetera cafeteraCC) {
		
		// Si son de marcas distintas o las cafeteras que comparamos son ambas de El Corte Ingl�s, devolvemos false.
		if(!cafeteraMM.getMarca().equals(cafeteraCC.getMarca()) || cafeteraMM.getPrecioMediaMarkt() == -1.0) return false;
	     
		//Son iguales si tienen igual numero de modelo, atributo que se calcula con el constructor de Cafetera.
	     if(cafeteraMM.getNumeroModelo().equals(cafeteraCC.getNumeroModelo())) {
	    	 return true;
	     }
	     
	     return false;
	}

}

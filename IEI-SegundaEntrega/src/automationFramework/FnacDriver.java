package automationFramework;

import java.util.List;

import org.openqa.selenium.WebDriver;

import app.Cafetera;

public class FnacDriver {
	private static String urlConnection = "https://www.fnac.es/Desayuno-y-cafe/s38477";
	
	public static List<Cafetera> Search(String articulo, List<String> marcas) {
		WebDriver driver = ChromeConnection.initChromeConnection(urlConnection);
		return null;
	}
}

package automationFramework;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;

import app.Cafetera;

public class FnacDriver {
	private static String urlConnection = "https://www.fnac.es/Desayuno-y-cafe/s38477";
	
	public static void Search(String articulo, List<String> marcas, List<Cafetera> cafeteras) {
		WebDriver driver = ChromeConnection.initChromeConnection(urlConnection);
	}
}

package app;

import java.util.ArrayList;
import java.util.List;

public class MarcaFilter {
	
	
	
	public static List<Cafetera> filtrarPorMarcas(List<Cafetera> todas, List<String> marcasMarcadas) {
		List<Cafetera> cafeterasFiltradas = new ArrayList<Cafetera>();
		for(Cafetera c: todas) {
			String marca = c.getMarca();
			if(marca.equals("De Longhi")|| marca.equals("De'Longhi")) marca = "Delongui";
			if(marcasMarcadas.contains(marca)) cafeterasFiltradas.add(c);
		}
		
		return cafeterasFiltradas;
	}

}

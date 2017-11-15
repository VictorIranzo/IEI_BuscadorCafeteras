package app;

import java.util.ArrayList;
import java.util.List;

public class MarcaFilter {
	
	
	
	public static List<Cafetera> filtrarPorMarcas(List<Cafetera> todas, List<String> marcasMarcadas) {
		if(marcasMarcadas.size() == 0) return todas;
		
		List<Cafetera> cafeterasFiltradas = new ArrayList<Cafetera>();
		for(Cafetera c: todas) {
			String marca = c.getMarca();
			//TODO: Reemplazar en la cración de cada Objeto Cafetera para que ponga siempre la marcas con este nombre.
			if(marca.equals("De Longhi")|| marca.equals("De'Longhi")) marca = "Delongui";
			if(marcasMarcadas.contains(marca)) cafeterasFiltradas.add(c);
		}
		
		return cafeterasFiltradas;
	}

}

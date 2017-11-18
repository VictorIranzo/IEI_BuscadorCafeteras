package app;

public class Cafetera {
	private String modelo;
	private String numeroModelo;		// Se calcula al invocar al constructor.
	private String marca;				// Siempre en mayúsculas.
	private double precioMediaMarkt;	// -1 si no existe en MediaMarkt.
	private double precioCorteIngles;	// -1 si no existe en El Corte Inglés.
	
	public Cafetera(String modelo, String marca, double precioMediaMarkt, double precioCorteIngles) {
		super();
		this.modelo = modelo;
		this.marca = marca;
		this.precioMediaMarkt = precioMediaMarkt;
		this.precioCorteIngles = precioCorteIngles;
		this.numeroModelo = obtenerNumeroModelo(modelo);
	}

	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	public String getNumeroModelo() {
		return numeroModelo;
	}	
	public void setNumeroModelo(String numeroModelo) {
		this.numeroModelo = numeroModelo;
	}
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public double getPrecioMediaMarkt() {
		return precioMediaMarkt;
	}
	public void setPrecioMediaMarkt(double precioMediaMarkt) {
		this.precioMediaMarkt = precioMediaMarkt;
	}
	public double getPrecioCorteIngles() {
		return precioCorteIngles;
	}
	public void setPrecioCorteIngles(double precioCorteIngles) {
		this.precioCorteIngles = precioCorteIngles;
	}
	
	// No se usa.
	@Override
	public boolean equals(Object o) {
		return (o instanceof Cafetera) 
				&& ((Cafetera) o).getModelo() == this.getModelo();
	}
	
	private static String obtenerNumeroModelo(String nombreModelo) {
		char[] caracteresModelo = nombreModelo.toCharArray();
		 String numeroModelo = "";
		 
	     for(int i= 0; i < caracteresModelo.length; i++){
	    	char c = caracteresModelo[i];
	    	
	    	//Buscamos el primer carácter numérico
	        if(Character.isDigit(c)) {
	        	
	        	/* Esto era una buena idea para extraer toda la palabra que contenía a ese carácter, 
	        	 * pero nos hemos dado cuenta que en MediaMarkt ponen el modelo como CG 343 y en
	        	 * El Corte inglés como CG343, por lo que no haría matching al comparar 343 con CG343.
	        	int k;
	        	for(k=i; k>=0; k--) {
	        		char c2 = caracteresModelo[k];
	        		if(Character.isWhitespace(c2)) break;
	        	}
	        	*/
	        	
	        	//Cogemos hasta el siguiente espacio en blanco
	            for(int j=i; j< caracteresModelo.length; j++) {
	            	char c2 = caracteresModelo[j];
	            	if(Character.isWhitespace(c2)) {
	            		numeroModelo = nombreModelo.substring(i,j);
	            		break;
	            	}
	            }
	            break;
	        }       
	     }
		return numeroModelo;
	}
}

package app;

public class Cafetera {
	private String modelo;
	private String marca;
	private double precioMediaMarkt;
	private double precioCorteIngles;
	
	public Cafetera(String modelo, String marca, double precioMediaMarkt, double precioCorteIngles) {
		super();
		this.modelo = modelo;
		this.marca = marca;
		this.precioMediaMarkt = precioMediaMarkt;
		this.precioCorteIngles = precioCorteIngles;
	}
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
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
	
	@Override
	public boolean equals(Object o) {
		return (o instanceof Cafetera) 
				&& ((Cafetera) o).getModelo() == this.getModelo();
	}
}

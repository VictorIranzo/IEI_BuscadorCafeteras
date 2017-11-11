package app;

public class Cafetera {
	private String modelo;
	private String marca;
	private double precioMediaMarkt;
	private double precioFnac;
	
	public Cafetera(String modelo, String marca, double precioMediaMarkt, double precioFnac) {
		super();
		this.modelo = modelo;
		this.marca = marca;
		this.precioMediaMarkt = precioMediaMarkt;
		this.precioFnac = precioFnac;
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
	public double getPrecioFnac() {
		return precioFnac;
	}
	public void setPrecioFnac(double precioFnac) {
		this.precioFnac = precioFnac;
	}
}

package us.lsi.tipos;

import us.lsi.geometria.Punto2D;

public class Circulo {	
	
	public static Circulo create(Double radio, Punto2D centro) {
		return new Circulo(radio, centro);
	}


	private Circulo(Double radio, Punto2D centro) {
		super();
		this.radio = radio;
		this.centro = centro;
	}

	private Double radio;	
	private Punto2D centro;
	
	
	public Double getArea() {
		return Math.PI*radio*radio;
	}

	
	public Punto2D getCentro() {
		return centro;
	}

	
	public Double getRadio() {
		return radio;
	}
	
	public void setRadio(Double radio) {
		this.radio = radio;
	}

	public void setCentro(Punto2D centro) {
		this.centro = centro;
	}

}

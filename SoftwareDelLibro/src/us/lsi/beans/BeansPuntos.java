package us.lsi.beans;

import us.lsi.geometria.Punto2D;

public class BeansPuntos extends Punto2D {

	public static ListenablePunto createListenable() {
		return new ListenablePunto();
	}

	public static ListenablePunto createListenable(Double x, Double y) {
		return new ListenablePunto(x, y);
	}

	public static Punto2D createPunto() {
		return Punto2D.create();
	}

	public static Punto2D createPunto(Double x, Double y) {
		return Punto2D.create(x, y);
	}

	public static VetoablePunto createVetoable() {
		return new VetoablePunto();
	}

	public static VetoablePunto createVetoable(Double x, Double y) {
		return new VetoablePunto(x, y);
	}
}

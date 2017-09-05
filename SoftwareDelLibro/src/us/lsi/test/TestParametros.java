package us.lsi.test;

import us.lsi.geometria.Punto2D;

public class TestParametros extends Test {
	
	public static double m(Integer i, String s, double d, Punto2D p) {
		i++;
		s += "   Modificado";
	    d = i;
		p.setX(d);
		return d;
    }

    public static void main(String[] args) {
		Integer i1 = 14;
		String s1 = "Valor Inicial";
		double  d1 = 0.75;
		Punto2D p1 = Punto2D.create(3.0,4.5);
		@SuppressWarnings("unused")
		double  r2 = m(i1,s1,d1,p1);
		mostrar("i1 = "+i1);
		mostrar("s1 = "+s1);
		mostrar("d1 = "+d1);
		mostrar ("p1 = "+ p1);
	}
}


package us.lsi.test;

import us.lsi.geometria.Punto2D;

public class TestParametros2 extends Test {
	public static void main(String[] args) {
		Integer i1 = 14;
		double  d1 = 0.75;
		Punto2D p1 = Punto2D.create(3.0,4.5);
	       @SuppressWarnings("unused")
		double r2;
		{
		    Integer i;
            double  d;
            Punto2D p;
            i = i1;
            d = d1;
            p = p1;
		    i++;
	        d = i;
		    p.setX(d);
		    r2 = d;
		}
		mostrar("i1 = "+i1);
		mostrar("d1 = "+d1);
		mostrar ("p1 = "+ p1);
	}

}

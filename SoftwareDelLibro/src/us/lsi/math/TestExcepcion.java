package us.lsi.math;

import us.lsi.test.Test;

public class TestExcepcion extends Test {
		public static void main(String[] args) {
			Racional r = null ;
			r = Racional.create(1, 0);
			mostrar (r);
			mostrar (" Final del programa");
		}
}

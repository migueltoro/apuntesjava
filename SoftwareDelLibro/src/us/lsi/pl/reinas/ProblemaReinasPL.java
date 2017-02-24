package us.lsi.pl.reinas;

import us.lsi.pl.AlgoritmoPLI;



public class ProblemaReinasPL {

	
	
	
	
	
	public static void main(String[] args) {
		ReinasPLI.numeroDeReinas = 8;
		String r = ReinasPLI.getConstraints();
//		System.out.println(r);
		AlgoritmoPLI a = AlgoritmoPLI.create();
		a.setConstraints(r);
		a.ejecuta();
		System.out.println("-------------------");	
		System.out.println("________");
		System.out.println("Objetivo = "+a.getObjetivo());
		System.out.println("________");
		for (int j = 0; j < a.getNumVar(); j++) {
			if (a.getSolucion(j)>0.) {
				System.out.println(a.getNames().get(j) + " = "
						+ a.getSolucion(j));
			}
		}
		System.out.println("________");
		
	}
}

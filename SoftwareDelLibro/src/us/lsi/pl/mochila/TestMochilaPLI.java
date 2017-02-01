package us.lsi.pl.mochila;

import us.lsi.pd.mochila.ProblemaMochila;
import us.lsi.pl.AlgoritmoPLI;


public class TestMochilaPLI {

	public static void main(String[] args) {
		String r = MochilaPLI.getConstraints2();
		AlgoritmoPLI a = AlgoritmoPLI.create();
		a.setConstraints(r);
		a.ejecuta();
		System.out.println("-------------------");	
		System.out.println("________");
		System.out.println("Objetivo = "+a.getObjetivo());
		System.out.println("________");
		for (int j = 0; j < a.getNumVar(); j++) {
			System.out.println(a.getNames().get(j)+" = "+a.getSolucion()[j]+",   "+ProblemaMochila.getNumMaxDeUnidades(j));
		}
		System.out.println("________");
		System.out.println(r);
	}

}

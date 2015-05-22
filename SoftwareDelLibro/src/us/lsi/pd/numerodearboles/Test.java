package us.lsi.pd.numerodearboles;

import us.lsi.algoritmos.Algoritmos;
import us.lsi.pd.AlgoritmoPD;
import us.lsi.pd.ProblemaPD;

public class Test {

	public static String ruta = "C:\\Users\\Boss\\Desktop\\Ficheros\\";

	public static void main(String[] args) {
		NumeroDeArboles.nmh = 3;
		ProblemaPD<Integer,Integer> p = NumeroDeArboles.create(3);
		AlgoritmoPD<Integer, Integer> a = Algoritmos.createPD(p);
		a.ejecuta();		
		a.showAllGraph(ruta+"NumeroDeArboles.gv","NumeroDeArboles",p);
		System.out.println("Solucion= "+a.getSolucionParcial(p).propiedad);

	}

}

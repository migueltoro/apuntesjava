package us.lsi.pd.numerodearboles;

import us.lsi.algoritmos.Algoritmos;
import us.lsi.pd.AlgoritmoPD;
import us.lsi.pd.ProblemaPD;

public class TestBinarios {

	public static String ruta = "C:\\Users\\Boss\\Desktop\\Ficheros\\";

	public static void main(String[] args) {
		ProblemaPD<Integer,Integer> p = NumeroDeArbolesBinarios.create(5);
		AlgoritmoPD<Integer, Integer> a = Algoritmos.createPD(p);
		a.ejecuta();		
		a.showAllGraph(ruta+"NumeroDeArboles.gv","NumeroDeArboles",p);
		System.out.println("Solucion= "+a.getSolucionParcial(p).propiedad);
	}

}

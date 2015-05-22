package us.lsi.pd.reinas;

import us.lsi.algoritmos.Algoritmos;
import us.lsi.bt.reinas.Reina;
import us.lsi.pd.AlgoritmoPD;
import us.lsi.pd.ProblemaPD;

import java.util.*;
import java.util.stream.Collectors;


public class TestReinasPD {

	public static void main(String[] args) {
		AlgoritmoPD.isRandomize = false;
		ProblemaPD<Set<List<Reina>>,Integer>  p = ProblemaReinasPDTodas.create();
		AlgoritmoPD<Set<List<Reina>>, Integer> a = Algoritmos.createPD(p);
		a.ejecuta();
		System.out.println("Solucion Parcial= "+a.getSolucionParcial(p).propiedad+"\n\n");
		System.out.println((a.getSolucion(p).stream().map(x->x.toString()).collect(Collectors.joining("\n"))));
		
		/*		int i=0;
		for (List<Reina> ls:a.getSolucion(p)) {
			System.out.println("Solucion= " + i +","+ ls);
			i++;
			//		a.showAllGraph(ruta+"numArboles.gv","NumArboles",p);
		}
*/
	}

}

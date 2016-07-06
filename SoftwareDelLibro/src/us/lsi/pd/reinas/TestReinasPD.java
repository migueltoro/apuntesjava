package us.lsi.pd.reinas;

import us.lsi.algoritmos.Algoritmos;
import us.lsi.pd.AlgoritmoPD;
import us.lsi.pd.ProblemaPD;



public class TestReinasPD {

	public static void main(String[] args) {
		AlgoritmoPD.isRandomize = false;
		ReinasPDNumSoluciones.numeroDeReinas = 10;
		ProblemaPD<Integer,Integer>  p = ReinasPDNumSoluciones.create();
		AlgoritmoPD<Integer, Integer> a = Algoritmos.createPD(p);
		a.ejecuta();
		System.out.println("Solucion Parcial= "+a.getSolucionParcial(p).propiedad+"\n\n");
		
		
		/*		int i=0;
		for (List<Reina> ls:a.getSolucion(p)) {
			System.out.println("Solucion= " + i +","+ ls);
			i++;
			//		a.showAllGraph(ruta+"numArboles.gv","NumArboles",p);
		}
*/
	}

}

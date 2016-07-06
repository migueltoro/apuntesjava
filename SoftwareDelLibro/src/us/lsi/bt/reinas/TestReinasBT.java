package us.lsi.bt.reinas;

import java.util.List;

import us.lsi.algoritmos.Algoritmos;
import us.lsi.bt.AlgoritmoBT;


public class TestReinasBT {

	/**
	 * @param args Argumentos
	 */
	public static void main(String[] args) {
		AlgoritmoBT.setFile("C:\\Users\\Boss\\Desktop\\Ficheros\\traza.txt");
		AlgoritmoBT.numeroDeSoluciones = 100;
		AlgoritmoBT.isRandomize = false;
		AlgoritmoBT.soloLaPrimeraSolucion = false;
		AlgoritmoBT.sizeRef = 15;
		ProblemaReinasBT.numeroDeReinas = 8;
		ProblemaReinasBT p = ProblemaReinasBT.create();
		AlgoritmoBT<List<Reina>,Integer> a = Algoritmos.createBT(p);
		a.ejecuta();
		System.out.println(a.soluciones.size());
//		System.out.println(Metricas.getMetricas().getTiempoDeEjecucion());
		for(List<Reina> lis : a.soluciones){
			System.out.println(lis+","+p.getNumeroDeConflictos(lis));
		}
	}
}

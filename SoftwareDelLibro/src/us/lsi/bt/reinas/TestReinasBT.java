package us.lsi.bt.reinas;


import us.lsi.algoritmos.Algoritmos;
import us.lsi.bt.AlgoritmoBT;


public class TestReinasBT {

	/**
	 * @param args Argumentos
	 */
	public static void main(String[] args) {
		AlgoritmoBT.calculaMetricas();
		AlgoritmoBT.numeroDeSoluciones = 1;
		AlgoritmoBT.isRandomize = true;
		AlgoritmoBT.sizeRef = 10;
		Reina.numeroDeReinas = 200;
		EstadoReinasBT2 p = EstadoReinasBT2.create();
		AlgoritmoBT<SolucionReinas,Integer> a = Algoritmos.createBT(p);
		a.ejecuta();
		System.out.println(a.getSoluciones().size());
		for(SolucionReinas s : a.getSoluciones()){
			System.out.println(s.getObjetivo()+","+s);
		}
		System.out.println(AlgoritmoBT.metricas);
	}
}

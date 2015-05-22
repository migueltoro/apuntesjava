package us.lsi.astar.vuelos;

import us.lsi.algoritmos.Algoritmos;
import us.lsi.astar.AStarAlgorithm;
import us.lsi.graphs.*;

public class Test {

	/**
	 * @param args Argumentos
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GrafoVuelos grafo = new GrafoVuelos(Vuelo.class);
		grafo = (GrafoVuelos) GraphsReader.newGraph("nvuelos.txt", StringFactory.getFactory(), Vuelo.getFactory(),grafo);
		grafo.horaDeLlegadaAlAeropuerto = 7.;
		System.out.println(grafo.vertexSet());
		AStarAlgorithm<String,Vuelo> a = Algoritmos.createAStar(grafo, "Sevilla", "Malaga");
		System.out.println(a.getPath());
		System.out.println(a.getPathLength());
	}

}


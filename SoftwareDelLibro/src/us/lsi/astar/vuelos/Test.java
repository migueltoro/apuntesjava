package us.lsi.astar.vuelos;

import us.lsi.algoritmos.Algoritmos;
import us.lsi.astar.AStarAlgorithm;
import us.lsi.graphs.*;


public class Test {
	
	public static String create(String[] a){
		return a[0];
	}

	/**
	 * @param args Argumentos
	 */
	public static void main(String[] args) {
		GrafoVuelos grafo = new GrafoVuelos(Vuelo::create);
		grafo = (GrafoVuelos) GraphsReader.newGraph("./ficheros/nvuelos.txt", Test::create, Vuelo::create,grafo,null);
		grafo.horaDeLlegadaAlAeropuerto = 7.;
		System.out.println(grafo.vertexSet());
		AStarAlgorithm<String,Vuelo> a = Algoritmos.createAStar(grafo, "Sevilla", "Malaga");
		System.out.println(a.getPath());
		System.out.println(a.getPath().getWeight());
	}

}


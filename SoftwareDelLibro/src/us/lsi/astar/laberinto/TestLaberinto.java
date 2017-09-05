package us.lsi.astar.laberinto;

import org.jgrapht.*;

import us.lsi.algoritmos.Algoritmos;
import us.lsi.astar.AStarAlgorithm;
import us.lsi.graphs.*;

public class TestLaberinto {

	
	public static void main(String[] args) {
		Casilla.iniDatos("ficheros\\laberinto.txt", 8, 6);
		Casilla c1 = Casilla.create(0, 5);
		Casilla c2 = Casilla.create(7, 5);
		LaberintoCaminoMinimo g = new LaberintoCaminoMinimo(c1,c2);
		AStarAlgorithm<Casilla,SimpleEdge<Casilla>> d = 
				Algoritmos.createAStar(g, c1, c2);
		GraphPath<Casilla,SimpleEdge<Casilla>> p = d.getPath();
		System.out.println(p.getWeight());
		System.out.println(p.getVertexList());
	}

}

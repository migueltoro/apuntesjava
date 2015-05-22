package us.lsi.astar.laberinto;

import org.jgrapht.*;

import us.lsi.algoritmos.Algoritmos;
import us.lsi.astar.AStarAlgorithm;
import us.lsi.graphs.*;

public class TestLaberinto {

	
	public static void main(String[] args) {
		LaberintoCaminoMinimo g = new LaberintoCaminoMinimo(
				SimpleEdge.<Casilla>getFactoria(),"laberinto.txt", 8, 6);
		AStarAlgorithm<Casilla,SimpleEdge<Casilla>> d = 
				Algoritmos.createAStar(g, Casilla.create(0, 5), Casilla.create(7, 5));
		GraphPath<Casilla,SimpleEdge<Casilla>> p = d.getPath();
		System.out.println(p.getWeight());
		System.out.println(Graphs.getPathVertexList(p));
		System.out.println(g.getNumVertexInEdgesOf());
	}

}

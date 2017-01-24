package us.lsi.astar.laberinto;

import us.lsi.astar.AStarGraph;
import us.lsi.graphs.SimpleEdge;
import us.lsi.graphs.UndirectedSimpleVirtualGraph;

public class LaberintoCaminoMaximo extends UndirectedSimpleVirtualGraph<Casilla, SimpleEdge<Casilla>> 
	implements AStarGraph<Casilla, SimpleEdge<Casilla>> {

	public LaberintoCaminoMaximo(String nf, Integer nx, Integer ny, Casilla... c) {
		super(c);		
	}
	
	@Override
	public double getEdgeWeight(SimpleEdge<Casilla> a) {
		return -1.;
	}

	@Override
	public double getWeightToEnd(Casilla startVertex, Casilla endVertex) {
		int x = Math.abs(endVertex.getX()-startVertex.getX())+ Math.abs(endVertex.getY()-startVertex.getY());
		return -x;
	}

}

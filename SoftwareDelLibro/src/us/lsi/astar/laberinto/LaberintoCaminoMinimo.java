package us.lsi.astar.laberinto;

import java.util.Set;
import java.util.function.Predicate;

import org.jgrapht.EdgeFactory;

import us.lsi.astar.AStarGraph;
import us.lsi.graphs.SimpleEdge;
import us.lsi.graphs.UndirectedSimpleVirtualGraph;

public class LaberintoCaminoMinimo extends UndirectedSimpleVirtualGraph<Casilla, SimpleEdge<Casilla>> 
	implements AStarGraph<Casilla, SimpleEdge<Casilla>> {

	public LaberintoCaminoMinimo(EdgeFactory<Casilla, SimpleEdge<Casilla>> edgeFactory,
			String nf, Integer nx, Integer ny) {
		super(edgeFactory);
		Casilla.iniDatos(nf, nx, ny);
	}

	@Override
	public double getEdgeWeight(SimpleEdge<Casilla> a) {
		return 1.;
	}

	@Override
	public double getVertexWeight(Casilla vertex) {
		return 0.;
	}

	@Override
	public double getVertexWeight(Casilla vertex, SimpleEdge<Casilla> edgeIn, SimpleEdge<Casilla> edgeOut) {
		return 0;
	}

	@Override
	public double getWeightToEnd(Casilla startVertex, Casilla endVertex, Predicate<Casilla> p, Set<Casilla> s) {
		int x = Math.abs(endVertex.getX()-startVertex.getX())+ Math.abs(endVertex.getY()-startVertex.getY());
		return x;
	}

	
}

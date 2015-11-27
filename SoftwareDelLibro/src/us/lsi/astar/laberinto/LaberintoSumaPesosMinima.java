package us.lsi.astar.laberinto;

import java.util.Set;
import java.util.function.Function;

import org.jgrapht.EdgeFactory;

import us.lsi.astar.AStarGraph;
import us.lsi.graphs.SimpleEdge;
import us.lsi.graphs.UndirectedSimpleVirtualGraph;

public class LaberintoSumaPesosMinima extends UndirectedSimpleVirtualGraph<Casilla, SimpleEdge<Casilla>> 
	implements AStarGraph<Casilla, SimpleEdge<Casilla>> {

	public LaberintoSumaPesosMinima(EdgeFactory<Casilla, SimpleEdge<Casilla>> edgeFactory,
			String nf, Integer nx, Integer ny) {
		super(edgeFactory);
		Casilla.iniDatos(nf, nx, ny);
	}



	@Override
	public double getVertexWeight(Casilla vertex) {
		// TODO Auto-generated method stub
		return vertex.getInfo();
	}

	@Override
	public double getVertexWeight(Casilla vertex, SimpleEdge<Casilla> edgeIn, SimpleEdge<Casilla> edgeOut) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getWeightToEnd(Casilla startVertex, Casilla endVertex, Function<Casilla,Double> p, Set<Casilla> s) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getEdgeWeight(SimpleEdge<Casilla> arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

}

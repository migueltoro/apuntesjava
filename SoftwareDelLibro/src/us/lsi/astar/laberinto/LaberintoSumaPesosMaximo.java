package us.lsi.astar.laberinto;



import us.lsi.astar.AStarGraph;
import us.lsi.graphs.SimpleEdge;
import us.lsi.graphs.UndirectedSimpleVirtualGraph;

public class LaberintoSumaPesosMaximo extends UndirectedSimpleVirtualGraph<Casilla, SimpleEdge<Casilla>> 
	implements AStarGraph<Casilla, SimpleEdge<Casilla>> {

	public LaberintoSumaPesosMaximo(String nf, Integer nx, Integer ny, Casilla... c) {
		super(c);
	}

	@Override
	public double getVertexWeight(Casilla vertex) {
		return -vertex.getInfo();
	}

	@Override
	public double getEdgeWeight(SimpleEdge<Casilla> arg0) {
		return 0;
	}

}

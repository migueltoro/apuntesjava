package us.lsi.astar.mochila;

import us.lsi.astar.AStarGraph;
import us.lsi.graphs.SimpleEdge;
import us.lsi.graphs.UndirectedSimpleVirtualGraph;
import us.lsi.pd.mochila.ProblemaMochila;

public class MochilaGrafo extends
		UndirectedSimpleVirtualGraph<MochilaVertex, SimpleEdge<MochilaVertex>>
		implements
		AStarGraph<MochilaVertex, SimpleEdge<MochilaVertex>> {

	
	
	public static MochilaGrafo create(MochilaVertex... v) {
		return new MochilaGrafo(v);
	}


	private MochilaGrafo(MochilaVertex... v) {
		super(v);
	}


	@Override
	public double getWeightToEnd(MochilaVertex startVertex, MochilaVertex endVertex) {
		ProblemaMochila actual = startVertex.getProblema();
		return -actual.getCotaSuperiorValorEstimado();
	}

	@Override
	public double getEdgeWeight(SimpleEdge<MochilaVertex> e){
		ProblemaMochila source = e.getSource().getProblema();
		ProblemaMochila target = e.getTarget().getProblema();
		Integer a = source.getAlternativa(target);
		return -a*ProblemaMochila.getValorObjeto(source.getIndex());
	}
}

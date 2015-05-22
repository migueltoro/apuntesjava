package us.lsi.astar.mochila;

import java.util.Set;
import java.util.function.Predicate;

import org.jgrapht.EdgeFactory;

import us.lsi.astar.AStarGraph;
import us.lsi.graphs.SimpleEdge;
import us.lsi.graphs.UndirectedSimpleVirtualGraph;
import us.lsi.pd.mochila.ProblemaMochila;

public class MochilaGrafo extends
		UndirectedSimpleVirtualGraph<MochilaVertex, SimpleEdge<MochilaVertex>>
		implements
		AStarGraph<MochilaVertex, SimpleEdge<MochilaVertex>> {

	
	
	public static MochilaGrafo create(EdgeFactory<MochilaVertex, SimpleEdge<MochilaVertex>> edgeFactory) {
		return new MochilaGrafo(edgeFactory);
	}

	public static MochilaGrafo create(
			EdgeFactory<MochilaVertex, SimpleEdge<MochilaVertex>> edgeFactory,
			MochilaVertex[] vs) {
		return new MochilaGrafo(edgeFactory, vs);
	}

	private MochilaGrafo(
			EdgeFactory<MochilaVertex, SimpleEdge<MochilaVertex>> edgeFactory,
			MochilaVertex[] vs) {
		super(edgeFactory, vs);
	}

	private MochilaGrafo(
			EdgeFactory<MochilaVertex, SimpleEdge<MochilaVertex>> edgeFactory) {
		super(edgeFactory);
	}

	@Override
	public double getVertexWeight(MochilaVertex vertex) {
		return 0;
	}

	@Override
	public double getVertexWeight(MochilaVertex vertex,
			SimpleEdge<MochilaVertex> edgeIn,
			SimpleEdge<MochilaVertex> edgeOut) {
		return 0;
	}

	@Override
	public double getWeightToEnd(MochilaVertex startVertex,
			MochilaVertex endVertex,
			Predicate<MochilaVertex> goal,
			Set<MochilaVertex> goalSet) {
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

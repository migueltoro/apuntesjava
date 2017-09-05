package us.lsi.flowgraph;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.jgrapht.graph.GraphWalk;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;


/**
 * La solución de una red de flujo
 * 
 * @author Miguel Toro
 *
 */
public class FlowGraphSolution {
	
	public static FlowGraphSolution create(FlowGraph flowGraph,
			String constrains, Map<FlowEdge, Double> edgeFlow,
			Map<FlowVertex, Double> sourceFlow, Map<FlowVertex, Double> sinkFlow,
			Double optFlow, Double goal) {
		return new FlowGraphSolution(flowGraph, constrains, edgeFlow,
				sourceFlow, sinkFlow, optFlow, goal);
	}

	private FlowGraph flowGraph;	
	private String constrains;
	private Map<FlowEdge, Double> edgeFlow;
	private Map<FlowVertex, Double> sourceFlow;
	private Map<FlowVertex, Double> sinkFlow;
	private Double optFlow;
	private Double goal;
	private Set<GraphWalk<FlowVertex,FlowEdge>> walks;
	
	private FlowGraphSolution(FlowGraph flowGraph, 
			String constrains,
			Map<FlowEdge, Double> edgeFlow, 
			Map<FlowVertex, Double> sourceFlow,
			Map<FlowVertex, Double> sinkFlow, 
			Double optFlow, Double goal) {
		super();
		this.flowGraph = flowGraph;
		this.constrains = constrains;
		this.edgeFlow = edgeFlow;
		this.sourceFlow = sourceFlow;
		this.sinkFlow = sinkFlow;
		this.optFlow = optFlow;
		this.goal = goal;
	}

	/**
	 * @return El grafo de la red de flujo
	 */
	public FlowGraph getFlowGraph() {
		return flowGraph;
	}

	/**
	 * @return Las restriccines asociadas a la red de flujo
	 */
	public String getConstrains() {
		return constrains;
	}

	/**
	 * @return El flujo por las aristas
	 */
	public Map<FlowEdge, Double> getEdgeFlow() {
		return edgeFlow;
	}

	/**
	 * @return El flujo que nace en los vértices fuente
	 */
	public Map<FlowVertex, Double> getSourceFlow() {
		return sourceFlow;
	}

	/**
	 * @return El flujo consumido en los vértices sumidero
	 */
	public Map<FlowVertex, Double> getSinkFlow() {
		return sinkFlow;
	}

	/**
	 * @return El flujo optimizado. Igual a la suma de los fluos en los vértices fuente
	 */
	public Double getOptFlow() {
		return optFlow;
	}

	/**
	 * @return Valor de la función  objetivo optimizada
	 */
	public Double getGoal() {
		return goal;
	}
	
	
	/**
	 * @param disjointEdges Si los caminos tienen aristas compartidas o no
	 * @return Conjunto de caminos de las fuentes a los sumideros, por aristas con flujo mayor que cero,
	 * con aristas compartidas o no
	 */
	public Set<GraphWalk<FlowVertex, FlowEdge>> getWalks(boolean disjointEdges) {
		if(walks==null)
			this.walks = calculaCaminos(disjointEdges);
		return walks;
	}	

	private Set<GraphWalk<FlowVertex, FlowEdge>> calculaCaminos(Boolean disjointEdges) {
		Set<FlowEdge> usedEdges = Sets.newHashSet();
		Set<GraphWalk<FlowVertex, FlowEdge>> sout = Sets.newHashSet();
		Set<FlowVertex> from = this.sourceFlow.keySet();
		Set<FlowVertex> to = this.sinkFlow.keySet();
		for (FlowVertex v : from) {
			List<FlowVertex> ls0 = Arrays.asList(v);
			GraphWalk<FlowVertex, FlowEdge> gw = new GraphWalk<FlowVertex, FlowEdge>(flowGraph, ls0, 0.);
			sout.add(gw);
		}
		Set<GraphWalk<FlowVertex, FlowEdge>> st = Sets.newHashSet();
		while (true){
			Set<FlowVertex> endVertex = sout.stream().map(x->x.getEndVertex()).collect(Collectors.toSet());
			if(to.containsAll(endVertex)) break;
			for (GraphWalk<FlowVertex, FlowEdge> gwf : sout) {
				FlowVertex ev = gwf.getEndVertex();
				if (to.contains(ev)) {
					st.add(gwf);
				} else {
					Set<FlowEdge> out = flowGraph.outgoingEdgesOf(ev);
					for (FlowEdge e : out) {
						if(disjointEdges && usedEdges.contains(e))continue;
						FlowVertex fv = flowGraph.getEdgeTarget(e);
						if (!gwf.getVertexList().contains(fv) && this.edgeFlow.get(e) > 0.) {
							if(disjointEdges) usedEdges.add(e);
							List<FlowVertex> ls = Lists.newArrayList(gwf.getVertexList());
							ls.add(flowGraph.getEdgeTarget(e));
							Double wg = gwf.getWeight();
							Double w = flowGraph.getEdgeWeight(e);
							GraphWalk<FlowVertex, FlowEdge> gwn = new GraphWalk<FlowVertex, FlowEdge>(
									flowGraph, ls, wg + w);
							st.add(gwn);
						}
					}
				}
			}
			sout = Sets.newHashSet(st);
			st.clear();
		}
		return sout;
	}
}

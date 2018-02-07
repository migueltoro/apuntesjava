package us.lsi.graphs.examples;



import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.jgrapht.Graph;
import org.jgrapht.alg.cycle.HierholzerEulerianCycle;
import org.jgrapht.alg.cycle.PatonCycleBase;
import org.jgrapht.alg.cycle.UndirectedCycleBase;
import org.jgrapht.alg.tour.TwoApproxMetricTSP;
import org.jgrapht.graph.GraphWalk;
import org.jgrapht.graph.SimpleWeightedGraph;

import us.lsi.graphs.GraphWalk2;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.GraphsReader;

public class Ciclos {

	public static void main(String[] args) {
		
		Graph<Ciudad,Carretera> graph =  new SimpleWeightedGraph<Ciudad,Carretera>(Carretera::create);
		graph =  GraphsReader.newGraph("./ficheros/andalucia.txt",Ciudad::create, Carretera::create,graph,Carretera::getKm);
		
		SimpleWeightedGraph<Ciudad, Carretera> gc = Graphs2.completeGraph(graph,200000.);
		
		TwoApproxMetricTSP<Ciudad, Carretera> tsp = new  TwoApproxMetricTSP<>();
		List<Ciudad> r3 = tsp.getTour(gc).getVertexList();
		GraphWalk<Ciudad,Carretera> gw = new GraphWalk<>(gc,r3,0.);
		
		System.out.println("Hamiltonian");
		System.out.println(r3);
		System.out.println(gw.getEdgeList());
		
		
		
		final Graph<Ciudad,Carretera> g = graph;
		
		HierholzerEulerianCycle<Ciudad,Carretera> hc = new HierholzerEulerianCycle<>();
		Boolean r2 = hc.isEulerian(graph);
		
		System.out.println(r2);
		
		UndirectedCycleBase<Ciudad,Carretera> sc = new PatonCycleBase<Ciudad,Carretera>(graph);
		List<List<Ciudad>> r = sc.findCycleBase();
		
		Set<GraphWalk2<Ciudad,Carretera>> sgw = 
				r.stream().map(x->new GraphWalk2<Ciudad,Carretera>(g,x))
						.collect(Collectors.toSet());
		
		sgw.stream().forEach(x->System.out.println(x.getWeight()+"==="+x.getLength()+"==="+x.getVertexList()));
	}

}

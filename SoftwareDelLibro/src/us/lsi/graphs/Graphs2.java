package us.lsi.graphs;


import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.jgrapht.EdgeFactory;
import org.jgrapht.Graph;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.graph.SimpleWeightedGraph;

import us.lsi.common.Lists2;
import us.lsi.graphs.examples.Carretera;
import us.lsi.graphs.examples.Ciudad;

public class Graphs2 {

	
	public static <V,E> SimpleDirectedWeightedGraph<V,E> toDirectedGraph(SimpleWeightedGraph<V,E> graph){
		SimpleDirectedWeightedGraph<V,E> gs = new SimpleDirectedWeightedGraph<V,E>(graph.getEdgeFactory());
		for(V v:graph.vertexSet()){
			gs.addVertex(v);
		}
		for(E e:graph.edgeSet()){
			E e1 = gs.addEdge(graph.getEdgeSource(e), graph.getEdgeTarget(e));
			E e2 = gs.addEdge(graph.getEdgeTarget(e), graph.getEdgeSource(e));
			gs.setEdgeWeight(e1, gs.getEdgeWeight(e));
			gs.setEdgeWeight(e2, gs.getEdgeWeight(e));
		}
		return gs;
	}
	
	public static <V,E> SimpleDirectedGraph<V,E> toDirectedGraph(SimpleGraph<V,E> graph){
		SimpleDirectedGraph<V,E> gs = new SimpleDirectedGraph<V,E>(graph.getEdgeFactory());
		for(V v:graph.vertexSet()){
			gs.addVertex(v);
		}
		for(E e:graph.edgeSet()){
			gs.addEdge(graph.getEdgeSource(e), graph.getEdgeTarget(e));
			gs.addEdge(graph.getEdgeTarget(e), graph.getEdgeSource(e));
		}
		return gs;
	}
	
	public static <V,E,G extends Graph<V,E>> G subGraph(G graph, Predicate<V> pv, Predicate<E> pe, 
			Function<EdgeFactory<V,E>,G> creator){
		
		Set<V> vertices = null;
	    Set<E> edges = null;
		
	    if (pv!=null) vertices = graph.vertexSet().stream().filter(pv).collect(Collectors.toSet());
		else vertices = graph.vertexSet();
		
		if (pe!=null) edges = graph.edgeSet().stream().filter(pe).collect(Collectors.toSet());
		else edges = graph.edgeSet();
		
		G r = creator.apply(graph.getEdgeFactory());
				
		vertices.stream().forEach(x->r.addVertex(x));
		edges.stream().forEach(x->r.addEdge(graph.getEdgeSource(x),graph.getEdgeTarget(x), x));
		
		return r;
	}
	
	public static <V,E> SimpleWeightedGraph<V,E> completeGraph(Graph<V,E> graph, Double w){
		SimpleWeightedGraph<V,E> base = new SimpleWeightedGraph<V,E>(graph.getEdgeFactory());
		List<V> vertices = Lists2.newList(graph.vertexSet());
		Integer n = vertices.size();
		vertices.stream().forEach(v->base.addVertex(v));
		graph.edgeSet().stream().forEach(e->base.addEdge(graph.getEdgeSource(e),graph.getEdgeTarget(e),e));		
		for(int i=0; i<n;i++){
			for(int j=i+1;j<n;j++){
				if(!base.containsEdge(vertices.get(i), vertices.get(j))){
					E e = graph.getEdgeFactory().createEdge(vertices.get(i), vertices.get(j));
					base.addEdge(vertices.get(i), vertices.get(j), e);
					base.setEdgeWeight(e, w);
				}
			}
		}
		return base;
	}
	
	
	public static void main(String[] args) {
		Graph<Ciudad,Carretera> graph =  new SimpleWeightedGraph<Ciudad,Carretera>(Carretera::create);
		graph =  GraphsReader.newGraph("./ficheros/andalucia.txt",Ciudad::create, Carretera::create,graph,Carretera::getKm);
		final Graph<Ciudad,Carretera> graph2 = graph;
		System.out.println(graph.vertexSet());
		System.out.println(graph.edgeSet().stream()
				.map(x->x.toString())
				.collect(Collectors.joining("\n", "=====\n", "\n")));
		System.out.println(graph.edgeSet().stream()
				.map(x->graph2.getEdgeSource(x)+","+graph2.getEdgeTarget(x)+","+graph2.getEdgeWeight(x))
				.collect(Collectors.joining("\n", "=====\n", "\n")));
		SimpleWeightedGraph<Ciudad, Carretera> gc = Graphs2.completeGraph(graph, 5000.);
		System.out.println("Complete graph");
		System.out.println(gc.vertexSet());
		System.out.println(gc.edgeSet().stream()
				.map(x->x.toString())
				.collect(Collectors.joining("\n", "=====\n", "\n")));
		System.out.println(gc.edgeSet().stream()
				.map(x->graph2.getEdgeSource(x)+","+graph2.getEdgeTarget(x)+","+graph2.getEdgeWeight(x))
				.collect(Collectors.joining("\n", "=====\n", "\n")));
	}
}

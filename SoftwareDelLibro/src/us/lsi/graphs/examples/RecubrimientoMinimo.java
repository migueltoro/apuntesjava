package us.lsi.graphs.examples;

import java.io.PrintWriter;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.ext.ComponentNameProvider;
import org.jgrapht.ext.DOTExporter;
import org.jgrapht.ext.IntegerComponentNameProvider;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.graph.SimpleWeightedGraph;

import us.lsi.common.Files2;
import us.lsi.common.Maps2;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.GraphsReader;

import org.jgrapht.alg.interfaces.MinimumVertexCoverAlgorithm.VertexCover;
import org.jgrapht.alg.interfaces.MinimumWeightedVertexCoverAlgorithm;
import org.jgrapht.alg.interfaces.SpanningTreeAlgorithm;
import org.jgrapht.alg.interfaces.SpanningTreeAlgorithm.SpanningTree;
import org.jgrapht.alg.spanning.KruskalMinimumSpanningTree;
import org.jgrapht.alg.vertexcover.RecursiveExactVCImpl;

/**
 * Calcula varios recubrimiento de un grafo
 * 
 * @author Miguel Toro
 *
 */
public class RecubrimientoMinimo {
	
	public static void main(String[] args) {
		
		Graph<Ciudad,Carretera> graph =  new SimpleWeightedGraph<Ciudad,Carretera>(Carretera::create);
		graph =  GraphsReader.newGraph("./ficheros/andalucia.txt",Ciudad::create, Carretera::create,graph,Carretera::getKm);
		
		SpanningTreeAlgorithm<Carretera> ast = new KruskalMinimumSpanningTree<>(graph);
		SpanningTree<Carretera> r = ast.getSpanningTree();
		
		Graph<Ciudad,Carretera> subGraph = Graphs2.subGraph(graph, 
				null,
				x->r.getEdges().contains(x), 
				x->new SimpleGraph<Ciudad, Carretera>(x));
		
		MinimumWeightedVertexCoverAlgorithm<Ciudad,Carretera> avc = new RecursiveExactVCImpl<>();
		
		Map<Ciudad,Double> habitantes = Maps2.newHashMap(x->1/x.getHabitantes());
		
		VertexCover<Ciudad> r2 = avc.getVertexCover(
				(UndirectedGraph<Ciudad, Carretera>) subGraph,habitantes);
		
		System.out.println(r);
		
		ComponentNameProvider<Ciudad> vertexIDProvider = new IntegerComponentNameProvider<>();
		
		DOTExporter<Ciudad,Carretera> de = new DOTExporter<Ciudad,Carretera>(
				vertexIDProvider,
				x->x.getNombre(), 
				x->x.getNombre(),
				x->r2.getVertices().contains(x)? Maps2.newHashMap("color","green","style","filled"): null,
				x->r.getEdges().contains(x)?Maps2.newHashMap("style","bold"): null);
		
		PrintWriter f = Files2.getWriter("./ficheros/recubrimientoAndalucia.gv");
		de.exportGraph(graph, f);
	}

	
	
}

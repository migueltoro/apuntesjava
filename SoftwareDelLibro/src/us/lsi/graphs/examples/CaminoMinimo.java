package us.lsi.graphs.examples;

import java.io.PrintWriter;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.io.ComponentNameProvider;
import org.jgrapht.io.DOTExporter;
import org.jgrapht.io.DefaultAttribute;
import org.jgrapht.io.IntegerComponentNameProvider;

import us.lsi.common.Files2;
import us.lsi.common.Maps2;
import us.lsi.graphs.GraphsReader;

/**
 * Resuelve un problema de camino mínimo
 * 
 * @author Miguel Toro
 *
 */
public class CaminoMinimo {

	public static void main(String[] args) {
		Graph<Ciudad,Carretera> graph =  new SimpleWeightedGraph<Ciudad,Carretera>(Carretera::create);
		graph =  GraphsReader.newGraph("./ficheros/andalucia.txt",Ciudad::create, Carretera::create,graph,Carretera::getKm);
		ShortestPathAlgorithm<Ciudad,Carretera> a = new DijkstraShortestPath<Ciudad,Carretera>(graph);
		Ciudad from = Ciudad.create("Huelva");
		Ciudad to = Ciudad.create("Almeria");
		GraphPath<Ciudad,Carretera> gp =  a.getPath(from,to);
		System.out.println(gp);
		System.out.println(gp.getVertexList());
		ComponentNameProvider<Ciudad> vertexIDProvider = new IntegerComponentNameProvider<>();
		DOTExporter<Ciudad,Carretera> de = new DOTExporter<Ciudad,Carretera>(
				vertexIDProvider,
				x->x.getNombre(), 
				x->x.getNombre()+"--"+x.getKm(),
				null,
				x->gp.getEdgeList().contains(x)?Maps2.newHashMap("style",
						DefaultAttribute.createAttribute("bold")): null);
		PrintWriter f = Files2.getWriter("./ficheros/caminoMinimoAndalucia.gv");
		de.exportGraph(graph, f);
	}

}

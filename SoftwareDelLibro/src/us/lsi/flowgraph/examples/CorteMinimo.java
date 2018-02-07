package us.lsi.flowgraph.examples;


import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.alg.StoerWagnerMinimumCut;
import org.jgrapht.alg.flow.EdmondsKarpMFImpl;
import org.jgrapht.graph.SimpleWeightedGraph;

import us.lsi.graphs.GraphsReader;
import us.lsi.graphs.examples.Carretera;
import us.lsi.graphs.examples.Ciudad;

/**
 * Resuelve varios problemas de corte mínimo
 * 
 * @author Miguel Toro
 *
 */
public class CorteMinimo {

	public static Graph<Ciudad,Carretera> creaFichero(String fileIn){
		
		Graph<Ciudad,Carretera> g =  new SimpleWeightedGraph<Ciudad,Carretera>(Carretera::create);
		g =  GraphsReader.newGraph(fileIn,Ciudad::create, Carretera::create,g,Carretera::getKm);
		return g;
	}
	
	
	public static void main(String[] args) {
		
		Graph<Ciudad,Carretera> graph = CorteMinimo.creaFichero("./ficheros/andalucia.txt");
		StoerWagnerMinimumCut<Ciudad,Carretera> a = new StoerWagnerMinimumCut<Ciudad,Carretera>(graph);
		Set<Ciudad> source = a.minCut();
		System.out.println(source);
		EdmondsKarpMFImpl<Ciudad,Carretera> a2 = new EdmondsKarpMFImpl<Ciudad,Carretera>(graph);
		Ciudad from = Ciudad.create("Antequera");
		Ciudad to = Ciudad.create("Almeria");
		Double r = a2.calculateMinCut(from, to);
		Set<Ciudad> source2 = a2.getSourcePartition();
		Set<Ciudad> target2 = a2.getSinkPartition();
		Set<Carretera> edges = a2.getCutEdges();
		System.out.println(r+","+source2+","+target2+","+edges);		
	}

}

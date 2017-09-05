package us.lsi.graphs.examples;

import java.io.PrintWriter;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.alg.ChromaticNumber;
import org.jgrapht.ext.ComponentNameProvider;
import org.jgrapht.ext.DOTExporter;
import org.jgrapht.ext.IntegerComponentNameProvider;
import org.jgrapht.graph.SimpleWeightedGraph;

import us.lsi.common.Colors;
import us.lsi.common.Files2;
import us.lsi.common.Maps2;
import us.lsi.graphs.GraphsReader;

/**
 * Resulve un problema de coloreado de grafos
 * 
 * @author Miguel Toro
 *
 */
public class ColoreadoDeGrafos {

	public static void main(String[] args) {
		
		Graph<Ciudad,Carretera> graph =  new SimpleWeightedGraph<Ciudad,Carretera>(Carretera::create);
		graph =  GraphsReader.newGraph("./ficheros/andalucia.txt",Ciudad::create, Carretera::create,graph,Carretera::getKm);
		
		int r = ChromaticNumber.findGreedyChromaticNumber((UndirectedGraph<Ciudad,Carretera>)graph);
		Map<Integer,Set<Ciudad>> m = ChromaticNumber.findGreedyColoredGroups((UndirectedGraph<Ciudad,Carretera>)graph);
		Map<Ciudad,String> colorDeCiudad = Maps2.newHashMap(Maps2.newHashMap(m),
															x->Colors.getNameOfColor(x));
		System.out.println(r);
		ComponentNameProvider<Ciudad> vertexIDProvider = new IntegerComponentNameProvider<>();
		DOTExporter<Ciudad,Carretera> de = new DOTExporter<Ciudad,Carretera>(
				vertexIDProvider,
				x->x.getNombre(), 
				x->x.getNombre(),
				x->Maps2.newHashMap("color",colorDeCiudad.get(x),"style", "filled"),
				null);
		PrintWriter f = Files2.getWriter("./ficheros/coloresAndalucia.gv");
		de.exportGraph(graph, f);
		System.out.println(m);
	}

}

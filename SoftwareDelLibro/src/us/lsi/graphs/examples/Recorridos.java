package us.lsi.graphs.examples;

import java.io.PrintWriter;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.io.DOTExporter;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.jgrapht.traverse.DepthFirstIterator;

import com.google.common.collect.Maps;

import us.lsi.common.Files2;
import us.lsi.graphs.GraphsReader;

/**
 * Calcula varios recorridos sobre un grafo
 * 
 * @author Miguel Toro
 *
 */
public class Recorridos {

	public static void main(String[] args) {

		Graph<Ciudad,Carretera> graph =  new SimpleWeightedGraph<Ciudad,Carretera>(Carretera::create);
		graph =  GraphsReader.newGraph("./ficheros/andalucia.txt",Ciudad::create, Carretera::create,graph,Carretera::getKm);
		
		DepthFirstIterator<Ciudad, Carretera> rp = new DepthFirstIterator<>(graph,Ciudad.create("Sevilla"));
		Map<Ciudad,Integer> m = Maps.newHashMap();
		Integer n = 0;
		while(rp.hasNext()){
			m.put(rp.next(), n);
			n++;
		}	
		
		DOTExporter<Ciudad,Carretera> de1 = new DOTExporter<Ciudad,Carretera>(
				x->m.get(x).toString(),
				x->x.getNombre()+"/"+m.get(x).toString(), 
				x->x.getNombre());
		
		PrintWriter f1 = Files2.getWriter("./ficheros/recorridoEnProfundidadAndalucia.gv");
		de1.exportGraph(graph, f1);
		
		BreadthFirstIterator<Ciudad, Carretera> ra = new BreadthFirstIterator<>(graph,Ciudad.create("Sevilla"));
		m.clear();
		n = 0;
		while(ra.hasNext()){
			m.put(ra.next(), n);
			n++;
		}	
		
		DOTExporter<Ciudad,Carretera> de2 = new DOTExporter<Ciudad,Carretera>(
				x->m.get(x).toString(),
				x->x.getNombre()+"/"+m.get(x).toString(), 
				x->x.getNombre());
		
		PrintWriter f2 = Files2.getWriter("./ficheros/recorridoEnAnchuraAndalucia.gv");
		de2.exportGraph(graph, f2);
	}

}

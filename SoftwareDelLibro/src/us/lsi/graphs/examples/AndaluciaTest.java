package us.lsi.graphs.examples;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.alg.ChromaticNumber;
import org.jgrapht.graph.SimpleGraph;

import com.google.common.collect.Sets;

import java.util.*;

import us.lsi.graphs.*;

import us.lsi.pd.floyd.Carretera;
import us.lsi.pd.floyd.Ciudad;


public class AndaluciaTest {

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Graph<Ciudad,Carretera> g =  new SimpleGraph<Ciudad,Carretera>(Carretera::create);
		g =  GraphsReader.newGraph("andalucia.txt",Ciudad::create, Carretera::create,g);
		int r = ChromaticNumber.findGreedyChromaticNumber((UndirectedGraph<Ciudad,Carretera>)g);
//		Map<Integer,Set<String>> m = ChromaticNumber.findGreedyColoredGroups(g);
		Map<Integer,Set<Ciudad>> m = ChromaticNumber.findGreedyColoredGroups((UndirectedGraph<Ciudad,Carretera>)g);
		System.out.println(g);
		System.out.println(r);
		System.out.println(m);
		Set<Ciudad> s = Sets.newHashSet();
		Set<Ciudad> ms = Sets.newHashSet();
		for(int i = 0; i< m.size();i++){
			for (Ciudad v: m.get(i)) {
				ms.add(v);
				s.add(v);
				s.addAll(Graphs.neighborListOf(g,v));
			}
			if(s.size()==g.vertexSet().size()) break;
		}
		System.out.println(ms);
		GraphsFileExporter.<Ciudad,Carretera>saveFile(g, "salida6.gsv", ms,new HashSet<Carretera>());
		
	}
}

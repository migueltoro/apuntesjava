package us.lsi.pd.floyd;

import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;

import us.lsi.algoritmos.Algoritmos;
import us.lsi.graphs.GraphView;
import us.lsi.pd.AlgoritmoPD;



public class TestFloyd {

	public static void main(String[] args) {
				
		GrafoDelMapa g = GrafoDelMapa.create(AlgoritmoPD.getRaiz()+"mapa.txt");
			
		System.out.println(g.getGrafo());
		
		GraphView<Ciudad,Carretera> gv = GraphView.create(g.getGrafo());
		
		int origen = gv.getIndex(new Ciudad("Cadiz"));
		int destino = gv.getIndex(new Ciudad("Almeria"));
		FloydPD<Ciudad,Carretera> p = FloydPD.create(origen,destino,gv);
		
		AlgoritmoPD<GraphPath<Ciudad,Carretera>,FloydPD.Alternativa> a = Algoritmos.createPD(p);
				
		a.ejecuta();
		
		GraphPath<Ciudad,Carretera> s = a.getSolucion(p);
		
		a.showAllGraph("solucionMapa.gv", "SolucionMapa", p);
		
		System.out.println(Graphs.getPathVertexList(s));
		
	}
}

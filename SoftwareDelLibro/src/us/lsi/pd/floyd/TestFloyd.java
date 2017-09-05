package us.lsi.pd.floyd;

import org.jgrapht.GraphPath;

import us.lsi.algoritmos.Algoritmos;
import us.lsi.graphs.GraphView;
import us.lsi.graphs.examples.Carretera;
import us.lsi.graphs.examples.Ciudad;
import us.lsi.pd.AlgoritmoPD;



public class TestFloyd {

	public static void main(String[] args) {
				
		GrafoDelMapa g = GrafoDelMapa.create("./ficheros/andalucia.txt");
			
		System.out.println(g.getGrafo());
		
		GraphView<Ciudad,Carretera> gv = GraphView.create(g.getGrafo());
		
		int origen = gv.getIndex(Ciudad.create("Cadiz"));
		int destino = gv.getIndex(Ciudad.create("Almeria"));
		FloydPD<Ciudad,Carretera> p = FloydPD.create(origen,destino,gv);
		AlgoritmoPD<GraphPath<Ciudad,Carretera>,FloydPD.Alternativa> a = Algoritmos.createPD(p);
				
		a.ejecuta();
		
		GraphPath<Ciudad,Carretera> s = a.getSolucion(p);
		
		a.showAllGraph("ficheros/solucionMapa.gv", "SolucionMapa", p);
		
		System.out.println(s.getVertexList());
		
		a.getAlternativasDeSolucion(p).toDOT("ficheros/alternativasMapa.gv", "Alternativas");
		
	}
}

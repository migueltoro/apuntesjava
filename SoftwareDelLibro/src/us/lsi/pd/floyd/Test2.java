package us.lsi.pd.floyd;


import us.lsi.graphs.GraphView;
import us.lsi.graphs.examples.Carretera;
import us.lsi.graphs.examples.Ciudad;
import us.lsi.pd.AlgunosTestsPD;
import us.lsi.tiposrecursivos.Tree;

public class Test2 {

	public static void main(String[] args) {
		GrafoDelMapa g = GrafoDelMapa.create("./ficheros/andalucia.txt");
		
		System.out.println(g.getGrafo());
		
		GraphView<Ciudad,Carretera> gv = GraphView.create(g.getGrafo());
		
		int origen = gv.getIndex(Ciudad.create("Cadiz"));
		int destino = gv.getIndex(Ciudad.create("Almeria"));
		FloydPD<Ciudad,Carretera> p = FloydPD.create(origen,destino,gv);
		
		
		Tree<FloydPD.Alternativa> t = AlgunosTestsPD.test2(p);
		AlgunosTestsPD.test1(p,t);

	}

}

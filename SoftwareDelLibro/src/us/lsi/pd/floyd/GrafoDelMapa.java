package us.lsi.pd.floyd;


import us.lsi.graphs.GraphsReader;

import org.jgrapht.WeightedGraph;
import org.jgrapht.graph.SimpleWeightedGraph;

public class GrafoDelMapa {

	
	public static GrafoDelMapa create(String fichero) {
		return new GrafoDelMapa(fichero);
	}

	private WeightedGraph<Ciudad,Carretera> grafo;		
	
	private GrafoDelMapa(String fichero) {
		super();
		leeDatos(fichero);		
	}

	public void leeDatos(String fichero){
		this.grafo = new SimpleWeightedGraph<Ciudad,Carretera>(Carretera.getFactory());
		this.grafo = (WeightedGraph<Ciudad, Carretera>) GraphsReader.newGraph(fichero, Ciudad.getFactory(),Carretera.getFactory(),grafo);			
		for(Carretera c: grafo.edgeSet()){
			grafo.setEdgeWeight(c, c.getKm());
		}		
	}

	public WeightedGraph<Ciudad, Carretera> getGrafo() {
		return grafo;
	}	
	
}

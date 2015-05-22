package us.lsi.astar.vuelos;

import java.util.Set;
import java.util.function.Predicate;

import org.jgrapht.EdgeFactory;
import org.jgrapht.graph.*;

import us.lsi.astar.AStarGraph;

public class GrafoVuelos extends DirectedMultigraph<String,Vuelo> implements AStarGraph<String, Vuelo> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public double horaDeLlegadaAlAeropuerto;
	

	public GrafoVuelos(Class<? extends Vuelo> arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}


	public GrafoVuelos(EdgeFactory<String, Vuelo> arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}


/*	public static GrafoVuelos create(Graph<String,Vuelo> gg) {
		GrafoVuelos grafo = new GrafoVuelos(gg.getEdgeFactory());
		for(String s:gg.vertexSet()){
			grafo.addVertex(s);
		}
		for(Vuelo v:gg.edgeSet()){
			grafo.addEdge(v.getFrom(), v.getTo(), v);
		}
		return grafo;
	}
	
*/	
	@Override
	public double getEdgeWeight(Vuelo v) {
		// TODO Auto-generated method stub
		return v.getDuracion();
	}

	
	@Override
	public double getVertexWeight(String vertex) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getVertexWeight(String vertex, Vuelo edgeIn, Vuelo edgeOut) {
		// TODO Auto-generated method stub
		double r;
		double horaDeLlegada;
		if(edgeIn==null){
			horaDeLlegada = this.horaDeLlegadaAlAeropuerto;
		} else {
			horaDeLlegada = edgeIn.getHoraDeLlegada();
		}
		r = edgeOut.getHoraDeSalida()-horaDeLlegada;
		if(r<0){
			r+=24.;
		}
		return r;
	}

	@Override
	public double getWeightToEnd(String startVertex, String endVertex, Predicate<String> p, Set<String> s) {
		return 0;
	}

}

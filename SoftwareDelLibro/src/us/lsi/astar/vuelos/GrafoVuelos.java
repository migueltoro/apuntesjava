package us.lsi.astar.vuelos;


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
	}


	public GrafoVuelos(EdgeFactory<String, Vuelo> arg0) {
		super(arg0);
	}
	
	@Override
	public double getEdgeWeight(Vuelo v){
		return v.getDuracion();
	}


	@Override
	public double getVertexWeight(String vertex, Vuelo edgeIn, Vuelo edgeOut) {
		double r;
		double horaDeLlegada;
		double horaDeSalida;
		if(edgeIn==null){
			horaDeLlegada = this.horaDeLlegadaAlAeropuerto;
		} else {
			horaDeLlegada = edgeIn.getHoraDeLlegada();
		}
		if(edgeOut==null){
			horaDeSalida = edgeIn.getHoraDeLlegada();
		} else {
			horaDeSalida = edgeOut.getHoraDeSalida();
		}
		r = horaDeSalida-horaDeLlegada;
		if(r<0){
			r+=24.;
		}
		return r;
	}

}

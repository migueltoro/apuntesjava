package us.lsi.ag.real;

import java.util.ArrayList;
import java.util.List;

import us.lsi.ag.Cromosoma;
import us.lsi.ag.ProblemaAGReal;
import us.lsi.common.Par;

public class ProblemaReal implements ProblemaAGReal<List<Double>> {

	public ProblemaReal(){
		
	}

	@Override
	public List<Double> getSolucion(Cromosoma<Double> chromosome) {
		return chromosome.decode();
	}

	@Override
	public Integer getNumeroDeVariables() {
		return 2;
	}

	@Override
	public List<Par<Double, Double>> getLimites() {
		List<Par<Double,Double>> ls = new ArrayList<>();
		ls.add(Par.create(0., 1.));
		ls.add(Par.create(0., 15.));
		return ls;
	}

	@Override
	public Double fitnessFunction(List<Double> ls) {
		Double a = ls.get(0);
		Double b = ls.get(1);
//		return (100 * Math.pow(Math.pow(a,2) - b,2) + Math.pow(1 - a,2))-constraint(a,b);
		return -(a*a+b*b)+constraint(a,b);
	}
	    
	    private double constraint(Double a, Double b){
	    	boolean r = true;
//	    	r = r && (a*b + a - b + 1.5) <=0;
//	   	r = r && (10 - a*b <=0);
	    	return r?0:10000000;
	    }
		
}

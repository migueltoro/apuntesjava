package us.lsi.ag.real;

import java.util.ArrayList;
import java.util.List;

import us.lsi.ag.ProblemaAGReal;
import us.lsi.ag.agchromosomes.IRealChromosome;
import us.lsi.common.Par;

public class ProblemaReal implements ProblemaAGReal<List<Double>> {

	public ProblemaReal(){
		
	}

	@Override
	public List<Double> getSolucion(IRealChromosome chromosome) {
		return chromosome.decode();
	}

	@Override
	public Integer getNumeroDeVariables() {
		return 2;
	}

	@Override
	public List<Par<Double, Double>> getLimites() {
		List<Par<Double,Double>> ls = new ArrayList<>();
		ls.add(Par.create(0., 15.));
		ls.add(Par.create(-15., 15.));
		return ls;
	}

	@Override
	public Double fitnessFunction(IRealChromosome chromosome) {
		List<Double> ls = chromosome.decode();
		Double a = ls.get(0);
		Double b = ls.get(1);
		Double k = 10000000.;
		return (a*a+b*b)- k*r1(a,b);
	}
	    
	    private double r1(Double a, Double b){
	    	// (-a + b) >=0;
	    	Double r = -a + b -1;	    	
	    	return r*r;
	    }
		
}

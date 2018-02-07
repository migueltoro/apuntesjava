package us.lsi.ag.real;

import java.util.List;

import us.lsi.ag.ValuesInRangeChromosome;
import us.lsi.ag.ValuesInRangeProblemAG;

public class ProblemaReal implements ValuesInRangeProblemAG<Double,List<Double>> {

	public ProblemaReal(){
		
	}

	@Override
	public List<Double> getSolucion(ValuesInRangeChromosome<Double> chromosome) {
		return chromosome.decode();
	}

	@Override
	public Integer getVariableNumber(){
		return 2;
	}
	
	@Override
	public Double getMax(Integer i) {		
		Double[] r = {15.,15.};
		return r[i];
	}

	@Override
	public Double getMin(Integer i) {
		Double[] r = {0.,-15.};
		return r[i];
	}

	@Override
	public Double fitnessFunction(ValuesInRangeChromosome<Double> chromosome) {
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

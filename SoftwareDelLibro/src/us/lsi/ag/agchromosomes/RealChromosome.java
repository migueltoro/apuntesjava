package us.lsi.ag.agchromosomes;

import java.util.List;

import us.lsi.ag.ProblemaAGReal;

public interface RealChromosome extends IChromosome<List<Double>> {

	Integer getNum();

	Double getSup(int i);

	Double getInf(int i);
	
	ProblemaAGReal<?> getProblema();
}
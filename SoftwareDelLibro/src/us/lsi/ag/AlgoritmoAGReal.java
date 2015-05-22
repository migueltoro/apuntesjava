package us.lsi.ag;

import org.apache.commons.math3.genetics.BinaryMutation;
import org.apache.commons.math3.genetics.Chromosome;


public class AlgoritmoAGReal extends AlgoritmoAG<Integer> {
	
	public AlgoritmoAGReal(ProblemaAGReal<?> problema) {
		super();
		super.mutationPolicy = new BinaryMutation();
		AlgoritmoAG.DIMENSION = problema.getNumeroDeVariables()*RealBinaryChromosome.numeroDeBits;
	}
	
	@Override
	public Chromosome getInitialChromosome() {
		return RealBinaryChromosome.getInitialChromosome();
	}
	
}

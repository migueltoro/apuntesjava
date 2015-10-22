package us.lsi.ag.agchromosomes;

import java.util.List;

import org.apache.commons.math3.genetics.AbstractListChromosome;
import org.apache.commons.math3.genetics.BinaryChromosome;
import org.apache.commons.math3.genetics.Chromosome;
import org.apache.commons.math3.genetics.InvalidRepresentationException;

import us.lsi.ag.ProblemaAG;
import us.lsi.ag.ProblemaAGBinario;

public class BinaryChromosomeModified extends BinaryChromosome implements IBinaryChromosome {
	
	public static ProblemaAGBinario<?> problema;
	
	/**
	 * Dimensión del cromosoma
	 */
	
	protected static int DIMENSION;
	
	public static void iniValues(ProblemaAG problema){
		BinaryChromosomeModified.problema = (ProblemaAGBinario<?>) problema; 
		BinaryChromosomeModified.DIMENSION = BinaryChromosomeModified.problema.getDimensionDelChromosoma();
	}

	public BinaryChromosomeModified(List<Integer> representation) throws InvalidRepresentationException {
		super(representation);
		this.ft = this.calculateFt();
	}

	public BinaryChromosomeModified(Integer[] representation) throws InvalidRepresentationException {
		super(representation);
		this.ft = this.calculateFt();
	}

	@Override
	public List<Integer> decode() {
		return getRepresentation();
	}

	@Override
	public double fitness() {
		return ft;
	}
	
	private Double ft = null;
	
	private double calculateFt(){
		return BinaryChromosomeModified.problema.fitnessFunction(this.decode());
	}

	@Override
	public AbstractListChromosome<Integer> newFixedLengthChromosome(List<Integer> ar) {
		return new BinaryChromosomeModified(ar);
	}

	public ProblemaAGBinario<?> getProblema() {
		return problema;
	}

	public static int getDimension() {
		return BinaryChromosomeModified.problema.getDimensionDelChromosoma();
	}

	@Override
	public Chromosome asChromosome() {
		return this;
	}
	
	public static IBinaryChromosome getInitialChromosome() {
		List<Integer> ls = BinaryChromosome.randomBinaryRepresentation(BinaryChromosomeModified.getDimension());
		return new BinaryChromosomeModified(ls);
	}
}

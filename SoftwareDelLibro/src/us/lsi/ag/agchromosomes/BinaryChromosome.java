package us.lsi.ag.agchromosomes;

import java.util.List;

import org.apache.commons.math3.genetics.AbstractListChromosome;
import org.apache.commons.math3.genetics.Chromosome;
import org.apache.commons.math3.genetics.InvalidRepresentationException;

import us.lsi.ag.ProblemaAG;
import us.lsi.ag.ProblemaAGBinary;

/**
 * @author Miguel Toro
 * 
 * <p> 
 * Un cromosoma cuya valor decodificado es una lista de ceros y unos del tamaño especificado en el problema.
 * La implementación es una adaptación de la clase {@link org.apache.commons.math3.genetics.Chromosome Chromosome} de Apache. </p>
 *
 */
public class BinaryChromosome extends org.apache.commons.math3.genetics.BinaryChromosome implements IBinaryChromosome {
	
	public static ProblemaAGBinary<?> problema;
	
	/**
	 * Dimensión del cromosoma
	 */
	
	protected static int DIMENSION;
	
	public static void iniValues(ProblemaAG problema){
		BinaryChromosome.problema = (ProblemaAGBinary<?>) problema; 
		BinaryChromosome.DIMENSION = BinaryChromosome.problema.getDimensionDelChromosoma();
	}

	public BinaryChromosome(List<Integer> representation) throws InvalidRepresentationException {
		super(representation);
		this.ft = this.calculateFt();
	}

	public BinaryChromosome(Integer[] representation) throws InvalidRepresentationException {
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
		return BinaryChromosome.problema.fitnessFunction(this);
	}

	@Override
	public AbstractListChromosome<Integer> newFixedLengthChromosome(List<Integer> ar) {
		return new BinaryChromosome(ar);
	}

	public ProblemaAGBinary<?> getProblema() {
		return problema;
	}

	public static int getDimension() {
		return BinaryChromosome.problema.getDimensionDelChromosoma();
	}

	@Override
	public Chromosome asChromosome() {
		return this;
	}
	
	public static BinaryChromosome getInitialChromosome() {
		List<Integer> ls = BinaryChromosome.randomBinaryRepresentation(BinaryChromosome.getDimension());
		return new BinaryChromosome(ls);
	}
}

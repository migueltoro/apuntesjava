package us.lsi.ag.agchromosomes;

import java.util.List;

import org.apache.commons.math3.genetics.AbstractListChromosome;
import org.apache.commons.math3.genetics.BinaryChromosome;
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
public class BinaryChromosome2 extends BinaryChromosome implements IBinaryChromosome {
	
	public static ProblemaAGBinary<?> problema;
	
	/**
	 * Dimensión del cromosoma
	 */
	
	protected static int DIMENSION;
	
	public static void iniValues(ProblemaAG problema){
		BinaryChromosome2.problema = (ProblemaAGBinary<?>) problema; 
		BinaryChromosome2.DIMENSION = BinaryChromosome2.problema.getDimensionDelChromosoma();
	}

	public BinaryChromosome2(List<Integer> representation) throws InvalidRepresentationException {
		super(representation);
		this.ft = this.calculateFt();
	}

	public BinaryChromosome2(Integer[] representation) throws InvalidRepresentationException {
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
		return BinaryChromosome2.problema.fitnessFunction(this);
	}

	@Override
	public AbstractListChromosome<Integer> newFixedLengthChromosome(List<Integer> ar) {
		return new BinaryChromosome2(ar);
	}

	public ProblemaAGBinary<?> getProblema() {
		return problema;
	}

	public static int getDimension() {
		return BinaryChromosome2.problema.getDimensionDelChromosoma();
	}

	@Override
	public Chromosome asChromosome() {
		return this;
	}
	
	public static BinaryChromosome2 getInitialChromosome() {
		List<Integer> ls = BinaryChromosome.randomBinaryRepresentation(BinaryChromosome2.getDimension());
		return new BinaryChromosome2(ls);
	}
}

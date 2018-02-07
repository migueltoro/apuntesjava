package us.lsi.ag.agchromosomes;

import java.util.List;

import org.apache.commons.math3.genetics.AbstractListChromosome;
import org.apache.commons.math3.genetics.InvalidRepresentationException;

import us.lsi.ag.ProblemAG;
import us.lsi.ag.ValuesInRangeChromosome;
import us.lsi.ag.ValuesInRangeProblemAG;

/**
 * @author Miguel Toro
 * 
 * <p> 
 * Un cromosoma cuya valor decodificado es una lista de ceros y unos del tamaño especificado en el problema.
 * La implementación es una adaptación de la clase {@link org.apache.commons.math3.genetics.Chromosome Chromosome} de Apache. </p>
 *
 */
public class BinaryChromosome extends org.apache.commons.math3.genetics.BinaryChromosome implements ValuesInRangeChromosome<Integer> {
	
	public static ValuesInRangeProblemAG<Integer,?> problem;
	
	/**
	 * Dimensión del cromosoma
	 */
	
	protected static int DIMENSION;
	
	@SuppressWarnings("unchecked")
	public static void iniValues(ProblemAG problema){
		BinaryChromosome.problem = (ValuesInRangeProblemAG<Integer,?>) problema; 
		BinaryChromosome.DIMENSION = BinaryChromosome.problem.getVariableNumber();
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
		return BinaryChromosome.problem.fitnessFunction(this);
	}

	@Override
	public AbstractListChromosome<Integer> newFixedLengthChromosome(List<Integer> ar) {
		return new BinaryChromosome(ar);
	}

	public ValuesInRangeProblemAG<Integer,?> getProblem() {
		return problem;
	}

	public static int getDimension() {
		return BinaryChromosome.problem.getVariableNumber();
	}
	
	public static BinaryChromosome getInitialChromosome() {
		List<Integer> ls = BinaryChromosome.randomBinaryRepresentation(BinaryChromosome.getDimension());
		return new BinaryChromosome(ls);
	}
}

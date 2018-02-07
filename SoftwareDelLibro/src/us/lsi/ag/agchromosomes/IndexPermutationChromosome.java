package us.lsi.ag.agchromosomes;

import java.util.List;

import org.apache.commons.math3.genetics.AbstractListChromosome;
import org.apache.commons.math3.genetics.InvalidRepresentationException;
import org.apache.commons.math3.genetics.RandomKey;

import us.lsi.ag.IndexChromosome;
import us.lsi.ag.ProblemAG;
import us.lsi.ag.IndexProblemAG;


import com.google.common.base.Preconditions;

/**
 * @author Miguel Toro
 * 
 * <p> Una implementación del tipo IndexChromosome. Toma como información la definición de un problema que implementa el interfaz IndexProblemAG.
 * A partir de esa información construye una secuencia normal. 
 * Asumimos que el número de objetos es <code>n </code>y el tamaño de la secuencia normal <code>r</code>. 
 * Lista decodificada es una permutación de la secuencia normal.</p>
 *  
 * <p> La lista decodificada está formada por una lista de  tamaño <code>r</code>, cuyos valores son 
 * índices en el rango <code> [0,n-1]</code>, y cada índice <code>i</code> se  repite un número de veces igual al 
 * dado por la multiplicidad máxima del objeto <code> i </code>
 * definida en el problema. </p>
 * 
 * <p> La implementación usa un cromosoma de clave aleatoria de tamaño <code> r </code>.
 * Es un cromosoma adecuado para codificar problemas de permutaciones </p>
 *
 */
public class IndexPermutationChromosome extends RandomKey<Integer> implements IndexChromosome {

	public static List<Integer> normalSequence = null;
	public static IndexProblemAG<?> problema;
	
	/**
	 * Dimensión del cromosoma
	 */
	
	public static int DIMENSION;
	
	public static void iniValues(ProblemAG problema){
		IndexPermutationChromosome.problema = (IndexProblemAG<?>) problema; 
		IndexPermutationChromosome.normalSequence = IndexPermutationChromosome.problema.getNormalSequence();
		IndexPermutationChromosome.DIMENSION = IndexPermutationChromosome.normalSequence.size();
	}

	
	public IndexPermutationChromosome(List<Double> representation)
			throws InvalidRepresentationException {
		super(representation);
		this.ft = this.calculateFt();
	}

	public IndexPermutationChromosome(Double[] representation)
			throws InvalidRepresentationException {
		super(representation);
		this.ft = this.calculateFt();
	}

	@Override
	public AbstractListChromosome<Double> newFixedLengthChromosome(List<Double> ls) {
		return new IndexPermutationChromosome(ls);
		
	}

	@Override
	public List<Integer> decode() {
		Preconditions.checkArgument(IndexPermutationChromosome.normalSequence!=null);
		return this.decode(IndexPermutationChromosome.normalSequence);
	}
	
	
	public static IndexPermutationChromosome getInitialChromosome() {
		List<Double> ls = RandomKey.randomPermutation(IndexPermutationChromosome.DIMENSION);
		return new IndexPermutationChromosome(ls);
	}

	
	@Override
	public double fitness() {
		return ft;
	}
	
	private double ft;
	
	private double calculateFt(){
		return IndexPermutationChromosome.problema.fitnessFunction(this);
	}

	@Override
	public IndexProblemAG<?> getProblem() {
		return IndexPermutationChromosome.problema;
	}

	public Integer getObjectsNumber() {
		return IndexPermutationChromosome.problema.getObjectsNumber();
	}

	public Integer getMax(int i) {
		return IndexPermutationChromosome.problema.getMaxMultiplicity(i);
	}
	
}
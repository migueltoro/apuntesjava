package us.lsi.ag.agchromosomes;

import java.util.List;

import org.apache.commons.math3.genetics.AbstractListChromosome;
import org.apache.commons.math3.genetics.Chromosome;
import org.apache.commons.math3.genetics.InvalidRepresentationException;

import us.lsi.ag.ProblemaAG;
import us.lsi.ag.ProblemaAGListInteger;

/**
 * @author Miguel Toro
 * 
 * <p> Una implementación del tipo IChromosome&lt;Integer&gt;. Toma como información la definición de un problema que implementa el interfaz ProblemaAGListInteger. </p>
 *  
 * <p> La lista decodificada está formada por una lista de  entero de tamaño r </p>
 * 
 * <p> La implementación usa una lista de enteros de tamaño r.
 * Es un cromosoma general para trabajar con problemas cuyas soluciones pueden ser modeladas por listas de enteros. </p>
 *
 */

public class ListIntegerChromosome extends AbstractListChromosome<Integer> implements IChromosome<List<Integer>> {

	public static ProblemaAGListInteger<?> problema;
	
	/**
	 * Dimensión del cromosoma
	 */
	
	public static int DIMENSION;
	
	public static void iniValues(ProblemaAG problema){
		ListIntegerChromosome.problema = (ProblemaAGListInteger<?>) problema; 
		ListIntegerChromosome.DIMENSION = ListIntegerChromosome.problema.getDimension();
	}
	
	public ListIntegerChromosome(List<Integer> representation) throws InvalidRepresentationException {
		super(representation);
		this.ft = this.calculateFt();
	}

	public ListIntegerChromosome(Integer[] representation) throws InvalidRepresentationException {
		super(representation);
		this.ft = this.calculateFt();
	}	
	
	@Override
	public List<Integer> decode() {
		return super.getRepresentation();
	}

	public static ListIntegerChromosome getInitialChromosome() {
		List<Integer> ls = ListIntegerChromosome.problema.getRandomList();
		return new ListIntegerChromosome(ls);
	}
	
	private double ft;
	
	private double calculateFt(){
		return ListIntegerChromosome.problema.fitnessFunction(this);
	}

	@Override
	public double fitness() {
		return ft;
	}

	@Override
	public Chromosome asChromosome() {
		return this;
	}

	@Override
	protected void checkValidity(List<Integer> arg0) throws InvalidRepresentationException {

	}

	@Override
	public AbstractListChromosome<Integer> newFixedLengthChromosome(List<Integer> ls) {
		return new ListIntegerChromosome(ls);
	}

	

}

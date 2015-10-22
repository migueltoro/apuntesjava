package us.lsi.ag.agchromosomes;

import java.util.List;

import org.apache.commons.math3.genetics.AbstractListChromosome;
import org.apache.commons.math3.genetics.BinaryChromosome;
import org.apache.commons.math3.genetics.Chromosome;
import org.apache.commons.math3.genetics.InvalidRepresentationException;

import us.lsi.ag.ProblemaAG;
import us.lsi.ag.ProblemaAGIndex;
import us.lsi.common.Lists2;

import com.google.common.base.Preconditions;

/**
 * @author Miguel Toro
 * 
 * 
 * Una implementación del tipo Cromosoma&lt;Integer&gt;. Toma como información la definición de un problema que implementa el interfaz ProblemaAGBag.
 * A partir de esa información construye una secuencia normal. Asumimos que el número de objetos es n y el tamaño de la secuencia normal r. 
 *  
 * La lista decodificada está formada por una lista de  tamaño menor o igual a r cuyos valores son 
 * índices en el rango [0,n-1], y cada índice i se puede repetir un máximo número de veces dado por la multiplicidad máxima del objeto i
 * definida en el problema.
 * 
 * La implementación usa un cromosoma binario del tamaño de la secuencia normal.
 * 
 * <p> Es un cromosoma adecuado para codificar problemas de subconjuntos de multiconjuntos</p>
 *
 */
public class BinaryIndexChromosome extends BinaryChromosome implements IndexChromosome {

	public static ProblemaAGIndex<?> problema;
	
	public static List<Integer> normalSequence;
	
	/**
	 * Dimensión del cromosoma
	 */
	
	public static int DIMENSION;
	
	public static void iniValues(ProblemaAG problema){
		BinaryIndexChromosome.problema = (ProblemaAGIndex<?>) problema; 
		BinaryIndexChromosome.normalSequence = BinaryIndexChromosome.problema.getNormalSequence();
		BinaryIndexChromosome.DIMENSION = BinaryIndexChromosome.normalSequence.size();
	}
	
	public BinaryIndexChromosome(List<Integer> representation)
			throws InvalidRepresentationException {
		super(representation);
		this.ft = this.calculateFt();
	}

	public BinaryIndexChromosome(Integer[] representation)
			throws InvalidRepresentationException {
		super(representation);
		this.ft = this.calculateFt();
	}

	@Override
	public AbstractListChromosome<Integer> newFixedLengthChromosome(List<Integer> ls) {
		return new BinaryIndexChromosome(ls);
	}

	/**
	 * @return Una lista de enteros obtenida filtrando la secuencia normal para incluir 
	 * sólo los seleccionados por el cromosoma binario 
	 */
	@Override
	public List<Integer> decode() {	
		List<Integer> r = Lists2.<Integer>nCopias(BinaryIndexChromosome.problema.getObjectsNumber(), 0);
		List<Integer> bn = this.getRepresentation();
		Preconditions.checkArgument(normalSequence.size() == bn.size(),normalSequence.size()+","+bn.size());
		for (int i = 0; i < normalSequence.size(); i++) {
			if (bn.get(i) == 1) {
				int k = normalSequence.get(i);
				r.set(k,r.get(k)+1);
			}
		}
		return r;
	}
	
	public static BinaryIndexChromosome getInitialChromosome() {
		List<Integer> ls = BinaryChromosome.randomBinaryRepresentation(BinaryIndexChromosome.DIMENSION);
		return new BinaryIndexChromosome(ls);
	}

	@Override
	public double fitness() {
		return ft;
	}
	
	private Double ft = null;
	
	private double calculateFt(){
		return BinaryIndexChromosome.problema.fitnessFunction(this.decode());
	}

	@Override
	public ProblemaAGIndex<?> getProblem() {
		return BinaryIndexChromosome.problema;
	}

	@Override
	public Integer getObjectsNumber() {
		return BinaryIndexChromosome.problema.getObjectsNumber();
	}

	@Override
	public Integer getMax(int i) {
		return BinaryIndexChromosome.problema.getMax(i);
	}

	@Override
	public Chromosome asChromosome() {
		return this;
	}

	
	
	
}
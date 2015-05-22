package us.lsi.ag;

import java.util.List;
import java.util.function.Function;

import org.apache.commons.math3.genetics.AbstractListChromosome;
import org.apache.commons.math3.genetics.BinaryChromosome;
import org.apache.commons.math3.genetics.InvalidRepresentationException;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

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
public class BagBinaryChromosome extends BinaryChromosome implements Cromosoma<Integer> {

	public static Function<List<Integer>,Double> fitnessFunction;
	public static List<Integer> normalSequence;
	
	public BagBinaryChromosome(List<Integer> representation)
			throws InvalidRepresentationException {
		super(representation);
		this.ft = this.calculateFt();
	}

	public BagBinaryChromosome(Integer[] representation)
			throws InvalidRepresentationException {
		super(representation);
		this.ft = this.calculateFt();
	}

	@Override
	public AbstractListChromosome<Integer> newFixedLengthChromosome(List<Integer> ls) {
		return new BagBinaryChromosome(ls);
	}

	/**
	 * @return Una lista de enteros obtenida filtrando la secuencia normal para incluir 
	 * sólo los seleccionados por el cromosoma binario 
	 */
	@Override
	public List<Integer> decode() {	
		List<Integer> r = Lists.newArrayList();
		List<Integer> bn = this.getRepresentation();
		Preconditions.checkArgument(normalSequence.size() == bn.size(),normalSequence.size()+","+bn.size());
		for (int i = 0; i < normalSequence.size(); i++) {
			if (bn.get(i) == 1) {
				r.add(normalSequence.get(i));
			}
		}
		return r;
	}
	
	public static BagBinaryChromosome getInitialChromosome() {
		List<Integer> ls = BinaryChromosome.randomBinaryRepresentation(AlgoritmoAG.DIMENSION);
		return new BagBinaryChromosome(ls);
	}

	@Override
	public double fitness() {
		return ft;
	}
	
	private Double ft = null;
	
	private double calculateFt(){
		return BagBinaryChromosome.fitnessFunction.apply(this.decode());
	}
}
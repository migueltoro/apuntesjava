package us.lsi.ag;

import java.util.List;
import java.util.function.Function;

import org.apache.commons.math3.genetics.AbstractListChromosome;
import org.apache.commons.math3.genetics.InvalidRepresentationException;
import org.apache.commons.math3.genetics.RandomKey;

import com.google.common.base.Preconditions;

/**
 * @author Miguel Toro
 * 
 * <p> Una implementación del tipo Cromosoma&lt;Integer&gt;. Toma como información la definición de un problema que implementa el interfaz ProblemaAGBag.
 * A partir de esa información construye una secuencia normal. Asumimos que el número de objetos es n y el tamaño de la secuencia normal r. 
 *  
 * La lista decodificada está formada por una lista de  tamaño r, cuyos valores son 
 * índices en el rango [0,n-1], y cada índice i se  repite un número de veces dado por la multiplicidad máxima del objeto i
 * definida en el problema.
 * 
 * La implementación usa un cromosoma de clave aleatoria de tamaño r.
 * Es un cromosoma adecuado para codificar problemas de permutaciones </p>
 *
 */
public class BagRandomKeyChromosome extends RandomKey<Integer> implements Cromosoma<Integer> {

	public static Function<List<Integer>,Double> fitnessFunction;
	public static List<Integer> normalSequence;
	
	public BagRandomKeyChromosome(List<Double> representation)
			throws InvalidRepresentationException {
		super(representation);
		this.ft = this.calculateFt();
	}

	public BagRandomKeyChromosome(Double[] representation)
			throws InvalidRepresentationException {
		super(representation);
		this.ft = this.calculateFt();
	}

	@Override
	public AbstractListChromosome<Double> newFixedLengthChromosome(List<Double> ls) {
		return new BagRandomKeyChromosome(ls);
		
	}

	@Override
	public List<Integer> decode() {
		Preconditions.checkArgument(BagRandomKeyChromosome.normalSequence!=null);
		return this.decode(BagRandomKeyChromosome.normalSequence);
	}
	
	
	public static BagRandomKeyChromosome getInitialChromosome() {
		List<Double> ls = RandomKey.randomPermutation(AlgoritmoAG.DIMENSION);
		return new BagRandomKeyChromosome(ls);
	}

	
	@Override
	public double fitness() {
		return ft;
	}
	
	private double ft;
	
	private double calculateFt(){
		return BagRandomKeyChromosome.fitnessFunction.apply(this.decode());
	}	
}
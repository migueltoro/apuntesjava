package us.lsi.ag;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.apache.commons.math3.genetics.AbstractListChromosome;
import org.apache.commons.math3.genetics.BinaryChromosome;
import org.apache.commons.math3.genetics.InvalidRepresentationException;

import us.lsi.common.Lists2;
import us.lsi.math.Math2;

/**
 * @author Miguel Toro
 * 
 * 
 * Una implementación del tipo Cromosoma&lt;Integer&gt;. Toma como información la definición de un problema que implementa el interfaz ProblemaAGBag.
 * Asumimos que el número de objetos es n. Usa una  secuencia normal implícita de tamaño n de la forma (0,1,2, ..., n-1). 
 * 
 * La lista decodificada está formada por una lista de  tamaño n cuyos elementos para cada i son 
 *  valores en en rango [0,m(i)], sinendo m(i) la multiplicidad máxima para i.
 * 
 * La implementación usa un cromosoma binario del tamaño n*nbits. 
 * Siendo nbits el número de bits usados para representar cada uno de los enteros.
 * 
 * <p> Es un cromosoma adecuado para codificar problemas de subconjuntos de multiconjuntos</p>
 *
 */
public class BagMultiChromosome extends BinaryChromosome implements Cromosoma<Integer> {
	
	public static Function<List<Integer>,Double> fitnessFunction;
	public static Integer numeroDeObjetos;
	public static List<Integer> multiplicidades;
	public static Integer numeroDeBits = 5;
	public static Integer pow = Math2.pow(2., numeroDeBits).intValue();
	
	public BagMultiChromosome(Integer[] representation) throws InvalidRepresentationException {
		super(representation);
		this.ft = this.calculateFt();
	}

	public BagMultiChromosome(List<Integer> representation) throws InvalidRepresentationException {
		super(representation);
		this.ft = this.calculateFt();
	}

	@Override
	public AbstractListChromosome<Integer> newFixedLengthChromosome(List<Integer> ls) {
		return new BagMultiChromosome(ls);
	}
	
	public List<Integer> decode() {
		List<Integer> ls = super.getRepresentation();
		List<Integer> r = new ArrayList<Integer>();
		int index1 = 0;
		for(int i = 0; i < numeroDeObjetos; i++){			
			int index2 = index1+numeroDeBits;
			Integer e = Lists2.decode(ls.subList(index1, index2));
			Integer d = Math2.escalaIncluded(e, pow, multiplicidades.get(i));
			r.add(d);
			index1 = index1+numeroDeBits;;
		}
		return r;
	}

	public List<Integer> getRepresentation(){
		return super.getRepresentation();
	}
	
	public static BagMultiChromosome getInitialChromosome() {
		List<Integer> ls = BinaryChromosome.randomBinaryRepresentation(AlgoritmoAG.DIMENSION);
		return new BagMultiChromosome(ls);
	}

	@Override
	public double fitness() {
		return ft;
	}
	
	private double ft;
	
	private double calculateFt(){
		return BagMultiChromosome.fitnessFunction.apply(this.decode());
	}
}

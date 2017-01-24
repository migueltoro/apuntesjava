package us.lsi.ag.agchromosomes;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.genetics.AbstractListChromosome;
import org.apache.commons.math3.genetics.BinaryChromosome;
import org.apache.commons.math3.genetics.Chromosome;
import org.apache.commons.math3.genetics.InvalidRepresentationException;

import us.lsi.ag.ProblemaAG;
import us.lsi.ag.ProblemaAGIndex;
import us.lsi.common.Lists2;
import us.lsi.math.Math2;

/**
 * @author Miguel Toro
 * 
 * 
 * <p> Una implementación del tipo Cromosoma&lt;Integer&gt;. Toma como información la definición de un problema que implementa el interfaz ProblemaAGIndex.
 * Asumimos que el número de objetos es n. Usa una  secuencia normal implícita de tamaño n de la forma (0,1,2, ..., n-1). </p>
 * 
 * <p> La lista decodificada está formada por una lista de  tamaño n cuyos elementos para cada i son 
 *  valores en en rango [0,m(i)], siendo m(i) la multiplicidad máxima para i. </p>
 * 
 * <p> La implementación usa un cromosoma binario del tamaño n*nbits. 
 * Siendo nbits el número de bits usados para representar cada uno de los enteros. </p>
 * 
 * <p> Es un cromosoma adecuado para codificar problemas de subconjuntos de multiconjuntos</p>
 *
 */
public class IndexRangeChromosome extends BinaryChromosome implements IndexChromosome {
	
	/**
	 * Número de bits usado para representar un entero. El rango de enteros que podemos obtener dependerá de este número de bits.
	 */
	public static Integer numeroDeBits = 5;
	
	public static ProblemaAGIndex<?> problema;
	
	/**
	 * Dimensión del cromosoma igual a numeroDeBits*getObjectsNumber()
	 */
	
	public static int DIMENSION;
	
	public static void iniValues(ProblemaAG problema){
		IndexRangeChromosome.problema = (ProblemaAGIndex<?>) problema; 
		IndexRangeChromosome.DIMENSION = IndexRangeChromosome.numeroDeBits*IndexRangeChromosome.problema.getObjectsNumber();
	}
	
	private static Integer pow = Math2.pow(2., numeroDeBits).intValue();
	
	public IndexRangeChromosome(Integer[] representation) throws InvalidRepresentationException {
		super(representation);
		this.ft = this.calculateFt();
	}

	public IndexRangeChromosome(List<Integer> representation) throws InvalidRepresentationException {
		super(representation);
		this.ft = this.calculateFt();
	}

	@Override
	public AbstractListChromosome<Integer> newFixedLengthChromosome(List<Integer> ls) {
		return new IndexRangeChromosome(ls);
	}
	
	public List<Integer> decode() {
		List<Integer> ls = super.getRepresentation();
		List<Integer> r = new ArrayList<Integer>();
		int index1 = 0;
		for(int i = 0; i < getObjectsNumber(); i++){			
			int index2 = index1+numeroDeBits;
			Integer e = Lists2.decode(ls.subList(index1, index2));
			Integer d = Math2.escalaIncluded(e, pow, getMax(i));
			r.add(d);
			index1 = index1+numeroDeBits;;
		}
		return r;
	}

	public List<Integer> getRepresentation(){
		return super.getRepresentation();
	}
	
	public static IndexRangeChromosome getInitialChromosome() {
		List<Integer> ls = BinaryChromosome.randomBinaryRepresentation(IndexRangeChromosome.DIMENSION);
		return new IndexRangeChromosome(ls);
	}

	@Override
	public double fitness() {
		return ft;
	}
	
	private double ft;
	
	private double calculateFt(){
		return IndexRangeChromosome.problema.fitnessFunction(this);
	}

	public Integer getObjectsNumber() {
		return IndexRangeChromosome.problema.getObjectsNumber();
	}

	public Integer getMax(int i) {
		return IndexRangeChromosome.problema.getMax(i);
	}

	@Override
	public ProblemaAGIndex<?> getProblem() {
		return IndexRangeChromosome.problema;
	}

	@Override
	public Chromosome asChromosome() {
		return this;
	}
	
	
	
}

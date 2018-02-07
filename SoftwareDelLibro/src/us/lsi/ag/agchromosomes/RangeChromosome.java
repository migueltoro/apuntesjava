package us.lsi.ag.agchromosomes;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.genetics.AbstractListChromosome;
import org.apache.commons.math3.genetics.BinaryChromosome;
import org.apache.commons.math3.genetics.InvalidRepresentationException;

import us.lsi.ag.ProblemAG;
import us.lsi.ag.ValuesInRangeChromosome;
import us.lsi.ag.ValuesInRangeProblemAG;
import us.lsi.math.Math2;

/**
 * @author Miguel Toro
 * 
 * 
 * <p> Una implementación del tipo ValuesInRangeCromosome&lt;Integer&gt;. Toma como información la definición de un problema que implementa el interfaz 
 * ValuesInRangeProblemAG. </p>
 * 
 * <p> Asumimos que el número de varibles es n. La lista decodificada está formada por una lista de  
 * enteros de tamaño n cuyos elementos para cada i son 
 * valores en en rango [getMin(i),getMax(i)]. </p>
 * 
 * <p> La implementación usa un cromosoma binario del tamaño n*nbits. 
 * Siendo nbits el número de bits usados para representar cada uno de los enteros. </p>
 * 
 * <p> Es un cromosoma adecuado para codificar problemas de subconjuntos de multiconjuntos</p>
 *
 */
public class RangeChromosome extends BinaryChromosome implements ValuesInRangeChromosome<Integer> {
	
	/**
	 * Número de bits usado para representar un entero. El rango de enteros que podemos obtener dependerá de este número de bits.
	 */
	public static Integer bitsNumber = 5;
	
	public static ValuesInRangeProblemAG<Integer,?> problem;
	
	/**
	 * Dimensión del cromosoma igual a bitsNumber*getVariableNumber()
	 */
	
	public static int DIMENSION;
	
	@SuppressWarnings("unchecked")
	public static void iniValues(ProblemAG problema){
		RangeChromosome.problem = (ValuesInRangeProblemAG<Integer,?>) problema; 
		RangeChromosome.DIMENSION = RangeChromosome.bitsNumber*RangeChromosome.problem.getVariableNumber();
	}
	
	private static Integer pow = Math2.pow(2., bitsNumber).intValue();
	
	public RangeChromosome(Integer[] representation) throws InvalidRepresentationException {
		super(representation);
		this.ft = this.calculateFt();
	}

	public RangeChromosome(List<Integer> representation) throws InvalidRepresentationException {
		super(representation);
		this.ft = this.calculateFt();
	}

	@Override
	public AbstractListChromosome<Integer> newFixedLengthChromosome(List<Integer> ls) {
		return new RangeChromosome(ls);
	}
	
	public List<Integer> decode() {
		List<Integer> ls = super.getRepresentation();
		List<Integer> r = new ArrayList<Integer>();
		int index1 = 0;
		for(int i = 0; i < getObjectsNumber(); i++){			
			int index2 = index1+bitsNumber;
			Integer e = Math2.decode(ls.subList(index1, index2));
			Integer d = getMin(i)+Math2.escalaIncluded(e, pow, getMax(i)-getMin(i));
			r.add(d);
			index1 = index1+bitsNumber;;
		}
		return r;
	}

	public List<Integer> getRepresentation(){
		return super.getRepresentation();
	}
	
	public static RangeChromosome getInitialChromosome() {
		List<Integer> ls = BinaryChromosome.randomBinaryRepresentation(RangeChromosome.DIMENSION);
		return new RangeChromosome(ls);
	}

	@Override
	public double fitness() {
		return ft;
	}
	
	private double ft;
	
	private double calculateFt(){
		return RangeChromosome.problem.fitnessFunction(this);
	}

	public Integer getObjectsNumber() {
		return RangeChromosome.problem.getVariableNumber();
	}

	public Integer getMax(int i) {
		return RangeChromosome.problem.getMax(i);
	}

	public Integer getMin(int i) {
		return RangeChromosome.problem.getMin(i);
	}
	
	public ValuesInRangeProblemAG<Integer,?> getProblem() {
		return RangeChromosome.problem;
	}
	
}

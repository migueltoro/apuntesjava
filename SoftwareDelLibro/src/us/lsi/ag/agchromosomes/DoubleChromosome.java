package us.lsi.ag.agchromosomes;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.genetics.AbstractListChromosome;
import org.apache.commons.math3.genetics.BinaryChromosome;
import org.apache.commons.math3.genetics.InvalidRepresentationException;

import com.google.common.base.Preconditions;

import us.lsi.ag.ProblemAG;
import us.lsi.ag.ValuesInRangeChromosome;
import us.lsi.ag.ValuesInRangeProblemAG;
import us.lsi.math.Math2;

/**
 * <p> Una implementación del tipo ValuesInRangeCromosome&lt;Double&gt;. Toma como información la definición de un problema que implementa el interfaz 
 * ValuesInRangeProblemAG. </p>
 * 
 * <p> Asumimos que el número de variables es n. La lista decodificada está formada por una lista de  
 * Double de tamaño n cuyos elementos para cada i son 
 * valores en en rango [getMin(i),getMax(i)]. </p>
 * 
 * <p> La implementación usa un cromosoma binario del tamaño n*bitsNumber. 
 * Siendo bitsNumber el número de bits usados para representar cada uno de los Double. </p>
 * 
 * <p> Es un cromosoma adecuado para codificar problemas de funciones  
 * reales en un espacio de n dimensiones </p>
 *
 */
public class DoubleChromosome extends BinaryChromosome implements ValuesInRangeChromosome<Double> {

	
	/**
	 * El número de bits usados para representar un real de la lista proporcionado por el cromosoa. La precisión del real depende del número de bits usados
	 */
	public static Integer numeroDeBits = 10;
	private static Double fact = null;
	public static ValuesInRangeProblemAG<Double,?> problema;
	
	/**
	 * Dimensión del cromosoma igual al numeroDeBits*getNumeroDeVariables()
	 */
	
	public static int DIMENSION;
	
	@SuppressWarnings("unchecked")
	public static void iniValues(ProblemAG problema){
		DoubleChromosome.problema = (ValuesInRangeProblemAG<Double,?>) problema;
		DoubleChromosome.DIMENSION = DoubleChromosome.problema.getVariableNumber()*DoubleChromosome.numeroDeBits;
	}
	
	public DoubleChromosome(Integer[] representation) throws InvalidRepresentationException {
		super(representation);
		this.ft = this.calculateFt();
	}

	public DoubleChromosome(List<Integer> representation) throws InvalidRepresentationException {
		super(representation);
		this.ft = this.calculateFt();
	}
	
	private Double getFact() {
		if(fact==null){
			fact = (double) (Math.pow(2,numeroDeBits)-1);
		}
		return fact;
	}

	@Override
	public AbstractListChromosome<Integer> newFixedLengthChromosome(List<Integer> ls) {
		return new DoubleChromosome(ls);
	}
	@Override
	public List<Double> decode() {
		List<Integer> ls = super.getRepresentation();
		List<Double> r = new ArrayList<Double>();
		for(int i = 0; i< this.getNum(); i++){
			int index1 = i*numeroDeBits;
			int index2 = index1+numeroDeBits;
			Preconditions.checkElementIndex(index1, ls.size());
			Preconditions.checkPositionIndex(index2, ls.size());
			Double e = (double) Math2.decode(ls.subList(index1, index2));
			Double x = this.getMin(i);
			Double y = this.getMax(i);
			Double d = x+(y-x)*e/this.getFact();
			r.add(d);
		}
		return r;
	}
	@Override
	public List<Integer> getRepresentation(){
		return super.getRepresentation();
	}
	
	public static DoubleChromosome getInitialChromosome() {
		List<Integer> ls = BinaryChromosome.randomBinaryRepresentation(DoubleChromosome.DIMENSION);
		return new DoubleChromosome(ls);
	}

	@Override
	public double fitness() {
		return ft;
	}
	
	private double ft;
	
	private double calculateFt(){
		return DoubleChromosome.problema.fitnessFunction(this);
	}

	public Integer getNum() {
		return DoubleChromosome.problema.getVariableNumber();
	}
	
	public Double getMax(int i) {
		return DoubleChromosome.problema.getMax(i);
	}
	
	public Double getMin(int i) {
		return DoubleChromosome.problema.getMin(i);
	}

	public  ValuesInRangeProblemAG<Double,?> getProblema() {
		return DoubleChromosome.problema;
	}
}

package us.lsi.ag;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.apache.commons.math3.genetics.AbstractListChromosome;
import org.apache.commons.math3.genetics.BinaryChromosome;
import org.apache.commons.math3.genetics.Chromosome;
import org.apache.commons.math3.genetics.InvalidRepresentationException;

import us.lsi.common.Lists2;
import us.lsi.common.Par;

/**
 * @author Miguel Toro
 * 
 * 
 * Una implementación del tipo Cromosoma&lt;Double&gt;. Toma como información la definición de un problema que implementa el interfaz ProblemaAGReal.
 * A partir de esa información construye una secuencia normal. Asumimos que el número de objetos es n y el tamaño de la secuencia normal r. 
 *  
 * La lista decodificada está formada por una lista de  tamaño nv,  cuyos elementos son valores en reales en el rango [li(i),ls(i)].
 * 
 * 
 * 
 * La implementación usa un cromosoma binario  de tamaño nv*nbits.
 * Es un cromosoma adecuado para codificar problemas de funciones  reales en un espacio de nv dimensiones
 *
 */
public class RealBinaryChromosome extends BinaryChromosome implements Cromosoma<Double>{

	
	public static Integer numeroDeBits = 10;
	public static Integer numeroDeVariables = null;
	public static List<Par<Double,Double>> limites = null;
	private static Double fact = null;
	public static Function<List<Double>,Double> fitnessFunction;
	
	public RealBinaryChromosome(Integer[] representation) throws InvalidRepresentationException {
		super(representation);
		this.ft = this.calculateFt();
	}

	public RealBinaryChromosome(List<Integer> representation) throws InvalidRepresentationException {
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
		return new RealBinaryChromosome(ls);
	}
	
	public List<Double> decode() {
		List<Integer> ls = super.getRepresentation();
		List<Double> r = new ArrayList<Double>();
		for(int i = 0; i< numeroDeVariables; i++){
			int index1 = i*numeroDeBits;
			int index2 = index1+numeroDeBits;
			Double e = (double) Lists2.decode(ls.subList(index1, index2));
			Double x = limites.get(i).p1;
			Double y = limites.get(i).p2;
			Double d = x+(y-x)*e/this.getFact();
			r.add(d);
		}
		return r;
	}

	public List<Integer> getRepresentation(){
		return super.getRepresentation();
	}
	
	public static RealBinaryChromosome getInitialChromosome() {
		List<Integer> ls = BinaryChromosome.randomBinaryRepresentation(AlgoritmoAG.DIMENSION);
		return new RealBinaryChromosome(ls);
	}

	@Override
	public double fitness() {
		return ft;
	}
	
	private double ft;
	
	private double calculateFt(){
		return RealBinaryChromosome.fitnessFunction.apply(this.decode());
	}
	
	@SuppressWarnings("unchecked")
	public static Cromosoma<Double> asCromosoma(Chromosome cr){
		return (Cromosoma<Double>) cr;
	}
}

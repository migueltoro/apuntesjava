package us.lsi.ag.agchromosomes;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.genetics.AbstractListChromosome;
import org.apache.commons.math3.genetics.BinaryChromosome;
import org.apache.commons.math3.genetics.Chromosome;
import org.apache.commons.math3.genetics.InvalidRepresentationException;

import com.google.common.base.Preconditions;

import us.lsi.ag.ProblemaAG;
import us.lsi.ag.ProblemaAGReal;
import us.lsi.common.Lists2;

/**
 * @author Miguel Toro
 * 
 * 
 * <p> Una implementación del tipo Cromosoma&lt;Double&gt;. Toma como información la definición de un problema que implementa el interfaz ProblemaAGReal.
 * A partir de esa información construye una secuencia normal. Asumimos que el número de objetos es n y el tamaño de la secuencia normal r. </p>
 *  
 * <p> La lista decodificada está formada por una lista de  tamaño nv,  cuyos elementos son valores en reales en el rango [li(i),ls(i)].</p>
 * 
 * 
 * 
 * <p> La implementación usa un cromosoma binario  de tamaño nv*nbits.
 * Es un cromosoma adecuado para codificar problemas de funciones  reales en un espacio de n dimensiones </p>
 *
 */
public class RealChromosome extends BinaryChromosome implements IRealChromosome {

	
	/**
	 * El número de bits usados para representar un real de la lista proporcionado por el cromosoa. La precisión del real depende del número de bits usados
	 */
	public static Integer numeroDeBits = 10;
	private static Double fact = null;
	public static ProblemaAGReal<?> problema;
	
	/**
	 * Dimensión del cromosoma igual al numeroDeBits*getNumeroDeVariables()
	 */
	
	public static int DIMENSION;
	
	public static void iniValues(ProblemaAG problema){
		RealChromosome.problema = (ProblemaAGReal<?>) problema;
		RealChromosome.DIMENSION = RealChromosome.problema.getNumeroDeVariables()*RealChromosome.numeroDeBits;
	}
	
	public RealChromosome(Integer[] representation) throws InvalidRepresentationException {
		super(representation);
		this.ft = this.calculateFt();
	}

	public RealChromosome(List<Integer> representation) throws InvalidRepresentationException {
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
		return new RealChromosome(ls);
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
			Double e = (double) Lists2.decode(ls.subList(index1, index2));
			Double x = this.getInf(i);
			Double y = this.getSup(i);
			Double d = x+(y-x)*e/this.getFact();
			r.add(d);
		}
		return r;
	}
	@Override
	public List<Integer> getRepresentation(){
		return super.getRepresentation();
	}
	
	public static RealChromosome getInitialChromosome() {
		List<Integer> ls = BinaryChromosome.randomBinaryRepresentation(RealChromosome.DIMENSION);
		return new RealChromosome(ls);
	}

	@Override
	public double fitness() {
		return ft;
	}
	
	private double ft;
	
	private double calculateFt(){
		return RealChromosome.problema.fitnessFunction(this);
	}
	
	@SuppressWarnings("unchecked")
	public static IChromosome<List<Double>> asCromosoma(Chromosome cr){
		return (IChromosome<List<Double>>) cr;
	}

	/* (non-Javadoc)
	 * @see us.lsi.ag.IRealChromosome#getNum()
	 */
	@Override
	public Integer getNum() {
		return RealChromosome.problema.getNumeroDeVariables();
	}

	/* (non-Javadoc)
	 * @see us.lsi.ag.IRealChromosome#getSup(int)
	 */
	@Override
	public Double getSup(int i) {
		return RealChromosome.problema.getLimites().get(i).p2;
	}

	/* (non-Javadoc)
	 * @see us.lsi.ag.IRealChromosome#getInf(int)
	 */
	@Override
	public Double getInf(int i) {
		return RealChromosome.problema.getLimites().get(i).p1;
	}

	@Override
	public ProblemaAGReal<?> getProblema() {
		return RealChromosome.problema;
	}
	
	@Override
	public Chromosome asChromosome() {
		return this;
	}
}

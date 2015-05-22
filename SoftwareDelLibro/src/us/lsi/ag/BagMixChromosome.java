package us.lsi.ag;

import java.util.List;











import java.util.function.Function;

import org.apache.commons.math3.genetics.AbstractListChromosome;
import org.apache.commons.math3.genetics.BinaryChromosome;
import org.apache.commons.math3.genetics.Chromosome;



import org.apache.commons.math3.genetics.InvalidRepresentationException;
import org.apache.commons.math3.genetics.RandomKey;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;


/**
 * 
 * 
 * 
 * 
 * @author Miguel Toro
 * 
 * 
 * Una implementación del tipo Cromosoma&lt;Integer&gt;. Toma como información la definición de un problema que implementa el interfaz ProblemaAGBag.
 * A partir de esa información construye una secuencia normal. Asumimos que el número de objetos es n y el tamaño de la secuencia normal r. 
 *  
 * La lista decodificada está formada por una lista de  tamaño menor o igual que r, cuyos valores son 
 * índices en el rango [0,n-1], y cada índice i se puede repetir un máximo número de veces dado por la multiplicidad máxima del objeto i
 * definida en el problema.
 * 
 * La implementación usa un cromosoma binario y otro de tipo clave aleatoria. Ambos de tamaño r.
 * Es un cromosoma adecuado para codificar problemas de permutaciones de subconjuntos de multiconjuntos
 *
 */
public class BagMixChromosome extends Chromosome implements Cromosoma<Integer> {

	public static List<Integer> normalSequence;
	
	private BinaryChromosome2 binary;
	private RandomKey2 randomKey;	
	
	public static Function<List<Integer>,Double> fitnessFunction;
	
	/**
	 * @param binary Un cromosoma binario
	 * @param randomKey Un cromosoma randomKey
	 */
	protected BagMixChromosome(Chromosome binary, Chromosome randomKey) {
		super();
		if(binary instanceof BinaryChromosome2){
			this.binary = (BinaryChromosome2)binary;
		}
		if(randomKey instanceof RandomKey2){
			this.randomKey = (RandomKey2) randomKey;
		}
		Preconditions.checkArgument(this.binary!=null);
		Preconditions.checkArgument(this.randomKey!=null);
		Preconditions.checkArgument(this.binary.getLength()==this.randomKey.getLength());	
		this.ft = this.calculateFt();
	}
	
	/**
	 * Un constructor adecuado para crear un objeto por defecto de este tipo
	 */
	public BagMixChromosome() {
		super();
		List<Integer> ls1 = BinaryChromosome2.randomBinaryRepresentation(100);
		List<Double>  ls2 = RandomKey2.randomPermutation(100);
		BinaryChromosome2 c1 = new BinaryChromosome2(ls1);
		RandomKey2 c2 = new RandomKey2(ls2);
		this.binary =  c1;
		this.randomKey = c2;	
		this.ft = 0.;
	}
	
	/* (non-Javadoc)
	 * @see org.apache.commons.math3.genetics.Fitness#fitness()
	 */
	
	
	@Override
	public double fitness() {
		return ft;
	}
	
	private Double ft;
	
	private double calculateFt(){
		return BagMixChromosome.fitnessFunction.apply(this.decode());
	}
	
	
	public int compareTo(Chromosome another) {
		if (!(another instanceof BagMixChromosome))
			throw new IllegalArgumentException();;
		BagMixChromosome other = (BagMixChromosome) another;
		Double f1 = this.fitness();
		Double f2 = other.fitness();
		return f1.compareTo(f2);
	}
	
	/**
	 * @return Una lista de enteros obtenida permutando la secuencia normal (0, 1, 2, ..., r-1) filtrada para incluir 
	 * sólo los seleccionados por el cromosoma binario 
	 */
	public List<Integer> decode() {	
		List<Integer> rk = randomKey.decode(normalSequence);
		List<Integer> r = Lists.newArrayList();
		List<Integer> bn = binary.getRepresentation();
		Preconditions.checkArgument(rk.size()==bn.size());
		for(int i=0; i< rk.size();i++){
			if(bn.get(i)==1){
				r.add(rk.get(i));
			}
		}
		return r;
	}

	/**
	 * @return La dimensión del cromosoma
	 */
	public int getLength() {
		return randomKey.getLength();
	}	
	
	@SuppressWarnings("unchecked")
	public static Cromosoma<Integer> asCromosoma(Chromosome cr){
		return (Cromosoma<Integer>) cr;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof BagMixChromosome))
			return false;
		BagMixChromosome other = (BagMixChromosome) obj;
		if (binary == null) {
			if (other.binary != null)
				return false;
		} else if (!binary.equals(other.binary))
			return false;
		if (randomKey == null) {
			if (other.randomKey != null)
				return false;
		} else if (!randomKey.equals(other.randomKey))
			return false;
		return true;
	}	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((binary == null) ? 0 : binary.hashCode());
		result = prime * result
				+ ((randomKey == null) ? 0 : randomKey.hashCode());
		return result;
	}

	public String toString() {
		return this.fitness()+","+this.decode();
	}

	public BinaryChromosome getBinary() {
		return binary;
	}

	public RandomKey<Integer> getRandomKey() {
		return randomKey;
	}
	
	/**
	 * @param dimension La dimensión del cromosoma
	 * @pre Debe estar inicializada la propiedad factory
	 * @return Un cromosoma mixto aleatorio
	 * 
	 */
	private static BagMixChromosome random(Integer dimension){
		List<Integer> ls1 = BinaryChromosome2.randomBinaryRepresentation(dimension);
		List<Double>  ls2 = RandomKey2.randomPermutation(dimension);
		BinaryChromosome2 c1 = new BinaryChromosome2(ls1);
		RandomKey2 c2 = new RandomKey2(ls2);
		return new BagMixChromosome(c1, c2);
	}
	
	public static BagMixChromosome getInitialChromosome() {
		return BagMixChromosome.random(AlgoritmoAG.DIMENSION);
	}

	private static class BinaryChromosome2 extends BinaryChromosome {		

		public BinaryChromosome2(Integer[] representation)
				throws InvalidRepresentationException {
			super(representation);
		}

		public BinaryChromosome2(List<Integer> representation)
				throws InvalidRepresentationException {
			super(representation);
		}

		@Override
		public double fitness() {
			return 0;
		}

		@Override
		public AbstractListChromosome<Integer> newFixedLengthChromosome(List<Integer> ls) {
			return new BinaryChromosome2(ls);
		}
		 
		@Override
		public List<Integer> getRepresentation(){
			return super.getRepresentation();
		}	
	}
	
	private static class RandomKey2 extends  RandomKey<Integer> {
		
		
		public RandomKey2(Double[] representation) throws InvalidRepresentationException {
			super(representation);
		}

		public RandomKey2(List<Double> representation) throws InvalidRepresentationException {
			super(representation);
		}

		@Override
		public double fitness() {
			return 0;
		}

		@Override
		public AbstractListChromosome<Double> newFixedLengthChromosome(List<Double> ls) {
			return new RandomKey2(ls);
		}
	}
	
	
}

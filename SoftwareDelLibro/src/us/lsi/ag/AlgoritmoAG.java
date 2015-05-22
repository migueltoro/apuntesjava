package us.lsi.ag;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.math3.genetics.Chromosome;
import org.apache.commons.math3.genetics.CrossoverPolicy;
import org.apache.commons.math3.genetics.CycleCrossover;
import org.apache.commons.math3.genetics.ElitisticListPopulation;
import org.apache.commons.math3.genetics.FixedElapsedTime;
import org.apache.commons.math3.genetics.FixedGenerationCount;
import org.apache.commons.math3.genetics.GeneticAlgorithm;
import org.apache.commons.math3.genetics.MutationPolicy;
import org.apache.commons.math3.genetics.NPointCrossover;
import org.apache.commons.math3.genetics.OnePointCrossover;
import org.apache.commons.math3.genetics.OrderedCrossover;
import org.apache.commons.math3.genetics.Population;
import org.apache.commons.math3.genetics.SelectionPolicy;
import org.apache.commons.math3.genetics.StoppingCondition;
import org.apache.commons.math3.genetics.TournamentSelection;
import org.apache.commons.math3.genetics.UniformCrossover;
import org.apache.commons.math3.random.JDKRandomGenerator;

import us.lsi.algoritmos.AbstractAlgoritmo;

/**
 * <p> Implementación de un Algoritmo Genético </p>
 * 
 * 
 * @author Miguel Toro
 *
 * @param <E> Tipo de los elementos de los cromosomas: Integer o Double
 */
public abstract class AlgoritmoAG<E> extends AbstractAlgoritmo {

	/**
	 * Dimensión del cromosoma
	 */
	
	protected static int DIMENSION;
	/**
	 * Tamaño de la población. Usualmente de un valor cercano a la DIMENSION de los cromosomas o mayor
	 */
	public static int POPULATION_SIZE = 30;
	/**
	 * Número de generaciones
	 */
	public static int NUM_GENERATIONS = Integer.MAX_VALUE;
	/**
	 * Tasa de elitismo. El porcentaje especificado de los mejores cromosomas pasa a la siguiente generación sin cambio
	 */
	public static double ELITISM_RATE = 0.2;
	/**
	 * Tasa de cruce: Indica con qué frecuencia se va a realizar la cruce. 
	 * Si no hay un cruce, la descendencia es copia exacta de los padres. 
	 * Si hay un cruce, la descendencia está hecha de partes del cromosoma de los padres. 
	 * Si la probabilidad de cruce es 100%, entonces todos los hijos se hacen mediante cruce de los padres
	 * Si es 0%, la nueva generación se hace de copias exactas de los cromosomas de los padres.
	 * El cruce se hace con la esperanza de que los nuevos cromosomas tendrán las partes buenas de los padres
	 * y tal vez los nuevos cromosomas serán mejores. Sin embargo, es bueno dejar una cierta parte de la población sobrevivir a la siguiente generación.
	 * 
	 * <br>
	 * Tasa de cruce. Valores usuales entre  0,.8 y 0.95
	 */
	public static double CROSSOVER_RATE = 0.8;
	/**
	 * La tasa de de mutación indica con qué frecuencia serán mutados cada uno de los cromosomas mutados. 
	 * Si no hay mutación, la descendencia se toma después de cruce sin ningún cambio. 
	 * Si se lleva a cabo la mutación, se cambia una parte del cromosoma. 
	 * Si probabilidad de mutación es 100%, toda cromosoma se cambia, si es 0%, no se cambia ninguno. 
	 * La mutación se hace para evitar que se caiga en un máximo local.
	 * 
	 * 
	 * Tasa de mutación. Valores usales entre 0.5 y 1.
	 */
	public static double MUTATION_RATE = 0.6;
	/**
	 * <p> Para aplicar los operadores de mutación se escogen dos cromosomas. 
	 * La técnica implementada para escoger cada uno de los dos cromosomas se denomina selección por torneo. 
	 * Se trata de organizar dos torneos. 
	 * En cada uno se elige el mejor cromosoma de entrre <code> TOURNAMENT_ARITY </code> cromosomas de la población seleccionados al azar. 
	 * Si el tamaño de <code> TOURNAMENT_ARITY </code> es más grande, los cromosomas
	 *  débiles tienen menor probabilidad de ser seleccionados.</p>
	 * 
	 * <p> Número de participantes en el torneo para elegir los cromosomas que participarán en el cruce </p>
	 * <p> Un valor típico es 2 </p>
	 */
	public static int TOURNAMENT_ARITY = 2;
	/**
	 * Número de soluciones a encontrar si fijamos el criterio de parada en SolutionsNumber
	 */
	public static int SOLUTIONS_NUMBER = 1;
	/**
	 * Valor mínimo de la fitness de los cromosomas en las soluciones que vamsos buscando si fijamos el criterio de parada en SolutionsNumber
	 */
	public static double FITNESS = 0.;	
	/**
	 * Tipo del operador de cruce
	 */
	public static CrossoverType crossoverType = CrossoverType.OnePoint;
	/**
	 * Número de puntos usados en la partición si se usa un operador de cruce de tipo NPointCrossover
	 */
	public static int NPOINTCROSSOVER = 3;
	/**
	 * La ratio si se usa el operador de cruce de tipo UniformCrossover
	 */
	public static double RATIO_UNIFORMCROSSOVER = 0.7;
	/**
	 * Condición de parada
	 */
	public static StoppingConditionType stoppingConditionType = StoppingConditionType.GenerationCount;
	/**
	 * Tiempo máximo transcurrido para finalizar el algoritmo si usamos la condición de finalización ElapsedTime.
	 */
	public static long MAX_ELAPSEDTIME = Long.MAX_VALUE;
	
	public static long INITIAL_TIME;
	
	
	protected CrossoverPolicy crossOverPolicy;	
	protected MutationPolicy mutationPolicy;
	protected SelectionPolicy selectionPolicy;
	
	
	/**
	 * <p>Distintos tipo de operadores de cruce </p>
	 *
	 * <ul>
	 * <li> <a href="http://commons.apache.org/proper/commons-math/apidocs/org/apache/commons/math3/genetics/OnePointCrossover.html" target="_blank"> OnePointCrossover </a>
	 * <li> <a href="http://commons.apache.org/proper/commons-math/apidocs/org/apache/commons/math3/genetics/NPointCrossover.html" target="_blank"> NPointCrossover </a>
	 * <li> <a href="http://commons.apache.org/proper/commons-math/apidocs/org/apache/commons/math3/genetics/CycleCrossover.html" target="_blank"> CycleCrossover </a>
	 * <li> <a href="http://commons.apache.org/proper/commons-math/apidocs/org/apache/commons/math3/genetics/OrderedCrossover.html" target="_blank"> OrderedCrossover </a>
	 * <li> <a href="http://commons.apache.org/proper/commons-math/apidocs/org/apache/commons/math3/genetics/UniformCrossover.html" target="_blank"> UniformCrossover </a>
	 * </ul> 
	 * 
	 */
	public enum CrossoverType{Cycle,NPoint,OnePoint,Ordered,Uniform};
	
	/**
	 * <p> Distintos tipos de condiciones de parada </p>
	 * 
	 * <ul>
	 * <li> ElapsedTime: Para cuando el tiempo transcurrido se el especificado en <code> elapsedTime </code>.
	 * <li> GenerationCount: Para cuando el número de generaciones sea igual al especificado en <code> NUM_GENERATIONS </code>
	 * <li> SolutionsNumber: Para cuando en una generación encuentra al menos SOLUTIONS_NUMBER de cromososmas 
	 * con <code> fitness</code>  igual o mayor <code> FITNESS </code>.
	 * </ul> 
	 *
	 */
	public enum StoppingConditionType{ElapsedTime,GenerationCount,SolutionsNumber};

	/**
	 * Lista con los mejores cromosomas de cada una de la generaciones si se usa la condición de parada SolutionsNumbers.
	 * En otro caso null.
 	 */
	public static List<Chromosome> bestChromosomes;
	

	protected Population initialPopulation;
	protected StoppingCondition stopCond;
	
	protected Chromosome bestFinal;
	protected Population finalPopulation;
	
	
	/**
	 * 
	 * 
	 */
	public AlgoritmoAG() {
		super();
		switch(crossoverType){
		case Cycle: crossOverPolicy = new CycleCrossover<E>();break;
		case NPoint: crossOverPolicy = new NPointCrossover<E>(NPOINTCROSSOVER);break;
		case OnePoint: crossOverPolicy = new OnePointCrossover<E>();break;
		case Ordered: crossOverPolicy = new OrderedCrossover<E>(); break;
		case Uniform: crossOverPolicy = new UniformCrossover<E>(RATIO_UNIFORMCROSSOVER); break;
		}
		switch(stoppingConditionType){
		case ElapsedTime: stopCond = new FixedElapsedTime(MAX_ELAPSEDTIME);break;
		case GenerationCount: stopCond = new FixedGenerationCount(NUM_GENERATIONS); break;
		case SolutionsNumber: stopCond = new SolutionsNumber(SOLUTIONS_NUMBER,NUM_GENERATIONS); break;
		}		
		this.selectionPolicy =  new TournamentSelection(TOURNAMENT_ARITY);	
		JDKRandomGenerator random = new JDKRandomGenerator();
		random.setSeed((int)System.currentTimeMillis());
		GeneticAlgorithm.setRandomGenerator(random);
	}

	/**
	 * Inicializa aleatoriamente la población.
	 */
	private ElitisticListPopulation randomPopulation() {
		List<Chromosome> popList = new LinkedList<>();

		for (int i = 0; i < POPULATION_SIZE; i++) {
			Chromosome randChrom = this.getInitialChromosome();
			popList.add(randChrom);
		}
		return new ElitisticListPopulation(popList, popList.size(), ELITISM_RATE);
	}	

	/**
	 * Ejecuta el algoritmo
	 */
	public void ejecuta() {
		INITIAL_TIME = System.currentTimeMillis();
		this.initialPopulation = randomPopulation();
		GeneticAlgorithm ga = new GeneticAlgorithm(
				crossOverPolicy, 
				CROSSOVER_RATE,
				mutationPolicy, 
				MUTATION_RATE, 
				selectionPolicy);
		finalPopulation = ga.evolve(initialPopulation, stopCond);
		bestFinal = finalPopulation.getFittestChromosome();
	}

	/**
	 * @return Población inicial
	 */
	public Population getInitialPopulation() {
		return initialPopulation;
	}

	/**
	 * @return El mejor cromosoma en la población final
	 */
	public Chromosome getBestFinal() {
		return bestFinal;
	}

	/**
	 * @return Población final
	 */
	public Population getFinalPopulation() {
		return finalPopulation;
	}	
	
	public abstract Chromosome getInitialChromosome();
	
	@SuppressWarnings("unchecked")
	public static <T> Cromosoma<T> asCromosoma(Chromosome cr){
		return (Cromosoma<T>) cr;
	}
	
}

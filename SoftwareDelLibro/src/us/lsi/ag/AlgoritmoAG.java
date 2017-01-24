package us.lsi.ag;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.math3.genetics.Chromosome;
import org.apache.commons.math3.genetics.CrossoverPolicy;
import org.apache.commons.math3.genetics.ElitisticListPopulation;
import org.apache.commons.math3.genetics.GeneticAlgorithm;
import org.apache.commons.math3.genetics.MutationPolicy;
import org.apache.commons.math3.genetics.Population;
import org.apache.commons.math3.genetics.SelectionPolicy;
import org.apache.commons.math3.genetics.StoppingCondition;
import org.apache.commons.math3.random.JDKRandomGenerator;

import com.google.common.base.Preconditions;

import us.lsi.ag.agchromosomes.ChromosomeFactory;
import us.lsi.ag.agchromosomes.ChromosomeFactory.ChromosomeType;
import us.lsi.ag.agstopping.StoppingConditionFactory;
import us.lsi.algoritmos.AbstractAlgoritmo;

/**
 * <p> Implementación de un Algoritmo Genético </p>
 * 
 * 
 * @author Miguel Toro
 *
 */
public class AlgoritmoAG extends AbstractAlgoritmo {

	
	/**
	 * Tamaño de la población. Usualmente de un valor cercano a la DIMENSION de los cromosomas o mayor
	 */
	public static int POPULATION_SIZE = 30;
	
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
	

	public static long INITIAL_TIME;
	
	
	private ChromosomeType tipo;
	private CrossoverPolicy crossOverPolicy;	
	private MutationPolicy mutationPolicy;
	private SelectionPolicy selectionPolicy;
	protected StoppingCondition stopCond;
		
	

	/**
	 * Lista con los mejores cromosomas de cada una de la generaciones si se usa la condición de parada SolutionsNumbers.
	 * En otro caso null.
 	 */
	public static List<Chromosome> bestChromosomes;
	

	protected Population initialPopulation;
	
	
	protected Chromosome bestFinal;
	protected Population finalPopulation;
	
	
	
	/**
	 * @param tipo El tipo del chromosoma
	 * @param problema Problema a resolver
	 */
	public AlgoritmoAG(ChromosomeType tipo, ProblemaAG problema) {
		super();
		this.tipo = tipo;				
		this.selectionPolicy =  ChromosomeFactory.getSelectionPolicy();
		this.mutationPolicy = ChromosomeFactory.getMutationPolicy(tipo, problema);
		this.crossOverPolicy = ChromosomeFactory.getCrossoverPolicy(tipo, problema);
		this.stopCond = StoppingConditionFactory.getStoppingCondition();
		ChromosomeFactory.iniValues(tipo, problema);
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
			Chromosome randChrom = ChromosomeFactory.randomChromosome(this.tipo).asChromosome();
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
		Preconditions.checkNotNull(this.initialPopulation);		
		
		GeneticAlgorithm ga = new GeneticAlgorithm(
				crossOverPolicy, 
				CROSSOVER_RATE,
				mutationPolicy, 
				MUTATION_RATE, 
				selectionPolicy);
		
		
		this.finalPopulation = ga.evolve(this.initialPopulation, this.stopCond);		
		Preconditions.checkNotNull(this.finalPopulation);
		this.bestFinal = this.finalPopulation.getFittestChromosome();
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
	
}

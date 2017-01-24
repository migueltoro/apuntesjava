package us.lsi.ag.agchromosomes;

import org.apache.commons.math3.genetics.BinaryMutation;
import org.apache.commons.math3.genetics.Chromosome;
import org.apache.commons.math3.genetics.CrossoverPolicy;
import org.apache.commons.math3.genetics.CycleCrossover;
import org.apache.commons.math3.genetics.MutationPolicy;
import org.apache.commons.math3.genetics.NPointCrossover;
import org.apache.commons.math3.genetics.OnePointCrossover;
import org.apache.commons.math3.genetics.OrderedCrossover;
import org.apache.commons.math3.genetics.RandomKeyMutation;
import org.apache.commons.math3.genetics.SelectionPolicy;
import org.apache.commons.math3.genetics.TournamentSelection;
import org.apache.commons.math3.genetics.UniformCrossover;

import us.lsi.ag.ProblemaAG;
import us.lsi.ag.ProblemaAGListInteger;
import us.lsi.ag.agoperators.SubListCrossoverPolicy;
import us.lsi.ag.agoperators.SubListMutationPolicy;

import com.google.common.base.Preconditions;


/**
 * @author Miguel Toro
 * 
 * <p> Una factoría de cromosomas de los distintos tipos implementados. </p>
 *
 */
public class ChromosomeFactory {
	
	/**
	 * Los diferentes tipos de cromosmomas implementados
	 *
	 */
	public enum ChromosomeType {Binary,ListInteger,IndexSubList,IndexRange,IndexPermutation,IndexPermutationSubList,Real,Expression}
	
	public static ChromosomeType tipo;

	/**
	 * @param tipo El tipo de cromosoma
	 * @return Un cromosoma aleatorio del tipo indicado
	 */
	public static IChromosome<?> randomChromosome(ChromosomeType tipo){
		IChromosome<?> chromosome = null;
		switch(tipo){
		case Binary: chromosome = BinaryChromosome2.getInitialChromosome(); break;
		case IndexSubList: chromosome = IndexSubListChromosome.getInitialChromosome(); break;
		case ListInteger: chromosome = ListIntegerChromosome.getInitialChromosome(); break;
		case IndexRange: chromosome = IndexRangeChromosome.getInitialChromosome(); break;
		case IndexPermutation: chromosome = IndexPermutationChromosome.getInitialChromosome(); break;
		case IndexPermutationSubList: chromosome = IndexPermutationSubListChromosome.getInitialChromosome(); break;
		case Real: chromosome = RealChromosome.getInitialChromosome(); break;
		case Expression: chromosome = ExpressionChromosome.getInitialChromosome(); break;
		}
		return chromosome;
	}
	
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
	 * @param tipo El tipo del cromosoma
	 * @param problema Las propiedades del probblema a resolver
	 * @return Un operador de cruce adecuado para un cromosma del tipo indicado
	 */
	public static CrossoverPolicy getCrossoverPolicy(ChromosomeType tipo, ProblemaAG problema){
		CrossoverPolicy crossOverPolicyBin = null;	
		switch(crossoverType){
		case Cycle: crossOverPolicyBin = new CycleCrossover<Integer>();break;
		case NPoint: crossOverPolicyBin = new NPointCrossover<Integer>(NPOINTCROSSOVER);break;
		case OnePoint: crossOverPolicyBin = new OnePointCrossover<Integer>();break;
		case Ordered: crossOverPolicyBin = new OrderedCrossover<Integer>(); break;
		case Uniform: crossOverPolicyBin = new UniformCrossover<Integer>(RATIO_UNIFORMCROSSOVER); break;
		}
		CrossoverPolicy crossOverPolicyKey = null;	
		switch(crossoverType){
		case Cycle: crossOverPolicyKey = new CycleCrossover<Double>();break;
		case NPoint: crossOverPolicyKey = new NPointCrossover<Double>(NPOINTCROSSOVER);break;
		case OnePoint: crossOverPolicyKey = new OnePointCrossover<Double>();break;
		case Ordered: crossOverPolicyKey = new OrderedCrossover<Double>(); break;
		case Uniform: crossOverPolicyKey = new UniformCrossover<Double>(RATIO_UNIFORMCROSSOVER); break;
		}
		CrossoverPolicy crossOverPolicy = null;	
		switch(tipo){
		case Binary: crossOverPolicy = crossOverPolicyBin; break;
		case IndexSubList: crossOverPolicy = crossOverPolicyBin; break;
		case ListInteger: crossOverPolicy = ((ProblemaAGListInteger<?>)problema).getCrossoverPolicy(); break;
		case IndexRange: crossOverPolicy = crossOverPolicyBin; break;
		case IndexPermutation: crossOverPolicy = crossOverPolicyKey; break;
		case IndexPermutationSubList: crossOverPolicy = new SubListCrossoverPolicy(crossOverPolicyBin,crossOverPolicyKey); break;
		case Real: crossOverPolicy = crossOverPolicyBin; break;
		case Expression: crossOverPolicy = crossOverPolicyBin; break;
		}
		Preconditions.checkState(crossOverPolicy!=null);
		return crossOverPolicy;
	}
	
	/**
	 * @param tipo El tipo del cromosoma
	 * @param problema El problema a resolver
	 * @return Un operador de mutación adecuado para un cromosoma del tipo indicado
	 */
	public static MutationPolicy getMutationPolicy(ChromosomeType tipo, ProblemaAG problema){
		MutationPolicy mutationPolicy = null;
		switch(tipo){
		case Binary:  mutationPolicy = new BinaryMutation()	; break;
		case IndexSubList: mutationPolicy = new BinaryMutation(); break;
		case ListInteger: mutationPolicy = ((ProblemaAGListInteger<?>)problema).getMutationPolicy(); break;
		case IndexRange: mutationPolicy = new BinaryMutation();	; break;
		case IndexPermutation: mutationPolicy = new RandomKeyMutation(); break;
		case IndexPermutationSubList: mutationPolicy = new SubListMutationPolicy(); break;
		case Real: mutationPolicy = new BinaryMutation();	; break;
		case Expression: mutationPolicy = new BinaryMutation();	; break;
		}
		Preconditions.checkState(mutationPolicy!=null);
		return mutationPolicy;
	}
	
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
	 * @return Un operador que implementa la política de selección escogida
	 */
	public static SelectionPolicy getSelectionPolicy(){	
		SelectionPolicy selectionPolicy = new TournamentSelection(TOURNAMENT_ARITY);
		Preconditions.checkState(selectionPolicy!=null);
		return selectionPolicy;
	}
	
	/**
	 * @param tipo Tipo de cromosoma
	 * @param problema El problema a resolver 
	 * @pos El método inicializa los parámetros relevantes de la clase que implementa el tipo indicado de cromosoma
	 */
	public static void iniValues(ChromosomeType tipo, ProblemaAG problema){
		switch(tipo){
		case Binary: BinaryChromosome2.iniValues(problema);break;
		case IndexSubList: IndexSubListChromosome.iniValues(problema);break;
		case ListInteger: ListIntegerChromosome.iniValues(problema);break;
		case IndexRange: IndexRangeChromosome.iniValues(problema); break;
		case IndexPermutation: IndexPermutationChromosome.iniValues(problema);break;
		case IndexPermutationSubList: IndexPermutationSubListChromosome.iniValues(problema);break;
		case Real: RealChromosome.iniValues(problema);break;
		case Expression: ExpressionChromosome.iniValues(problema);break;
		}
	}
	
	/**
	 * @pre Es un IBinaryChromosome
	 * @param cr Un cromosoma instancia de la clase Chromosome de Apache.
	 * @return Un cromosoma de tipo IBinaryChromosome
	 */
	public static BinaryChromosome2 asBinary(Chromosome cr){
		Preconditions.checkArgument(cr instanceof BinaryChromosome2);
		return (BinaryChromosome2) cr;
	}
	
	/**
	 * @pre Es un ListIntegerChromosome
	 * @param cr Un cromosoma instancia de la clase Chromosome de Apache.
	 * @return Un cromosoma de tipo ListIntegerChromosome
	 */
	public static ListIntegerChromosome asListInteger(Chromosome cr){
		Preconditions.checkArgument(cr instanceof ListIntegerChromosome);
		return (ListIntegerChromosome) cr;
	}
	
	/**
	 * @pre Es un RealChromosome
	 * @param cr Un cromosoma instancia de la clase Chromosome de Apache.
	 * @return Un cromosoma de tipo RealChromosome
	 */
	public static IRealChromosome asReal(Chromosome cr){
		Preconditions.checkArgument(cr instanceof IRealChromosome);
		return (IRealChromosome) cr;
	}
	
	/**
	 * @pre Es un IndexChromosome
	 * @param cr Un cromosoma instancia de la clase Chromosome de Apache.
	 * @return Un cromosoma de tipo IndexChromosome
	 */
	public static IndexChromosome asIndex(Chromosome cr){
		Preconditions.checkArgument(cr instanceof IndexChromosome);
		return (IndexChromosome) cr;
	}

	
	/**
	 * @pre Es un IExpressionChromosome&lt;T&gt;
	 * @param <T> El tipo del resultado de la expresión 
	 * @param cr Un cromosoma instancia de la clase Chromosome de Apache.
	 * @return Un cromosoma de tipo IExpressionChromosome&lt;T&gt;
	 */
	
	@SuppressWarnings("unchecked")
	public static <T> IExpressionChromosome<T> asExpression(Chromosome cr) {
		Preconditions.checkArgument(cr instanceof IExpressionChromosome);
		return (IExpressionChromosome<T>) cr;
	}
}

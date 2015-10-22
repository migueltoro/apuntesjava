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
import us.lsi.ag.agoperators.SubListCrossoverPolicy;
import us.lsi.ag.agoperators.SubListMutationPolicy;

import com.google.common.base.Preconditions;


public class ChromosomeFactory {
	
	public enum ChromosomeType {Binary,BinaryIndex,IntegerIndex,PermutationIndex,PermutationIndexSubList, Real, Expression}
	
	public static ChromosomeType tipo;

	public static IChromosome<?> randomChromosome(ChromosomeType tipo){
		IChromosome<?> chromosome = null;
		switch(tipo){
		case Binary: chromosome = BinaryChromosomeModified.getInitialChromosome(); break;
		case BinaryIndex: chromosome = BinaryIndexChromosome.getInitialChromosome(); break;
		case IntegerIndex: chromosome = IntegerIndexChromosome.getInitialChromosome(); break;
		case PermutationIndex: chromosome = PermutationIndexChromosome.getInitialChromosome(); break;
		case PermutationIndexSubList: chromosome = PermutationIndexSubListChromosome.getInitialChromosome(); break;
		case Real: chromosome = RealListChromosome.getInitialChromosome(); break;
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
	
	public static <E> CrossoverPolicy getCrossoverPolicy(ChromosomeType tipo){
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
		case BinaryIndex: crossOverPolicy = crossOverPolicyBin; break;
		case IntegerIndex: crossOverPolicy = crossOverPolicyBin; break;
		case PermutationIndex: crossOverPolicy = crossOverPolicyKey; break;
		case PermutationIndexSubList: crossOverPolicy = new SubListCrossoverPolicy(crossOverPolicyBin,crossOverPolicyKey); break;
		case Real: crossOverPolicy = crossOverPolicyBin; break;
		case Expression: crossOverPolicy = crossOverPolicyBin; break;
		}
		Preconditions.checkState(crossOverPolicy!=null);
		return crossOverPolicy;
	}
	
	public static MutationPolicy getMutationPolicy(ChromosomeType tipo){
		MutationPolicy mutationPolicy = null;
		switch(tipo){
		case Binary:  mutationPolicy = new BinaryMutation()	; break;
		case BinaryIndex: mutationPolicy = new BinaryMutation(); break;
		case IntegerIndex: mutationPolicy = new BinaryMutation();	; break;
		case PermutationIndex: mutationPolicy = new RandomKeyMutation(); break;
		case PermutationIndexSubList: mutationPolicy = new SubListMutationPolicy(); break;
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
	
	public static SelectionPolicy getSelectionPolicy(){	
		SelectionPolicy selectionPolicy = new TournamentSelection(TOURNAMENT_ARITY);
		Preconditions.checkState(selectionPolicy!=null);
		return selectionPolicy;
	}
	
	public static void iniValues(ChromosomeType tipo, ProblemaAG problema){
		switch(tipo){
		case Binary: BinaryChromosomeModified.iniValues(problema);break;
		case BinaryIndex: BinaryIndexChromosome.iniValues(problema);break;
		case IntegerIndex: IntegerIndexChromosome.iniValues(problema); break;
		case PermutationIndex: PermutationIndexChromosome.iniValues(problema);break;
		case PermutationIndexSubList: PermutationIndexSubListChromosome.iniValues(problema);break;
		case Real: RealListChromosome.iniValues(problema);break;
		case Expression: ExpressionChromosome.iniValues(problema);break;
		}
	}
	
	public static IBinaryChromosome asBinary(Chromosome cr){
		Preconditions.checkArgument(cr instanceof IBinaryChromosome);
		return (IBinaryChromosome) cr;
	}
	
	public static RealChromosome asReal(Chromosome cr){
		Preconditions.checkArgument(cr instanceof RealChromosome);
		return (RealChromosome) cr;
	}
	
	public static IndexChromosome asIndex(Chromosome cr){
		Preconditions.checkArgument(cr instanceof IndexChromosome);
		return (IndexChromosome) cr;
	}

	@SuppressWarnings("unchecked")
	public static <T> IExpressionChromosome<T> asExpression(Chromosome cr) {
		Preconditions.checkArgument(cr instanceof IExpressionChromosome);
		return (IExpressionChromosome<T>) cr;
	}
}

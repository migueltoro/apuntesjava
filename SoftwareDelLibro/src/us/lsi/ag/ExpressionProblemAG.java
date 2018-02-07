package us.lsi.ag;

import java.util.List;

import us.lsi.ag.agchromosomes.ExpressionChromosome;
import us.lsi.tiposrecursivos.AccumulatorExp;
import us.lsi.tiposrecursivos.Operator;

/**
 * @author Miguel Toro
 *
 * @param <S> Tipo de la solución
 * @param <T> Tipo del resultado de la expresión buscada
 * 
 * <p> Tipo adecuado para modelar problemas dónde queremos encontrar una expresión, 
 * formada por un conjunto de operadores dados, un conjunto de variables y un conjunto de constantes, tal que su valor se ajuste 
 * a algunos requisitos dados </p>
 */
public interface ExpressionProblemAG<S,T> extends ProblemAG {

	
	/**
	 * @return La longitud de la cabeza de un gen
	 */
	Integer getHeadLength();
	
	/**
	 * @return Número de Genes
	 */
	Integer getNumGenes();
	
	/**
	 * @return Número de variables
	 */
	Integer getNumVariables();
	
	/**
	 * @return Numero de constantes
	 */
	Integer getNumConstants();
	
	/**
	 * @return El rango máximo del valor de cada constante. Cada constante tendrá un valor en el rango 0..getMaxValueConstant()-1
	 */
	Integer getMaxValueConstant();
	
	/**
	 * @return Número de operadores
	 */
	List<Operator> getOperators();	
	
	/**
	 * @return Expresión n-aria, de aridad variable, que combinará las expresiones asociadas a cada uno de los genes del cromosoma
	 */
	AccumulatorExp<T,T> getAccumulator();
	
	/**
	 * @param e Un número entero
	 * @return Un valor de tipo T resultado de la conversión del entero e.
	 */
	T convert(Integer e);
	
	
	/**
	 * @param chromosome Un cromosoma 
	 * @return La función de fitness del cromosoma
	 */
	Double fitnessFunction(ExpressionChromosome<T> chromosome);
	
	/**
	 * @param chromosome Es un cromosoma
	 * @return La solución definida por el cromosoma
	 */
	S getSolucion(ExpressionChromosome<T> chromosome);
}

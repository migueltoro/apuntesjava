package us.lsi.ag;

import java.util.List;

import us.lsi.ag.agchromosomes.IExpressionChromosome;
import us.lsi.common.Exp;
import us.lsi.common.NaryExp;

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
public interface ProblemaAGExpression<S,T> extends ProblemaAG {

	/**
	 * @return Número de operadores
	 */
	Integer getNumOperators();
	
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
	List<Exp<T>> getOperators();	
	
	/**
	 * @return Expresión n-aria, de aridad varaible, que combinará las expresiones asociadas a cada uno de los genes del cromosoma
	 */
	NaryExp<T> getNaryExp();
	
	/**
	 * @param e Un número entero
	 * @return Un valor de tipo T resultado de la conversión del entero e.
	 */
	T convert(Integer e);
	
	
	/**
	 * @param chromosome Un cromosoma 
	 * @return La función de fitness del cromosoma
	 */
	Double fitnessFunction(IExpressionChromosome<T> chromosome);
	
	/**
	 * @param chromosome Es un cromosoma
	 * @return La solución definida por el cromosoma
	 */
	S getSolucion(IExpressionChromosome<T> chromosome);
}

package us.lsi.ag.agchromosomes;

import us.lsi.ag.ProblemaAGExpression;
import us.lsi.common.ConstantExp;
import us.lsi.common.Exp;
import us.lsi.common.VariableExp;

/**
 * @author Miguel Toro
 *
 * @param <T> El tipo del resultado de la expresión
 * 
 * <p> Representa un cromosoma cuyo valor decodificado es
 * una expresión formada por un conjunto de operadores dados, un conjunto de variables y un conjunto de constantes </p>
 */
public interface IExpressionChromosome<T> extends IChromosome<Exp<T>> {
	
	/**
	 * @return El problema que se va a resolver
	 */
	ProblemaAGExpression<?,T> getProblem();
	
	/**
	 * @return Numero de operadores
	 */
	Integer getNumOperators();
	
	/**
	 * @return Número de variables
	 */
	Integer getNumVariables();
	
	/**
	 * @return Número de constantes
	 */
	Integer getNumConstants();
	
	/**
	 * @param i Un indice en el rango <code> 0..getNumOperators()-1 </code>
	 * @return El operador de índice i
	 */
	Exp<T> getOperator(int i);
	
	/**
	 * @param i Un índice en el rango <code> 0..getNumVariables()-1 </code>
	 * @return La vraible de índice i
	 */
	VariableExp<T> getVariable(int i);
	
	/**
	 * @param i Un índice en el rango <code> 0..getNumConstants()-1 </code>
	 * @return la constante de índice i
	 */
	ConstantExp<T> getConstant(int i);
	
	/**
	 * @return la expresión asociada al cromosoma
	 */
	Exp<T> getExp();	

}

package us.lsi.ag;

import java.util.List;

import us.lsi.ag.agchromosomes.IExpressionChromosome;
import us.lsi.common.Exp;
import us.lsi.common.NaryExp;

public interface ProblemaAGExpression<S,T> extends ProblemaAG {

	Integer getNumOperators();
	
	Integer getNumVariables();
	
	Integer getNumConstants();
	
	Integer getMaxValueConstant();
	
	List<Exp<T>> getOperators();	
	
	NaryExp<T> getNaryExp();
	
	T convert(Integer e);
	
	/**
	 * @param ls Lista de valores de las variables
	 * @pre ls.size() == getNumeroDeVariables()
	 * @return Fitness del cromosoma
	 */
	Double fitnessFunction(IExpressionChromosome<T> chromosome);
	
	/**
	 * @param chromosome Es un cromosoma
	 * @return La solución definida por el cromosoma
	 */
	S getSolucion(IExpressionChromosome<T> chromosome);
}

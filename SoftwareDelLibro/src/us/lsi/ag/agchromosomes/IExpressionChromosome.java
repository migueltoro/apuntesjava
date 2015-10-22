package us.lsi.ag.agchromosomes;

import us.lsi.ag.ProblemaAGExpression;
import us.lsi.common.ConstantExp;
import us.lsi.common.Exp;
import us.lsi.common.VariableExp;

public interface IExpressionChromosome<T> extends IChromosome<Exp<T>> {
	
	ProblemaAGExpression<?,T> getProblem();
	
	Integer getNumOperators();
	
	Integer getNumVariables();
	
	Integer getNumConstants();
	
	Exp<T> getOperator(int i);
	
	VariableExp<T> getVariable(int i);
	
	ConstantExp<T> getConstant(int i);
	
	Exp<T> getExp();	

}

package us.lsi.ag.expression;

import java.util.Arrays;
import java.util.List;

import com.google.common.base.Preconditions;

import us.lsi.ag.ExpressionProblemAG;
import us.lsi.ag.agchromosomes.ExpressionChromosome;
import us.lsi.tiposrecursivos.AccumulatorExp;
import us.lsi.tiposrecursivos.BinaryOperatorExp;
import us.lsi.tiposrecursivos.Exp;
import us.lsi.tiposrecursivos.Operator;
import us.lsi.tiposrecursivos.UnaryOperatorExp;
import us.lsi.tiposrecursivos.Exp.ExpType;

public class Expression implements ExpressionProblemAG<Exp<Double>, Double> {

	public static List<Double> lsx = Arrays.asList(0.0,50.0,100.0,150.0,200.0,250.0,300.0,350.0,400.0,450.0,500.0,550.0,600.0,650.0,700.0,750.0,
			800.0,850.0,900.0,950.0);
	public static List<Double> lsv = Arrays.asList(3.0,130003.0,1020003.0,3420003.0,8080003.0,
			1.5750003E7,2.7180003E7,4.3120003E7,6.4320003E7,9.1530003E7,1.25500003E8,1.66980003E8,
			2.16720003E8,2.75470003E8,3.43980003E8,4.23000003E8,5.13280003E8,6.15570003E8,7.30620003E8,
			8.59180003E8);		   
	@Override
	public Integer getNumVariables() {
		return 1;
	}

	@Override
	public Integer getNumConstants() {
		return 2;
	}

	@Override
	public Integer getMaxValueConstant() {
		return 4;
	}

	@Override
	public List<Operator> getOperators() {		
		BinaryOperatorExp<Double,Double,Double> plus = Operator.getBinary("+", ExpType.Double, ExpType.Double);
		BinaryOperatorExp<Double,Double,Double> multiply = Operator.getBinary("*", ExpType.Double, ExpType.Double);
		UnaryOperatorExp<Double,Double>  sqrt = Operator.getUnary("sqrt", ExpType.Double);
		UnaryOperatorExp<Double,Double> pot2 = Operator.getUnary("^2", ExpType.Double);
		UnaryOperatorExp<Double,Double> pot3 = Operator.getUnary("^3", ExpType.Double);
		List<Operator> exp = Arrays.asList(plus,multiply,pot2,pot3,sqrt);
		return exp;
	}

	
	
	@Override
	public Double convert(Integer e) {
		return (double) e.floatValue();
	}

	@Override
	public Double fitnessFunction(ExpressionChromosome<Double> cr) {
		Preconditions.checkArgument(lsx.size()==lsv.size());
		Double r =0.;
		Double r2;
		for(int i=0;i<lsx.size();i++){
			cr.getVariable(0).setValue(lsx.get(i));
			r2 = cr.getExp().val()-lsv.get(i);
			r = r + r2*r2;			
		}
		return -r;
	}

	@Override
	public Exp<Double> getSolucion(ExpressionChromosome<Double> chromosome) {
		return chromosome.getExp();
	}

	@Override
	public String toString() {
		return "Problem Expression [getOperators()=" + getOperators() + "]";
	}

	@Override
	public AccumulatorExp<Double,Double> getAccumulator() {
		return Operator.getAccumulator("+", ExpType.Double);
	}

	@Override
	public Integer getNumGenes() {
		return 3;
	}

	@Override
	public Integer getHeadLength() {
		return 3;
	}

}

package us.lsi.ag.expression;

import java.util.Arrays;
import java.util.List;

import com.google.common.base.Preconditions;

import us.lsi.ag.ProblemaAGExpression;
import us.lsi.ag.agchromosomes.IExpressionChromosome;
import us.lsi.common.BinaryExpS;
import us.lsi.common.Exp;
import us.lsi.common.NaryExp;
import us.lsi.common.UnaryExp;
import us.lsi.common.UnaryExpS;

public class Expression implements ProblemaAGExpression<Exp<Double>, Double> {

	public static List<Double> lsx = Arrays.asList(0.0,50.0,100.0,150.0,200.0,250.0,300.0,350.0,400.0,450.0,500.0,550.0,600.0,650.0,700.0,750.0,800.0,850.0,900.0,950.0);
	public static List<Double> lsv = Arrays.asList(7.0,214.07106781186548,417.0,619.2474487139159,821.142135623731,1022.8113883008419,
			1224.3205080756888,1425.7082869338697,1627.0,1828.2132034355964,2029.3606797749978,2230.4520787991173,2431.494897427832,
			2632.495097567964,2833.457513110646,3034.3861278752584,3235.284271247462,3436.1547594742265,3637.0,3837.822070014845);
			   
	
	@Override
	public Integer getNumOperators() {
		return this.getOperators().size();
	}

	@Override
	public Integer getNumVariables() {
		return 1;
	}

	@Override
	public Integer getNumConstants() {
		return 3;
	}

	@Override
	public Integer getMaxValueConstant() {
		return 10;
	}

	@Override
	public List<Exp<Double>> getOperators() {
		BinaryExpS<Double> plus = Exp.createBinaryS((x,y)->x+y, "+");
		BinaryExpS<Double> multiply = Exp.createBinaryS((x,y)->x*y, "*");
		UnaryExpS<Double> sqrt = Exp.<Double>createUnaryS(x->Math.sqrt(x), "Q", UnaryExp.Tipo.Pre);
		UnaryExpS<Double> pot2 = Exp.createUnaryS(x->x*x, "^2", UnaryExp.Tipo.Pos);
		UnaryExpS<Double> pot3 = Exp.createUnaryS(x->x*x*x, "^3", UnaryExp.Tipo.Pos);
		List<Exp<Double>> exp = Arrays.<Exp<Double>>asList(plus,multiply,pot2,pot3,sqrt);
		return exp;
	}

	@Override
	public Double convert(Integer e) {
		return (double) e.floatValue();
	}

	@Override
	public Double fitnessFunction(IExpressionChromosome<Double> cr) {
		Preconditions.checkArgument(lsx.size()==lsv.size());
		Double r =0.;
		Double r2;
		for(int i=0;i<lsx.size();i++){
			cr.getVariable(0).setValue(lsx.get(i));
			r2 = cr.getExp().eval()-lsv.get(i);
			r = r + r2*r2;			
		}
		return -r;
	}

	@Override
	public Exp<Double> getSolucion(IExpressionChromosome<Double> chromosome) {
		return chromosome.getExp();
	}

	@Override
	public String toString() {
		return "Problem Expression [getOperators()=" + getOperators() + "]";
	}

	@Override
	public NaryExp<Double> getNaryExp() {
		return Exp.createNary(null, (x,y)->x+y, "+");
	}
	
	

}

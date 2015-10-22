package us.lsi.common;

import java.util.Arrays;
import java.util.List;

public class TestExp {

	

	public static void main(String[] args) {
		VariableExp<Integer> v1 = Exp.createVariable(10, "x");
		VariableExp<Integer> v2 = Exp.createVariable(5, "y");
		ConstantExp<Integer> c1 = Exp.createConstant(15);		
		BinaryExp<Integer, Integer, Integer> plus = Exp.createBinary((x,y)->x+y, "+");
		BinaryExp<Integer, Integer, Integer> multiply = Exp.createBinary((x,y)->x*y, "*");
		UnaryExp<Integer, Integer> sqrt = Exp.<Integer,Integer>createUnary(x->(int)Math.sqrt(x), "Q",UnaryExp.Tipo.Pre);
		UnaryExp<Integer, Integer> pot = Exp.<Integer, Integer>createUnary(x->x*x*x, "^3",UnaryExp.Tipo.Pos);
		List<Exp<Integer>> exp = Arrays.<Exp<Integer>>asList(v1,v2,c1,plus,multiply,sqrt,pot);
	    List<Integer> ls = Arrays.<Integer>asList(5,4,5,3,6,3,5,0,1,1,2,1,2,1,2,1);
	    Exp<Integer> e = Exp.levels(ls, 3, exp).get(0).get(0);
		System.out.println(e.toString()+","+e.eval());
	}

}

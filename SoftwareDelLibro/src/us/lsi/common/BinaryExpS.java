package us.lsi.common;

import java.util.function.BinaryOperator;

public class BinaryExpS<R> extends BinaryExp<R, R, R> {

	BinaryExpS(Exp<R> left, Exp<R> right, BinaryOperator<R> operator,String symbol) {
		super(left, right, operator, symbol);
	}

	BinaryExpS(BinaryOperator<R> operator, String symbol) {
		super(operator, symbol);
	}
	
	@Override
	public BinaryExpS<R> asBinary() {
		return this;
	}
	
	@Override
	public Exp<R> clone() {
		Exp<R> e1 = this.op1;
		Exp<R> e2 = this.op2;
		if(e1!= null) e1 = e1.clone();
		if(e2!= null) e2 = e2.clone();
		return Exp.createBinaryS(e1, e2, (BinaryOperator<R>) this.operator, this.symbol);
	}
	
}

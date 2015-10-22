package us.lsi.common;

import java.util.function.UnaryOperator;

public class UnaryExpS<R> extends UnaryExp<R, R> {

	UnaryExpS(Exp<R> op, UnaryOperator<R> operator, String symbol, Tipo tipo) {
		super(op, operator, symbol, tipo);
	}

	UnaryExpS(UnaryOperator<R> operator, String symbol, Tipo tipo) {
		super(operator, symbol, tipo);
	}

	@Override
	public UnaryExpS<R> asUnary() {
		return (UnaryExpS<R>) this;
	}
	
	@Override
	public Exp<R> clone() {
		Exp<R> op = this.op;
		if(op!= null) op = op.clone();
		return Exp.createUnaryS(op, (UnaryOperator<R>) this.operator, this.symbol,this.tipo);
	}	
}

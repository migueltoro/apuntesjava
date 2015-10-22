package us.lsi.common;

public class TernaryExpS<R> extends TernaryExp<R, R, R, R> {

	TernaryExpS(Exp<R> op1, Exp<R> op2, Exp<R> op3,
			us.lsi.common.TernaryExp.TriFunction<R, R, R, R> operator,
			String symbol) {
		super(op1, op2, op3, operator, symbol);
	}

	TernaryExpS(
			us.lsi.common.TernaryExp.TriFunction<R, R, R, R> operator,
			String symbol) {
		super(operator, symbol);
	}

	@Override
	public TernaryExpS<R> asTernary() {
		return this;
	}
	
	@Override
	public Exp<R> clone() {
		Exp<R> e1 = this.op1;
		Exp<R> e2 = this.op2;
		Exp<R> e3 = this.op3;
		if(e1!= null) e1 = e1.clone();
		if(e2!= null) e2 = e2.clone();
		if(e3!= null) e3 = e3.clone();
		return Exp.createTernaryS(e1, e2, e3, this.operator, this.symbol);
	}
}

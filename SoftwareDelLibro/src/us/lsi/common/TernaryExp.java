package us.lsi.common;

public class TernaryExp<R,T1, T2, T3> extends Exp<R> {

	public static interface TriFunction<S1,S2,S3,T> {
		T apply(S1 op1,S2 op2,S3 op3);
	}
	
	protected Exp<T1> op1 = null;
	protected Exp<T2> op2 = null;
	protected Exp<T3> op3 = null;
	protected TriFunction<T1,T2,T3,R> operator;
	protected String symbol = null;
	
	TernaryExp(Exp<T1> op1, Exp<T2> op2, Exp<T3> op3, TriFunction<T1,T2,T3,R> operator, String symbol) {
		super();
		this.op1 = op1;
		this.op2 = op2;
		this.op3 = op3;
		this.operator = operator;
		this.symbol = symbol;
	}

	TernaryExp(TriFunction<T1,T2,T3,R> operator, String symbol) {
		super();
		this.op1 = null;
		this.op2 = null;
		this.op2 = null;
		this.operator = operator;
		this.symbol = symbol;
	}
	
	@Override
	public Integer getArity() {
		return 3;
	}

	public Exp<T1> getOp1() {
		return this.op1;
	}

	public void setOp1(Exp<T1> op1) {
		this.op1 = op1;
	}

	public Exp<T2> getOp2() {
		return this.op2;
	}

	public void setOp2(Exp<T2> op2) {
		this.op2 = op2;
	}

	public void setOp(Exp<T1> op1, Exp<T2> op2, Exp<T3> op3) {
		this.op1 = op1;
		this.op2 = op2;
		this.op3 = op3;
	}
	
	public TriFunction<T1,T2,T3,R> getOperator() {
		return operator;
	}

	@Override
	public R eval() {
		return operator.apply(op1.eval(), op2.eval(),op3.eval());
	}
	
	public String toString() {
		String r = this.symbol;
		if (this.op1!=null) {
			r = this.symbol + "(" + this.op1.toString() + "," + this.op2.toString() + "," + this.op3.toString() + ")";
		}
		return r;
	}

	@Override
	public Exp<R> clone() {
		Exp<T1> e1 = this.op1;
		Exp<T2> e2 = this.op2;
		Exp<T3> e3 = this.op3;
		if(e1!= null) e1 = e1.clone();
		if(e2!= null) e2 = e2.clone();
		if(e3!= null) e3 = e3.clone();
		return Exp.createTernary(e1, e2, e3, this.operator,
				this.symbol);
	}
	
	@Override
	public UnaryExpS<R> asUnary() {
		throw new IllegalStateException("Not a subtype");
	}

}

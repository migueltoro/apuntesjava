package us.lsi.common;

import java.util.function.BiFunction;


/**
 * @author Miguel Toro
 * 
 * <p> Expresión binaria
 *
 * @param <R> Tipo del resultado 
 * @param <T1> Tipo del primer operando
 * @param <T2> Tipo del segundo operando
 */
public class BinaryExp<R,T1,T2> extends Exp<R> {

	protected Exp<T1> op1 = null;
	protected Exp<T2> op2 = null;
	protected BiFunction<T1,T2,R> operator;
	protected String symbol = null;
	
	BinaryExp(Exp<T1> left, Exp<T2> right, BiFunction<T1,T2,R> operator, String symbol) {
		super();
		this.op1 = left;
		this.op2 = right;
		this.operator = operator;
		this.symbol = symbol;
	}

	BinaryExp(BiFunction<T1,T2,R> operator, String symbol) {
		super();
		this.op1 = null;
		this.op2 = null;
		this.operator = operator;
		this.symbol = symbol;
	}
	
	@Override
	public Integer getArity() {
		return 2;
	}

	public Exp<T1> getOp1() {
		return op1;
	}

	public void setOp1(Exp<T1> op1) {
		this.op1 = op1;
	}

	public Exp<T2> getOp2() {
		return op2;
	}

	public void setOp2(Exp<T2> op2) {
		this.op2 = op2;
	}

	public void setOp(Exp<T1> op1, Exp<T2> op2) {
		this.op1 = op1;
		this.op2 = op2;
	}
	
	public BiFunction<T1,T2,R> getOperator() {
		return operator;
	}

	@Override
	public R eval() {
		return operator.apply(op1.eval(), op2.eval());
	}
	
	public String toString() {
		String r = this.symbol;
		if (op1!=null) {
			r = "(" + op1.toString() + this.symbol + op2.toString() + ")";
		}
		return r;
	}

	@Override
	public Exp<R> clone() {
		Exp<T1> e1 = this.op1;
		Exp<T2> e2 = this.op2;
		if(e1!= null) e1 = e1.clone();
		if(e2!= null) e2 = e2.clone();
		return Exp.createBinary(e1, e2, this.operator, this.symbol);
	}
}

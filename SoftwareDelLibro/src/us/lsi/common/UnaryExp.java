package us.lsi.common;

import java.util.function.Function;



public class UnaryExp<R,T> extends Exp<R> {	
	
	protected Exp<T> op;	
	protected Function<T,R> operator;
	protected String symbol;
	public enum Tipo{Pre,Pos}
	protected Tipo tipo = Tipo.Pre;
	
	protected UnaryExp(Exp<T> op, Function<T,R> operator, String symbol,Tipo tipo) {
		super();
		this.op = op;
		this.operator = operator;
		this.symbol = symbol;
		this.tipo = tipo;
	}

	protected UnaryExp(Function<T,R> operator,String symbol,Tipo tipo) {
		super();
		this.op = null;
		this.operator = operator;
		this.symbol = symbol;
		this.tipo = tipo;
	}

	@Override
	public Integer getArity() {
		return 1;
	}

	public Exp<T> getOp() {
		return op;
	}

	public void setOp(Exp<T> op) {
		this.op = op;
	}

	public Function<T,R> getOperator() {
		return operator;
	}

	@Override
	public R eval() {
		return operator.apply(op.eval());
	} 
	
	public String toString(){
		String r = this.symbol;
		if (this.op!=null) {
			switch(tipo) {
			case Pre: r = this.symbol + "(" + this.op.toString() + ")"; break;
			case Pos: r =  "(" + this.op.toString() + ")"+this.symbol; break;
			}
		}
		return r;
	}
	
	@Override
	public Exp<R> clone() {
		Exp<T> op = this.op;
		if(op!= null) op = op.clone();
		return Exp.createUnary(op, this.operator, this.symbol, this.tipo);
	}	
}

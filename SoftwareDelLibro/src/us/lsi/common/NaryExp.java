package us.lsi.common;

import java.util.List;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

public class NaryExp<R> extends Exp<R> {

	private List<Exp<R>> ops = null;
	private BinaryOperator<R> operator;
	private String symbol = null;	

	NaryExp(List<Exp<R>> ops, BinaryOperator<R> operator, String symbol) {
		super();
		this.ops = ops;
		this.operator = operator;
		this.symbol = symbol;
	}

	@Override
	public Integer getArity() {
		return ops.size();
	}

	@Override
	public R eval() {
		return ops.stream().map(x->x.eval()).collect(Collectors.reducing(this.operator)).get();
	}

	@Override
	public NaryExp<R> clone() {
		List<Exp<R>> ops = this.ops;
		if(ops!= null) ops = ops.stream().map(x->x.clone()).collect(Collectors.toList());
		return Exp.createNary(ops, this.operator, this.symbol);
	}


	public List<Exp<R>> getOps() {
		return ops;
	}

	public BinaryOperator<R> getOperator() {
		return operator;
	}

	public String getSymbol() {
		return symbol;
	}

	@Override
	public String toString() {
		return "("+ops.stream().map(x->x.toString()).collect(Collectors.joining(this.symbol))+")";
	}

	public void setOps(List<Exp<R>> ops) {
		this.ops = ops;
	}

	
}

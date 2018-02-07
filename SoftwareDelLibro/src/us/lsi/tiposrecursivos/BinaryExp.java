package us.lsi.tiposrecursivos;

import java.util.Map;

import us.lsi.algoritmos.AbstractAlgoritmo;
import us.lsi.common.Maps2;

/**
 * @author Miguel Toro
 * 
 * <p> Expresión binaria
 *
 * @param <R> Tipo del resultado 
 * @param <T1> Tipo del primer operando
 * @param <T2> Tipo del segundo operando
 */
public class BinaryExp<T1,T2,R> extends Exp<R> {

	private Exp<T1> op1 = null;
	private Exp<T2> op2 = null;
	private BinaryOperatorExp<T1,T2,R> operator = null;
	private int id;
	private static int lastId = 0;
	private Boolean flag = false;
	
	BinaryExp(Exp<T1> left, Exp<T2> right, BinaryOperatorExp<T1,T2,R> operator) {
		super();
		this.op1 = left;
		this.op2 = right;
		this.operator = operator;
		this.id = lastId;
		lastId++;
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

	public BinaryOperatorExp<T1, T2, R> getOperator() {
		return operator;
	}

	@Override
	public String getName() {
		return operator.getName();
	}
	
	@Override
	public R val() {
		return this.operator.getCode().apply(op1.val(), op2.val());
	}
	
	public String toString() {	
		String sOp1 = this.getOp1().toString();
		String sOp2 = this.getOp2().toString();
		if(op1.getPriority() < this.getPriority()){
			sOp1 = "("+sOp1+")";
		}
		if(op2.getPriority() < this.getPriority()){
			sOp2 = "("+sOp2+")";
		}
		return String.format(sOp1+operator.getShortName()+sOp2);
	}

	@Override
	public Exp<R> copy() {
		return Exp.binary(this.op1.copy(), this.op2.copy(), this.operator);
	}

	public Boolean isBinary() {
		return true;
	}
	
	@Override
	public Boolean isConstant() {
		return this.op1.isConstant() && this.op2.isConstant();
	}

	@Override
	public Exp<R> simplify() {
		Exp<R> r;
		if(op1.isConstant() && op2.isConstant()){
			r = Exp.constant(this.val());
		} else {
			r = Exp.binary(op1.simplify(),op2.simplify(), operator);
		} 
		return r;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((op1 == null) ? 0 : op1.hashCode());
		result = prime * result + ((op2 == null) ? 0 : op2.hashCode());
		result = prime * result
				+ ((operator == null) ? 0 : operator.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof BinaryExp))
			return false;
		BinaryExp<?,?,?> other = (BinaryExp<?,?,?>) obj;
		if (op1 == null) {
			if (other.op1 != null)
				return false;
		} else if (!op1.equals(other.op1))
			return false;
		if (op2 == null) {
			if (other.op2 != null)
				return false;
		} else if (!op2.equals(other.op2))
			return false;
		if (operator == null) {
			if (other.operator != null)
				return false;
		} else if (!operator.equals(other.operator))
			return false;
		return true;
	}

	@Override
	public Boolean match(Exp<?> exp) {
		Boolean r = false;
		if(exp.isPlaceHolderExp()){
			exp.<R>asPlaceHolderExp().setExp(this);
			r = true;
		} else if(exp.isBinary() && this.operator.equals(exp.getOperator())){
			BinaryExp<?,?,?> t = exp.asBinary();
			r = this.op1.match(t.op1) && this.op2.match(t.op2);
		}
		return r;
	}

	@Override
	protected String getId() {
		return "BE_"+id;
	}

	@Override
	protected void toDOT(String file) {
		if (!flag) {
			String s1 = "    \"%s\" [label=\"%s\"];";
			AbstractAlgoritmo.getFile().println(
					String.format(s1, getId(), this.operator.getShortName()));
			String s2 = "    \"%s\" -> \"%s\";";
			AbstractAlgoritmo.getFile().println(
					String.format(s2, getId(), op1.getId()));
			AbstractAlgoritmo.getFile().println(
					String.format(s2, getId(), op2.getId()));
			op1.toDOT(file);
			op2.toDOT(file);
		}
		flag = true;
	}
	
	@Override
	protected void setFlagFalse() {
		flag = false;
		op1.setFlagFalse();
		op2.setFlagFalse();
	}
	
	@Override
	public ExpType getExpType() {
		return operator.getExpType()[2];
	}
	
	@Override
	protected Map<String, Exp<?>> vars() {
		Map<String, Exp<?>> r = Maps2.newHashMap(op1.vars());
		r.putAll(op2.vars());;
		return r;
	}
	
	@Override
	public Integer getPriority() {
		return this.operator.getPriority();
	}
	
	@Override
	public Exp<R> ifMatchTransform(Exp<?> pattern, Map<String,Exp<?>> vars, String patternResult) {
		Exp<T1> r1 = op1.ifMatchTransform(pattern, vars, patternResult);
		Exp<T2> r2 = op2.ifMatchTransform(pattern,vars, patternResult);
		Exp<R> r = Exp.binary(r1,r2, this.operator);
		Exp<?> copy = pattern.copy();
		if(r.match(copy)){
			Map<String,Exp<?>> m = copy.getVars();
			Map<String,Exp<?>> m2 = 
					Maps2.<String,Exp<?>,Exp<?>>newHashMap(m, 
							x->x.isPlaceHolderExp()?x.asPlaceHolderExp().getExp():x);			
			m2.putAll(vars);
			r = Exp.string(patternResult,m2);
		}
		return r;
	}
}

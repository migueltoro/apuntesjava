package us.lsi.tiposrecursivos;

import java.util.Map;

import us.lsi.algoritmos.AbstractAlgoritmo;
import us.lsi.common.Maps2;

public class UnaryExp<T,R> extends Exp<R> {	
	
	private Exp<T> op;	
	private UnaryOperatorExp<T,R> operator;
	private int id;
	private static int lastId = 0;
	private Boolean flag = false;
	
	protected UnaryExp(Exp<T> op, UnaryOperatorExp<T,R> operator) {
		super();
		this.op = op;
		this.operator = operator;
		this.id = lastId;
		lastId++;
	}


	@Override
	public Integer getArity() {
		return 1;
	}

	@Override
	public String getName() {
		return operator.getName();
	}
	
	public Exp<T> getOp() {
		return op;
	}

	public void setOp(Exp<T> op) {
		this.op = op;
	}

	public UnaryOperatorExp<T,R> getOperator() {
		return operator;
	}

	@Override
	public R val() {
		return operator.getCode().apply(op.val());
	} 
	
	public String toString(){
		String sOp = this.getOp().toString();
		if(op.getPriority() < this.getPriority()){
			sOp = "("+sOp+")";
		}
		return String.format(this.operator.getFormat(),sOp);
	}
	
	@Override
	public Exp<R> copy() {
		return Exp.unary(this.getOp().copy(), this.operator);
	}

	public Boolean isUnary() {
		return true;
	}
	
	@Override
	protected String getId() {
		return "U_"+id;
	}
	
	@Override
	public Boolean isConstant() {
		return this.getOp().isConstant();
	}	
	
	@SuppressWarnings("unchecked")
	@Override
	public Exp<R> simplify() {
		Exp<R> r;
		if(this.isConstant()){
			r = Exp.constant(this.val());
		} else if(getName().equals("U_SquareDouble") && op.getName().equals("U_SqrtDouble")){
			r = (Exp<R>) op.asUnary().getOp().simplify();
		} else {
			r = Exp.unary(op.simplify(), operator);
		}		
		return r;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((op == null) ? 0 : op.hashCode());
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
		if (!(obj instanceof UnaryExp))
			return false;
		UnaryExp<?,?> other = (UnaryExp<?,?>) obj;
		if (op == null) {
			if (other.op != null)
				return false;
		} else if (!op.equals(other.op))
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
		} else if(exp.isUnary() && this.operator.equals(exp.getOperator())){
			UnaryExp<?,?> t = exp.asUnary();
			r = this.op.match(t.op);
		}
		return r;
	}
	
	@Override
	protected void toDOT(String file) {
		if (!flag) {
			String s1 = "    \"%s\" [label=\"%s\"];";
			AbstractAlgoritmo.getFile().println(
					String.format(s1, getId(), this.operator.getShortName()));
			String s2 = "    \"%s\" -> \"%s\";";
			AbstractAlgoritmo.getFile().println(
					String.format(s2, getId(), op.getId()));
			op.toDOT(file);
		}
		flag = true;
	}
	
	@Override
	protected void setFlagFalse() {
		flag = false;
		op.setFlagFalse();
	}

	@Override
	public ExpType getExpType() {
		return operator.getExpType()[1];
	}
	
	@Override
	protected Map<String, Exp<?>> vars() {
		Map<String, Exp<?>> r = Maps2.newHashMap(op.vars());
		return r;
	}


	@Override
	public Integer getPriority() {
		return 12;
	}

	@Override
	public Exp<R> ifMatchTransform(Exp<?> pattern, Map<String,Exp<?>> vars,String patternResult) {
		Exp<T> r1 = op.ifMatchTransform(pattern, vars, patternResult);
		Exp<R> r = Exp.unary(r1, this.operator);
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

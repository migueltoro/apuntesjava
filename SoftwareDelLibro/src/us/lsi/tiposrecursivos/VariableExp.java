package us.lsi.tiposrecursivos;

import java.util.Map;

import us.lsi.algoritmos.AbstractAlgoritmo;
import us.lsi.common.Maps2;
import us.lsi.common.Preconditions;

public class VariableExp<R> extends Exp<R> implements Operator {

	private R value;
	private String name;
	private int id;
	private static int lastId = 0;
	private Boolean flag = false;
	ExpType expType;	
	
	VariableExp(String name, R value, ExpType expType) {
		super();
		this.name = name;
		this.value = value;
		this.expType = expType;
		this.id = lastId;
		lastId++;
	}

	@Override
	public Integer getArity() {
		return 0;
	}

	@Override
	public Operator getOperator() {
		return this;
	}
	
	@Override
	public R val() {
		Preconditions.checkState(value!=null,String.format("La variable %s tiene valor null",name));
		return value;
	}

	public R getValue() {
		return value;
	}

	public void setValue(R value) {
		this.value = value;
	}
	
	public String getId() {
		return "V_"+id;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public Exp<R> copy() {
		return Exp.variable(this.name, this.value, this.expType);
	}


	public String getName() {
		return name;
	}
	
	public Boolean isVariable() {
		return true;
	}

	@Override
	public Boolean match(Exp<?> exp) {
		Boolean r = false;
		if(exp.isPlaceHolderExp()){
			exp.<R>asPlaceHolderExp().setExp(this);
			r = true;
		} else if(exp.isVariable()){
			if(this.getName().equals(exp.asVariable().getName())) r = true;
		}
		return r;
	}
	
	@Override
	public Boolean isConstant() {
		return false;
	}

	@Override
	public Exp<R> simplify() {
		return this;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof VariableExp))
			return false;
		VariableExp<?> other = (VariableExp<?>) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	protected void toDOT(String file) {
		if (!flag) {
			String s = "    \"%s\" [label=\"%s\"];";
			AbstractAlgoritmo.getFile().println(
					String.format(s, getId(), getShortName()));
		}	
		flag = true;
	}

	@Override
	public String getShortName() {
		return getName();
	}
	
	@Override
	protected void setFlagFalse() {
		flag = false;
	}
	
	@Override
	public ExpType getExpType() {
		return expType;
	}

	@Override
	protected Map<String, Exp<?>> vars() {
		return Maps2.newHashMap(name, this);
	}

	@Override
	public Integer getPriority() {
		return 12;
	}

	@Override
	public Exp<R> ifMatchTransform(Exp<?> pattern,Map<String,Exp<?>> vars,String result) {
		return this;
	}
	
}

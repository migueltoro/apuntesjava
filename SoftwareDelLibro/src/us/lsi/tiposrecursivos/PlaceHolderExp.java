package us.lsi.tiposrecursivos;

import java.util.Map;

import us.lsi.algoritmos.AbstractAlgoritmo;
import us.lsi.common.Maps2;
import us.lsi.common.Preconditions;

public class PlaceHolderExp<R> extends Exp<R> {

	private Exp<R> exp = null;
	private String name;
	private Integer id;
	ExpType expType;
	private static int lastId = 0;
	private Boolean flag = false;

	PlaceHolderExp(String name, ExpType expType) {
		super();
		this.name = name;
		this.expType = expType;
		this.exp = null;
		this.id = lastId;
		lastId++;
	}

	public Exp<R> getExp() {
		return exp;
	}

	public void setExp(Exp<R> exp) {
		this.exp = exp;
	}

	@Override
	public Integer getArity() {
		return 0;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Operator getOperator() {
		Preconditions.checkState(false, "Sin operador");
		return null;
	}

	@Override
	public R val() {
		Preconditions.checkState(false, String.format("La variable %s no es evaluable", name));
		return null;
	}

	@Override
	public Exp<R> copy() {
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
		if (!(obj instanceof PlaceHolderExp))
			return false;
		PlaceHolderExp<?> other = (PlaceHolderExp<?>) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public Boolean match(Exp<?> exp) {
		return false;
	}
	@Override
	public Boolean isPlaceHolderExp() {
		return true;
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
	public String toString() {
		return name;
	}

	@Override
	protected String getId() {
		return "PH_"+id;
	}
	
	@Override
	protected void toDOT(String file) {
		if (!flag) {
			String s = "    \"%s\" [label=\"%s\"];";
			AbstractAlgoritmo.getFile().println(
					String.format(s, getId(), getName()));
		}	
		flag = true;
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
	public Exp<R> ifMatchTransform(Exp<?> pattern,Map<String,Exp<?>> vars, String result) {
		return this;
	}
	
	
}

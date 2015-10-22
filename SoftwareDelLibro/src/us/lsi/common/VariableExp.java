package us.lsi.common;


public class VariableExp<R> extends Exp<R> {

	private R value;
	private String name;
	
	VariableExp(String name) {
		super();
		this.value = null;
		this.name = name;
	}	
	
	VariableExp(R value, String name) {
		super();
		this.value = value;
		this.name = name;
	}

	@Override
	public Integer getArity() {
		return 0;
	}

	@Override
	public R eval() {
		return value;
	}

	public R getValue() {
		return value;
	}

	public void setValue(R value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public Exp<R> clone() {
		return Exp.createVariable(this.value, this.name);
	}

	@Override
	public VariableExp<R> asVariable() {
		return this;
	}
	
	
}

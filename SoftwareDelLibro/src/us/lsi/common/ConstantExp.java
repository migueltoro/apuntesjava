package us.lsi.common;


public class ConstantExp<R> extends Exp<R> {

	private R value;	

	ConstantExp(R value) {
		super();
		this.value = value;
	}
	
	@Override
	public Integer getArity() {
		return 0;
	}

	@Override
	public R eval() {
		return value;
	}
		
    public String toString(){
    	return this.value.toString();
    }

	@Override
	public Exp<R> clone() {
		return Exp.createConstant(value);
	}

	@Override
	public ConstantExp<R> asConstant() {
		return this;
	}
	
}

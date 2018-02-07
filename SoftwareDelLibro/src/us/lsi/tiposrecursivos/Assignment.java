package us.lsi.tiposrecursivos;

import us.lsi.algoritmos.AbstractAlgoritmo;

public class Assignment<E> extends Sentence {

	private VariableExp<E> var;
	private Exp<E> exp;
	private int id;
	private static int lastId = 0;
	private Boolean flag = false;
	
	Assignment(VariableExp<E> var, Exp<E> exp) {
		super();
		this.var = var;
		this.exp = exp;
		this.id = lastId;
		lastId++;
	}

	public Exp<E> getExp() {
		return exp;
	}

	public VariableExp<E> getVar() {
		return var;
	}
	
	public String getId() {
		return "As_"+id;
	}

	@Override
	public Assignment<E> copy() {	
		return Sentence.assignment(Exp.variable(var.getName(),var.getValue(),var.getExpType()), exp.copy());
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Assignment))
			return false;
		Assignment<?> other = (Assignment<?>) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public void execute() {
		var.setValue(exp.val());
	}
	
	@Override
	protected void toDOT(String file) {
		if (!flag) {
			String s1 = "    \"%s\" [label=\"%s\"];";
			AbstractAlgoritmo.getFile().println(
					String.format(s1, getId(), "<="));
			String s2 = "    \"%s\" -> \"%s\";";
			AbstractAlgoritmo.getFile().println(
					String.format(s2, getId(), var.getId()));
			AbstractAlgoritmo.getFile().println(
					String.format(s2, getId(), exp.getId()));
			var.toDOT(file);
			exp.toDOT(file);
		}	
		flag = true;
	}
	
	@Override
	public String toString(){
		String s = "%s = %s;\n";
		return String.format(s, var.getName(), exp);
	}
	
	@Override
	public String getName() {
		return "<=";
	}

	@Override
	protected void setFlagFalse() {
		flag = false;
		this.var.setFlagFalse();
		this.exp.setFlagFalse();
	}

	
}

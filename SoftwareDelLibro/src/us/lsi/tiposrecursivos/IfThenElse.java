package us.lsi.tiposrecursivos;

import us.lsi.algoritmos.AbstractAlgoritmo;

public class IfThenElse extends Sentence {

	private Exp<Boolean> condition;
	private Sentence consequent;
	private Sentence alternative;
	private int id;
	private static int lastId = 0;
	private Boolean flag = false;
	
	IfThenElse(Exp<Boolean> condition, Sentence consequent,Sentence alternative) {
		super();
		this.condition = condition;
		this.consequent = consequent;
		this.alternative = alternative;
		id = lastId;
		lastId++;
	}

	@Override
	public Sentence copy() {
		return Sentence.ifThenElse(condition.copy(), consequent.copy(), alternative.copy());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((alternative == null) ? 0 : alternative.hashCode());
		result = prime * result
				+ ((condition == null) ? 0 : condition.hashCode());
		result = prime * result
				+ ((consequent == null) ? 0 : consequent.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof IfThenElse))
			return false;
		IfThenElse other = (IfThenElse) obj;
		if (alternative == null) {
			if (other.alternative != null)
				return false;
		} else if (!alternative.equals(other.alternative))
			return false;
		if (condition == null) {
			if (other.condition != null)
				return false;
		} else if (!condition.equals(other.condition))
			return false;
		if (consequent == null) {
			if (other.consequent != null)
				return false;
		} else if (!consequent.equals(other.consequent))
			return false;
		return true;
	}

	@Override
	protected String getId() {
		return "If_"+id;
	}

	@Override
	public void execute() {
		if(condition.val())
			consequent.execute();
		else 
			this.alternative.execute();
	}
	
	@Override
	protected void toDOT(String file) {
		if (!flag) {
			String s1 = "    \"%s\" [label=\"%s\"];";
			AbstractAlgoritmo.getFile().println(
					String.format(s1, getId(), getName()));
			String s2 = "    \"%s\" -> \"%s\" [label=\"%s\"];";
			AbstractAlgoritmo.getFile().println(
					String.format(s2, getId(), condition.getId(),"Condition"));
			AbstractAlgoritmo.getFile().println(
					String.format(s2, getId(), consequent.getId(),"Yes"));
			AbstractAlgoritmo.getFile().println(
					String.format(s2, getId(), alternative.getId(),"No"));
			condition.toDOT(file);
			consequent.toDOT(file);
			alternative.toDOT(file);
		}
		flag = true;
	}

	@Override
	public String getName() {
		return "IfThenElse";
	}
	
	@Override
	protected void setFlagFalse() {
		flag = false;
		condition.setFlagFalse();
		alternative.setFlagFalse();
		consequent.setFlagFalse();
	}
	
	@Override
	public String toString(){
		String s = "if(%s){\n %s} else { \n %s}\n";
		return String.format(s,condition,consequent,alternative);
	}
}

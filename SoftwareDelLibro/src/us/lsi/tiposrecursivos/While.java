package us.lsi.tiposrecursivos;

import us.lsi.algoritmos.AbstractAlgoritmo;

public class While extends Sentence {
	
	private Exp<Boolean> condition;
	private Sentence block;
	private int id;
	private static int lastId = 0;
	private Boolean flag = false;
	
	
	While(Exp<Boolean> condition, Sentence block) {
		super();
		this.condition = condition;
		this.block = block;
		id = lastId;
		lastId++;
	}


	@Override
	public While copy() {
		return Sentence.whileSentence(condition.copy(), block.copy());
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((block == null) ? 0 : block.hashCode());
		result = prime * result
				+ ((condition == null) ? 0 : condition.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof While))
			return false;
		While other = (While) obj;
		if (block == null) {
			if (other.block != null)
				return false;
		} else if (!block.equals(other.block))
			return false;
		if (condition == null) {
			if (other.condition != null)
				return false;
		} else if (!condition.equals(other.condition))
			return false;
		return true;
	}


	@Override
	protected String getId() {
		return "W_"+id;
	}


	@Override
	public void execute() {
		while(condition.val())
			block.execute();
	}
	
	@Override
	protected void toDOT(String file) {
		if (!flag) {
			String s1 = "    \"%s\" [label=\"%s\"];";
			AbstractAlgoritmo.getFile().println(
					String.format(s1, getId(), this.getName()));
			String s2 = "    \"%s\" -> \"%s\"  [label=\"%s\"];";
			AbstractAlgoritmo.getFile().println(
					String.format(s2, getId(), condition.getId(),"Condition"));
			AbstractAlgoritmo.getFile().println(
					String.format(s2, getId(), block.getId(),"Block"));
			condition.toDOT(file);
			block.toDOT(file);
		}	
		flag = true;
	}


	@Override
	public String getName() {
		return "While";
	}
	
	@Override
	protected void setFlagFalse() {
		flag = false;
		condition.setFlagFalse();
		block.setFlagFalse();
	}
	
	@Override
	public String toString(){
		String s = "while(%s){\n %s}\n";
		return String.format(s,condition,block);
	}
}

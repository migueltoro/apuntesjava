package us.lsi.tiposrecursivos;

import java.util.List;
import java.util.stream.Collectors;

import us.lsi.algoritmos.AbstractAlgoritmo;

public class Sentences extends Sentence {

	private List<Sentence> sentences;
	private int id;
	private static int lastId = 0;
	private Boolean flag = false;

	Sentences(List<Sentence> sentences) {
		super();
		this.sentences = sentences;
		this.id = lastId;
		lastId++;
	}

	@Override
	public Sentences copy() {
		return Sentence.sentences(sentences.stream().map(x->x.copy()).collect(Collectors.toList()));
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((sentences == null) ? 0 : sentences.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Sentences))
			return false;
		Sentences other = (Sentences) obj;
		if (sentences == null) {
			if (other.sentences != null)
				return false;
		} else if (!sentences.equals(other.sentences))
			return false;
		return true;
	}

	@Override
	protected String getId() {
		return "Ss_"+id;
	}

	@Override
	public void execute() {
		sentences.stream().forEach(x->x.execute());
	}
	
	@Override
	protected void toDOT(String file) {
		if (!flag) {
			String s1 = "    \"%s\" [label=\"%s\"];";
			AbstractAlgoritmo.getFile().println(
					String.format(s1, getId(), getName()));
			String s2 = "    \"%s\" -> \"%s\"  [label=\"%d\"];";
			for (int i = 0; i < sentences.size(); i++) {
				AbstractAlgoritmo.getFile()
						.println(
								String.format(s2, getId(), sentences.get(i)
										.getId(), i));
				sentences.get(i).toDOT(file);
			}
		}
		flag = true;
	}

	@Override
	public String getName() {
		return "Block";
	}
	
	@Override
	protected void setFlagFalse() {
		flag = false;
		sentences.stream().forEach(x->x.setFlagFalse());
	}
	
	@Override
	public String toString(){
		return sentences.stream().map(x->x.toString()).collect(Collectors.joining());
	}
	
}

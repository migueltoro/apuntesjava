package us.lsi.pd.matrices;

public class Matriz {

	public final Integer f;
	public final Integer c;
	public Matriz(Integer f, Integer c) {
		super();
		this.f = f;
		this.c = c;
	}
	
	@Override
	public String toString() {
		return "Matriz [f=" + f + ", c=" + c + "]";
	}
}

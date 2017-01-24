package us.lsi.graphs;


/**
 * @author Miguel Toro
 *
 * @param <V> Tipo de los vértices que conecta la arista
 * @param <A> Tipo de la alternativa asociada a la arista
 */
public class AlternativeSimpleEdge<A, V> extends SimpleEdge<V> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static <A,V> AlternativeSimpleEdge<A,V> create(V c1, V c2, A a) {
		return new AlternativeSimpleEdge<A,V>(c1, c2, a);
	}


	public static <A,V> AlternativeSimpleEdge<A,V> create(V c1, V c2, A a,
			double weight) {
		return new AlternativeSimpleEdge<A,V>(c1, c2, a, weight);
	}

	private A alternative;
	
	protected AlternativeSimpleEdge(V c1, V c2, A a) {
		super(c1, c2);
		this.alternative = a;
	}
	

	protected AlternativeSimpleEdge(V c1, V c2, A a, double weight) {
		super(c1, c2, weight);
		this.alternative = a;
	}

	public A getAlternative() {
		return alternative;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((alternative == null) ? 0 : alternative.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof AlternativeSimpleEdge))
			return false;
		AlternativeSimpleEdge<?,?> other = (AlternativeSimpleEdge<?,?>) obj;
		if (alternative == null) {
			if (other.alternative != null)
				return false;
		} else if (!alternative.equals(other.alternative))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return super.toString()+","+this.alternative;
	}

	
	

}

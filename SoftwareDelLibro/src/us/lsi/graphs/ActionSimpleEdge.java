package us.lsi.graphs;


/**
 * @author Miguel Toro
 *
 * @param <V> Tipo de los vértices que conecta la arista
 * @param <A> Tipo de la acción asociada a la arista
 */
public class ActionSimpleEdge<A, V> extends SimpleEdge<V> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static <A,V> ActionSimpleEdge<A,V> create(V c1, V c2, A a) {
		return new ActionSimpleEdge<A,V>(c1, c2, a);
	}


	public static <A,V> ActionSimpleEdge<A,V> create(V c1, V c2, A a,
			double weight) {
		return new ActionSimpleEdge<A,V>(c1, c2, a, weight);
	}

	private A accion;
	
	protected ActionSimpleEdge(V c1, V c2, A a) {
		super(c1, c2);
		this.accion = a;
	}
	

	protected ActionSimpleEdge(V c1, V c2, A a, double weight) {
		super(c1, c2, weight);
		this.accion = a;
	}

	public A getAction() {
		return accion;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((accion == null) ? 0 : accion.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof ActionSimpleEdge))
			return false;
		ActionSimpleEdge<?,?> other = (ActionSimpleEdge<?,?>) obj;
		if (accion == null) {
			if (other.accion != null)
				return false;
		} else if (!accion.equals(other.accion))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return super.toString()+","+this.accion;
	}

	
	

}

package us.lsi.astar.jarras;

import us.lsi.graphs.SimpleEdge;
import us.lsi.pd.jarras.Accion;

public class EdgeJarras<V> extends SimpleEdge<V> {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static <V> EdgeJarras<V> create(V c1, V c2, Accion a) {
		return new EdgeJarras<V>(c1, c2, a);
	}

	private Accion accion;

	public Accion getAccion() {
		return accion;
	}

	private EdgeJarras(V c1, V c2, Accion a) {
		super(c1, c2);
		this.accion = a;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((accion == null) ? 0 : accion.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof EdgeJarras))
			return false;
		EdgeJarras<?> other = (EdgeJarras<?>) obj;
		if (accion == null) {
			if (other.accion != null)
				return false;
		} else if (!accion.equals(other.accion))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "EdgeJarras [accion=" + accion + ", source=" + source
				+ ", target=" + target + "]";
	}
	
	
	
}

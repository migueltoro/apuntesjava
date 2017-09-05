package us.lsi.graphs;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Miguel Toro
 *
 * @param <A> Tipo de la acción
 * @param <V> Tipo del vértice
 * 
 * 
 * <a> Tipo adecuado para modelar un vértice de un grafo virtual simple cuyas aristas están 
 * definidas por un conjunto finito de acciones. 
 * Cada acción válida identifica de forma única uno de los vécinos del vértice. 
 * Cada vértice conoce sus vecinos y la forma de llegar a ellos mediante una de las acciones válidas disponibles </a>
 */
public abstract class ActionVirtualVertex<A, V extends ActionVirtualVertex<A,V>> 
			implements VirtualVertex<V, ActionSimpleEdge<A,V>> {

	public ActionVirtualVertex() {
	}
	
	/**
	 * @return Si es un valor válido del tipo
	 */
	public abstract boolean isValid();
	/**
	 * @pre isApplicable(a)
	 * @param a Una acción
	 * @return El vecino tras tomar esa acción
	 */
	public abstract V neighbor(A a);
	/**
	 * @param a Una acción
	 * @return Si la acción es aplicable en este vértice
	 */
	public abstract boolean isApplicable(A a);
	
	/**
	 * Para ser implementado por el subtipo
	 * @return Lista de acciones disponibles
	 */
	public abstract List<A> acciones();
	protected abstract V getThis();
	
	private Set<V> neighbors = null;
	private Set<ActionSimpleEdge<A,V>> edges = null;
	

	@Override
	public Set<V> getNeighborListOf() {
		if (this.neighbors==null) {
			this.neighbors=acciones().stream().filter(x -> this.isApplicable(x))
					.map(x -> this.neighbor(x)).collect(Collectors.toSet());
		}
		return this.neighbors;
	}

	@Override
	public Set<ActionSimpleEdge<A,V>> edgesOf() {
		if (this.edges==null) {
			this.edges=acciones()
					.stream()
					.filter(x -> this.isApplicable(x))
					.map(x -> ActionSimpleEdge.create(getThis(),
							this.neighbor(x), x)).collect(Collectors.toSet());
		}
		return edges;
	}

	@Override
	public boolean isNeighbor(V e) {
		return this.getNeighborListOf().contains(e);
	}

	
}

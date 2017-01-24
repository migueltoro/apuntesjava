package us.lsi.graphs;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Miguel Toro
 *
 * @param <A> Tipo de la alternativa
 * @param <V> Tipo del vértice
 * 
 * 
 * <a> Tipo adecuado para modelar un vértice de un grafo virtual simple cuyas aristas están 
 * definidas por un conjunto finito de alternativas. 
 * Cada alternativa válida identifica de forma única uno de los vécinos del vértice. 
 * Cada vértice conce sus vecinos y la forma de llegar a ellos medainte una de las alternativas válidas disponibles </a>
 */
public abstract class AlternativeVirtualVertex<A, V extends AlternativeVirtualVertex<A,V>> 
			implements VirtualVertex<V, AlternativeSimpleEdge<A,V>> {

	public AlternativeVirtualVertex() {
	}
	
	/**
	 * @return Si es un valor válido del tipo
	 */
	public abstract boolean isValid();
	/**
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
	 * @return Lista de valores del tipo enumerado A
	 */
	protected abstract List<A> values();
	protected abstract V getThis();
	
	private Set<V> neighbors = null;
	private Set<AlternativeSimpleEdge<A,V>> edges = null;
	

	@Override
	public Set<V> getNeighborListOf() {
		if (neighbors == null) {
			neighbors = values().stream()
					.filter(x -> this.isApplicable(x))
					.map(x -> this.neighbor(x))
					.collect(Collectors.toSet());
		}
		return neighbors;
	}

	@Override
	public Set<AlternativeSimpleEdge<A,V>> edgesOf() {
		if (edges == null) {
			edges = values().stream()
					.filter(x -> this.isApplicable(x))
					.<AlternativeSimpleEdge<A,V>>map(x -> AlternativeSimpleEdge.create(getThis(),this.neighbor(x),x))
					.collect(Collectors.toSet());
		}
		return edges;
	}

	@Override
	public boolean isNeighbor(V e) {
		return this.getNeighborListOf().contains(e);
	}

	
}

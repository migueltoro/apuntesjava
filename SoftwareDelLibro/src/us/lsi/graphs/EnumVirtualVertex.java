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
 * <a> Tipo adecuado para modelar un vértice de un grafo virtual simple cuyas aristas están 
 * definidas por un conjunto finito de acciones codificadas en un tipo enumerado A </a>
 */
public abstract class EnumVirtualVertex<A extends Enum<A>, V extends EnumVirtualVertex<A,V>> 
			implements VirtualVertex<V, SimpleEdge<V>> {

	public EnumVirtualVertex() {
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
	 * <a>Para ser implementado por el subtipo </a>
	 * @param v Vértice opuesto
	 * @return Devuelve una arista del vértice actual al opuesto
	 */
	protected abstract SimpleEdge<V> createEdgeTo(V v);
	
	/**
	 * Para ser implementado por el subtipo
	 * @return Lista de valores del tipo enumerado A
	 */
	protected abstract List<A> values();
	private Set<V> neighbors = null;
	private Set<SimpleEdge<V>> edges = null;

	@Override
	public Set<V> getNeighborListOf() {
		if (neighbors == null) {
			neighbors = values().stream().filter(x -> this.isApplicable(x))
					.map(x -> this.neighbor(x)).collect(Collectors.toSet());
		}
		return neighbors;
	}

	@Override
	public Set<SimpleEdge<V>> edgesOf() {
		if (edges == null) {
			edges = values().stream().filter(x -> this.isApplicable(x))
					.map(x -> createEdgeTo(this.neighbor(x)))
					.collect(Collectors.toSet());
		}
		return edges;
	}

	@Override
	public boolean isNeighbor(V e) {
		return this.getNeighborListOf().contains(e);
	}

}

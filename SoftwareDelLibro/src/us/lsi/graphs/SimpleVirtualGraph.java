package us.lsi.graphs;

import java.util.*;

import org.jgrapht.*;

/**
 * <p> Implementación de un grafo virtual simple 
 * Asumimos que los vértices son subtipo de VirtualVertex &lt; V,E &gt;
 * Asumimos que las aristas son subtipos de SimpleEdge &lt; V &gt; 
 * </p>
 * 
 * <p> El grafo es inmutable por lo que no están permitadas las operación de modificación.
 *  Si se invoca una operación para modificar el grafo se disparará 
 * la excepción UnsupportedOperationException </p>
 * 
 * @see us.lsi.graphs.VirtualVertex
 * 
 * 
 * 
 * @author Miguel Toro
 *
 * @param <V> El tipo de los vértices
 * @param <E> El tipo de las aristas
 * 
 */
public class SimpleVirtualGraph<V extends VirtualVertex<V,E>, E extends SimpleEdge<V>>
	implements Graph<V, E> {
	
	
	private EdgeFactory<V,E> edgeFactory;
	private Set<V> vertexSet;
		
	protected SimpleVirtualGraph(EdgeFactory<V, E> edgeFactory, V[] vertex) {
		super();
		this.edgeFactory = edgeFactory;
		this.vertexSet = new HashSet<V>();
		for(V v:vertex){
			vertexSet.add(v);
		}
	}

	protected SimpleVirtualGraph(EdgeFactory<V, E> edgeFactory){
		super();
		this.edgeFactory = edgeFactory;
		this.vertexSet = new HashSet<V>();
	}
	
	@Override
	public EdgeFactory<V, E> getEdgeFactory() {
		return edgeFactory;
	}

	@Override
	public boolean containsEdge(E e) {
		return e.getSource().isNeighbor(e.getTarget());
	}

	@Override
	public boolean containsEdge(V v1, V v2) {
		return v1.isNeighbor(v2);
	}
	@Override
	public boolean containsVertex(V v) {
		return v.isValid();
	}
	@Override
	public V getEdgeSource(E e) {
		return e.getSource();
	}

	@Override
	public V getEdgeTarget(E e) {
		return e.getTarget();
	}

	@Override
	public double getEdgeWeight(E e) {
		return e.getEdgeWeight();
	}
	
	@Override
	public E getEdge(V v1, V v2) {
		E a = null;
		if (v1.isNeighbor(v2)) {
			a = getEdgeFactory().createEdge(v1, v2);
		}
		return a;
	}
	
	@Override
	public Set<E> getAllEdges(V v1, V v2) {
		Set<E> s = new HashSet<>();
		if (v1.isNeighbor(v2))
			s.add(getEdgeFactory().createEdge(v1, v2));
		return s;
	}
	
	@Override
	public Set<V> vertexSet(){
		return vertexSet; 
	}
	
	@Override
	public Set<E> edgesOf(V v) {
		return v.edgesOf();
	}
	
	public Set<V> getNeighborListOf(V v) {
		return v.getNeighborListOf();
	}
	
	
	/* 
	 * @see org.jgrapht.Graph#edgeSet()
	 * @exception UnsupportedOperationException Si se invoca
	 */
	@Override
	public Set<E> edgeSet() {
		throw new UnsupportedOperationException();
	}
	
	/* 
	 * @see org.jgrapht.Graph#addEdge(java.lang.Object, java.lang.Object)
	 * @exception UnsupportedOperationException Si se invoca
	 */
	@Override
	public E addEdge(V arg0, V arg1) {
		throw new UnsupportedOperationException();
	}

	
	/* 
	 * @see org.jgrapht.Graph#addEdge(java.lang.Object, java.lang.Object, java.lang.Object)
	 * @exception UnsupportedOperationException Si se invoca
	 */
	@Override
	public boolean addEdge(V arg0, V arg1, E arg2) {
		throw new UnsupportedOperationException();
	}


	/* 
	 * @see org.jgrapht.Graph#addVertex(java.lang.Object)
	 * @exception UnsupportedOperationException Si se invoca
	 */
	@Override
	public boolean addVertex(V arg0) {
		throw new UnsupportedOperationException();
	}

	/*
	 * @see org.jgrapht.Graph#removeAllEdges(java.util.Collection)
	 * @exception UnsupportedOperationException Si se invoca
	 */
	@Override
	public boolean removeAllEdges(Collection<? extends E> arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<E> removeAllEdges(V arg0, V arg1) {
		throw new UnsupportedOperationException();
	}

	/* 
	 * @see org.jgrapht.Graph#removeAllVertices(java.util.Collection)
	 * @exception UnsupportedOperationException Si se invoca
	 */
	@Override
	public boolean removeAllVertices(Collection<? extends V> arg0) {
		throw new UnsupportedOperationException();
	}

	/* 
	 * @see org.jgrapht.Graph#removeEdge(java.lang.Object)
	 * @exception UnsupportedOperationException Si se invoca
	 */
	@Override
	public boolean removeEdge(E arg0) {
		throw new UnsupportedOperationException();
	}

	/* 
	 * @see org.jgrapht.Graph#removeEdge(java.lang.Object, java.lang.Object)
	 * @exception UnsupportedOperationException Si se invoca
	 */
	@Override
	public E removeEdge(V arg0, V arg1) {
		throw new UnsupportedOperationException();
	}

	/* 
	 * @see org.jgrapht.Graph#removeVertex(java.lang.Object)
	 * @exception UnsupportedOperationException Si se invoca
	 */
	@Override
	public boolean removeVertex(V arg0) {
		throw new UnsupportedOperationException();
	}	

}

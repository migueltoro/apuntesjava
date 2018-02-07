package us.lsi.graphs;

import java.util.*;

import org.jgrapht.*;

/**
 * <p> Implementación de un grafo virtual simple 
 * Asumimos que los vértices son subtipo de VirtualVertex &lt; V,E &gt;
 * Asumimos que las aristas son subtipos de SimpleEdge &lt; V &gt; 
 * </p>
 * 
 * <p> El grafo es inmutable por lo que no están permitadas las operación de modificación. Tampoco
 * están permitidas las operaciones de consulta de todos los vértices o todas las aristas.
 *  Si se invocan alguna de ellas se disparará 
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
/**
 * @author Boss
 *
 * @param <V> Tipo de los vértices
 * @param <E> Tipo de las aristas
 */
public class SimpleVirtualGraph<V extends VirtualVertex<V,E>, E extends SimpleEdge<V>>
	implements Graph<V, E> {
			
	private Set<V> vertexSet;
	
	public SimpleVirtualGraph(){
		super();
		this.vertexSet = new HashSet<>();
	}
	
	/**
	 * @param vertexSet Conjunto de vértices del grafo que queremos hacer explícitos.
	 */
	@SafeVarargs
	public SimpleVirtualGraph(V... vertexSet){
		super();
		this.vertexSet = new HashSet<>();
		for(V v:vertexSet){
			this.vertexSet.add(v);
		}
	}
	
	@Override
	public EdgeFactory<V, E> getEdgeFactory() {
		return null;
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
			a = v1.edgesOf()
					.stream()
					.filter(e->e.otherVertex(v1).equals(v2))
					.findFirst()
					.get();
		}
		return a;
	}
	
	@Override
	public Set<E> getAllEdges(V v1, V v2) {
		Set<E> s = new HashSet<>();
		if (v1.isNeighbor(v2))
			s.add(getEdge(v1, v2));
		return s;
	}
	
	
	
	/** 
	 * @return Conjunto de vértices del grafo que se han hecho explícitos en el constructor.
	 */
	@Override
	public Set<V> vertexSet(){
		return this.vertexSet;
	}
	
	@Override
	public Set<E> edgesOf(V v) {
		return v.edgesOf();
	}
	
	
	/**
	 * @see org.jgrapht.Graph#edgeSet()
	 * @throw UnsupportedOperationException
	 */
	@Override
	public Set<E> edgeSet() {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * @see org.jgrapht.Graph#addEdge(java.lang.Object, java.lang.Object)
	 * @throw UnsupportedOperationException
	 */
	@Override
	public E addEdge(V arg0, V arg1) {
		throw new UnsupportedOperationException();
	}

	
	/**
	 * @see org.jgrapht.Graph#addEdge(java.lang.Object, java.lang.Object, java.lang.Object)
	 * @throw UnsupportedOperationException
	 */
	@Override
	public boolean addEdge(V arg0, V arg1, E arg2) {
		throw new UnsupportedOperationException();
	}


	/**
	 * @see org.jgrapht.Graph#addVertex(java.lang.Object)
	 * @throw UnsupportedOperationException
	 */
	@Override
	public boolean addVertex(V arg0) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see org.jgrapht.Graph#removeAllEdges(java.util.Collection)
	 * @throw UnsupportedOperationException
	 */
	@Override
	public boolean removeAllEdges(Collection<? extends E> arg0) {
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @throw UnsupportedOperationException
	 */
	@Override
	public Set<E> removeAllEdges(V arg0, V arg1) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see org.jgrapht.Graph#removeAllVertices(java.util.Collection)
	 * @throw UnsupportedOperationException
	 */
	@Override
	public boolean removeAllVertices(Collection<? extends V> arg0) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see org.jgrapht.Graph#removeEdge(java.lang.Object)
	 * @throw UnsupportedOperationException
	 */
	@Override
	public boolean removeEdge(E arg0) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see org.jgrapht.Graph#removeEdge(java.lang.Object, java.lang.Object)
	 * @throw UnsupportedOperationException
	 */
	@Override
	public E removeEdge(V arg0, V arg1) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see org.jgrapht.Graph#removeVertex(java.lang.Object)
	 * @throw UnsupportedOperationException
	 */
	@Override
	public boolean removeVertex(V arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int degreeOf(V arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public GraphType getType() {
		throw new UnsupportedOperationException();
	}

	@Override
	public int inDegreeOf(V arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<E> incomingEdgesOf(V arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int outDegreeOf(V arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<E> outgoingEdgesOf(V arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setEdgeWeight(E arg0, double arg1) {
		throw new UnsupportedOperationException();
		
	}	
	
	

}

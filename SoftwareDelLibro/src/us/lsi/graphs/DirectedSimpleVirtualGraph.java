package us.lsi.graphs;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.jgrapht.DirectedGraph;
import org.jgrapht.EdgeFactory;
/**
 * <p> Implementación de un grafo virtual simple y dirigido
 * Asumimos que los vértices son subtipo de VirtualDirectedVertex &lt; V,E &gt;
 * Asumimos que las aristas son subtipos de SimpleEdge &lt; V &gt; 
 * </p>
 * 
 * @see us.lsi.graphs.VirtualDirectedVertex
 * 
 * <p> El grafo es no modificable por lo que no están permitadas las operación de modificación </p>
 * 
 * @author Miguel Toro
 *
 * @param <V> El tipo de los vértices
 * @param <E> El tipo de las aristas
 * 
 */
public class DirectedSimpleVirtualGraph<V extends VirtualDirectedVertex<V,E>, E extends SimpleEdge<V>>
		extends SimpleVirtualGraph<V, E> 
		implements DirectedGraph<V, E> {
		

	private Map<V, Set<E>> incomingEdgesOf;
	private Map<V, Set<E>> outgoingEdgesOf;
	private Map<V, Integer> inDegreeOf;
	private Map<V, Integer> outDegreeOf;

	public DirectedSimpleVirtualGraph(EdgeFactory<V,E> edgeFactory, V[] vs) {
		super(edgeFactory,vs);
		this.incomingEdgesOf = new HashMap<>();
		this.outgoingEdgesOf = new HashMap<>();
		this.inDegreeOf = new HashMap<>();
		this.outDegreeOf = new HashMap<>();
	}

	public DirectedSimpleVirtualGraph(EdgeFactory<V,E> edgeFactory) {
		super(edgeFactory);
		this.incomingEdgesOf = new HashMap<>();
		this.outgoingEdgesOf = new HashMap<>();
		this.inDegreeOf = new HashMap<>();
		this.outDegreeOf = new HashMap<>();
	}
	@Override
	public Set<E> edgesOf(V v) {
		Set<E> r = new HashSet<>(this.incomingEdgesOf(v));
		r.addAll(this.outgoingEdgesOf(v));
		return r;
	}
	
	@Override
	public int inDegreeOf(V v) {
		Integer r;
		if (inDegreeOf.containsKey(v)) {
			r = inDegreeOf.get(v);
		} else {
			r = this.edgesOf(v).size();
			inDegreeOf.put(v, r);
		}
		return r;
	}

	@Override
	public Set<E> incomingEdgesOf(V v) {
		Set<E> r;
		if (incomingEdgesOf.containsKey(v)) {
			r = incomingEdgesOf.get(v);
		} else {
			r = v.incomingEdgesOf();
			incomingEdgesOf.put(v, r);
		}
		return r;
	}

	@Override
	public int outDegreeOf(V v) {
		Integer r;
		if (outDegreeOf.containsKey(v)) {
			r = outDegreeOf.get(v);
		} else {
			r = this.edgesOf(v).size();
			outDegreeOf.put(v, r);
		}
		return r;
	}

	@Override
	public Set<E> outgoingEdgesOf(V v) {
		Set<E> r;
		if (outgoingEdgesOf.containsKey(v)) {
			r = outgoingEdgesOf.get(v);
		} else {
			r = v.outgoingEdgesOf();
			outgoingEdgesOf.put(v, r);
		}
		return r;
	}
	
	public Integer getNumVertexInOutgoingEdgesOf(){
		return outgoingEdgesOf.size();
	}
}

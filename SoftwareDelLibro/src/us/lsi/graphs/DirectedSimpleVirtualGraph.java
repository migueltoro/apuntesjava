package us.lsi.graphs;

import java.util.HashSet;
import java.util.Set;
import org.jgrapht.Graph;
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
		implements Graph<V, E> {
		

	public DirectedSimpleVirtualGraph() {
		super();
	}
		
	@SafeVarargs
	public DirectedSimpleVirtualGraph(V... vertexSet) {
		super(vertexSet);
	}



	@Override
	public Set<E> edgesOf(V v) {
		Set<E> r = new HashSet<>(this.incomingEdgesOf(v));
		r.addAll(this.outgoingEdgesOf(v));
		return r;
	}
	
	@Override
	public int inDegreeOf(V v) {
		return v.incomingEdgesOf().size();
	}

	@Override
	public Set<E> incomingEdgesOf(V v) {
		return v.incomingEdgesOf();
	}

	@Override
	public int outDegreeOf(V v) {
		return v.outgoingEdgesOf().size();
	}

	@Override
	public Set<E> outgoingEdgesOf(V v) {
		return v.outgoingEdgesOf();
	}
	
}

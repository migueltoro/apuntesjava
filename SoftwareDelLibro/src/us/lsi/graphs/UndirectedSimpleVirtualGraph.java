package us.lsi.graphs;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.jgrapht.EdgeFactory;
import org.jgrapht.UndirectedGraph;

/**
 * <p> Implementación de un grafo virtual simple y no dirigido
 * Asumimos que los vértices son subtipo de VirtualVertex &lt; V,E &gt;
 * Asumimos que las aristas son subtipos de SimpleEdge &lt; V &gt; 
 * </p>
 * 
 * @see us.lsi.graphs.VirtualDirectedVertex
 * 
 * <p> El grafo es inmutable por lo que no están permitadas las operación de moficación.  </p>
 * 
 * @author Miguel Toro
 *
 * @param <V> El tipo de los vértices
 * @param <E> El tipo de las aristas
 * 
 */
public class UndirectedSimpleVirtualGraph<V extends VirtualVertex<V,E>, E extends SimpleEdge<V>>
		extends SimpleVirtualGraph<V,E> 
		implements UndirectedGraph<V,E>{

	private Map<V,Set<E>> edgesOf;	
	private Map<V,Integer> degreeOf;
	
	
	
	public UndirectedSimpleVirtualGraph(EdgeFactory<V,E> edgeFactory, V[] vs) {
		super(edgeFactory,vs);
		this.edgesOf = new HashMap<>();		
		this.degreeOf = new HashMap<>();
	}
	
	public UndirectedSimpleVirtualGraph(EdgeFactory<V,E> edgeFactory) {
		super(edgeFactory);
		this.edgesOf = new HashMap<>();		
		this.degreeOf = new HashMap<>();
	}
	
	@Override
	public Set<E> edgesOf(V v) {
		Set<E> r;
		if(edgesOf.containsKey(v)){
			r = edgesOf.get(v);
		}else {
			r = v.edgesOf();
			edgesOf.put(v, r);
		}			
		return r;
	}

	
	@Override
	public int degreeOf(V v) {
		Integer r;		
		if (degreeOf.containsKey(v)) {
			r = degreeOf.get(v);
		}else {
			r = this.edgesOf(v).size();
			degreeOf.put(v, r);
		}
		return r;
	}	
	
	public Integer getNumVertexInEdgesOf(){
		return edgesOf.size();
	}
}

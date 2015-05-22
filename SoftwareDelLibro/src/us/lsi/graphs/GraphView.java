package us.lsi.graphs;

import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class GraphView<V, E> {

	public static <V, E> GraphView<V, E> create(Graph<V, E> grafo) {
		return new GraphView<V, E>(grafo);
	}

	private List<V> vertices;
	private Graph<V,E> grafo;
	private Integer numVertices;
	private Map<V,Integer> map;
	
	private GraphView(Graph<V,E> grafo) {
		this.grafo = grafo;
		this.vertices = Lists.newArrayList(grafo.vertexSet());
		this.map = Maps.newHashMap();
		Integer n = 0;
		for(V v:vertices){
			map.put(v,n);
			n++;
		}
		this.numVertices = this.vertices.size();
	}

	public List<V> getVertices() {
		return vertices;
	}

	public Graph<V, E> getGrafo() {
		return grafo;
	}

	public Integer getNumVertices() {
		return numVertices;
	}

	public V getVertice(int i) {
		return vertices.get(i);
	}
	
	public Integer getIndex(V v) {
		return map.get(v);
	}
	
	public Double getWeight(int i, int j) {
		Preconditions.checkArgument(this.isEdge(i,j));
		V v1 = getVertice(i);
		V v2 = getVertice(j);
		E cr = grafo.getEdge(v1,v2);
		return grafo.getEdgeWeight(cr);
	}
	
	public boolean isEdge(int i, int j) {
		V v1 = getVertice(i);
		V v2 = getVertice(j);
		E e = grafo.getEdge(v1,v2);
		return e==null?false:true;
	}
	
	public E getEdge(int i, int j) {
		Preconditions.checkArgument(this.isEdge(i,j));
		V v1 = getVertice(i);
		V v2 = getVertice(j);
		E e = grafo.getEdge(v1,v2);
		return e;
	}
}

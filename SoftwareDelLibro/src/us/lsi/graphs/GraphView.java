package us.lsi.graphs;

import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * @author Miguel Toro 
 *
 * @param <V> Tipo de los vértices
 * @param <E> Tipo de las aristas
 * 
 * <a> Una vista de un grafo dónde cada vértice se identifica con un entero </a>
 */
public class GraphView<V, E> {

	/**
	 * @param grafo Un grafo
	 * @return la vista del grafo
	 * @param <V> El tipo de los vértices
	 * @param <E> El tipo de las aristas
	 */
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

	/**
	 * @return La lista de vértices del grafo
	 */
	public List<V> getVertices() {
		return vertices;
	}

	/**
	 * @return El grafo original
	 */
	public Graph<V, E> getGrafo() {
		return grafo;
	}

	/**
	 * @return El número de vértices
	 */
	public Integer getNumVertices() {
		return numVertices;
	}

	/**
	 * @pre i en el rango  0..getNumVertices()-1
	 * @param i Un índice en el rango 0..getNumVertices()-1
	 * @return El vértices de índice <code> i </code>
	 */
	public V getVertice(int i) {
		return vertices.get(i);
	}
	
	/**
	 * @pre El grafo contiene al vértice v
	 * @param v Un vértice del grafo
	 * @return El entero correspondiente al vértice v
	 */
	public Integer getIndex(V v) {
		Preconditions.checkArgument(this.grafo.containsVertex(v));
		return map.get(v);
	}
	
	/**
	 * @pre hay una arista de i a j
	 * @param i Un índice válido
	 * @param j Un índice válido
	 * @return El peso de la arista que va de i a j
	 */
	public Double getWeight(int i, int j) {
		Preconditions.checkArgument(this.isEdge(i,j));
		V v1 = getVertice(i);
		V v2 = getVertice(j);
		E cr = grafo.getEdge(v1,v2);
		return grafo.getEdgeWeight(cr);
	}
	
	/**
	 * @pre i, j en el rango  0..getNumVertices()-1
	 * @param i Un índice válido de un vértice
	 * @param j Un índice válido de un vértice
	 * @return Si hay una arista entre los dos vértices dados por sus identifadores
	 */
	public boolean isEdge(int i, int j) {
		V v1 = getVertice(i);
		V v2 = getVertice(j);
		E e = grafo.getEdge(v1,v2);
		return e==null?false:true;
	}
	
	/**
	 * @pre isEdge(i, j)
	 * @param i Un índice válido de un vértice
	 * @param j Un índice válido de un vértice
	 * @return La arista entre i y j
	 */
	public E getEdge(int i, int j) {
		Preconditions.checkArgument(this.isEdge(i,j));
		V v1 = getVertice(i);
		V v2 = getVertice(j);
		E e = grafo.getEdge(v1,v2);
		return e;
	}
}

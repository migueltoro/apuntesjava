package us.lsi.graphs;


/**
 * <a> Tipo que deben los vértices de un grafo que queremos leer de un fichero cuyas líneas
 * representan una arista. Las líneas deben ser de la forma </a>
 * 
 * <a>s1,v1,v2,v3 </a>
 * 
 * <a>Dónde s1 es un identificador únicos del vértice y v1, ... propiedades de la arista </a>
 * 
 * @author Miguel Toro
 *
 * @param <V> El tipo de los vértices
 */
public interface StringVertexFactory<V> {
	/**
	 * @param formato Los campos de la línea correspondiente: s1,v1,v2,v3
	 * @return El vértice creado
	 */
	public V createVertex(String[] formato);
}

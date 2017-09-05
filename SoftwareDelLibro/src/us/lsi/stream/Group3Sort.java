package us.lsi.stream;

import java.util.*;


/**
 * Es un tipo que asocia claves de tipo K1, K2 con valores de tipo R. Una versión compacta de un SortedMap &lt;K1, Map &lt; K2, Map &lt; K3, &gt; &gt; &gt;.
 * 
 * @author Miguel Toro
 *
 * @param <K1> Tipo de la primera clave 
 * @param <K2> Tipo de la segunda clave 
 * @param <K3> Tipo de la tercera clave 
 * @param <R> Tipo de los valores
 */class Group3Sort<K1, K2, K3, R> extends Group3<K1,K2,K3,R> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static <K1, K2, K3, R> Group3Sort<K1, K2, K3, R> create(
			SortedMap<K1, ? extends SortedMap<K2, SortedMap<K3, R>>> m) {
		return new Group3Sort<K1, K2, K3, R>(m);
	}

	public String toString() {
		return toString(0);
	}

	public Group2<K2, K3, R> getGroup(K1 x) {
		return Group2Sort.create(this.get(x));
	}

	@SuppressWarnings("unchecked")
	private Group3Sort(SortedMap<K1, ? extends SortedMap<K2, SortedMap<K3, R>>> m) {
		super((Map<? extends K1, ? extends Map<K2, Map<K3, R>>>) m);
	}

}

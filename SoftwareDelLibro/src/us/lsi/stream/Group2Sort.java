package us.lsi.stream;

import java.util.*;


/**
 * Es un tipo que asocia claves de tipo K1, K2 con valores de tipo R. Una versión compacta de un SortedMap &lt;K1, Map &lt; K2,R&gt; &gt;.
 * 
 * @author Miguel Toro
 *
 * @param <K1> Tipo de la primera clave 
 * @param <K2> Tipo de la segunda clave 
 * @param <R> Tipo de los valores
 */
class Group2Sort<K1, K2, R> extends Group2<K1,K2,R> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static <K1, K2, R> Group2Sort<K1, K2, R> create(
			SortedMap<K1, ? extends SortedMap<K2, R>> m) {
		return new Group2Sort<K1, K2, R>(m);
	}

	public String toString() {
		return toString(0);
	}

	public Group1<K2, R> getGroup(K1 x) {
		return Group1Sort.create(this.get(x));
	}

	private Group2Sort(SortedMap<K1, ? extends SortedMap<K2, R>> m) {
		super(m);
	}
}

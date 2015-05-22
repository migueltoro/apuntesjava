package us.lsi.stream;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Es un tipo que asocia claves de tipo K1 con valores de tipo R. Una versión compacta de un Map&lt;K1,R&gt;.
 * 
 * @author Miguel Toro
 *
 * @param <K1> Tipo de la clave
 * @param <R> Tipo de los valores
 */
public class Group1<K1, R> extends HashMap<K1, R> implements Map<K1, R> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static <K1, R> Group1<K1, R> create(Map<? extends K1, ? extends R> m) {
		return new Group1<K1, R>(m);
	}

	public String toString(int n) {
		return this
				.keySet()
				.stream()
				.<String> map(
						x -> Grouping.nIndex(n) + x.toString() + ",   "
								+ this.get(x))
				.collect(Collectors.joining("\n"));
	}

	public String toString() {
		return toString(0);
	}

	public R getGroup(K1 x) {
		return this.get(x);
	}

	protected Group1(Map<? extends K1, ? extends R> m) {
		super(m);
	}
	
}

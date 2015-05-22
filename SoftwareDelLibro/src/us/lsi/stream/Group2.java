package us.lsi.stream;

import java.util.*;
import java.util.stream.Collectors;




/**
 * Es un tipo que asocia claves de tipo K1, K2 con valores de tipo R. Una versión compacta de un Map &lt;K1, Map &lt; K2,R&gt; &gt;.
 * 
 * @author Miguel Toro
 *
 * @param <K1> Tipo de la primera clave 
 * @param <K2> Tipo de la segunda clave 
 * @param <R> Tipo de los valores
 */
public class Group2<K1, K2, R> extends HashMap<K1, Map<K2, R>> implements Map<K1, Map<K2, R>> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static <K1, K2, R> Group2<K1, K2, R> create(
			Map<? extends K1, ? extends Map<K2, R>> m) {
		return new Group2<K1, K2, R>(m);
	}

	public String toString(int n){
		return this
				.keySet()
				.stream()
				.<String>map(x->Grouping.nIndex(n)+x.toString()+"\n"+this.getGroup(x).toString(n+1))
				.collect(Collectors.joining("\n"));
	}
	
	public String toString(){
		return toString(0);
	}

	public Group1<K2, R> getGroup(K1 x){
		return Group1.create(this.get(x));
	}

	protected Group2(Map<? extends K1, ? extends Map<K2, R>> m) {
		super(m);
	}
	
}

package us.lsi.common;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.SetMultimap;

public class MultiMaps2 {

	/**
	 * @param <K> El tipo de las claves del ListMultimap
	 * @param <V> El tipo de los valores del ListMultimap
	 * @return Un ListMultimap vacío
	 */
	public static <K,V> ListMultimap<K,V> newListMultimap(){
		return ArrayListMultimap.<K,V>create();
	}
	/**
	 * @param <K> El tipo de las claves del ListMultimap
	 * @param <V> El tipo de los valores del ListMultimap
	 * @param m un Multimap
	 * @return Un ListMultimap copia
	 */
	public static <K,V> ListMultimap<K,V> newListMultimap(Multimap<K,V> m){
		return ArrayListMultimap.create(m);
	}
	/**
	 * @param <K> El tipo de las claves del ListMultimap
	 * @param <V> El tipo de los valores del ListMultimap
	 * @param m un Map
	 * @return Un ListMultimap copia
	 */
	public static <K,V> ListMultimap<K,V> newListMultimap(Map<K,List<V>> m){
		ListMultimap<K, V> r = ArrayListMultimap.<K,V>create();
		m.entrySet()
			.stream()
			.<Tuple2<K,V>>flatMap(x->x.getValue().stream().map(y->Tuple2.create(x.getKey(),y)))
			.forEach(x->r.put(x.getV1(),x.getV2()));    
		return r;
	}
	/**
	 * @param <K> El tipo de las claves del ListMultimap
	 * @param <V> El tipo de los valores del ListMultimap
	 * @param entries Una secuencia de pares
	 * @return Un ListMultimap creado a partir de los pares de entrada
	 */
	public static <K, V> ListMultimap<K, V> newListMultimap(@SuppressWarnings("unchecked") Entry<? extends K, ? extends V>... entries){
		ListMultimap<K, V> m = ArrayListMultimap.<K,V>create();
		Arrays.asList(entries)
			.stream()
			.forEach(x->m.put(x.getKey(),x.getValue())); 		
		return m;
	}
	/**
	 * @param <K> El tipo de las claves del SetMultimap
	 * @param <V> El tipo de los valores del SetMultimap
	 * @return Un SetMultimap vacío
	 */
	public static <K,V> SetMultimap<K,V> newSetMultimap(){
		return HashMultimap.<K,V>create();
	}
	/**
	 * @param <K> El tipo de las claves del SetMultimap
	 * @param <V> El tipo de los valores del SetMultimap
	 * @param m un Multimap
	 * @return Un SetMultimap copia
	 */
	public static <K,V> SetMultimap<K,V> newSetMultimap(Multimap<K,V> m){
		return HashMultimap.create(m);
	}
	/**
	 * @param <K> El tipo de las claves del SetMultimap
	 * @param <V> El tipo de los valores del SetMultimap
	 * @param entries Una secuencia de pares
	 * @return Un SetMultimap creado a partir de los pares de entrada
	 */
	public static <K, V> SetMultimap<K, V> newSetMultimap(@SuppressWarnings("unchecked") Entry<? extends K, ? extends V>... entries){
		SetMultimap<K, V> m = HashMultimap.<K,V>create();
		Arrays.asList(entries)
		.stream()
		.forEach(x->m.put(x.getKey(),x.getValue())); 		
	return m;
	}
}

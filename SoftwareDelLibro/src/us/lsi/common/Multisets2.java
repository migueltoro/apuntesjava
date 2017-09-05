package us.lsi.common;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

public class Multisets2 {

	/**
	 * @param <E> Tipo de los elementos del Multiset
	 * @return Un Multiset vacío
	 */
	public static <E> Multiset<E> newHashMultiset(){
		return HashMultiset.<E>create();
	}

	/**
	 * @param <E> Tipo de los elementos del Multiset
	 * @param it Un iterable
	 * @return Un Multiset construido a partir del iterable
	 */
	public static <E> Multiset<E> newHashMultiset(Iterable<E> it){
		return HashMultiset.<E>create(it);
	}
	
	/**
	 * @param <E> Tipo de los elementos del Multiset
	 * @param m Un Map
	 * @return Un Multiset construido a partir del Map
	 */
	public static <E> Multiset<E> newHashMultiset(Map<E,Integer> m){
		Multiset<E> r = HashMultiset.create();
		m.entrySet().stream().forEach(x->r.add(x.getKey(),x.getValue()));
		return r;
	}
	
	/**
	 * @param <E> Tipo de los elementos del Multiset
	 * @param m Un Multiset
	 * @return Un Map construido a partir del Multiset
	 */
	public static <E> Map<E,Integer> asMap(Multiset<E> m){
		Map<E,Integer> r = new HashMap<>();;
		m.entrySet().stream().forEach(x->r.put(x.getElement(),x.getCount()));
		return r;
	}
	
	/**
	 * @param <E> Tipo de los elementos del Multiset
	 * @param entries Una secuencia de elementos
	 * @return Un Multiset construidom a partir de la secuencia de elementos
	 */
	public static <E> Multiset<E> newHashMultiset(@SuppressWarnings("unchecked") E... entries){
		Multiset<E> s = HashMultiset.<E>create();
		Arrays.asList(entries)
		.stream()
		.forEach(x->s.add(x)); 		
		return s;
	}
	
}

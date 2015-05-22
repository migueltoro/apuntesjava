package us.lsi.common;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.SetMultimap;

public class MultiMaps2 {

	public static <K,V> ListMultimap<K,V> newListMultimap(){
		return ArrayListMultimap.<K,V>create();
	}
	
	public static <K,V> ListMultimap<K,V> newListMultimap(Multimap<K,V> m){
		return ArrayListMultimap.create(m);
	}
	
	public static <K, V> ListMultimap<K, V> newListMultimap(@SuppressWarnings("unchecked") Entry<? extends K, ? extends V>... entries){
		ListMultimap<K, V> m = ArrayListMultimap.<K,V>create();
		for(Entry<? extends K, ? extends V> e : entries){
			m.put(e.key, e.value);
		}
		return m;
	}

	public static <K,V> SetMultimap<K,V> newSetMultimap(){
		return HashMultimap.<K,V>create();
	}
	
	public static <K,V> SetMultimap<K,V> newSetMultimap(Multimap<K,V> m){
		return HashMultimap.create(m);
	}
	
	public static <K, V> SetMultimap<K, V> newSetMultimap(@SuppressWarnings("unchecked") Entry<? extends K, ? extends V>... entries){
		SetMultimap<K, V> m = HashMultimap.<K,V>create();
		for(Entry<? extends K, ? extends V> e : entries){
			m.put(e.key, e.value);
		}
		return m;
	}
}

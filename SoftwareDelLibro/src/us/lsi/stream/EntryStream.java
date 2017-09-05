package us.lsi.stream;

import java.util.Comparator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.function.BinaryOperator;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.SetMultimap;

import java.util.Map.Entry;

public class EntryStream<K, V> extends Stream2<Entry<K, V>> {
	
	public EntryStream(Stream<Entry<K, V>> st) {
		super(st);
	}
	
	public ListMultimap<K,V> toListMultimap() {
		return this.collect(Collectors2.<K,V>toListMultimap());
	}
	
	public SetMultimap<K,V> toSetMultimap() {
		return this.collect(Collectors2.<K,V>toSetMultimap());
	}
	
	public Map<K,V> toMap(){
		return this.collect(Collectors.<Entry<K,V>,K,V>toMap(e-> e.getKey(), e-> e.getValue()));
	}
	
	public Map<K, V> toMap(BinaryOperator<V> bo) {
		return this.collect(Collectors.<Entry<K,V>,K,V>toMap(e-> e.getKey(), e-> e.getValue(), bo));
	}
	
	public <M extends Map<K, V>> Map<K, V> toMap(BinaryOperator<V> operator, Supplier<M> supplier) {
		return this.collect(Collectors.<Entry<K,V>, K, V, M>toMap(e-> e.getKey(),  e-> e.getValue(), operator,
				supplier));
	}
	
	public BiMap<K,V> toBiMap(BinaryOperator<V> operator) {
		return this.collect(Collectors.<Entry<K,V>, K, V, BiMap<K,V>>toMap(e-> e.getKey(),  e-> e.getValue(), operator,
				()->HashBiMap.<K,V>create()));
	}
	
	public SortedMap<K, V> toMap(BinaryOperator<V> operator, Comparator<K> cmp) {
		return this.collect(Collectors.<Entry<K,V>, K, V, TreeMap<K,V>>toMap(e-> e.getKey(),  e-> e.getValue(), operator,
				()->new TreeMap<K,V>(cmp)));
	}
	
}

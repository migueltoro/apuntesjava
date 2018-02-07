package us.lsi.common;

import java.util.*;
import java.util.Map.Entry;
import java.util.AbstractMap.SimpleEntry;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.jgrapht.Graph;
import org.jgrapht.graph.SimpleGraph;

import us.lsi.graphs.GraphsReader;
import us.lsi.graphs.examples.Carretera;
import us.lsi.graphs.examples.Ciudad;


public class Maps2 {
		
	public static <K,V> Entry<K,V> newEntry(K key,V value){	
		return new SimpleEntry<>(key,value);
	}
	
	public static <K,V> Map<K,V> newHashMap(Map<K,V> r){
		return new HashMap<>(r);
	}
	
	public static <K,V> Map<K,V> newHashMap(K key,V value){
		Map<K,V> m = new HashMap<>();
		m.put(key,value);
		return m;
	}
	
	public static <K,V> Map<K,V> newHashMap(K key1,V value1,K key2,V value2){
		Map<K,V> m = new HashMap<>();
		m.put(key1,value1);
		m.put(key2,value2);
		return m;
	}
	
	public static <K,V> Map<K,V> newHashMap(K key1,V value1,K key2,V value2,K key3,V value3){
		Map<K,V> m = new HashMap<>();
		m.put(key1,value1);
		m.put(key2,value2);
		m.put(key3,value3);
		return m;
	}
	
	/**
	 * @param <K> tipo de las claves
	 * @param <V> tipo de los valores
	 * @param <U> tipo de la colección
	 * @param m Un Map
	 * @return Un map inverso asumiendo que los elementos en todos los conjuntos imagen son distintos
	 */
	public static <K,V,U extends Collection<V>> Map<V,K> reverseHashMap(Map<K,U> m){
		return m.keySet().stream()
				.<Entry<K,V>>flatMap(x->m.get(x).stream().map(y->Maps2.newEntry(x,y)))
				.collect(Collectors.toMap(z->z.getValue(), z->z.getKey()));
	}
	
	
	
	/**
	 * @param <K> tipo de las claves
	 * @param <V> tipo de los valores
	 * @param <R> nuevo tipo de los valores
	 * @param f una función
	 * @param m Un Map
	 * @return Un map cambiando los valores imagen aplicandole una función
	 */
	public static <K,V,R> Map<K,R> newHashMap(Map<K,V> m,Function<V,R> f){
		return m.entrySet().stream()
				.map(x->Maps2.newEntry(x.getKey(), f.apply(x.getValue())))
				.collect(Collectors.toMap(x->x.getKey(), x->x.getValue()));
	}
	
	/**
	 * @param <K> tipo de las claves
	 * @param <V> tipo de los valores
	 * @param entries Una serie de pares clave valor
	 * @return Un Map construido con esas claves
	 */
	@SuppressWarnings("unchecked")
	public static <K, V> Map<K, V> newHashMap(Entry<? extends K, ? extends V>... entries) {
        Map<K, V> result = new HashMap<>(entries.length);

        for (Entry<? extends K, ? extends V> entry : entries)
            if (entry.getValue() != null)
                result.put(entry.getKey(), entry.getValue());

        return result;
    }
    
	/**
	 * @param <K> El tipo de las claves 
	 * @param <V> El tipo de los valores 
	 * @param f Una función
	 * @return Un Map cuyo dominio y valores son los de la función. Este Map sólo tiene disponible el método get.
	 */
	public static <K,V> Map<K, V> newHashMap(Function<K,V> f){
		MapOfFunction<K,V> r = new Maps2.MapOfFunction<>(f);
		return r;
		
	}

	public static void main(String[] args) {
		Graph<Ciudad,Carretera> graph =  new SimpleGraph<Ciudad,Carretera>(Carretera::create);
		graph =  GraphsReader.newGraph("./ficheros/andalucia.txt",Ciudad::create, Carretera::create,graph,Carretera::getKm);
		Set<Ciudad> ciudades = graph.vertexSet();
		Function<Ciudad,Double> f = Ciudad::getHabitantes;
		Map<Ciudad,Double> habitantes = Maps2.newHashMap(x->1/x.getHabitantes());
//		ciudades.stream().forEach(x->System.out.println(x.getNombre()+","+x.getHabitantes()+","+habitantes.get(x)));
		List<Ciudad> ls = Lists2.newList(ciudades);
		System.out.println(ls);
		System.out.println(ls.get(0).getHabitantes());
		System.out.println(f.apply(ls.get(0)));
		System.out.println(ls.get(0).getNombre()+","+ls.get(0).getHabitantes()+","+habitantes.get(ls.get(0)));
		
	}
	
	
	
	private static class MapOfFunction<K,V>  extends HashMap<K,V> implements Map<K,V>{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		Function<? super K, ? extends V> f;
		
		public MapOfFunction(Function<? super K, ? extends V> f) {
			super();
			this.f = f;
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public V get(Object key){
			V r = null;
			try {
				return this.computeIfAbsent((K) key, f);
			} catch (ClassCastException e) {
				r = null;
			}
			return r;
		}

		@Override
		public int size() {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean isEmpty() {
			return false;
		}

		@Override
		public boolean containsKey(Object key) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean containsValue(Object value) {
			throw new UnsupportedOperationException();
		}

		@Override
		public V put(K key, V value) {
			throw new UnsupportedOperationException();
		}

		@Override
		public V remove(Object key) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void putAll(Map<? extends K, ? extends V> m) {
			throw new UnsupportedOperationException();
			
		}

		@Override
		public void clear() {
			throw new UnsupportedOperationException();
			
		}

		@Override
		public Set<K> keySet() {
			throw new UnsupportedOperationException();
		}

		@Override
		public Collection<V> values() {
			throw new UnsupportedOperationException();
		}

		@Override
		public Set<java.util.Map.Entry<K, V>> entrySet() {
			throw new UnsupportedOperationException();
		}	
		
	}
} 

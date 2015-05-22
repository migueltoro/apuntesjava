package us.lsi.stream;

/**
 * <p> Esta clase proporciona métodos de extensión del tipo <code> Stream> </code> proporcionado por el API de Java. 
 * La clase {@link us.lsi.stream.Stream2 Stream2} combina estos métodos junto con los proporcionados por <code> Stream> </code>
 * además de los métodos de factoría de la clase  {@link us.lsi.stream.Streams2 Streams2} </p>
 * 
 * @author Miguel Toro
 */

import com.google.common.base.Preconditions;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.*;

import com.google.common.collect.*;

import java.util.function.*;
import us.lsi.math.*;


public class StreamExtensions {

	/**
	 * @pre st no puede ser vacía
	 * @param st Un Strream
	 * @param <T> el tipo de los elementos de la secuencia
	 * @return Un elemento de st escogido  al azar
	 * 
	 */
	public static <T> T elementRandom(Stream<T> st){	    
		Preconditions.checkNotNull(st);
		List<T> r = st.collect(Collectors.<T>toList());		
		Preconditions.checkNotNull(!r.isEmpty());
		T e = r.get(0);
		if(r.size() > 1){	
			int pos = Math2.getEnteroAleatorio(0, r.size());
			e = r.get(pos);
		}
	    return e;
	}
	/**
	 * @pre st no es vacía
	 * @param st Un Stream
	 * @param <T> el tipo de los elementos de la secuencia
	 * @return Un Stream con un sólo elemento escogido al azar de entre los elementos
	 * del stream original
	 */
	public static <T> Stream<T> unitaryRandomStream(Stream<T> st) {
		Preconditions.checkNotNull(st);
		List<T> lis = st.collect(Collectors.<T> toList());
		int pos = Math2.getEnteroAleatorio(0, lis.size());
		Preconditions.checkElementIndex(pos, lis.size());
		if(lis.isEmpty())
			return Stream.empty();
		else
			return Stream.of(lis.get(pos));
	}
	
	
	
	
	/**
	 * @param s Un stream 
	 * @param pt un predicado que especifica el dominio
	 * @param <T> El tipo de los elementos de la secuencia
	 * @return Una nueva stream formada por los elementos de de s que cumplen pt 
	 * hasta que uno no lo cumple
	 */
	public static <T> Stream<T> whileIncluded(Stream<T> s, Predicate<T> pt) {	
		UnmodifiableIterator<T> um = new Streams2.IteratorWhile<T>(s.iterator(), pt);
		return StreamSupport.stream(Spliterators.spliteratorUnknownSize(um, 
				Spliterator.IMMUTABLE | Spliterator.ORDERED),false);
	}
	
	/**
	 * @param s Un stream 
	 * @param s1 Una secuencia adicional de stream
	 * @param <T> El tipo de los elementos de la secuencia
	 * @return Un stream fromado con los parámetros concatenados
	 */
	@SafeVarargs
	public static <T> Stream<T> concat(Stream<T> s, Stream<T>... s1) {
		return Stream.<T>concat(s, Arrays.stream(s1).flatMap(Function.<Stream<T>>identity()));
	}

	

	/**
	 * @param stream1 Un stream
	 * @param stream2 Un segundo stream
	 * @param f1 Una función que calcula una clave para los elementos de stream1
	 * @param f2 Una función que calcula una clave para los elementos de stream2
	 * @param fr Una función que calcula un nuevo valor a partir  de uno procedente de stream1 y otro del stream2
	 * @param <T> El tipo de los elementos de la primera secuencia
	 * @param <U> El tipo de los elementos de la segunda secuencia
	 * @param <K> El tipo de los elementos de la clave
	 * @param <R> El tipo de los elementos de la secuencia resultante
	 * @return Un stream resultado del joint de stream1 y stream2
	 */
	public static <T, U, K, R> Stream<R> joint(Stream<T> stream1,
			Stream<U> stream2,
			Function<? super T, ? extends K> f1,
			Function<? super U, ? extends K> f2, 
			BiFunction<T, U, R> fr) {

		final Map<K, List<T>> map1 = stream1.collect(Collectors.groupingBy(f1));
		final Map<K, List<U>> map2 = stream2.collect(Collectors.groupingBy(f2));

		Stream<R> r = Stream
				.concat(map1.keySet().stream(), map2.keySet().stream())
				.distinct()
				.filter(x -> map1.containsKey(x) && map2.containsKey(x))
				.flatMap(
						x -> StreamExtensions.cartesianProduct(() -> map1.get(x)
								.iterator(), () -> map2.get(x).iterator(), fr));

		return r;
	}
	
	public static <T, K1> Group1<K1, List<T>> grouping1(
			Stream<T> stream,
			Function<? super T, ? extends K1> f1) {
		Map<K1, List<T>> r = stream.collect(Collectors.groupingBy(f1));
		return Group1.create(r);
	}
		
	public static <T, K1, R> Group1<K1,R> grouping1Sort(
			Stream<T> stream,
			Function<? super T, ? extends K1> f1,
			Comparator<K1> cmp,
			Collector<T,?,R> cl) {
		Supplier<SortedMap<K1,R>> s1 = ()-> new TreeMap<>(cmp);
		SortedMap<K1,R> r = stream.collect(Collectors.groupingBy(f1,s1,cl));
		return Group1Sort.create(r);
	}
	
	public static <T, K1, R> Group1<K1,R> grouping1Sort(
			Stream<T> stream,
			Function<? super T, ? extends K1> f1,
			Collector<T,?,R> cl) {
		Supplier<SortedMap<K1,R>> s1 = ()-> new TreeMap<>();
		SortedMap<K1,R> r = stream.collect(Collectors.groupingBy(f1,s1,cl));
		return Group1Sort.create(r);
	}
	
	public static <T, K1, K2> Group2<K1, K2, List<T>> grouping2(
			Stream<T> stream,
			Function<? super T, ? extends K1> f1,
			Function<? super T, ? extends K2> f2) {
		Map<K1, Map<K2, List<T>>> r = stream.collect(Collectors.groupingBy(f1,
				Collectors.<T, K2> groupingBy(f2)));
		return Group2.create(r);
	}

	
	public static <T, K1, K2, R> Group2<K1,K2,R>  grouping2Sort(
			Stream<T> stream,
			Function<? super T, ? extends K1> f1,
			Function<? super T, ? extends K2> f2,
			Comparator<K1> cmp1,
			Comparator<K2> cmp2,
			Collector<T,?,R> cl) {
		Supplier<SortedMap<K1,SortedMap<K2,R>>> s1 = () -> new TreeMap<>(cmp1);
		Supplier<SortedMap<K2,R>> s2 = () -> new TreeMap<>(cmp2);
		SortedMap<K1,SortedMap<K2,R>> r = stream.collect(Collectors.groupingBy(f1,s1,
				Collectors.groupingBy(f2,s2,cl)));
		return Group2Sort.create(r);
	}
	
	public static <T, K1, K2, R> Group2<K1,K2,R>  grouping2Sort(
			Stream<T> stream,
			Function<? super T, ? extends K1> f1,
			Function<? super T, ? extends K2> f2,
			Collector<T,?,R> cl) {
		Supplier<SortedMap<K1,SortedMap<K2,R>>> s1 = () -> new TreeMap<>();
		Supplier<SortedMap<K2,R>> s2 = () -> new TreeMap<>();
		SortedMap<K1,SortedMap<K2,R>> r = stream.collect(Collectors.groupingBy(f1,s1,
				Collectors.groupingBy(f2,s2,cl)));
		return Group2Sort.create(r);
	}
	
	
	public static <T, K1, K2, K3> Group3<K1, K2, K3, List<T>> grouping3(
			Stream<T> stream, 
			Function<? super T, ? extends K1> f1,		
			Function<? super T, ? extends K2> f2,
			Function<? super T, ? extends K3> f3) {
		Map<K1, Map<K2, Map<K3, List<T>>>> r = stream.collect(Collectors
				.groupingBy(f1,
						Collectors.groupingBy(f2, Collectors.<T,K3>groupingBy(f3))));
		return Group3.create(r);
	}

	public static <T, K1, K2, K3, R> Group3<K1,K2,K3,R> grouping3Sort(
			Stream<T> stream, 
			Function<? super T, ? extends K1> f1,
			Function<? super T, ? extends K2> f2,
			Function<? super T, ? extends K3> f3,
			Comparator<K1> cmp1,
			Comparator<K2> cmp2,
			Comparator<K3> cmp3,
			Collector<T,?,R> cl) {
		Supplier<SortedMap<K1,SortedMap<K2,SortedMap<K3, R>>>> s1 = () -> new TreeMap<>(cmp1);
		Supplier<SortedMap<K2,SortedMap<K3, R>>> s2 =() -> new TreeMap<>(cmp2);
		Supplier<SortedMap<K3, R>> s3 = () -> new TreeMap<>(cmp3);
		SortedMap<K1,SortedMap<K2,SortedMap<K3,R>>> r = stream.collect(Collectors
				.groupingBy(f1,s1,
						Collectors.groupingBy(f2, s2,
								Collectors.groupingBy(f3,s3,cl))));
		return Group3Sort.create(r);
	}
	
	public static <T, K1, K2, K3, R> Group3<K1,K2,K3,R> grouping3Sort(
			Stream<T> stream, 
			Function<? super T, ? extends K1> f1,
			Function<? super T, ? extends K2> f2,
			Function<? super T, ? extends K3> f3,
			Collector<T,?,R> cl) {
		Supplier<SortedMap<K1,SortedMap<K2,SortedMap<K3, R>>>> s1 = () -> new TreeMap<>();
		Supplier<SortedMap<K2,SortedMap<K3, R>>> s2 =() -> new TreeMap<>();
		Supplier<SortedMap<K3, R>> s3 = () -> new TreeMap<>();
		SortedMap<K1,SortedMap<K2,SortedMap<K3,R>>> r = stream.collect(Collectors
				.groupingBy(f1,s1,
						Collectors.groupingBy(f2, s2,
								Collectors.groupingBy(f3,s3,cl))));
		return Group3Sort.create(r);
	}

	public static <T, U, R> Stream<R> cartesianProduct(
			Stream<T> s1, 
			Stream<U> s2,
			BiFunction<T, U, R> f) {
		Supplier<Iterator<T>> sp1 = () -> s1.iterator();
		List<U> ls = s2.collect(Collectors.<U>toList());
		Supplier<Iterator<U>> sp2 = () -> ls.iterator();
		return Streams2.fromUnmodifiableIterator(new Streams2.ProductoCartesiano<>(sp1,
						sp2, f));
	}
	
	public static <T, U, R> Stream<R> cartesianProduct(
			Stream<T> s1, 
			Supplier<Iterator<U>> sp2,
			BiFunction<T, U, R> f) {
		Supplier<Iterator<T>> sp1 = () -> s1.iterator();
		return Streams2.fromUnmodifiableIterator(new Streams2.ProductoCartesiano<>(sp1,
						sp2, f));
	}
	
	public static <T, U, R> Stream<R> cartesianProduct(
			Supplier<Iterator<T>> sp1,
			Supplier<Iterator<U>> sp2,
			BiFunction<T, U, R> f) {
		return Streams2.fromUnmodifiableIterator(new Streams2.ProductoCartesiano<>(sp1,
						sp2, f));
	}

	public static void toFile(Stream<String> s, String file) {
		try {
			final PrintWriter f = new PrintWriter(new BufferedWriter(
					new FileWriter(file)));
			s.forEach(x -> {
				f.println(x);
			});
			f.close();
		} catch (IOException e) {
			throw new IllegalArgumentException(
					"No se ha podido crear el fichero " + file);
		}
	}

}

package us.lsi.stream;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import us.lsi.common.Tuple2;
import us.lsi.math.Math2;

import com.google.common.base.Preconditions;

/**
 * <p> Esta clase proporciona métodos de extensión del tipo <code> Stream </code> proporcionado por el API de Java. 
 * La clase {@link us.lsi.stream.Stream2 Stream2} combina estos métodos junto con los proporcionados por <code> Stream </code>
 * además de los métodos de factoría de la clase  {@link us.lsi.stream.Streams Streams2} </p>
 * 
 * 
 * * @author Miguel Toro
 */
public class Streams {

	
	/**
	 * @param a Un entero
	 * @param b Un entero
	 * @param c Un entero
	 * @return Un stream de enteros que forman una progresión 
	 * aritmética desde a hasta b con razón c sin incluir el valor b.
	 */
	public static IntStream range(Integer a, Integer b, Integer c){
		Preconditions.checkArgument(a==b ||(b-a)*c > 0);
		Integer hasta = (b-a)/c;
		if((b-a)%c == 0){
			hasta = hasta - 1;
		} 
		return IntStream.rangeClosed(0, hasta).map(x->a+c*x);
	}
	/**
	 * @param a Un entero
	 * @param b Un entero
	 * @param c Un entero
	 * @return Un stream de enteros que forman una progresión 
	 * aritmética desde a hasta b con razón c incluyendo el valor b se es posible
	 */
	public static IntStream rangeClosed(Integer a, Integer b, Integer c){		
		Preconditions.checkArgument(a==b || (b-a)*c > 0);
		Integer hasta = (b-a)/c;
		return IntStream.rangeClosed(0, hasta).map(x->a+c*x);
	}
	/**
	 * @param a Un entero
	 * @param b Un entero
	 * @param c Un entero
	 * @return Un stream de enteros que forman una progresión 
	 * aritmética desde a hasta b con razón c sin incluir el valor b.
	 */
	public static LongStream range(Long a, Long b, Long c){
		Preconditions.checkArgument((b-a)*c > 0);
		Long hasta = (b-a)/c;
		if((b-a)%c == 0){
			hasta = hasta - 1;
		} 
		return LongStream.rangeClosed(0, hasta).map(x->a+c*x);
	}
	/**
	 * @param a Un entero
	 * @param b Un entero
	 * @param c Un entero
	 * @return Un stream de enteros que forman una progresión 
	 * aritmética desde a hasta b con razón c incluyendo el valor b se es posible
	 */
	public static LongStream rangeClosed(Long a, Long b, Long c){
		Preconditions.checkArgument((b-a)*c > 0);
		Long hasta = (b-a)/c;
		return LongStream.rangeClosed(0, hasta).map(x->a+c*x);
	}
	
	/**
	 * @param <T> Tipo de los elementos del stream
	 * @param st Un stream
	 * @return Un elemento del stream escogido aleatoriamente
	 */
	public static <T> T elementRandom(Stream<T> st){
		List<T> ls = st.collect(Collectors.toList());
		Integer n = ls.size();
		return ls.get(Math2.getEnteroAleatorio(0, n));
	}
	
	/**
	 * @param <T> Tipo de los elementos del primer stream
	 * @param <U> Tipo de los elementos del segundo stream
	 * @param <R> Tipo de los elementos del stream resultado
	 * @param s1 Un Stream
	 * @param s2 Un Segundo Stream
	 * @param f Una Bifunction que opera un elemento del primer stream con un del segundo para 
	 * dar un resultado
	 * @return El resultado de operar los pares posibles de s1 y s2 con la bifunción f
	 */
	public static <T, U, R> Stream<R> cartesianProduct(Stream<T> s1, Stream<U> s2, BiFunction<T, U, R> f) {
		List<U> ls = s2.collect(Collectors.toList());
		return s1.flatMap(x->ls.stream().map(y->f.apply(x,y)));
	}

	/**
	 * @param <T> Tipo de los elementos del primer stream
	 * @param <U> Tipo de los elementos del segundo stream
	 * @param <R> Tipo de los elementos del stream resultado
	 * @param s1 Una colección
	 * @param s2 Una segunda colección
	 * @param f Una Bifunction que opera un elemento del primer stream con un del segundo para 
	 * dar un resultado
	 * @return El resultado de operar los pares posibles de s1 y s2 con la bifunción f
	 */
	public static <T, U, R> List<R> cartesianProduct(Collection<T> s1, Collection<U> s2, BiFunction<T, U, R> f) {
		return s1.stream().<R>flatMap(x->s2.stream().map(y->f.apply(x,y))).collect(Collectors.toList());
	}

	/**
	 * @param s Una stream
	 * @param file Un fichero donde guardar los elementos de la stream
	 */
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
	
	
	
	/**
	 * @param v0 Un valor inicial
	 * @param f Una función para calcular el elemento siguiente
	 * @param pt Un predicado para comprobar que cada valor está en un dominio
	 * @param <T> El tipo de los elementos de la secuencia
	 * @return Un stream formados por valores que comienzan en v0, continuan por los siguientes valores 
	 * calculados por f mientras que se cumpla p
	 */
	public static <T> Stream<T> whilePredicate(T v0, UnaryOperator<T> f, Predicate<T> pt) {
		Stream.Builder<T> b = Stream.builder();
		Stream.iterate(v0, f).peek(x->b.accept(x)).filter(pt.negate()).findFirst();
		return b.build().filter(pt);
	}

	
	/**
	 * @param <T> Tipo de los elementos del stream
	 * @param st Un stream
	 * @param pt Un predicado
	 * @return Un stream con los primeros elementos que cumplen el predicado
	 */
	public static <T> Stream<T> whilePredicate(Stream<T> st, Predicate<T> pt) {
		Stream.Builder<T> b = Stream.builder();
		st.peek(x->b.add(x)).filter(pt.negate()).findFirst();
		return b.build().filter(pt);
	}
	
	/**
	 * @pre 
	 * @param v0 Un valor inicial
	 * @param f Una función para calcular el elemento siguiente
	 * @param pt Un predicado para comprobar que cada valor está en un dominio
	 * @param <T> El tipo de los elementos de la secuencia
	 * @return Un stream formados por valores que comienzan en v0, continuan por los siguientes valores 
	 * calculados por f hasta el primero que no cumpla p incluido
	 */
	public static <T> Stream<T> untilNotPredicateIncluded(T v0, UnaryOperator<T> f, Predicate<T> pt) {
		Stream.Builder<T> b = Stream.builder();
		Stream.iterate(v0, f).peek(x->b.add(x)).filter(pt.negate()).findFirst().get();
		return b.build();
	}

	
	/**
	 * @param <T> Tipo de los elementos del primer stream
	 * @param st Un stream
	 * @param pt Un predicado
	 * @return Un stream con los primeros elementos hasta el primero que no cumpla p incluido
	 */
	public static <T> Stream<T> untilNotPredicateIncluded(Stream<T> st, Predicate<T> pt) {
		Stream.Builder<T> b = Stream.builder();
		st.peek(x->b.add(x)).filter(pt.negate()).findFirst().get();
		return b.build();
	}
	
	/**
	 * @param file Un fichero
	 * @return Un stream formado por las líneas del fichero
	 * @exception IllegalArgumentException si no se encucntra el fichero
	 */
	
	public static Stream<String> fromFile(String file) {
		Stream<String> r = null;
		try {
			r = Files.lines(Paths.get(file), Charset.defaultCharset());
		} catch (IOException e) {
			throw new IllegalArgumentException(
					"No se ha encontrado el fichero " + file);
		}
		return r;
	}
	/**
	 * @return Un stream formado por las líneas escritas en la consola
	 */
	public static Stream<String> fromConsole() {
		BufferedReader f = new BufferedReader(new InputStreamReader(System.in));
		return f.lines();
	}

	/**
	 * @param s Un String
	 * @param delim Unos delimitadores
	 * @return Un stream formado por los elementos resultantes de separar el String original
	 * por los delimitadores
	 */
	public static Stream<String> fromString(String s, String delim) {
		String[] r = s.split(delim);
		return Arrays.stream(r).<String> map(x -> x.trim())
				.filter(x-> x.length() > 0);
	}

	
	/**
	 * @param sm Un String
	 * @param <E> El tipo de los elementos de la secuencia
	 * @return Un stream formado por los pares de elementos formados por un elemento y el siguiente en sm
	 */
	public static <E> Stream<Tuple2<E,E>> toPairStream(Stream<E> sm) {
		List<E> ls = new ArrayList<>();
		ls.add(null);
		Stream<Tuple2<E,E>> r = sm
					.map(e->Tuple2.create(ls.get(0), e))	
					.peek(p-> ls.set(0,p.getV2()))
					.filter(p-> p.getV1()!=null);
		return r;
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

		Set<K> sk = new HashSet<K>(map1.keySet());
		sk.retainAll(map2.keySet());
		Stream<R> r = sk.stream()
				.<R>flatMap(x -> Streams.cartesianProduct(map1.get(x), map2.get(x), fr).stream());
		return r;
	}
	
	/**
	 * @param <T> Tipo de los elementos del stream
	 * @param <K1> Tipo de los elementos de las claves
	 * @param stream Un stream
	 * @param f1 Un función de agrupación
	 * @return Un grupo de nivel 1
	 */
	public static <T, K1> Group1<K1, List<T>> grouping1(
			Stream<T> stream,
			Function<? super T, ? extends K1> f1) {
		Map<K1, List<T>> r = stream.collect(Collectors.groupingBy(f1));
		return Group1.create(r);
	}
		
	/**
	 * @param <T> Tipo de los elementos del stream
	 * @param <K1> Tipo de las claves
	 * @param <R> Tipo de las imagenes
	 * @param stream Un stream
	 * @param f1 Una función de agrupación
	 * @param cmp Un orden
	 * @param cl Un acumulador adicional para poder acumular cada uno de los grupos
	 * @return Un grupo ordenado de nivel 1
	 */
	public static <T, K1, R> Group1Sort<K1,R> grouping1Sort(
			Stream<T> stream,
			Function<? super T, ? extends K1> f1,
			Comparator<K1> cmp,
			Collector<T,?,R> cl) {
		Supplier<SortedMap<K1,R>> s1 = ()-> new TreeMap<>(cmp);
		SortedMap<K1,R> r = stream.collect(Collectors.groupingBy(f1,s1,cl));
		return Group1Sort.create(r);
	}
	/**
	 * @param <T> Tipo de los elementos del stream
	 * @param <K1> Tipo de las claves
	 * @param <R> Tipo de las imagenes
	 * @param stream Un stream
	 * @param f1 Una función de agrupación
	 * @param cl Un acumulador adicional para poder acumular cada uno de los grupos
	 * @return Un grupo ordenado de nivel 1
	 */
	public static <T, K1, R> Group1Sort<K1,R> grouping1Sort(
			Stream<T> stream,
			Function<? super T, ? extends K1> f1,
			Collector<T,?,R> cl) {
		Supplier<SortedMap<K1,R>> s1 = ()-> new TreeMap<>();
		SortedMap<K1,R> r = stream.collect(Collectors.groupingBy(f1,s1,cl));
		return Group1Sort.create(r);
	}
	
	/**
	 * @param <T> Tipo de los elementos del stream
	 * @param <K1> Tipo de las claves del primer grupo
	 * @param <K2> Tipo de las claves del segundo grupo
	 * @param stream Un stream
	 * @param f1 Una función para agrupar el primer nivel
	 * @param f2 Una función para agrpar el segundo nivel
	 * @return Un grupo de nivel 2
	 */
	public static <T, K1, K2> Group2<K1, K2, List<T>> grouping2(
			Stream<T> stream,
			Function<? super T, ? extends K1> f1,
			Function<? super T, ? extends K2> f2) {
		Map<K1, Map<K2, List<T>>> r = stream.collect(Collectors.groupingBy(f1,
				Collectors.<T, K2> groupingBy(f2)));
		return Group2.create(r);
	}

	
	/**
	 * @param <T> Tipo de los elementos del primer stream
	 * @param <K1> Tipo de las claves del primer grupo
	 * @param <K2> Tipo de las claves del segundo grupo
	 * @param <R> Tipo de las imagenes
	 * @param stream Un stream
	 * @param f1 Una función para agrupar el primer nivel
	 * @param f2 Una función para agrupar el segundo nivel
	 * @param cmp1 Un orden para las claves del primer nivel
	 * @param cmp2 Un orden para las claves del segundo nivel
	 * @param cl Un acumulador adicional para el segundo nivel
	 * @return Un  grupo ordenado de segundo nivel
	 */
	public static <T, K1, K2, R> Group2Sort<K1,K2,R>  grouping2Sort(
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
	
	/**
	 * @param <T> Tipo de los elementos del primer stream
	 * @param <K1> Tipo de las claves del primer grupo
	 * @param <K2> Tipo de las claves del segundo grupo
	 * @param <R> Tipo de las imagenes
	 * @param stream Un stream
	 * @param f1 Una función para agrupar el primer nivel
	 * @param f2 Una función para agrupar el segundo nivel
	 * @param cl Un acumulador adicional para el segundo nivel
	 * @return Un  grupo ordenado de segundo nivel
	 */
	public static <T, K1, K2, R> Group2Sort<K1,K2,R>  grouping2Sort(
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
	
	/**
	 * @param <T> Tipo de los elementos del primer stream
	 * @param <K1> Tipo de las claves del primer grupo
	 * @param <K2> Tipo de las claves del segundo grupo
	 * @param <K3> Tipo de las claves del tercer grupo
	 * @param stream Un stream
	 * @param f1 Una función para agrupar el primer nivel
	 * @param f2 Una función para agrupar el segundo nivel
	 * @param f3 Una función para agrupar el tercer nivel
	 * @return Un  grupo ordenado de segundo nivel
	 */
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
	
	/**
	 * @param <T> Tipo de los elementos del primer stream
	 * @param <K1> Tipo de las claves del primer grupo
	 * @param <K2> Tipo de las claves del segundo grupo
	 * @param <K3> Tipo de las claves del tercer grupo
	 * @param <R> Tipo de las imagenes
	 * @param stream Un stream
	 * @param f1 Una función para agrupar el primer nivel
	 * @param f2 Una función para agrupar el segundo nivel
	 * @param f3 Una función para agrupar el tercer nivel
	 * @param cmp1 Un orden para las claves del primer nivel
	 * @param cmp2 Un orden para las claves del segundo nivel
	 * @param cmp3 Un orden para las claves del tercer nivel
	 * @param cl Un acumulador adicional para el tercer nivel
	 * @return Un  grupo ordenado de tercer nivel
	 */
	public static <T, K1, K2, K3, R> Group3Sort<K1,K2,K3,R> grouping3Sort(
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
	/**
	 * @param <T> Tipo de los elementos del primer stream
	 * @param <K1> Tipo de las claves del primer grupo
	 * @param <K2> Tipo de las claves del segundo grupo
	 * @param <K3> Tipo de las claves del tercer grupo
	 * @param <R> Tipo de las imagenes
	 * @param stream Un stream
	 * @param f1 Una función para agrupar el primer nivel
	 * @param f2 Una función para agrupar el segundo nivel
	 * @param f3 Una función para agrupar el tercer nivel
	 * @param cl Un acumulador adicional para el tercer nivel
	 * @return Un  grupo ordenado de tercer nivel
	 */
	public static <T, K1, K2, K3, R> Group3Sort<K1,K2,K3,R> grouping3Sort(
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
	
	
}

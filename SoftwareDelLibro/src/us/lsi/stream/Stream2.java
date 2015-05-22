package us.lsi.stream;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.SortedSet;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import java.util.function.UnaryOperator;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;


import us.lsi.common.Entry;
import us.lsi.common.Par;

import com.google.common.collect.Iterators;
import com.google.common.collect.Multiset;
import com.google.common.collect.UnmodifiableIterator;

/**
 * @author Miguel Toro
 *
 * @param <T> El tipo de los elementos del stream
 */
public class Stream2<T> implements Stream<T> {

	
	protected Stream<T> st;
	
	
	
	public static <T> Stream2<T> create(Stream<T> st) {
		return new Stream2<T>(st);
	}


	protected Stream2(Stream<T> st) {
		super();
		this.st = st;
	}

	
	@Override
	public Iterator<T> iterator() {
		return st.iterator();
	}


	@Override
	public Spliterator<T> spliterator() {
		return st.spliterator();
	}


	@Override
	public boolean isParallel() {
		return st.isParallel();
	}


	@Override
	public Stream2<T> sequential() {
		return Stream2.create(st.sequential());
	}


	@Override
	public Stream2<T> parallel() {
		return Stream2.create(st.parallel());
	}


	@Override
	public Stream2<T> unordered() {
		return Stream2.create(st.unordered());
	}


	@Override
	public Stream2<T> filter(Predicate<? super T> predicate) {
		return Stream2.create(st.filter(predicate));
	}


	@Override
	public <R> Stream2<R> map(Function<? super T, ? extends R> mapper) {
		return Stream2.create(st.map(mapper));
	}


	@Override
	public IntStream mapToInt(ToIntFunction<? super T> mapper) {
		return st.mapToInt(mapper);
	}


	@Override
	public LongStream mapToLong(ToLongFunction<? super T> mapper) {
		return st.mapToLong(mapper);
	}


	@Override
	public DoubleStream mapToDouble(ToDoubleFunction<? super T> mapper) {
		return st.mapToDouble(mapper);
	}


	@Override
	public <R> Stream2<R> flatMap(
			Function<? super T, ? extends Stream<? extends R>> mapper) {
		return Stream2.create(st.flatMap(mapper));
	}


	@Override
	public IntStream flatMapToInt(
			Function<? super T, ? extends IntStream> mapper) {
		return st.flatMapToInt(mapper);
	}


	@Override
	public LongStream flatMapToLong(
			Function<? super T, ? extends LongStream> mapper) {
		return st.flatMapToLong(mapper);
	}


	@Override
	public DoubleStream flatMapToDouble(
			Function<? super T, ? extends DoubleStream> mapper) {
		return st.flatMapToDouble(mapper);
	}


	@Override
	public Stream2<T> distinct() {
		return Stream2.create(st.distinct());
	}


	@Override
	public Stream2<T> sorted() {
		return Stream2.create(st.sorted());
	}


	@Override
	public Stream2<T> sorted(Comparator<? super T> comparator) {
		return Stream2.create(st.sorted(comparator));
	}


	@Override
	public Stream2<T> peek(Consumer<? super T> consumer) {
		return Stream2.create(st.peek(consumer));
	}


	@Override
	public Stream2<T> limit(long maxSize) {
		return Stream2.create(st.limit(maxSize));
	}

	@Override
	public void forEach(Consumer<? super T> action) {
		st.forEach(action);
	}


	@Override
	public void forEachOrdered(Consumer<? super T> action) {
		st.forEachOrdered(action);
	}


	@Override
	public Object[] toArray() {
		return st.toArray();
	}


	@Override
	public <A> A[] toArray(IntFunction<A[]> generator) {
		return st.toArray(generator);
	}


	@Override
	public T reduce(T identity, BinaryOperator<T> accumulator) {
		return st.reduce(identity, accumulator);
	}


	@Override
	public Optional<T> reduce(BinaryOperator<T> accumulator) {
		return st.reduce(accumulator);
	}


	@Override
	public <U> U reduce(U identity, BiFunction<U, ? super T, U> accumulator,
			BinaryOperator<U> combiner) {
		return st.reduce(identity, accumulator, combiner);
	}


	@Override
	public <R> R collect(Supplier<R> resultFactory,
			BiConsumer<R, ? super T> accumulator, BiConsumer<R, R> combiner) {
		return st.collect(resultFactory, accumulator, combiner);
	}


	@Override
	public <R, A> R collect(Collector<? super T, A, R> collector) {
		return st.collect(collector);
	}


	@Override
	public Optional<T> min(Comparator<? super T> comparator) {
		return st.min(comparator);
	}


	@Override
	public Optional<T> max(Comparator<? super T> comparator) {
		return st.max(comparator);
	}


	@Override
	public long count() {
		return st.count();
	}


	@Override
	public boolean anyMatch(Predicate<? super T> predicate) {
		return st.anyMatch(predicate);
	}


	@Override
	public boolean allMatch(Predicate<? super T> predicate) {
		return st.allMatch(predicate);
	}


	@Override
	public boolean noneMatch(Predicate<? super T> predicate) {
		return st.noneMatch(predicate);
	}


	@Override
	public Optional<T> findFirst() {
		return st.findFirst();
	}


	@Override
	public Optional<T> findAny() {
		return st.findAny();
	}

	
	/**
	 * 
	 * @param <E> El tipo de los elementos del stream
	 * @return Un StreamBuilder
	 */
	public static <E> Stream.Builder<E> builder() {
		return Stream.builder();
	}
	
	/**
	 * 
	 * @param <E> El tipo de los elementos del stream
	 * @return Un stream vacío
	 */
	public static <E> Stream2<E> empty() {
		return Stream2.<E>create(Stream.<E>empty());
	}
	
	/**
	 * 
	 * @param t Un valor de tipo T
	 * @param <E> El tipo de los elementos del stream
	 * @return Un stream con un elmento
	 */
	public static <E> Stream2<E> of(E t) {
		return Stream2.<E>create(Stream.<E>of(t));
	}
	
	/**
	 * 
	 * @param values Valores de tipo T
	 * @param <E> El tipo de los elementos del stream
	 * @return Un stream con los valores en values
	 */
	@SafeVarargs
	public static <E> Stream2<E> of(E... values) {
		return Stream2.<E>create(Stream.<E>of(values));
	}
	
	/**
	 * 
	 * 
	 * @param seed El valor inicial
	 * @param f Un operador unario
	 * @param <E> El tipo de los elementos del stream
	 * @return Un stream con los valores generados por el operador al ser aplicado sucesivamente a seed
	 */
	public static <E> Stream2<E> iterate(E seed, UnaryOperator<E> f) {
		return Stream2.<E>create(Stream.<E>iterate(seed,f));
	}
	
	/**
	 * 
	 * @param s Un supplier
	 * @param <E> El tipo de los elementos del stream
	 * @return Un stream con los valores sucesivamente proporcionados por s
	 */
	public static <E> Stream2<E> generate(Supplier<E> s) {
		return Stream2.<E>create(Stream.<E>generate(s));
	}

	/**
	 * @pre a == b o (b- a) c &gt; 0
	 * @param a Un entero
	 * @param b Un entero
	 * @param c Un entero
	 * @return Un stream formado por los enteros a + c i con 0 &le; i   y a + c i &lt; b
	 */
	public static IntStream range(Integer a, Integer b, Integer c){
		return Streams2.range(a, b, c);
	}
	
	/**
	 * @pre a == b o (b- a) c &gt; 0
	 * @param a Un entero
	 * @param b Un entero
	 * @param c Un entero
	 * @return Un stream formado por los enteros a + c i con 0 &le; i   y a + c i &le; b
	 */
	public static IntStream rangeClosed(Integer a, Integer b, Integer c){
		return Streams2.rangeClosed(a, b, c);
	}
	
	/**
	 * @pre (b- a) &ge; 0
	 * @param a Un entero
	 * @param b Un entero
	 * @return Un stream formado por los enteros a, a+1, a+2, ... hasta b sin incluir
	 */
	public static IntStream range(Integer a, Integer b){
		return Streams2.range(a, b, 1);
	}
	/**
	 * @pre a == b o (b- a) c &gt; 0
	 * @param a Un entero
	 * @param b Un entero
	 * @param c Un entero
	 * @return Un stream formado por los enteros a + c i con 0 &le; i   y a + c i &lt; b
	 */
	public static LongStream range(Long a, Long b, Long c){
		return Streams2.range(a, b, c);
	}
	/**
	 * @pre a == b o (b- a) c &gt; 0
	 * @param a Un entero
	 * @param b Un entero
	 * @param c Un entero
	 * @return Un stream formado por los enteros a + c i con 0 &le; i   y a + c i &le; b
	 */
	public static LongStream rangeClosed(Long a, Long b, Long c){
		return Streams2.rangeClosed(a, b, c);
	}
	/**
	 * @pre (b- a) &ge; 0
	 * @param a Un entero
	 * @param b Un entero
	 * @return Un stream formado por los enteros a, a+1, a+2, ... hasta b sin incluir
	 */
	public static LongStream range(Long a, Long b){
		return Streams2.range(a, b, 1L);
	}
	
	/**
	 * @param file Un fichero de entrada
	 * @return Un stream con las líneas del fichero
	 */
	public static Stream2<String> fromFile(String file){
		return Stream2.create(Streams2.fromFile(file));
	}
	
	/**
	 * @param s Un String
	 * @param delim Un conjunto de delimitadores o un aexpresión regular en general
	 * @return Un stream con los elementos de s separados por los delimitadores
	 * 
	 */
	public static Stream2<String> fromString(String s, String delim){
		return Stream2.create(Streams2.fromString(s,delim));
	}
	/**
	 * @param it Un Iterable
	 * @param <T> El tipo de los elementos de la secuencia
	 * @return Un Stream construido a partir del iterable
	 */
	public static <T> Stream2<T> fromUnmodifiableIterable(Iterable<T> it) {
		return Stream2.fromUnmodifiableIterator(it.iterator());
	}

	/**
	 * @param it Un Iterator
	 * @param <T> El tipo de los elementos de del iterador
	 * @return Un Stream construido a partir del iterador
	 */
	public static <T> Stream2<T> fromUnmodifiableIterator(Iterator<T> it) {
		UnmodifiableIterator<T> um = Iterators.unmodifiableIterator(it);
		return Stream2.create(StreamSupport.stream(Spliterators.spliteratorUnknownSize(um,
				Spliterator.IMMUTABLE | Spliterator.ORDERED),false));
	}
	
	/**
	 * @pre this no puede ser vacía
	 * @return Un elemento de this escogido  al azar
	 * 
	 */
	public T elementRandom()  {
		return StreamExtensions.elementRandom(this);
	}
	
	/**
	 * @pre this no es vacía
	 * @return Un Stream con un sólo elemento escogido al azar de entre los elementos
	 * del stream original
	 */
	public Stream2<T> unitaryRandomStream(){
		return  Stream2.create(StreamExtensions.unitaryRandomStream(this));
	}
	
	
	/**
	 * @param st Un stream
	 * @return La concatenación de this con st
	 */
	public Stream2<T> concat(Stream2<T> st){
		return  Stream2.create(Stream.concat((Stream<T>)this,(Stream<T>)st));
	}
	
	/**
	 * @return Una lista con los elementos del stream
	 */
	public List<T> toList() {
		return this.collect(Collectors.<T>toList());
	}
	
	/**
	 * @return Un conjunto con los elementos del stream
	 */
	public Set<T> toSet() {
		return this.collect(Collectors.<T>toSet());
	}
	
	/**
	 * @param cmp Un orden sobre los elementos de tipo T
	 * @return Un conjunto ordenado según cmp que contiene los elementos de this
	 */
	public SortedSet<T> toSortedSet(Comparator<T> cmp) {
		return this.collect(Collectors2.<T>toSortedSet(cmp));
	}
	
	/**
	 * @return Un Multiconjunto que contiene los elementos de this
	 */
	public Multiset<T> toMultiSet() {
		return this.collect(Collectors2.<T>toMultiset());
	}
	
	/**
	 * @param pt Un predicado sobre elementos de tipo T
	 * @return Una substream de this que contiene elementos de this que verifican pt. 
	 * El stream devuelto acaba cuando se encuentra un elemento que no verifica pt. Ese elemento no se incluye.
	 */
	public Stream2<T> whileIncluded(Predicate<T> pt) {
		UnmodifiableIterator<T> um = new Streams2.IteratorWhile<T>(this.iterator(), pt);
		return Stream2.create(StreamSupport.stream(
				Spliterators.spliteratorUnknownSize(um, Spliterator.IMMUTABLE | Spliterator.ORDERED),false));
	}
	
	
	/**
	 * @param s2 Un stream
	 * @param f Una bifunción
	 * @param <U> El tipo de los elementos de s2
	 * @param <R> El tipo de los elementos del stream resultante
	 * @return Un stream formado por los valores  calculados por la bifunción a partir de los pares formados 
	 * por un elemento de this y otro de s2
	 */
	public <U, R> Stream2<R> cartesianProduct(Stream<U> s2, BiFunction<T, U, R> f){
		return Stream2.create(StreamExtensions.cartesianProduct(this, s2, f));
	}

	/**
	 * @param s2 Un stream
	 * @param f1 Una función
	 * @param f2 Una función
	 * @param fr Una bifunción
	 * @param <U> El tipo de los elementos de s2
	 * @param <K> El tipo de las claves
	 * @param <R> El tipo de los elementos del stream resultante
	 * @return Un stream formado por los valores calculados por fr a partir de los pares de elementos, 
	 * en this y s2 respectivamente, que tienen la misma clave calculada por f1 y f2 respectivamente
	 */
	public <U, K, R> Stream2<R> joint(Stream<U> s2,Function<? super T, ? extends K> f1,
			Function<? super U, ? extends K> f2, 
			BiFunction<T, U, R> fr){
		return Stream2.create(StreamExtensions.joint(this, s2, f1, f2, fr));
	}
	
	/**
	 * @param f1 Una función para calcular la clave
	 * @param <K1> El tipo de la claves
	 * @return Un grupo de nivel 1 formado según las claves calculadas por f1
	 */
	public <K1> Group1<K1, List<T>> groupBy1(Function<? super T, ? extends K1> f1){
		return StreamExtensions.grouping1(this, f1);
	}
	
	/**
	 * @param f1 Una función para calcular las claves
	 * @param cmp Un orden sobre las claves
	 * @param cl Un mecanismo para agrupar elementos de tipo T en otro de tipo R
	 * @param <K1> El tipo de la claves
	 * @param <R> El tipo de los valores asociados a las claves
	 * @return Un grupo de nivel 1 con claves ordenadas según cmp y los grupos con la misma clave agrupados según cl
	 */
	public  <K1, R> Group1<K1,R> groupBy1(Function<? super T, ? extends K1> f1, Comparator<K1> cmp,
			Collector<T,?,R> cl) {
		return StreamExtensions.grouping1Sort(this, f1, cmp, cl);
	}
	
	/**
	 * @param f1 Una función para calcular las claves
	 * @param cl Un mecanismo para agrupar elementos de tipo T en otro de tipo R
	 * @param <K1> El tipo de la claves
	 * @param <R> El tipo de los valores resultantes asociados a las claves
	 * @return Un grupo de nivel 1 y los grupos con la misma clave agrupados según cl
	 */
	public <K1, R> Group1<K1,R> groupBy1(Function<? super T, ? extends K1> f1, Collector<T,?,R> cl){
		return StreamExtensions.grouping1Sort(this, f1, cl);
	}
	
	/**
	 * @param f1 Una función para calcular la primera clave
	 * @param f2 Una función para calcular la segunda clave
	 * @param <K1> El tipo de la primera clave
	 * @param <K2> El tipo de la segunda clave
	 * @return Un grupo de nivel 2 formado según las claves calculadas por f1 y f2
	 */
	public <K1, K2> Group2<K1, K2, List<T>> groupBy2(Function<? super T, ? extends K1> f1,
			Function<? super T, ? extends K2> f2){
		return StreamExtensions.grouping2(this, f1, f2);
	}
	
	/**
	 * @param f1 Una función para calcular la primera clave
	 * @param f2 Una función para calcular la segunda clave
	 * @param cmp1 Un orden para la primera clave
	 * @param cmp2 Un orden para la segunda clave
	 * @param cl Un mecanismo para agrupar elementos de tipo T en otro de tipo R
	 * @param <K1> El tipo de la claves
	 * @param <K2> El tipo de la claves
	 * @param <R> El tipo de los valores resultantes asociados a las claves
	 * @return Un grupo de nivel 2 con claves ordenadas según cmp1 y cmp2 y los grupos con las mismas claves agrupados según cl
	 */
	public <K1, K2, R> Group2<K1,K2,R>  groupBy2(Function<? super T, ? extends K1> f1,
			Function<? super T, ? extends K2> f2,
			Comparator<K1> cmp1,
			Comparator<K2> cmp2,
			Collector<T,?,R> cl){
		return StreamExtensions.grouping2Sort(this, f1, f2, cmp1, cmp2, cl);
	}
	
	/**
	 * @param f1 Una función para calcular la primera clave
	 * @param f2 Una función para calcular la segunda clave
	 * @param cl Un mecanismo para agrupar elementos de tipo T en otro de tipo R
	 * @param <K1> El tipo de la claves
	 * @param <K2> El tipo de la claves
	 * @param <R> El tipo de los valores resultantes asociados a las claves
	 * @return Un grupo de nivel 2 y los grupos con las mismas claves agrupados según cl
	 */
	public <K1, K2, R> Group2<K1,K2,R>  groupBy2(Function<? super T, ? extends K1> f1,
			Function<? super T, ? extends K2> f2,
			Collector<T,?,R> cl) {
		return StreamExtensions.grouping2Sort(this, f1, f2, cl);
	}
	
	/**
	 * @param f1 Una función para calcular la primera clave
	 * @param f2 Una función para calcular la segunda clave
	 * @param f3 Una función para calcular la tercera clave
	 * @param <K1> El tipo de la primera clave
	 * @param <K2> El tipo de la segunda clave
	 * @param <K3> El tipo de la tercera clave
	 * @return Un grupo de nivel 3 formado según las claves calculadas por f1, f2 y f3
	 */
	public <K1, K2, K3> Group3<K1, K2, K3, List<T>> groupBy3(Function<? super T, ? extends K1> f1,		
			Function<? super T, ? extends K2> f2,
			Function<? super T, ? extends K3> f3) {
		return StreamExtensions.grouping3(this, f1, f2, f3);
	}
	
	/**
	 * @param f1 Una función para calcular la primera clave
	 * @param f2 Una función para calcular la segunda clave
	 * @param f3 Una función para calcular la tercera clave
	 * @param cmp1 Un orden para la primera clave
	 * @param cmp2 Un orden para la segunda clave
	 * @param cmp3 Un orden para la tercera clave
	 * @param cl Un mecanismo para agrupar elementos de tipo T en otro de tipo R
	 * @param <K1> El tipo de la primera clave
	 * @param <K2> El tipo de la segunda clave
	 * @param <K3> El tipo de la tercera clave
	 * @param <R> El tipo de de los elementos del stream resultante
	 * @return Un grupo de nivel 3, con las claves ordenadas según  cmp1, cmp2 y cmp3 respectivamente, 
	 * y formado según las claves calculadas por f1, f2 y f3
	 */
	public <K1, K2, K3, R> Group3<K1,K2,K3,R> groupBy3(Function<? super T, ? extends K1> f1,
			Function<? super T, ? extends K2> f2,
			Function<? super T, ? extends K3> f3,
			Comparator<K1> cmp1,
			Comparator<K2> cmp2,
			Comparator<K3> cmp3,
			Collector<T,?,R> cl){
		return StreamExtensions.grouping3Sort(this, f1, f2, f3, cmp1, cmp2, cmp3, cl);
	}
	/**
	 * @param f1 Una función para calcular la primera clave
	 * @param f2 Una función para calcular la segunda clave
	 * @param f3 Una función para calcular la tercera clave
	 * @param cl Un mecanismo para agrupar elementos de tipo T en otro de tipo R
	 * @param <K1> El tipo de la primera clave
	 * @param <K2> El tipo de la segunda clave
	 * @param <K3> El tipo de la tercera clave
	 * @param <R> El tipo de de los elementos resultantes asociados a las claves
	 * @return Un grupo de nivel 3, con las claves ordenadas según  cmp1, cmp2 y cmp3 respectivamente, 
	 * y formado según las claves calculadas por f1, f2 y f3
	 */
	public <K1, K2, K3, R> Group3<K1,K2,K3,R> groupBy3(Function<? super T, ? extends K1> f1,
			Function<? super T, ? extends K2> f2,
			Function<? super T, ? extends K3> f3,
			Collector<T,?,R> cl){
		return StreamExtensions.grouping3Sort(this, f1, f2, f3, cl);
	}
	
	/**
	 * @param f Una  función
	 * @return Un stream de String
	 */
	public StrStream mapToStr(Function<T,String> f){
		return new StrStream(this.<String>map(f));
	}
	
	/**
	 * @param f1 Una función para calcular la clave
	 * @param f2 Una función para calcular el valor
	 * @param <K> El tipo de la clave
	 * @param <V> El tipo del valor
	 * @return Un stream de pares clave-valor
	 */
	public <K,V> EntryStream<K,V> mapToEntry(Function<? super T, ? extends K> f1,
			Function<? super T, ? extends V> f2){
		return new EntryStream<K,V>(this.<Entry<K,V>>map(x->Entry.<K,V>create(f1.apply(x),f2.apply(x))));
				
	}

	
	/**
	 * 
	 * @return Un stream de pares de valores consecutivos en st
	 */
	public Stream<Par<T,T>> toParStream(){
		return Streams2.toParStream(this);
				
	}


	@Override
	public void close() {
		st.close();
	}


	@Override
	public Stream<T> onClose(Runnable r) {
		return st.onClose(r);
	}


	@Override
	public Stream<T> skip(long n) {
		return st.skip(n);
	}
	
	
}

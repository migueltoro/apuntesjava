package us.lsi.stream;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import us.lsi.common.Par;

import com.google.common.base.Preconditions;
import com.google.common.collect.Iterators;
import com.google.common.collect.UnmodifiableIterator;

/**
 * <p> Esta clase proporciona métodos de extensión del tipo <code> Stream </code> proporcionado por el API de Java. 
 * La clase {@link us.lsi.stream.Stream2 Stream2} combina estos métodos junto con los proporcionados por <code> Stream </code>
 * además de los métodos de factoría de la clase  {@link us.lsi.stream.Streams2 Streams2} </p>
 * 
 * 
 * * @author Miguel Toro
 */
public class Streams2 {

	
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
	 * @param it Un Iterable
	 * @param <T> El tipo de los elementos de la secuencia
	 * @return Un Stream construido a partir del iterable
	 */
	public static <T> Stream<T> fromUnmodifiableIterable(Iterable<T> it) {
		return Streams2.fromUnmodifiableIterator(it.iterator());
	}

	/**
	 * @param it Un Iterator
	 * @param <T> El tipo de los elementos de del iterador
	 * @return Un Stream construido a partir del iterador
	 */
	public static <T> Stream<T> fromUnmodifiableIterator(Iterator<T> it) {
		UnmodifiableIterator<T> um = Iterators.unmodifiableIterator(it);
		return StreamSupport.stream(Spliterators.spliteratorUnknownSize(um,
				Spliterator.IMMUTABLE | Spliterator.ORDERED), false);
	}
	
	/**
	 * @param v0 Un valor inicial
	 * @param f Una función para calcular el elemento siguiente
	 * @param pt Un predicado para comprobar que cada valor está en un dominio
	 * @param <T> El tipo de los elementos de la secuencia
	 * @return Un stream formados por valores que comienzan en v0, continuan por los siguientes valores 
	 * calculados por f mientras que se cumpla p
	 */
	public static <T> Stream<T> whileIncluded(T v0, UnaryOperator<T> f,
			Predicate<T> pt) {
		UnmodifiableIterator<T> um = new Streams2.IteratorWhile<T>(Stream.iterate(v0, f)
				.iterator(), pt);
		return StreamSupport.stream(Spliterators.spliteratorUnknownSize(um,
				Spliterator.IMMUTABLE | Spliterator.ORDERED), false);
	}
	
	/**
	 * @param file Un fichero
	 * @return Un stream formado por las líneas del fichero
	 * @exception IllegalArgumentException si no se encucntra el fichero
	 */
	@SuppressWarnings("resource")
	public static Stream<String> fromFile(String file) {
		BufferedReader f = null;
		try {
			f = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException(
					"No se ha encontrado el fichero " + file);
		}
		return f.lines();
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
		return Arrays.<String> stream(r).<String> map((String x) -> x.trim())
				.filter((String x) -> x.length() > 0);
	}

	
	/**
	 * @param sm Un String
	 * @param <E> El tipo de los elementos de la secuencia
	 * @return Un stream formado por los pares de elementos formados por un elemento y el siguiente en sm
	 */
	public static <E> Stream<Par<E,E>> toParStream(Stream<E> sm) {
		Object[] ls = {null};
		@SuppressWarnings("unchecked")
		Stream<Par<E,E>> r = 
				sm.map(e->Par.<E,E>create((E)ls[0], e))
				  .peek(p-> ls[0]=p.getP2())
				  .filter(p-> p.getP1()!=null);
		return r;
	}
	
	
	static class IteratorWhile<T> extends UnmodifiableIterator<T>
			implements Iterator<T> {
	
		private final Iterator<T> iterator;
		private final Predicate<T> predicate;
		private T estado = null;
	
		public IteratorWhile(Iterator<T> iterator, Predicate<T> predicate) {
			super();
			this.iterator = iterator;
			this.predicate = predicate;
			if (iterator.hasNext()) {
				estado = iterator.next();
			}
	
		}
	
		@Override
		public boolean hasNext() {
			return estado != null && predicate.test(estado);
		}
	
		@Override
		public T next() {
			T old = estado;
			if (iterator.hasNext()) {
				estado = iterator.next();
			}
			return old;
		}
	
	}

	

	static class ProductoCartesiano<T, U, R> extends
				UnmodifiableIterator<R> implements Iterator<R> {
	
	//		private final Supplier<Iterator<T>> site1;
			private final Supplier<Iterator<U>> site2;
			private final Iterator<T> ite1;
			private Iterator<U> ite2;
			private final BiFunction<T, U, R> f;
			private T t1 = null;
			private U t2 = null;
	
			public ProductoCartesiano(Supplier<Iterator<T>> site1,
					Supplier<Iterator<U>> site2, BiFunction<T, U, R> f) {
				super();
	//			this.site1 = site1;
				this.site2 = site2;
				this.ite1 = site1.get();
				this.ite2 = site2.get();
				this.f = f;
				if (ite1.hasNext()) {
					t1 = ite1.next();
				}
				if (ite2.hasNext()) {
					t2 = ite2.next();
				}
				Preconditions.checkArgument(t1 != null && t2 != null,
						"Los iteradores deben ser no vacíos");
			}
	
			@Override
			public boolean hasNext() {
				// TODO Auto-generated method stub
				return t1 != null && t2 != null;
			}
	
			@Override
			public R next() {
				// TODO Auto-generated method stub
				T oldT1 = t1;
				U oldT2 = t2;
				if (ite2.hasNext()) {
					t2 = ite2.next();
				} else if (ite1.hasNext()) {
					t1 = ite1.next();
					ite2 = site2.get();
					t2 = ite2.next();
				} else {
					t1 = null;
					t2 = null;
				}
				return f.apply(oldT1, oldT2);
			}
	
		}

		static class ToIterable<E> implements Iterable<E> {
			private Iterator<E> it;

			public ToIterable(Iterator<E> it) {
				super();
				this.it = it;
			}

			@Override
			public Iterator<E> iterator() {
				return it;
			}

			@Override
			public void forEach(Consumer<? super E> action) {
				// TODO Auto-generated method stub
				
			}
			
			
		}
	  
}

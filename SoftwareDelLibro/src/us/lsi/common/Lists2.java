package us.lsi.common;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import us.lsi.math.Math2;
import us.lsi.stream.Stream2;

import com.google.common.collect.*;

public class Lists2 {
	
	/**
	 * @param <E> tipo de los elementos de la lista
	 * @param ls Una lista
	 * @return Un conjunto formado por los elementos de la lista
	 */
	public static <E> Set<E> toSet(List<E> ls){
		return ls.stream().collect(Collectors.toSet());
	}
	/**
	 * @param <E> tipo de los elementos de la lista
	 * @param ls Una lista
	 * @pos La lista queda con el último elemento eliminado
	 * @pre la lista no puede estar vacía
	 * @return El último elemento eliminado
	 */
	public static <E> E removeLast(List<E> ls){
		int last = ls.size()-1;
		E e = ls.remove(last);
		return e;
	}
	
	/**
	 * @param <E> tipo de los elementos de la lista
	 * @param ls Una lista
	 * @param e Un elemento
	 * @pos La lista queda con e añadido en primer lugar
	 */
	public static <E> void addFirst(List<E> ls, E e){
		ls.add(0,e);
	}
	
	/**
	 * @param <E> Tipo de los elementos
	 * @param lis Una lista 
	 * @return Un array equivalente a la lista
	 */
	@SuppressWarnings("unchecked")
	public static <E> E[] toArray(List<E> lis){
		Preconditions.checkArgument(lis!=null && !lis.isEmpty(), "La lista no puede ser vacía ni null");	
		Class<E> type = (Class<E>) lis.get(0).getClass();
		return lis.stream().toArray((int x)->(E[])Array.newInstance(type, x));
	}

	/**
	 * @param <E> Tipo de los elementos
	 * @param lista Una lista
	 * @param i Un índice de la lista
	 * @param j Un índice de la lista
	 * @pos Quedan intercambiadas las casillas de índices i y j en la lista
	 * @pre Tanto i como j deben ser índices de la lista
	 */
	public static <E> void intercambia(List<E> lista, int i, int j){
		int size = lista.size();
		Preconditions.checkPositionIndex(i, size);
		Preconditions.checkPositionIndex(j, size);
		E aux;
		aux = lista.get(i);
		lista.set(i, lista.get(j));
		lista.set(j, aux);
	}

	/**
	 * @param <T> Tipo de los elementos
	 * @param ls Un array
	 * @param i Un índice de la lista
	 * @param j Un índice de la lista
	 * @pos Quedan intercambiadas las casillas de índices i y j en el array
	 * @pre Tanto i como j deben ser índices del array
	 */
	public static <T> void intercambia(T[] ls, int i, int j){
		int size = ls.length;
		Preconditions.checkPositionIndex(i, size);
		Preconditions.checkPositionIndex(j, size);
		T aux = ls[i];
		ls[i] = ls[j];
		ls[j] = aux;
	}
	
	/**
	 * @param <T> Tipo de los elementos
	 * @return Devuelve una lista vacía
	 */
	public static <T> List<T> newList(){
	    return new ArrayList<T>();
	}
	
	
	/**
	 * @param <T> Tipo de los elementos
	 * @param n Número de copias
	 * @param a Elemento a copiar
	 * @return Devuelve una lista formada por n copias de a
	 */
	public static <T> List<T> nCopias(int n, T a){
	    List<T> v = new ArrayList<T>();
	    for(int i=0;i<n;i++){
	       v.add(a);
	    }
	    return v;
	}

	/**
	 * @param a Limite inferior
	 * @param b Limite superior
	 * @return Devuelve la lista formada por los enteros de a hasta b en pasos de 1
	 */
	public static List<Integer> newList(Integer a, Integer b){
		return Lists2.newList(a, b, 1);
	}

	/**
	 * @param a Limite inferior
	 * @param b Limite superior
	 * @param c Paso
	 * @return Devuelve la lista formada por los enteros de a hasta b en pasos de c
	 */
	public static List<Integer> newList(Integer a, Integer b, Integer c){
		return Stream2.create(Stream2.range(a, b, c).boxed()).toList();
	}
	/**
	 * @param a Limite inferior
	 * @param b Limite superior
	 * @return Devuelve la lista formada por los reales de a hasta b en pasos de 1
	 */
	public static List<Double> newList(Double a, Double b){
		Preconditions.checkArgument(a<=b);
		List<Double> s = Lists.newArrayList();
		for(double i = a; i<b; i++){
			s.add(i);
		}
		return s;
	}
	/**
	 * @param a Limite inferior
	 * @param b Limite superior
	 * @param c Paso
	 * @return Devuelve la lista formada por los reales de a hasta b en pasos de c
	 */
	public static List<Double> newList(Double a, Double b, Double c){
		Preconditions.checkArgument(a<=b  && c>0);
		List<Double> s = Lists.newArrayList();
		for(double i = a; i<b; i=i+c){
			s.add(i);
		}
		return s;
	}
	
	/**
	 * @param <E> tipo de los elementos
	 * @param e Una serie de elementos
	 * @return Una lista construida a partir de ellos
	 */
	@SafeVarargs
	public static <E> List<E> newList(E... e){
		return Arrays.stream(e).collect(Collectors.toList());
	}
	
	/**
	 * @param <E> Tipo de los elementos de la lista
	 * @param <U> Tipo de la collección
	 * @param collection Una colección
	 * @return La colección convertida en lista
	 */
	public static <E,U extends Collection<E>> List<E> newList(U collection){
		return collection.stream().collect(Collectors.toList());
	}
	
	/**
	 * @param <E> Tipo de los elementos de la lista
	 * @param ls1 Una lista
	 * @param ls2 Una segunda lista
	 * @return La concatenación d elas dos listas
	 */
	public static <E> List<E> concat(List<E> ls1, List<E> ls2){
		List<E> r = Lists2.newList(ls1);
		r.addAll(ls2);
		return r;
	}
	
	/**
	 * @param <T> Tipo de los elementos
	 * @param ls Una lista
	 * @return Una lista unitaria escogida aleatoriamente o vacía si lo es la de entrada
	 */
	public static <T> List<T> randomUnitary(List<T> ls){
		List<T> r = Lists.newArrayList();
		if(!ls.isEmpty()){
			int e = Math2.getEnteroAleatorio(0, ls.size());
			r.add(ls.get(e));	
		}
		return r;
	}

	/**
	 * @param <T> Tipo de los elementos
	 * @param ls Una lista
	 * @return Una lista con la casilla primera intercambiada por la última, 
	 * la segunda por la penúltima, etc.
	 */
	public static <T> List<T> reverse(List<T> ls){
		final int n = ls.size();
		return IntStream.range(0,n)
				.mapToObj(i->ls.get(n-1-i))
				.collect(Collectors.toList());
	}
	
}


package us.lsi.common;

import java.lang.reflect.Array;
import java.util.*;

import us.lsi.math.Math2;
import us.lsi.stream.Stream2;

import com.google.common.collect.*;
import com.google.common.base.*;

public class Lists2 {
	
	/**
	 * @param <E1> Tipo de los elementos
	 * @param <E2> Tipo de los elementos
	 * @param lista1 Una lista
	 * @param lista2 Una lista 
	 * @return El producto cartesiano como una lista de pares
	 */
	public static <E1,E2> List<Par<E1,E2>> cartesianProduct(List<E1> lista1, List<E2> lista2){
		List<Par<E1,E2>> lp = Lists.newLinkedList();
		for(E1 e1: lista1){
			for(E2 e2:lista2){
				lp.add(Par.create(e1, e2));
			}
		}
		return lp;
	}
	
	/**
	 * @param <E1> Tipo de los elementos
	 * @param <E2> Tipo de los elementos
	 * @param s1 Un conjunto
	 * @param s2 Un conjunto
	 * @return El producto cartesiano como un conjunto de pares de pares
	 */
	public static <E1,E2> Set<Par<E1,E2>> cartesianProduct(Set<E1> s1, Set<E2> s2){
		Set<Par<E1,E2>> lp = Sets.newHashSet();
		for(E1 e1: s1){
			for(E2 e2: s2){
				lp.add(Par.create(e1, e2));
			}
		}
		return lp;
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
	 * @param ls Un alista de bits
	 * @return El número entero conrrespondiente
	 */
	public static  Integer decode(List<Integer> ls){
		Integer r = 0;
		for(Integer e:ls){
			r = r*2+e;
		}
		return r;
	}
	
}


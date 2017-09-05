package us.lsi.common;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Miguel Toro
 *
 * <p> Implementación de una Cola </p>
 * 
 * @param <T> El tipo de los elementos de la cola
 */
public class Stack2<T> {

	/**
	 * @param <T> el tipo de las etiquetas del árbol
	 * @return Una pila vacía
	 */
	public static <T> Stack2<T> create() {
		return new Stack2<T>();
	}

	private List<T> elements;

	private Stack2() {
		super();
		this.elements = new ArrayList<T>();
	}
	/**
	 * @return Verdadero si la pila está vacía
	 */
	public boolean isEmpty() {
		return elements.isEmpty();
	}
	/**
	 * @return El número de elementos de la pila
	 */
	public int size() {
		return elements.size();
	}
	/**
	 * @param e El elemento a añadir a la pila
	 * @return Verdadero si la pila ha quedado modificada
	 */
	public boolean add(T e) {
		return elements.add(e);
	}
	/**
	 * @return Devuelve el primer elemento de la pila y lo elimina.
	 * 
	 */
	public T remove() {
		return elements.remove(elements.size()-1);
	}
	/**
	 * @return Devuelve el primer elemento de la pila pero no lo elimina.
	 * Dispara una excepción si la pila está vacía.
	 */
	public T peek() {
		return elements.get(elements.size()-1);
	}

	@Override
	public String toString() {
		return elements.toString();
	}
	
}

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
public class Queue2<T> {

	/**
	 * @param <T> el tipo de las etiquetas del árbol
	 * @return Una cola vacía
	 */
	public static <T> Queue2<T> create() {
		return new Queue2<T>();
	}

	private List<T> elements;

	private Queue2() {
		super();
		this.elements = new ArrayList<T>();
	}
	
	/**
	 * @return Verdadero si la cola está vacía
	 */
	public boolean isEmpty() {
		return elements.isEmpty();
	}
	
	/**
	 * @return El número de elementos de la cola
	 */
	public int size() {
		return elements.size();
	}
	
	/**
	 * @param e El elemento a añadir a la cola
	 * @return Verdadero si la cola ha quedado modificada
	 */
	public boolean add(T e) {
		return elements.add(e);
	}
	
	/**
	 * @return Devuelve el primer elemento de la cola y lo elimina. 
	 * Dispara una excepción si la cola está vacía.
	 */
	public T remove() {
		return elements.remove(0);
	}
	/**
	 * @return Devuelve el primer elemento de la cola pero no lo elimina.
	 * Dispara una excepción si la cola está vacía.
	 */
	public T peek() {
		return elements.get(0);
	}
	
	@Override
	public String toString() {
		return elements.toString();
	}
}

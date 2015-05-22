package us.lsi.basictypes;

import java.lang.reflect.Array;
import java.util.Arrays;

import com.google.common.base.Preconditions;

/**
 * Una implementación de un array de tamaño variable
 * 
 * @author Miguel Toro
 *
 * @param <E> Tipo de los elementos
 */
public class BasicDynamicArray<E> {
	
	public static <E> BasicDynamicArray<E> create() {
		return new BasicDynamicArray<E>();
	}

	public static <E> BasicDynamicArray<E> create(int capacity) {
		return new BasicDynamicArray<E>(capacity);
	}

	public static <E> BasicDynamicArray<E> create(BasicDynamicArray<E> a) {
		return new BasicDynamicArray<E>(a);
	}

	public static <E> BasicDynamicArray<E> create(E[] a) {
		return new BasicDynamicArray<E>(a);
	}

	private int capacity;
	private int size;
	private E[] elements;
	private final int INITIAL_CAPACITY = 10;
	private final int GROWING_FACTOR = 2;
	
	private BasicDynamicArray() {
		super();
		this.capacity = INITIAL_CAPACITY;
		this.size = 0;
		this.elements = null;
	}
	
	private BasicDynamicArray(int capacity) {
		super();
		this.capacity = capacity;
		this.size = 0;
		this.elements = null;
	}
	
	private BasicDynamicArray(BasicDynamicArray<E> a) {
		super();
		this.capacity = a.capacity;
		this.size = a.size();
		this.elements = Arrays.copyOf(a.elements,a.capacity);
	}
	
	private BasicDynamicArray(E[] a) {
		super();
		this.capacity = a.length;
		this.size = capacity;
		this.elements = Arrays.copyOf(a, capacity);
	}

    private void grow(int newCapacity){
    	E[] oldElements = elements;
    	capacity = newCapacity;
    	elements = Arrays.copyOf(oldElements, capacity);
    }
	
    public int size() {
		return size;
	}

    public boolean isEmpty(){
    	return size == 0;
    }
    
	public E get(int index) {
    	Preconditions.checkElementIndex(index, size);
		return elements[index];
	}
    
	@SuppressWarnings("unchecked")
	public E set(int index, E e){		
		if(size == 0){
			if(index >= capacity){
				capacity = index+1;
			}
			elements = (E[]) Array.newInstance(e.getClass(), capacity);
		}
		if(index >=  capacity){
			grow(index+1);
		}	
		if(index >= size){
			size = index+1;
		}
		E r = get(index);
		elements[index]= e;
		return r;
	}
	
	@SuppressWarnings("unchecked")
	public boolean add(E e) {
		if(size == 0){
			elements = (E[]) Array.newInstance(e.getClass(), capacity);
		}
		if(size==capacity){
			grow(capacity*GROWING_FACTOR);
		}
		elements[size] = e;
		size++;   	
		return true;
	}
	
	public void add(int index, E e) {
		Preconditions.checkPositionIndex(index, size);
		add(e);
		// size ya ha quedado aumentado
		for(int i = size-1; i > index; i--){
			elements[i]= elements[i-1];
		}
		elements[index]=e;
	}
	
	public E remove(int index) {
		Preconditions.checkElementIndex(index, size);
		E e = elements[index];
		for(int i = index; i < size-1; i++){
			elements[i]= elements[i+1];
		}
		size --;
		return e;
	}
	
	public E[] toArray(){
		E[] r = Arrays.copyOf(this.elements, size);
		return r;
	}
	
	public String toString(){
		String s = "{";
		boolean prim = true;
		for(int i=0; i< size; i++){
			if(prim){
				prim = false;
				s = s+elements[i];
			}else{
				s = s+","+elements[i];
			}
		}
		s = s+"}";
		return s;
	}
}

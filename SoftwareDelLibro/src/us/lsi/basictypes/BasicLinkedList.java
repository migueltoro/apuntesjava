package us.lsi.basictypes;

import com.google.common.base.Preconditions;



/**
 * Una implementación de una lista enlazada
 * 
 * 
 * @author Miguel Toro
 *
 * @param <E> Tipo de los elementos
 */
public class BasicLinkedList<E> {
	private Entry<E> first;
	private Entry<E> last;	
	private int size;
	//invariant size ==0   <=> first == null && last == null
	
	public BasicLinkedList() {
		super();
		this.first = null;
		this.last = null;
		this.size=0;
	}

	public int size() {
		return size;
	}
	
	public boolean isEmpty(){
		return size==0;
	}
	
	public E get(int index){
		return entryInPos(index).getElement();
	}
	
	public E set(int index, E e){
		Entry<E> e1 = entryInPos(index);
		E r = e1.getElement();
		e1.setElement(e);
		return r;
	}
	
	public boolean add(E e){
		Entry<E> e1 = new Entry<E>(e);
		if(last==null){
			first = e1;
			last=e1;
		}else{
			last.setNext(e1);
			last = e1;
		}
		size++;
		return true;
	}
	
	
	
	public void add(int index, E e){
		Preconditions.checkPositionIndex(index, size);
		Entry<E> ne = new Entry<E>(e);
		if(index==size){
			add(e);
		} else if(index==0){
			ne.setNext(first);
			first = ne;
		} else {
			Entry<E> pe = entryInPos(index-1);
			ne.setNext(pe.getNext());
			pe.setNext(ne);
		}
		size++;
	}
	
	private Entry<E> entryInPos(int index){
		Preconditions.checkElementIndex(index, size);
	    Entry<E> pe = first;
	    for(int p = 0 ; p < index; p++){
	    	pe = pe.getNext();
	    }
		return pe;
	}
	
	public E remove(int index){
		Preconditions.checkElementIndex(index, size);
		Entry<E> e = null;
		E element;
		if(index==0){
			e = first;
			first = first.getNext();
			element = e.getElement();
		} else {
			Entry<E> pe = entryInPos(index-1);
			element = pe.getNext().getElement();
			if(index == size-1){
				last = pe;
			}else{
				pe.setNext(pe.getNext().getNext());
			}
		}
		size--;
		return element;
	}
	
	
	
	public String toString(){
		String s = "{";
		boolean prim = true;
		for(Entry<E> e = first;e!=null;e = e.getNext()){
			if(prim){
				prim = false;
				s = s+e.getElement();
			}else{
				s = s+","+e.getElement();
			}
		}
		s = s+"}";
		return s;
	}
	
	public class Entry<F> {
		private F element;
		private Entry<F> next;
		public Entry(F element, Entry<F> next) {
			super();
			this.element = element;
			this.next = next;
		}
		public Entry(F element) {
			super();
			this.element = element;
			this.next = null;
		}
		public F getElement() {
			return element;
		}
		public void setElement(F element) {
			this.element = element;
		}
		public Entry<F> getNext() {
			return next;
		}
		public void setNext(Entry<F> next) {
			this.next = next;
		}	
	}

	
}

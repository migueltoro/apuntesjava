package us.lsi.concurrent.ejemplos;


public class BaseBoundedBuffer<V> {

	private final V[] buf;
	private int tail = 0;
	private int head = 0 ;
	private int count = 0;
	
	
	@SuppressWarnings("unchecked")
	public BaseBoundedBuffer(int capacity) {
		if (capacity <= 0)
            throw new IllegalArgumentException();
		this.buf = (V[]) new Object[capacity];
	}
	
	protected void doPut(V v){
		buf[tail] = v;
		if (++tail == buf.length)
			tail = 0;
		++count;
	}
	
	protected V doTake(){
		V v = buf[head];
		buf[head] = null;
		if (++head == buf.length)
			head = 0;
		--count;
		return v;
	}
	
	public boolean isFull(){
		return count == buf.length;
	}
	
	public boolean isEmpty(){
		return count == 0;
	}
	
}

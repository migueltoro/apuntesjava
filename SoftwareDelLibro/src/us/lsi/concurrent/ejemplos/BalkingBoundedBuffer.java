package us.lsi.concurrent.ejemplos;


import net.jcip.annotations.*;

@ThreadSafe
public class BalkingBoundedBuffer<V> extends BaseBoundedBuffer<V> {

	
	public BalkingBoundedBuffer(int capacity) {
		super(capacity);
	}

	@GuardedBy("this")
	public synchronized void put(V v) throws BufferFullException {
		if (isFull())
			throw new BufferFullException();
		doPut(v);
	}
	@GuardedBy("this")
	public synchronized V take()throws BufferEmptyException {
		if (isFull())
			throw new BufferEmptyException();
		return doTake();
	}
}

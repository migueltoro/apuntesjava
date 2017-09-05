package us.lsi.concurrent.ejemplos;

import java.util.concurrent.*;

import net.jcip.annotations.*;


@ThreadSafe
public class BoundedBufferSemaphore<V> extends BaseBoundedBuffer<V> {
    private final Semaphore availableItems, availableSpaces;
    
	public BoundedBufferSemaphore(int capacity) {
        super(capacity);
        availableItems = new Semaphore(0);
        availableSpaces = new Semaphore(capacity);
    }

	@Override
    public boolean isEmpty() {
        return availableItems.availablePermits() == 0;
    }
	@Override
    public boolean isFull() {
        return availableSpaces.availablePermits() == 0;
    }

    public void put(V x) throws InterruptedException {
        availableSpaces.acquire();
        synchronized (this) {
			super.doPut(x);
		}
		availableItems.release();
    }

    public V take() throws InterruptedException {
        availableItems.acquire();
        V item;
        synchronized(this){
        	item = super.doTake();
        }
        availableSpaces.release();
        return item;
    }
}


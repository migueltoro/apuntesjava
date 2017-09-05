package us.lsi.concurrent.ejemplos;

import net.jcip.annotations.ThreadSafe;

import com.google.common.util.concurrent.*;

@ThreadSafe
public class BoundedBufferMonitor<V> extends BaseBoundedBuffer<V> {

	private Monitor monitor;
	private Monitor.Guard notFull;
	private Monitor.Guard notEmpty;
	
	public BoundedBufferMonitor(int capacity) {
		super(capacity);
		monitor = new Monitor();
		
		notFull = new Monitor.Guard(monitor) {		
			@Override
			public boolean isSatisfied() {
				return !isFull();
			}
		};
		
		notEmpty = new Monitor.Guard(monitor) {		
			@Override
			public boolean isSatisfied() {
				return !isEmpty();
			}
		};
	}
	
	public void put(V v) throws InterruptedException {
		monitor.enterWhen(notFull);
		try {			
			super.doPut(v);
		} finally{
			monitor.leave();
		}
		
	}
		
	public V take() throws InterruptedException {
		V r;
		monitor.enterWhen(notEmpty);
		try {			
			r = super.doTake();
		} finally{
			monitor.leave();
		}
		return r;
	}

}

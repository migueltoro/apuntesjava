package us.lsi.concurrent.ejemplos;

import java.util.concurrent.atomic.*;

import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class Counter2 {
	
	private AtomicLong value = new AtomicLong();
	
	public long getValue() {
	      return value.longValue();
	}

	public long increment() {
	      if (value.longValue() == Long.MAX_VALUE)
	            throw new IllegalStateException("counter overflow");
	      return value.incrementAndGet();
	}
}

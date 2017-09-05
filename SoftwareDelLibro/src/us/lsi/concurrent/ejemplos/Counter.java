package us.lsi.concurrent.ejemplos;

import net.jcip.annotations.*;

@ThreadSafe
public final class Counter {
//		@GuardedBy("this")
	    private long value = 0;

	    public synchronized long getValue() {
	        return value;
	    }

	    public synchronized long increment() {
	        if (value == Long.MAX_VALUE)
	            throw new IllegalStateException("counter overflow");
	        return ++value;
	    }
}


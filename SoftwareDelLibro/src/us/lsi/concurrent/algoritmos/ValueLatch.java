package us.lsi.concurrent.algoritmos;

import java.util.concurrent.*;

import net.jcip.annotations.*;

@ThreadSafe
public class ValueLatch<T> {
	@GuardedBy("this") private T value = null;
	private final CountDownLatch done = new CountDownLatch(1);

	public boolean isSet() {
		return (done.getCount() == 0);
	}

	public synchronized void setValue(T newValue) {
		if (!isSet()) {
			value = newValue;
			done.countDown();
		}
	}

	public T getValue() throws InterruptedException {
		done.await();
		synchronized (this) {
			return value;
		}
	}
}

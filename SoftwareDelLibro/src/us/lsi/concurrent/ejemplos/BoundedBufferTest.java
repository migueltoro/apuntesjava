package us.lsi.concurrent.ejemplos;

import static junit.framework.Assert.*;

import org.junit.Test;


public class BoundedBufferTest {
	
		
	private static final long LOCKUP_DETECT_TIMEOUT = 1000;

	@Test
	public void testIsEmptyWhenConstructed() {
//		BoundedBufferMonitor<Integer> bb = new BoundedBufferMonitor<Integer>(10);
		BoundedBufferSemaphore<Integer> bb = new BoundedBufferSemaphore<Integer>(10);
		assertTrue(bb.isEmpty());
		assertFalse(bb.isFull());
	}

	@Test
	public void testIsFullAfterPuts() throws InterruptedException {
//		BoundedBufferMonitor<Integer> bb = new BoundedBufferMonitor<Integer>(10);
		BoundedBufferSemaphore<Integer> bb = new BoundedBufferSemaphore<Integer>(10);
		for (int i = 0; i < 10; i++)
			bb.put(i);
		assertTrue(bb.isFull());
		assertFalse(bb.isEmpty());
	}

	@Test
	public void testTakeBlocksWhenEmpty() {
//		final BoundedBufferMonitor<Integer> bb = new BoundedBufferMonitor<Integer>(10);
		final BoundedBufferSemaphore<Integer> bb = new BoundedBufferSemaphore<Integer>(10);
		Thread taker = new Thread() {
			public void run() {
				try {
					@SuppressWarnings("unused")
					int unused = bb.take();
					fail(); // if we get here, it's an error
				} catch (InterruptedException success) {
				}
			}
		};
		try {
			taker.start();
			Thread.sleep(LOCKUP_DETECT_TIMEOUT);
			taker.interrupt();
			taker.join(LOCKUP_DETECT_TIMEOUT);
			assertFalse(taker.isAlive());
		} catch (Exception unexpected) {
			fail();
		}
	}
}

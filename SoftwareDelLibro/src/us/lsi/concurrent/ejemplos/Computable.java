package us.lsi.concurrent.ejemplos;

public interface Computable<A, V> {
	V compute(A arg) throws InterruptedException;
}

package us.lsi.iterate;

public interface RecursiveState<R, E extends RecursiveState<R,E>> extends IterateState<R, E> {
	
	R combine(R r);

}

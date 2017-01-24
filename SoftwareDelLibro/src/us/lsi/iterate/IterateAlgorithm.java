package us.lsi.iterate;


import java.util.stream.Stream;;

/**
 * @author Miguel Toro
 *
 * <p> Algoritmo iterativo esen sus diversas formas y sus equivalente recursivo final </p>
 *
 * @param <R> Tipo de los datos devueltos por el algoritmo
 * @param <E> Tipo del estado del algoritmo
 */
public class IterateAlgorithm<R,E extends IterateState<R,E>> {

	public static <R, E extends IterateState<R,E>> R iterateStream (E e) {		
		return Stream.iterate(e, s->s.next())
				.filter(s->!s.domain())
				.findFirst()
				.get()
				.ret();				
	}
	
	public static <R, E extends IterateState<R,E>> R iterateWhile (E e){
		E st = e;
		while(st.domain()){
			st = st.next();
		}
		return st.ret();
	}
	
	public static <R, E extends IterateState<R,E>> R recursiveFinal (E e){
		R r;
		if(!e.domain()){
			r = e.ret();
		}else {
			r = recursiveFinal(e.next());
		}
		return r;
	}

	
	
	public static <R, E extends RecursiveState<R,E>> R recursiveNoFinal (E e){
		R r;
		if(!e.domain()){
			r = e.ret();
		}else {
			r = e.combine(recursiveNoFinal(e.next()));
		}
		return r;
	}
	
	
}

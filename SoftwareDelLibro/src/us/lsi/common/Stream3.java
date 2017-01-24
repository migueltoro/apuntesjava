package us.lsi.common;

import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;
import java.util.stream.Stream;;

public class Stream3 {

	public static Stream<ParInteger> parStream(Integer a, Integer b){
		return IntStream.range(a,b)
				.boxed()
				.<ParInteger>flatMap(x->IntStream.range(a,b).boxed().map(y->ParInteger.create(x,y)));
	}
	
	public static <T> Stream<Par<T,T>> parStream(List<T> ls){
		return ls.stream()
				.<Par<T,T>>flatMap(x->ls.stream().map(y->Par.create(x,y)));
	}
	
	public static <T> Stream<Par<T,T>> parStream(Set<T> ls){
		return ls.stream()
				.<Par<T,T>>flatMap(x->ls.stream().map(y->Par.create(x,y)));
	}
	
	public static void main(String[] args){
		Stream3.parStream(0, 5).forEach(x->System.out.println(x));
	}

}

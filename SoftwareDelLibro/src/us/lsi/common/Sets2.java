package us.lsi.common;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

import us.lsi.stream.Stream2;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Sets2 {

	public static Set<Integer> newSet(Integer a, Integer b, Integer c){		
		return Stream2.create(Stream2.range(a, b, c).boxed()).toSet();
	}	
	
	public static Set<Integer> newSet(Integer a, Integer b){
		return IntStream.range(a,b).boxed().collect(Collectors.toSet());
	}
	
	@SafeVarargs
	public static <E> Set<E> newSet(E... e){
		return Arrays.stream(e).collect(Collectors.toSet());
	}

	public static <E,U extends Collection<E>> Set<E> newSet(U elements){
		return elements.stream().collect(Collectors.toSet());
	}
}

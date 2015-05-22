package us.lsi.stream;

import java.util.*;
import java.util.stream.Collectors;


class PrintStream {
	
	public static String nIndex(int n){
		StringBuffer s = new StringBuffer();
		for(int i =0; i<n;i++){
			s.append("     ");
		}
		return s.toString();
	}
	
	public static <K,V> String listToString(List<V> ls){
		return ls
				.stream()
				.<String>map(x->x.toString())
				.collect(Collectors.joining("(", ",",")"));
	}
	
	public static <K1,R,M extends Map<K1,R>> String mapToString(M map,int n){
		return map
				.keySet()
				.stream()
				.<String>map(x->nIndex(n)+x.toString()+","+map.get(x))
				.collect(Collectors.joining("\n"));
	}
	
	
	public static <K1,K2,R,N extends Map<K2,R>, M extends Map<K1,N>> String map2ToString(M map, int n){
		return map
				.keySet()
				.stream()
				.<String>map(x->nIndex(n)+x.toString()+"\n"+mapToString(map.get(x),n+1))
				.collect(Collectors.joining("\n"));
	}
	
	public static <K1,K2,K3,R,L extends Map<K3,R>, N extends Map<K2,L>, M extends Map<K1,N>> String map3ToString(M map, int n){
		return map
				.keySet()
				.stream()
				.<String>map(x->nIndex(n)+x.toString()+"\n"+PrintStream.<K2,K3,R,L,N>map2ToString(map.get(x),n+1))
				.collect(Collectors.joining("\n"));
	}
}

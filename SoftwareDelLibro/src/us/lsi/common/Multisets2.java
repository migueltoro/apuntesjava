package us.lsi.common;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

public class Multisets2 {

	public static <E> Multiset<E> newHashMultiset(){
		return HashMultiset.<E>create();
	}

	public static <E> Multiset<E> newHashMultiset(Iterable<E> it){
		return HashMultiset.<E>create(it);
	}
	
	public static <E> Multiset<E> newHashMultiset(@SuppressWarnings("unchecked") E... it){
		Multiset<E> s = HashMultiset.<E>create();
		for(E e : it){
			s.add(e);
		}
		return s;
	}
	
}

package us.lsi.pd.mochila;


import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.*;

import com.google.common.collect.Lists;

public class TestIterableInteger {

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(getAlternativas());
		System.out.println(getAlternativas());
		System.out.println(Lists.newArrayList(getAlternativas()));
		System.out.println(getAlternativas());
		for(Integer e: getAlternativas()){
			System.out.println("     "+e);
		}
	}
	
	public static Iterable<Integer> getAlternativas() {
		return IntStream.rangeClosed(0, 27)
			.boxed()
			.filter(x-> x%2==0)
			.sorted(Comparator.<Integer>reverseOrder())
			.collect(Collectors.<Integer>toList());
	}
	

}

package us.lsi.common;

import java.util.Arrays;
import java.util.function.BiPredicate;

import com.google.common.collect.Lists;


public class TestString {

	public static void main(String[] args) {
		for(String s: StringExtensions2.toArray("Antonio, Juan    Pedro", "[ ,]")){
			System.out.println(s);
		}
		
		Comparator2<Integer> cmp = (Integer e1, Integer e2)->e1-e2;
		System.out.println(cmp.isLT(2, 3));

		BiPredicate<Integer,Integer> b = cmp::isLT;
		
		System.out.println(b.test(2, 3));
		
		Integer[] s = Lists2.toArray(Lists.newArrayList(3,4,5,6,7,8));
		System.out.println(Arrays.toString(s));
	}

}

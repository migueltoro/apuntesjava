package us.lsi.iterativo;

import java.util.Arrays;
import java.util.List;

import com.google.common.collect.Lists;

public class EjemplosIterativos {

	public static int binom(int n, int k) {
		List<Integer> lsa = Arrays.asList(1);
		int i = 1;
		while (i <= n) {
			List<Integer> ls = Lists.newArrayList();
			for (int s = 0; s <= i; s++) {
				if (s == 0 || s == i) {
					ls.add(1);
				} else if (s == 1 || s == i - 1) {
					ls.add(i);
				} else {
					ls.add(lsa.get(s - 1) + lsa.get(s));
				}
			}
			i = i + 1;
			lsa = Lists.newArrayList(ls);
		}
		return lsa.get(k);
	}
	
	public static long fib(int n){
			int i = 0;
		    int a = 1;
			int b = 0;
			while(i < n){
				i = i+1;
				int a0 = a;
				a = a0+b;
				b = a0;
			}
			return b;
	}
	
	public static long pot(int b,int n){
		int e = b;
		int a = 1;
		while( n > 0){
	        	if(n%2==1){
			     a = a * e;
			}
			e = e * e;
			n = n/2;
		}
		return a;
	}

	public static long mcd(int a,int b) {
		while(b > 0){
		   int a0 = a;
		   a = b;
		   b = a0%b;   
	    }
		return a;
	}
	public static void main(String[] args) {
		System.out.println(binom(10,5));
		System.out.println(fib(10));
		System.out.println(pot(8,5));
		System.out.println(mcd(10546,3280));
	}

}

package us.lsi.dyv.ejemplos;

public class EjemplosRecursivos {

	public static long binomio(long n, long k){
		long r;
		if(k == 0 || k == n){
			r = 1;
		}else if (k == 1 || k == n-1){
			r = n;
		}else {
			r = binomio(n-1,k-1)+binomio(n-1,k);
		}
		return r;
	}
	
	public static long fib(int n){
		long r;
		if(n<=1){
			r = n;
		}else {
			r = fib(n-1)+fib(n-2);		
	    }
		return r;
	}
	
	public static void main(String[] args) {
		System.out.println(binomio(10L,5L));
	}

}

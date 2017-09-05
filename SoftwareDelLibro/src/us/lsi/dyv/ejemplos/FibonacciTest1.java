package us.lsi.dyv.ejemplos;

import java.math.BigInteger;

import us.lsi.algoritmos.Algoritmos;
import us.lsi.dyv.AlgoritmoDyVCM;

public class FibonacciTest1 {

	public static void main(String[] args) {
		
		Integer n = 7;
		AlgoritmoDyVCM<BigInteger,BigInteger> a = Algoritmos.createDyVCM(Fibonacci.create(n));
		a.ejecuta();
		BigInteger r = a.getSolucion();
		System.out.println(r);
	}

}

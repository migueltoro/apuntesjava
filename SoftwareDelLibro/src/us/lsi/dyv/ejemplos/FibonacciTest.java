package us.lsi.dyv.ejemplos;

import org.junit.Test;

import us.lsi.algoritmos.Algoritmos;
import us.lsi.dyv.AlgoritmoDyVCM;
import us.lsi.dyv.AlgoritmoDyVSM;
import java.math.BigInteger;

public class FibonacciTest {

	@Test
	public void test1() {
		Integer n = 7;
		AlgoritmoDyVCM<BigInteger,BigInteger> a = Algoritmos.createDyVCM(Fibonacci.create(n));
		a.ejecuta();
		BigInteger r = a.getSolucion();
		assert(r.equals(new BigInteger("13")));
	}
	
	@Test
	public void test2() {
		Integer n = 7;
		AlgoritmoDyVSM<BigInteger,BigInteger> a = Algoritmos.createDyVSM(Fibonacci.create(n));
		a.ejecuta();
		BigInteger r = a.getSolucion();
		assert(r.equals(new BigInteger("13")));
	}

	@Test
	public void test3() {
		Integer n = 40;
		AlgoritmoDyVCM<BigInteger,BigInteger> a = Algoritmos.createDyVCM(Fibonacci.create(n));
		a.ejecuta();
		BigInteger r = a.getSolucion();
		System.out.println(r);
		assert(true);
	}
	
	@Test
	public void test4() {
		Integer n = 40;
		AlgoritmoDyVSM<BigInteger,BigInteger> a = Algoritmos.createDyVSM(Fibonacci.create(n));
		a.ejecuta();
		BigInteger r = a.getSolucion();
		System.out.println(r);
		assert(true);
	}
}

package us.lsi.math;
import java.math.*;

public class MatrizFibonacci {
	private BigInteger a;
	private BigInteger b;
	private static MatrizFibonacci base = 
		    new MatrizFibonacci(BigInteger.ONE,BigInteger.ZERO);
	
	private static BigInteger dos = new BigInteger("2");

	public MatrizFibonacci(BigInteger a1, BigInteger b1){
		a = a1;
		b = b1;
	}
		
	public BigInteger getA(){
		return a;
	}
	
	public BigInteger getB(){
		return b;
	}
	
	public BigInteger get(int i, int j){
		BigInteger r;
		if(i == 0 && j == 0) r = a;
		else if(i == 1 && j == 0) r = b;
		else if(i == 0 && j == 1) r = a.add(b);
		else if(i == 1 && j == 1) r = a;
		else throw new IllegalArgumentException("Indices no permitidos");
		return r;
	}
	
	public String toString(){
		BigInteger amasb = a.add(b); 
		String s = "(("+amasb+", "+a+"),"+"("+a+", "+b+"))";
		return s;
	}
	
	public MatrizFibonacci getSquare() {
		BigInteger a1 = getA();
		BigInteger b1 = getB();
		BigInteger ac = a1.multiply(a1);
		BigInteger bc = b1.multiply(b1);
		BigInteger ar = ac.add(dos.multiply(a1).multiply(b1));
		BigInteger br = (ac).add(bc);
		return new MatrizFibonacci(ar,br);
	}

	public MatrizFibonacci getMultiplyBase() {
		return new MatrizFibonacci(a.add(b), a);
	}
	
	public MatrizFibonacci getSum(MatrizFibonacci n2) {
		BigInteger a1 = getA();
		BigInteger b1 = getB();
		BigInteger a2 = n2.getA();
		BigInteger b2 = n2.getB();
		return new MatrizFibonacci(a1.add(a2),b1.add(b2));
	}

	public MatrizFibonacci getZero() {
		return new MatrizFibonacci(BigInteger.ZERO,BigInteger.ZERO);
	}
	
	public static MatrizFibonacci getOne() {
		return new MatrizFibonacci(BigInteger.ZERO,BigInteger.ONE);
	}
	
	public static MatrizFibonacci getBase(){
		return base;
	}

}

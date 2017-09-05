package us.lsi.dyv.ejemplos;

import java.util.List;

import us.lsi.dyv.ProblemaDyV;

import java.math.BigInteger;

import com.google.common.base.Preconditions;


public class Fibonacci implements ProblemaDyV<BigInteger, BigInteger> {

	public static Fibonacci create(Integer n) {
		return new Fibonacci(n);
	}

	private Integer n;	
	
	private Fibonacci(Integer n) {
		super();
		this.n = n;
	}
		

	public Integer getN() {
		return n;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((n == null) ? 0 : n.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Fibonacci))
			return false;
		Fibonacci other = (Fibonacci) obj;
		if (n == null) {
			if (other.n != null)
				return false;
		} else if (!n.equals(other.n))
			return false;
		return true;
	}

	

	@Override
	public String toString() {
		return "Fibonacci [n=" + n + "]";
	}


	@Override
	public int size() {
		return n;
	}

	@Override
	public boolean esCasoBase() {
		return n==0 || n==1;
	}

	@Override
	public BigInteger getSolucionCasoBase() {
		BigInteger r = null;
		if(n==0){
			r = BigInteger.ZERO;
		}else if(n==1){
			r = BigInteger.ONE;
		}
		return r;
	}

	@Override
	public BigInteger combina(List<BigInteger> ls) {
		Preconditions.checkArgument(ls.size() == 2);
		return ls.get(0).add(ls.get(1));
	}

	@Override
	public ProblemaDyV<BigInteger, BigInteger> getSubProblema(int i) {
		Preconditions.checkArgument(i>=0 && i<2,"El valor de i es "+i);
		return Fibonacci.create(n-i-1);
	}

	@Override
	public int getNumeroDeSubProblemas() {
		return 2;
	}

	@Override
	public BigInteger getSolucion(BigInteger e) {
		return e;
	}

}

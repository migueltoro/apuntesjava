package us.lsi.iterate;

public class Mcd implements IterateState<Long, Mcd> {

	public long a;
	public long b;	

	public Mcd(Long a, Long b) {
		super();
		this.a = a;
		this.b = b;
	}

	@Override
	public Mcd next() {
		Long a, b;
		a = this.b;
		b = this.a%this.b;
		return new Mcd(a,b);
	}

	@Override
	public Long ret() {
		return this.a;
	}

	@Override
	public boolean domain() {
		return this.b > 0;
	}
	
	@Override
	public String toString() {
		return "Mcd [a=" + a + ", b=" + b + "]";
	}

	public static void main(String[] args) {
		Mcd e = new Mcd(1024L,2048L);
		System.out.println(e);
		Long r1 = IterateAlgorithm.iterateStream(e);
		Long r2 = IterateAlgorithm.iterateWhile(e);
		Long r3 = IterateAlgorithm.recursiveFinal(e);
		System.out.println(r1+","+r2+","+r3);
		
	}

}

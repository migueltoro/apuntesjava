package us.lsi.math;

import java.util.stream.Stream;

import us.lsi.stream.Stream2;
import us.lsi.test.Test;


public class TestRacional2 extends Test {

	
	public static void main(String[] args) {
		
		Stream<String> st = Stream2.fromFile("racional1.txt");
		st.forEach(x->System.out.println(x));
	/*	
		Racional r = Racionales.create(-6, -4);
		mostrar("r ="+r);
		File f = new File("ejemplo.txt");
		mostrar(f+"   "+f.exists());
		mostrar(f.getAbsolutePath());
		File f2 = new File("C:\\Users\\mtoro\\Documents\\workspace\\tema1\\","ejemplo.txt");
		mostrar(f2);
		Racional r2 = Racionales.create("-3/5");
		mostrar(r2);
		Racional r1 = r.copia();
		mostrar(r1);
		r1.guarda("racional.txt");
		Racional r4 = Racionales.creaDeFichero("racional.txt");
		mostrar(r4);
		Racional r5 = new RacionalImpl(-7, 6);
		Racional r6 = new RacionalImpl(-1, 6);

		
		List<Racional> l = new ArrayList<Racional>();
		Function<Racional,Void> f1 = new AccionModificaNumerador();
		l.add(r5);
		l.add(r6);

		Iterables2.modify(l, new AccionModificaNumerador());
		
		System.out.println(l);
		
		Iterable<Void> it = Iterables.transform(l, new AccionModificaNumerador());

		System.out.println(it);
		System.out.println(l);
		
		f1.apply(r5);
		
		System.out.println(r5);
		
		Racional[] rr = {r,r1,r2,r4,r5,r6};
		Iterable<Racional> agregado = Arrays.asList(rr);
		mostrar("1  "+agregado);
		Integer[] ar ;
		Integer num = 0;
		for (Racional e : agregado) {
				if (e.getValorReal()>0.) {
					num++;          
				}
		}
		int i = 0;
		ar = new Integer[num];
		for (Racional e : agregado) {
				if (e.getValorReal()>0.) {
					ar[i] = e.getNumerador();
		                i++;
		         }
		} 
		mostrar(ar.length);
		mostrar(ar);
		Double r8 = Double.MIN_VALUE;
		Ordering<Punto> or = Ordering.from(new PuntoX());
	
	}
	
	private static class PuntoX implements Comparator<Punto> {

		@Override
		public int compare(Punto a1, Punto a2) {
			int r = a1.getX().compareTo(a2.getX());
			return r;
		}
		
	}
	
	private static class AccionModificaNumerador implements Function<Racional, Void> {
		public Void apply(Racional r) {
			r.setNumerador(1);
			return null;
		}
	}

*/
	}
}

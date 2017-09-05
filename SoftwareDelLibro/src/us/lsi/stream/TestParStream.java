package us.lsi.stream;

public class TestParStream {

	public static void main(String[] args) {
		
		Stream2.iterate(3, (Integer x) -> x + 5)
		.whilePredicate(x->x<=100)	
		.toPairStream()
		.forEach(p->System.out.println(p));
		Stream2.iterate(3, (Integer x) -> x + 5)
				.whilePredicate(x->x<=100)
				.forEach(p->System.out.println(p));
	}

}

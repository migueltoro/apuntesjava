package us.lsi.stream;

import us.lsi.common.ParInteger;

public class TestStreams2 {

	public static void main(String[] args) {
		Stream2.iterate(3, (Integer x) -> x + 5)
				.whileIncluded(x -> x <= 100)
				.<Integer,ParInteger>cartesianProduct(Stream2.iterate(5, (Integer x) -> x + 7)
									.whileIncluded((Integer x) -> x < 100),
										(Integer x, Integer y) -> ParInteger.create(x, y))
				.sorted((ParInteger p1, ParInteger p2)->p1.p1+p1.p2-(p2.p1+p2.p2))
				.forEach(r -> System.out.println(r));
	}

}

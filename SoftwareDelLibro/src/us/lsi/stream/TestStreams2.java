package us.lsi.stream;

import java.util.stream.IntStream;

import us.lsi.common.PairInteger;



public class TestStreams2 {

	public static void main(String[] args) {
		Streams.cartesianProduct(IntStream.range(0, 20).boxed(),
								 IntStream.range(0, 20).boxed(),
				                 (x,y)->PairInteger.create(x, y))
//				.sorted((ParInteger p1, ParInteger p2)->p1.p1+p1.p2-(p2.p1+p2.p2))
				.forEach(r -> System.out.println(r));
	}

}

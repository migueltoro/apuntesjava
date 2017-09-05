package us.lsi.java8ejemplos;


import java.util.Set;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.IntStream;
import java.util.stream.DoubleStream;

import com.google.common.collect.Sets;

import us.lsi.common.PairInteger;
import us.lsi.stream.*;



/**
 *
 * @author Miguel Toro
 */
public class OtrosEjemplos {

	private static <T> Consumer<T> imprimeEnConsola() {
		return x -> System.out.println(x);
	}

	private static <T, R> BiConsumer<T, R> imprimeEnConsola2() {
		return (x, y) -> System.out.println(x + "," + y);
	}

	private static IntConsumer imprimeEnConsolaInt() {
		return x -> System.out.println(x);
	}

	public static void ejemplo1() {

		Double r = DoubleStream.iterate(7.0, x -> x * x).filter(x -> x > 3000)
				.findFirst().getAsDouble();

		System.out.println("r= " + r);

	}

	public static void ejemplo2() {
		IntStream.range(23, 550).forEach(imprimeEnConsolaInt());
	}

	public static void ejemplo3() {
		Stream.concat(IntStream.range(50, 60).boxed(),
				IntStream.range(2, 20).boxed()).forEach(imprimeEnConsola());
	}

	public static void ejemplo4() {

		IntStream.range(0, 5).boxed().forEach(imprimeEnConsola());

		System.out.println("========");

		IntStream.range(10, 15).boxed().forEach(imprimeEnConsola());

		System.out.println("========");

		Stream2.create(IntStream.range(0, 5).boxed())
				.<Integer, Boolean, Integer> joint(
						Stream2.create(IntStream.range(10, 15).boxed()),
						x -> x % 2 == 0, x -> x % 3 == 0, (x, y) -> x + y)
				.forEach(imprimeEnConsola());

	}

	public static void ejemplo5() {
		Stream2<Long> s0  = Stream2.of(1L, 2L, 3L, 4L, 5L);
		Stream2<Long> s1 = Stream2.of(4L, 10L, 9L, 29L);
		Stream2<Long> s2 = Stream2.of(5L, 15L, 20L, 39L);
		
		
		s0.concat(s1)
				.joint(s2, x -> x % 2, x -> x % 3, (x, y) -> x + y)
				.groupBy2(x -> x % 10, x -> x % 2, Collectors.summarizingLong(x -> x))
				.forEach(imprimeEnConsola2());
	}

	public static void ejemplo6() {
		Set<Integer> s1 = Sets.newHashSet(1, 3, 5);
		Set<Integer> s2 = Sets.newHashSet(16, 13, 15);
		Stream2.create(s1.stream())
				.cartesianProduct(s2.stream(),
						(x, y) -> PairInteger.create(x, y))
				.forEach(imprimeEnConsola());
	}

	public static void ejemplo7() {
		Stream2.iterate(3L, x -> x * x).whilePredicate(x -> 81L - x >= 0)
				.forEach(imprimeEnConsola());
	}

	public static void ejemplos8() {
		Stream2.of(1L, 2L, 3L, 4L, 5L)
				.joint(Stream.of(4L, 10L, 9L, 29L), x -> x % 5, x -> x % 3, (x, y) -> x +  y)
				.forEach(imprimeEnConsola());
	}

	public static void ejemplos9() {
		Stream2.<Integer>iterate(0, x -> x + 4)
				.whilePredicate(x -> x <= 1000)
				.toList().forEach(imprimeEnConsola());
	}

	
}

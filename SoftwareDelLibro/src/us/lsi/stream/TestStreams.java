package us.lsi.stream;


import us.lsi.tipos.Employed;

public class TestStreams {

	public static void main(String[] args) {

		String ss = Stream2
				.of(Employed.create("Antonio", "1", "23", 20., 1),
						Employed.create("Pepe", "2", "23", 10., 1),
						Employed.create("Pepe2", "3", "23", 15., 1),
						Employed.create("Rafael", "4", "23", 19., 3),
						Employed.create("José", "5", "23", 31., 2),
						Employed.create("Mariano2", "6", "23", 42., 3),
						Employed.create("José2", "5", "23", 31., 2),
						Employed.create("Mariano2", "6", "23", 42., 3))
				.groupBy3(Employed::getPosition,
						Employed::getSalary,
						(Employed x) -> x.getName().charAt(0))
				.toString();
		// .collect(Collectors.groupingBy(Person::getPosition,
		// Collectors.< >groupingBy(Person::getSalary,
		// Collectors.<Person,String>groupingBy(Person::getName))))
		// .forEach((x,y)->System.out.println(x+","+y.toString()));
		;

		System.out.println(ss);
	}
}

package us.lsi.stream;

import java.io.File;
import java.io.FileNotFoundException;

public class TestStreamFactory {

	/**
	 * @param args Argumentos
	 * @throws FileNotFoundException Si no encuentra el fichero
	 */
	public static void main(String[] args) throws FileNotFoundException {
		Streams.fromFile("mapa.txt")
				.flatMap(((String x)-> Streams.fromString(x, "[;,\n]")))
				.forEach(x->System.out.println(x));
		File s = new File("mapa.txt");
		System.out.println(s.getAbsolutePath());
		Streams.rangeClosed(10, 0, -2).forEach(x->System.out.println(x));;
	}

}

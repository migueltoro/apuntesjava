package us.lsi.common;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

public class StringExtensions2 {
	
	public static void toFile(String s, String file){
		try {
			final PrintWriter f = new PrintWriter(new BufferedWriter(
					new FileWriter(file)));
				f.println(s);
			f.close();
		} catch (IOException e) {
			throw new IllegalArgumentException(
					"No se ha podido crear el fichero " + file);
		}
	}
	
	public static String[] toArray(String s, String delim){
		return Arrays.<String>stream(s.split(delim))
				.<String>map((String x) -> x.trim())
				.filter((String x) -> x.length() > 0)
				.toArray((int x)-> new String[x]);
	}
	
	public static void toConsole(String s){
		System.out.println(s);
	}
	
}

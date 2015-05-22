package us.lsi.stream;


import java.util.stream.Stream;

public class StrStream extends Stream2<String> {
	
	public StrStream(Stream<String> st) {
		super(st);
	}

	public void toFile(String file){
		StreamExtensions.toFile(this, file);
	}
	

}

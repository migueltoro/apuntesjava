package us.lsi.testxtend;

//import static extension java.util.Collections.*;
//import static us.lsi.stream.Grouping1.*;
//import static us.lsi.stream.Grouping2.*;
import java.util.stream.Stream;
import static java.util.stream.Collectors.*;
//import java.util.function.*;

import static extension us.lsi.common.StringExtensions2.*
import us.lsi.stream.Stream2;

class TestXtend {
	
	def static void main(String[] args) {	
		
		Stream2.of(1L,2L,3L,4L,5L)
			.concat(Stream2.of(4L,10L,9L,29L))
//			.joint(Stream2.of(4L,10L,9L,29L),[x|x%2],[x|x%3],[x,y|x+","+y])
			.groupBy2([x|x%10],[x|x%2],summarizingLong([x|x]))
			.toString()
			.toFile('resultados.txt')
//			.forEach([x|println(x)])
			;	
		val s1 = newHashSet(1, 3, 5)
		val s2 = newHashSet(16, 13, 15)
		Stream2.create(s1.stream()).cartesianProduct(s2.stream(),[x,y|new Pair(x,y)])
			.forEach([x|System.out.println(x)])
		Stream2.iterate(3L,[x|x*x])
			.whileIncluded([x|81L-x>=0])
			.forEach([x|println(x)])
		Stream2.of(1L,2L,3L,4L,5L)
			.joint(Stream.of(4L,10L,9L,29L),[x|x%5],[x|x%3],[x,y|x+","+y+","+x%5+","+y%3])
			.forEach([x|println(x)])
	}
}
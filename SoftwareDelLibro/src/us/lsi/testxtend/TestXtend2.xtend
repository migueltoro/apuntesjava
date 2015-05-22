package us.lsi.testxtend

import us.lsi.stream.Stream2;


class TestXtend2 {
	
		
	def static void main(String[] args) {
		Stream2.<Integer>iterate(0,[Integer x|x+4])
			.whileIncluded([x|x<=1000])
			.toList()
			.forEach([x|System.out.println(x);]);		
	}
}
package us.lsi.testxtend

import java.util.List;
import java.util.Map;
import us.lsi.common.Maps2;
import us.lsi.common.Entry;

import com.google.common.collect.Lists;
import static extension us.lsi.common.StringExtensions2.*;

class ToStringExtensions {
	
	def static <E> String toString2(List<E> ls) '''
		«FOR p : ls BEFORE '{' SEPARATOR ',' AFTER '}' » 
			«p.toString()»
    	«ENDFOR»
	'''	
	
	def static <K,V> String toString2(Map<K,V> m) '''
		«FOR p : m.keySet() BEFORE '{' SEPARATOR ',' AFTER '}'» 
			<«p», «m.get(p)»>
      	«ENDFOR»
	'''	
	def static void main(String[] args) {
		Lists.newArrayList(2,3,4,5,6,-23)
			.toString2()
			.toConsole();
		Maps2.newHashMap(Entry.create(1,2),Entry.create(3,2),Entry.create(5,2),Entry.create(7,2))
			.toString2()
			.toConsole();
	}
}
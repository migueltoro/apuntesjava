package us.lsi.stream;

import java.util.*;

class Group1Sort<K1, R> extends Group1<K1, R>{

	private static final long serialVersionUID = 1L;
	
	public static <K1, R> Group1Sort<K1, R> create(
			SortedMap<K1, ? extends R> m) {
		return new Group1Sort<K1, R>(m);
	}
	
	public String toString(){
		return toString(0);
	}
	
	public R getGroup(K1 x){
		return this.get(x);
	}

	private Group1Sort(SortedMap<K1, ? extends R> m) {
		super(m);
	}
		
}

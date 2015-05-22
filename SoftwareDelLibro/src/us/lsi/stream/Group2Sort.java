package us.lsi.stream;

import java.util.*;

class Group2Sort<K1, K2, R> extends Group2<K1,K2,R> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static <K1, K2, R> Group2Sort<K1, K2, R> create(
			SortedMap<K1, ? extends SortedMap<K2, R>> m) {
		return new Group2Sort<K1, K2, R>(m);
	}

	public String toString() {
		return toString(0);
	}

	public Group1<K2, R> getGroup(K1 x) {
		return Group1Sort.create(this.get(x));
	}

	private Group2Sort(SortedMap<K1, ? extends SortedMap<K2, R>> m) {
		super(m);
	}
}

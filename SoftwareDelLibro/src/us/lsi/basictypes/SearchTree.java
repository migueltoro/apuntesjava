package us.lsi.basictypes;
import java.util.*;

public interface SearchTree<E> {
	int size();
	boolean isEmpty();
	boolean add(E e);
	E remove(E e);
	boolean contains(E e);
	E min();
	E max();
	Comparator<E> comparator();
}

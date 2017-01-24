package us.lsi.common;

import java.util.Comparator;
import java.util.List;

/**
 * 
 * Un Comparator con más métodos
 * 
 * @author Miguel Toro
 *
 * @param <T> El tipo de los elementos sobre los que se establece el orden
 */
public interface Comparator2<T> extends Comparator<T> {
	
	default boolean isGT(T e1, T e2){
		return compare(e1, e2)>0;
	}
	
	default boolean isGE(T e1, T e2){
		return compare(e1, e2)>=0;
	}
	
	default boolean isLT(T e1, T e2){
		return compare(e1, e2)<0;
	}
	
	default boolean isLE(T e1, T e2){
		return compare(e1, e2)<=0;
	}
	
	default boolean isEQ(T e1, T e2){
		return compare(e1, e2)==0;
	}
	
	default boolean isOrdered(List<T> ls){
		boolean r = true;
		 for (int j = 0; j < ls.size()-1; j++) {
			r = isLE(ls.get(j), ls.get(j + 1));
			if(!r) break;
		}
		return r;
	}

	public static <E> Comparator2<E> from(Comparator<E> c){
		return  new Comparator2<E>(){
			@Override
			public int compare(E o1, E o2) {
				return c.compare(o1, o2);
			}		
		};
	}
}

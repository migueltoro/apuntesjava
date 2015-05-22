package us.lsi.common;

import com.google.common.base.Preconditions;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;



public class Par<T1, T2> {

	public static <T1, T2> Par<T1, T2> create(T1 p1, T2 p2) {
		return new Par<T1, T2>(p1, p2);
	}

	public static <T1, T2> Par<T1, T2> create(Par<T1, T2> t) {
		return new Par<T1, T2>(t.getP1(), t.getP2());
	}
	
	public static <T> Par<T, T> create(List<T> t) {
		Preconditions.checkArgument(t.size()==2);
		return create(t.get(0), t.get(1));
	}
	
	public static <T> Par<T, T> create(T[] t) {
		Preconditions.checkArgument(t.length==2);
		return create(t[0], t[1]);
	}
	
	public  static <A,R,S,E,U> Par<A,Par<S,U>>  transformP2N2(Par<A,Par<S,E>> tt, Function<E,U> f){	
		return create(tt.getP1(),create(tt.getP2().getP1(),f.apply(tt.getP2().getP2())));
	}
	
	public static <E1,E2> Predicate<Par<E1,E2>> predicate(BiPredicate<E1,E2> p){
		return (Par<E1,E2> pp)->p.test(pp.getP1(), pp.getP2());
	}
	
	public static <E1,E2,R> Function<Par<E1,E2>,R> function(BiFunction<E1,E2,R> f){
		return (Par<E1,E2> pp)->f.apply(pp.getP1(), pp.getP2());
	}
	
	public T1 p1;
	public T2 p2;
	protected Par(T1 p1, T2 p2) {
		super();
		this.p1 = p1;
		this.p2 = p2;
	}
	public T1 getP1() {
		return p1;
	}
	public T2 getP2() {
		return p2;
	}
	
	public Par<T1,T2> setP1(T1 v){
		return create(v,getP2());
	}
	
	public Par<T1,T2> setP2(T2 v){
		return create(getP1(),v);
	}
	
	public <R> Par<R,T2> transformP1(Function<T1,R> f){
		return create(f.apply(getP1()),getP2());
	}
	
	public <R> Par<T1,R> transformP2(Function<T2,R> f){
		return create(getP1(),f.apply(getP2()));
	}
		
	public boolean isP1(Predicate<T1> p){
		return p.test(getP1());
	}
	
	public boolean isP2(Predicate<T2> p){
		return p.test(getP2());
	}
	
	public boolean is(BiPredicate<T1,T2> p){
		return p.test(getP1(),getP2());
	}	
	
	
	
	@Override
	public String toString() {
		return "(" + p1 + "," + p2 + ")";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((p1 == null) ? 0 : p1.hashCode());
		result = prime * result + ((p2 == null) ? 0 : p2.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Par<?,?> other = (Par<?, ?>) obj;
		if (p1 == null) {
			if (other.p1 != null)
				return false;
		} else if (!p1.equals(other.p1))
			return false;
		if (p2 == null) {
			if (other.p2 != null)
				return false;
		} else if (!p2.equals(other.p2))
			return false;
		return true;
	}
	
	
	
}

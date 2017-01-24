package us.lsi.common;

import java.util.List;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;

public class Tupla3<T1, T2, T3> {

	public static <T1, T2, T3> Tupla3<T1, T2, T3> create(T1 p1, T2 p2, T3 p3) {
		return new Tupla3<T1, T2, T3>(p1, p2, p3);
	}
	
	public static <T1, T2, T3> Tupla3<T1, T2, T3> create(Tupla3<T1, T2, T3> t) {
		return new Tupla3<T1, T2, T3>(t.getP1(), t.getP2(), t.getP3());
	}
	
	public static <T> Tupla3<T, T, T> create(List<T> t) {
		Preconditions.checkArgument(t.size()==3);
		return create(t.get(0), t.get(1), t.get(2));
	}
	
	public static <T> Tupla3<T, T, T> create(T[] t) {
		Preconditions.checkArgument(t.length==3);
		return create(t[0], t[1], t[2]);
	}
	
	public static <T1, T2, T3> Tupla3<T1, T2, T3> flattenP1(Par<Par<T1,T2>,T3> t) {
		return create(t.getP1().getP1(),t.getP1().getP2(),t.getP2());
	}
	
	public static <T1, T2, T3> Tupla3<T1, T2, T3> flattenP2(Par<T1, Par<T2,T3>> t) {
		return create(t.getP1(),t.getP2().getP1(),t.getP2().getP2());
	}
	
	
	private T1 p1;
	private T2 p2;
	private T3 p3;
	
	private Tupla3(T1 p1, T2 p2, T3 p3) {
		super();
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
	}
	public T1 getP1() {
		return p1;
	}
	public T2 getP2() {
		return p2;
	}
	public T3 getP3() {
		return p3;
	}
	public Tupla3<T1,T2,T3> setP1(T1 v){
		return create(v,getP2(),getP3());
	}
	
	public Tupla3<T1,T2,T3> setP2(T2 v){
		return create(getP1(),v,getP3());
	}
	
	public Tupla3<T1,T2,T3> setP3(T3 v){
		return create(getP1(),getP2(),v);
	}

	public <R> Tupla3<R,T2,T3> transformP1(Function<T1,R> f){
		return create(f.apply(getP1()),getP2(),getP3());
	}
	
	public <R> Tupla3<T1,R,T3> transformP2(Function<T2,R> f){
		return create(getP1(),f.apply(getP2()),getP3());
	}
	
	public <R> Tupla3<T1,T2,R> transformP3(Function<T3,R> f){
		return create(getP1(),getP2(),f.apply(getP3()));
	}
	
	public boolean isP1(Predicate<T1> p){
		return p.apply(getP1());
	}
	
	public boolean isP2(Predicate<T2> p){
		return p.apply(getP2());
	}
	
	public boolean isP3(Predicate<T3> p){
		return p.apply(getP3());
	}
	
	@Override
	public String toString() {
		return "(" + p1 + "," + p2 + "," + p3 + ")";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((p1 == null) ? 0 : p1.hashCode());
		result = prime * result + ((p2 == null) ? 0 : p2.hashCode());
		result = prime * result + ((p3 == null) ? 0 : p3.hashCode());
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
		Tupla3<?, ?, ?> other = (Tupla3<?, ?, ?>) obj;
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
		if (p3 == null) {
			if (other.p3 != null)
				return false;
		} else if (!p3.equals(other.p3))
			return false;
		return true;
	}
	
	
}

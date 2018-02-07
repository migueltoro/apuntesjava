package us.lsi.common;

import java.util.List;

import com.google.common.base.Preconditions;

public class Tuple5<T1, T2, T3, T4, T5> {
	
	public static <T1, T2, T3, T4, T5> Tuple5<T1, T2, T3, T4,T5> create(T1 p1, T2 p2, T3 p3, T4 p4, T5 p5) {
		return new Tuple5<T1, T2, T3, T4, T5>(p1, p2, p3, p4, p5);
	}
	
	public static <T1, T2, T3,T4, T5> Tuple5<T1, T2, T3, T4, T5> create(Tuple5<T1, T2, T3, T4, T5> t) {
		return new Tuple5<T1, T2, T3, T4, T5>(t.getV1(), t.getV2(), t.getV3(), t.getV4(), t.getV5());
	}
	
	public static <T> Tuple5<T, T, T, T, T> create(List<T> t) {
		Preconditions.checkArgument(t.size()==5);
		return create(t.get(0), t.get(1), t.get(2), t.get(3), t.get(4));
	}
	
	public static <T> Tuple5<T, T, T, T, T> create(T[] t) {
		Preconditions.checkArgument(t.length==5);
		return create(t[0], t[1], t[2], t[3], t[4]);
	}
	
	public final T1 v1;
	public final T2 v2;
	public final T3 v3;
	public final T4 v4;
	public final T5 v5;
	
	protected Tuple5(T1 p1, T2 p2, T3 p3, T4 p4, T5 p5) {
		super();
		this.v1 = p1;
		this.v2 = p2;
		this.v3 = p3;
		this.v4 = p4;
		this.v5 = p5;
	}

	public T1 getV1() {
		return v1;
	}

	public T2 getV2() {
		return v2;
	}

	public T3 getV3() {
		return v3;
	}

	public T4 getV4() {
		return v4;
	}

	public T5 getV5() {
		return v5;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((v1 == null) ? 0 : v1.hashCode());
		result = prime * result + ((v2 == null) ? 0 : v2.hashCode());
		result = prime * result + ((v3 == null) ? 0 : v3.hashCode());
		result = prime * result + ((v4 == null) ? 0 : v4.hashCode());
		result = prime * result + ((v5 == null) ? 0 : v5.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Tuple5))
			return false;
		Tuple5<?,?,?,?,?> other = (Tuple5<?,?,?,?,?>) obj;
		if (v1 == null) {
			if (other.v1 != null)
				return false;
		} else if (!v1.equals(other.v1))
			return false;
		if (v2 == null) {
			if (other.v2 != null)
				return false;
		} else if (!v2.equals(other.v2))
			return false;
		if (v3 == null) {
			if (other.v3 != null)
				return false;
		} else if (!v3.equals(other.v3))
			return false;
		if (v4 == null) {
			if (other.v4 != null)
				return false;
		} else if (!v4.equals(other.v4))
			return false;
		if (v5 == null) {
			if (other.v5 != null)
				return false;
		} else if (!v5.equals(other.v5))
			return false;
		return true;
	}		

	@Override
	public String toString() {
		return "(" + v1 + "," + v2 + "," + v3 + ","
				+ v4 +","+ v5+")";
	}
	
}

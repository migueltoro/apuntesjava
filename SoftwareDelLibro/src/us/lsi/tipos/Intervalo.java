package us.lsi.tipos;



public class Intervalo<T extends Comparable<? super T>> {
	
	public static <T extends Comparable<? super T>> Intervalo<T> create(T li, T ls) {
		return new Intervalo<T>(li, ls);
	}

	public static <T extends Comparable<? super T>> Intervalo<T> create(Intervalo<T> it) {
		return create(it.limiteInferior,it.limiteSuperior);
	}
	
	private T limiteInferior;
	private T limiteSuperior;  
	
	
	private Intervalo(T li, T ls) {
		if(!(li.compareTo(ls)<=0)) throw new IllegalArgumentException("El Limite Superior debe ser mayor que el Inferior");
		limiteInferior = li;
		limiteSuperior = ls;
	}

	public T getLimiteInferior() {
		return limiteInferior;
	}

	public T getLimiteSuperior() {
		return limiteSuperior;
	}

	public boolean contiene(T o) {
		return limiteInferior.compareTo(o)<=0 && 
		       limiteSuperior.compareTo(o)>=0;
	}

	public boolean contiene(Intervalo<T> inter) {
		return limiteInferior.compareTo(inter.limiteInferior)<=0 && 
			   limiteSuperior.compareTo(inter.limiteSuperior)>=0;
	}
	
	@Override
	public String toString() {
		return "[" + limiteInferior + ", " + limiteSuperior + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((limiteInferior == null) ? 0 : limiteInferior.hashCode());
		result = prime * result
				+ ((limiteSuperior == null) ? 0 : limiteSuperior.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Intervalo))
			return false;
		Intervalo<?> other = (Intervalo<?>) obj;
		if (limiteInferior == null) {
			if (other.limiteInferior != null)
				return false;
		} else if (!limiteInferior.equals(other.limiteInferior))
			return false;
		if (limiteSuperior == null) {
			if (other.limiteSuperior != null)
				return false;
		} else if (!limiteSuperior.equals(other.limiteSuperior))
			return false;
		return true;
	}

}   	

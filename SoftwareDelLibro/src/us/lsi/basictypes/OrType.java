package us.lsi.basictypes;

public class OrType<U, R> {

	public static <U, R> OrType<U, R> fromU(U typeU) {
		return new OrType<U, R>(typeU, null);
	}

	public static <U, R> OrType<U, R> fromR(R typeR) {
		return new OrType<U, R>(null, typeR);
	}
	
	private U typeU;
	private R typeR;
	
	private OrType(U typeU, R typeR) {
		super();
		this.typeU = typeU;
		this.typeR = typeR;
	}

	public U getU() {
		return typeU;
	}

	public R getR() {
		return typeR;
	}
	
	boolean isU(){
		return this.typeU != null;
	}
	
	boolean isR(){
		return this.typeR != null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((typeR == null) ? 0 : typeR.hashCode());
		result = prime * result + ((typeU == null) ? 0 : typeU.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof OrType))
			return false;
		OrType<?,?> other = (OrType<?,?>) obj;
		if (typeR == null) {
			if (other.typeR != null)
				return false;
		} else if (!typeR.equals(other.typeR))
			return false;
		if (typeU == null) {
			if (other.typeU != null)
				return false;
		} else if (!typeU.equals(other.typeU))
			return false;
		return true;
	}
	
	
}

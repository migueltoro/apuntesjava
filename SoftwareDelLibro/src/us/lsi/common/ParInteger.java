package us.lsi.common;


public class ParInteger extends Par<Integer, Integer> {

	private ParInteger(Integer p1, Integer p2) {
		super(p1,p2);
	}

	public static ParInteger create(Integer p1, Integer p2) {
		return new ParInteger(p1, p2);
	}

	public static ParInteger create(ParInteger t) {
		return new ParInteger(t.getP1(), t.getP2());
	}
	
	
	
}

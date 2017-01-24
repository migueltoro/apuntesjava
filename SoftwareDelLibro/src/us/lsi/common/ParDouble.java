package us.lsi.common;


public class ParDouble extends Par<Double, Double> {

	private ParDouble(Double p1, Double p2) {
		super(p1,p2);
	}

	public static ParDouble create(Double p1, Double p2) {
		return new ParDouble(p1, p2);
	}

	public static ParDouble create(ParDouble t) {
		return new ParDouble(t.getP1(), t.getP2());
	}
	
	
	
}
package us.lsi.common;


public class PairDouble extends Tuple2<Double, Double> {

	private PairDouble(Double p1, Double p2) {
		super(p1,p2);
	}

	public static PairDouble create(Double p1, Double p2) {
		return new PairDouble(p1, p2);
	}

	public static PairDouble create(PairDouble t) {
		return new PairDouble(t.getV1(), t.getV2());
	}
	
	
	
}
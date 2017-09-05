package us.lsi.common;


public class PairInteger extends Tuple2<Integer, Integer> {

	private PairInteger(Integer p1, Integer p2) {
		super(p1,p2);
	}

	public static PairInteger create(Integer p1, Integer p2) {
		return new PairInteger(p1, p2);
	}

	public static PairInteger create(PairInteger t) {
		return new PairInteger(t.getV1(), t.getV2());
	}
	
}

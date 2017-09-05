package us.lsi.common;

public class PairLong extends Tuple2<Long, Long> {

	public PairLong(Long p1, Long p2) {
		super(p1, p2);
	}
	
	public static PairLong create(Long p1, Long p2) {
		return new PairLong(p1, p2);
	}

	public static PairLong create(PairLong t) {
		return new PairLong(t.getV1(), t.getV2());
	}
}

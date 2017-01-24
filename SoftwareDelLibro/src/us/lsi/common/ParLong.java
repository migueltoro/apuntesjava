package us.lsi.common;

public class ParLong extends Par<Long, Long> {

	public ParLong(Long p1, Long p2) {
		super(p1, p2);
	}
	
	public static ParLong create(Long p1, Long p2) {
		return new ParLong(p1, p2);
	}

	public static ParLong create(ParLong t) {
		return new ParLong(t.getP1(), t.getP2());
	}
}

package us.lsi.bt.sudoku;

public class Casilla {

	public static Casilla create(Integer x, Integer y, Integer sc,
			boolean isFree, Integer info, Integer pos) {
		return new Casilla(x, y, sc, isFree, info, pos);
	}

	public Integer x;
	public Integer y;
	public Integer sc;
	public boolean isFree;
	public Integer info;
	public Integer pos;
	
	private Casilla(Integer x, Integer y, Integer sc, boolean isFree,
			Integer info, Integer pos) {
		super();
		this.x = x;
		this.y = y;
		this.sc = sc;
		this.isFree = isFree;
		this.info = info;
		this.pos = pos;
	}
	
	
	
}

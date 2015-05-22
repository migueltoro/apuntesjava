package us.lsi.bt.sudoku;

public class ParInteger {
	
	public static ParInteger create(Integer x, Integer y1, Integer y2) {
		return new ParInteger(x, y1, y2);
	}


	final Integer x;
	final Integer y1;
	final Integer y2;
	
	
	private ParInteger(Integer x, Integer y1, Integer y2) {
		super();
		this.x = x;
		this.y1 = y1;
		this.y2 = y2;
	}
	
	
}

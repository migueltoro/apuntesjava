package us.lsi.sa.sudoku;

import com.google.common.base.Preconditions;

/**
 * @author Miguel Toro
 *
 *<p> La casilla de un Sudoku
 */
public class Casilla {

	public static Casilla create(Integer x, Integer y) {
		return new Casilla(x, y);
	}

	public static Casilla create(Integer p) {
		return new Casilla(p);
	}

	/**
	 * La coordenada x de la casilla en 0..CuadroSudoku.numeroDeFilas-1
	 */
	public Integer x;
	/**
	 * La coordenada y de la casilla en 0..CuadroSudoku.numeroDeFilas-1
	 */
	public Integer y;
	/**
	 * El subcuadro de la casilla en 0..CuadroSudoku.numeroDeFilas-1
	 */
	public Integer sc;
	/**
	 * La posición de la casilla
	 */
	public Integer p;
	/**
	 * Si la casilla está libre
	 */
	public boolean isFree;
	
	private Casilla(Integer x, Integer y) {
		super();
		Preconditions.checkArgument(0 <= x && x < CuadroSudoku.numeroDeFilas);
		Preconditions.checkArgument(0 <= y && y < CuadroSudoku.numeroDeFilas);
		this.x = x;
		this.y = y;
		this.sc = 
	    this.p = y*CuadroSudoku.numeroDeFilas+x;	
		this.isFree = !CuadroSudoku.casillasOcupadas.contains(this.x, this.y);
	}
	
	private Casilla(Integer p){
		Preconditions.checkArgument(0<=p && p < CuadroSudoku.numeroDeCasillas);	
		this.y = p/CuadroSudoku.numeroDeFilas;
		this.x = p%CuadroSudoku.numeroDeFilas;
		Integer tm = CuadroSudoku.tamSubCuadro;
		this.sc = x/tm+tm*(y/tm);
		this.p = p;
		this.isFree = !CuadroSudoku.casillasOcupadas.contains(this.x, this.y);
	}

	@Override
	public String toString() {
		return "(" + x + "," + y + "," + sc + "," + p
				+ "," + isFree + ")";
	}

	
}

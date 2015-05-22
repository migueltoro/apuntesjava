package us.lsi.sa.sudoku;

import us.lsi.bt.sudoku.CuadroSudoku;
import us.lsi.bt.sudoku.ProblemaSudoku;
import us.lsi.common.ParInteger;
import us.lsi.math.Math2;
import us.lsi.sa.EstadoSA;

public class EstadoSudokuSAAp1 implements EstadoSA<EstadoSudokuSAAp1, CuadroSudoku, ParInteger> {
	
	public static EstadoSudokuSAAp1 create(CuadroSudoku cuadro) {
		return new EstadoSudokuSAAp1(cuadro);
	}

	CuadroSudoku cuadro;
		
	EstadoSudokuSAAp1(CuadroSudoku cuadro) {
		super();
		this.cuadro = CuadroSudoku.create(cuadro);
	}

	@Override
	public EstadoSudokuSAAp1 next(ParInteger a) {
		CuadroSudoku c = CuadroSudoku.create(this.cuadro);
		c.intercambia(a.p1, a.p2);		
		return EstadoSudokuSAAp1.create(c);
	}

	@Override
	public ParInteger getAlternativa() {
		Integer x = Math2.getEnteroAleatorio(0, ProblemaSudoku.numeroDeFilas);		
		ParInteger p = Math2.getParAleatorioYDistinto(0,ProblemaSudoku.posicionesLibresEnX.get(x).size());
		return ParInteger.create(ProblemaSudoku.posicionesLibresEnX.get(x).get(p.p1), ProblemaSudoku.posicionesLibresEnX.get(x).get(p.p2));
	}

	@Override
	public CuadroSudoku getSolucion() {
		return this.cuadro;
	}

	@Override
	public boolean condicionDeParada() {
		return this.getObjetivo()==0;
	}

	@Override
	public double getObjetivo() {
		return this.cuadro.getObjetivo();
	}

	@Override
	public EstadoSudokuSAAp1 copia() {
		return EstadoSudokuSAAp1.create(this.cuadro);
	}
	
	@Override
	public boolean esSolucion(CuadroSudoku s) {
		return ProblemaSudoku.esSolucion(s);
	}

}

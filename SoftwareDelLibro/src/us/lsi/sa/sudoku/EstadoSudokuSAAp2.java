package us.lsi.sa.sudoku;


import us.lsi.bt.sudoku.CuadroSudoku;
import us.lsi.bt.sudoku.ProblemaSudoku;
import us.lsi.common.ParInteger;
import us.lsi.math.Math2;
import us.lsi.sa.EstadoSA;

public class EstadoSudokuSAAp2 implements
		EstadoSA<EstadoSudokuSAAp2, CuadroSudoku, ParInteger> {

	public static EstadoSudokuSAAp2 create(CuadroSudoku cuadro) {
		return new EstadoSudokuSAAp2(cuadro);
	}

	CuadroSudoku cuadro;

	EstadoSudokuSAAp2(CuadroSudoku cuadro) {
		this.cuadro = cuadro;
	}

	@Override
	public EstadoSudokuSAAp2 next(ParInteger a) {
		CuadroSudoku c = CuadroSudoku.create(this.cuadro);
		c.intercambia(a.p1, a.p2);
		return EstadoSudokuSAAp2.create(c);
	}

	@Override
	public ParInteger getAlternativa() {
		ParInteger r;
		Integer x = Math2.getEnteroAleatorio(0, ProblemaSudoku.numeroDeFilas);
		ParInteger p = Math2.getParAleatorioYDistinto(0,
				ProblemaSudoku.posicionesLibresEnSubCuadro.get(x).size());
		r = ParInteger.create(ProblemaSudoku.posicionesLibresEnSubCuadro.get(x)
				.get(p.p1), ProblemaSudoku.posicionesLibresEnSubCuadro.get(x)
				.get(p.p2));
		return r;
	}

	@Override
	public CuadroSudoku getSolucion() {
		return this.cuadro;
	}

	@Override
	public boolean condicionDeParada() {
		return this.getObjetivo() == 0;
	}

	@Override
	public double getObjetivo() {
		return this.cuadro.getObjetivo();
	}

	@Override
	public EstadoSudokuSAAp2 copia() {
		return EstadoSudokuSAAp2.create(this.cuadro);
	}

	@Override
	public boolean esSolucion(CuadroSudoku s) {
		return ProblemaSudoku.esSolucion(s);
	}
}

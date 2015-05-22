package us.lsi.bt.sudoku;

import us.lsi.bt.EstadoBT;
import us.lsi.stream.Stream2;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;


public class EstadoSudokuBT implements EstadoBT<CuadroSudoku, Integer> {

	public static EstadoSudokuBT create() {
		return new EstadoSudokuBT();
	}

	public static EstadoSudokuBT create(CuadroSudoku cuadro, Integer pos) {
		return new EstadoSudokuBT(cuadro, pos);
	}

	public CuadroSudoku cuadro;
	public Integer pos;
	private int x;
	private int y;
	
	private EstadoSudokuBT() {
		super();
		this.cuadro = CuadroSudoku.create();
		this.pos = 0;
		calculaDerivadas();
	}
	
	private EstadoSudokuBT(CuadroSudoku cuadro, Integer pos) {
		super();
		this.cuadro = CuadroSudoku.create(cuadro);		
		this.pos = pos;	
		calculaDerivadas();
	}
	
	private void calculaDerivadas() {	
		if (this.size() >0) {
			Casilla casilla = ProblemaSudoku.getCasilla(ProblemaSudoku.posicionesLibres.get(pos));
			this.x = casilla.x;
			this.y = casilla.y;
		}
	}
	
	@Override
	public void avanza(Integer a) {
		this.cuadro.setInfo(this.x, this.y, a);		
		this.pos = this.pos+1;
		calculaDerivadas();
	}

	@Override
	public void retrocede(Integer a) {		
		this.pos = this.pos-1;
		calculaDerivadas();
		this.cuadro.setInfo(x, y, 0);	
	}

	@Override
	public int size() {
		return ProblemaSudoku.posicionesLibres.size()-pos;
	}

	@Override
	public boolean isFinal() {		
		return this.size()==0;
	}

	@Override
	public List<Integer> getAlternativas() {
		List<Integer> r;
		if (this.size() <= 0) {
			r = Stream2.<Integer> empty().toList();
		} else {
			Stream<Integer> st = IntStream
					.rangeClosed(1, ProblemaSudoku.numeroDeFilas)
					.filter((int a) -> !this.cuadro.getValoresOcupadosEnPos(this.pos).contains(a)).boxed();
			r = Stream2.create(st).toList();
		}
		return r;
	}

	@Override
	public CuadroSudoku getSolucion() {
		return CuadroSudoku.create(this.cuadro);
	}

	@Override
	public Double getObjetivo() {
		return 0.;
	}

	@Override
	public Double getObjetivoEstimado(Integer a) {
		return Double.MAX_VALUE;
	}
	
}

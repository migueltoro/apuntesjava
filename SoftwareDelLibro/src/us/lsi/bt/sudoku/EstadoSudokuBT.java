package us.lsi.bt.sudoku;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;

import us.lsi.bt.EstadoBT;
import us.lsi.sa.sudoku.CuadroSudoku;

public class EstadoSudokuBT implements EstadoBT<CuadroSudoku, Integer> {

	public static EstadoSudokuBT create(List<Integer> alternativasYaEscogidas) {
		return new EstadoSudokuBT(alternativasYaEscogidas);
	}

	public static EstadoSudokuBT create() {
		return new EstadoSudokuBT(Lists.newArrayList());
	}
	
	private int pos;
	private List<Integer> alternativasYaEscogidas;
	private CuadroSudoku cuadro;
		
	private EstadoSudokuBT(List<Integer> alternativasYaEscogidas) {
		super();
		this.pos = alternativasYaEscogidas.size();
		this.alternativasYaEscogidas = alternativasYaEscogidas;
		this.cuadro = CuadroSudoku.create(completa(this.alternativasYaEscogidas));
	}
	
	private List<Integer> completa(List<Integer> ls){
		List<Integer> plus = Collections.nCopies(CuadroSudoku.numPosicionesLibres-ls.size(), 0);
		List<Integer> r = Lists.newArrayList(ls);
		r.addAll(plus);
		return r;
	}
	
	@Override
	public Tipo getTipo() {
		return Tipo.AlgunasSoluciones;
	}

	@Override
	public EstadoBT<CuadroSudoku, Integer> getEstadoInicial() {
		return EstadoSudokuBT.create();
	}

	
	@Override
	public EstadoSudokuBT avanza(Integer a) {
		this.alternativasYaEscogidas.add(a);
		this.pos = this.alternativasYaEscogidas.size();	
		this.cuadro = CuadroSudoku.create(completa(this.alternativasYaEscogidas));
		return this;
	}

	@Override
	public EstadoSudokuBT retrocede(Integer a) {
		this.alternativasYaEscogidas.remove(pos-1);
		this.pos = this.alternativasYaEscogidas.size();
		this.cuadro = CuadroSudoku.create(completa(this.alternativasYaEscogidas));
		return this;
	}

	@Override
	public int size() {
		return CuadroSudoku.numPosicionesLibres-this.pos;
	}

	@Override
	public boolean esCasoBase() {
		boolean r = this.pos == CuadroSudoku.numPosicionesLibres;
		return r;
	}

	@Override
	public List<Integer> getAlternativas() {
		List<Integer> ls = cuadro.getValoresLibresEnPos(CuadroSudoku.posicionesLibres.get(this.pos)).stream().collect(Collectors.toList());
		return ls;
	}

	@Override
	public CuadroSudoku getSolucion() {
		return this.cuadro;
	}	

	@Override
	public Double getObjetivoEstimado(Integer a) {
		return Double.MAX_VALUE;
	}

	public List<Integer> getAlternativasYaEscogidas() {
		return alternativasYaEscogidas;
	}

	
	
}

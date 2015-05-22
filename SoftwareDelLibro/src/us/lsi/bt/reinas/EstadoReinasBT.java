package us.lsi.bt.reinas;

import java.util.List;
import java.util.stream.Stream;
import java.util.stream.IntStream;

import com.google.common.base.Preconditions;

import us.lsi.bt.EstadoBT;
import us.lsi.stream.Stream2;

public class EstadoReinasBT implements EstadoBT<List<Reina>, Integer> {


	public static EstadoReinasBT create() {
		return new EstadoReinasBT();
	}

	private TableroDeReinas tablero;
		
	private EstadoReinasBT(TableroDeReinas tablero) {
		super();
		this.tablero = TableroDeReinas.create(tablero);
	}

	private EstadoReinasBT() {
		super();
		this.tablero = TableroDeReinas.create();
	}
	
	@Override
	public void avanza(Integer a) {
		this.tablero = this.tablero.add(a);		
	}

	@Override
	public void retrocede(Integer a) {
		Preconditions.checkArgument(a.equals(this.tablero.last()));
		this.tablero = this.tablero.remove();	
	}

	@Override
	public int size() {
		return ProblemaReinasBT.numeroDeReinas-tablero.getNumDeReinas();
	}

	@Override
	public boolean isFinal() {
		return size() <=0;
	}

	
	@Override
	public List<Integer> getAlternativas() {
		Stream<Integer> s = IntStream
				.range(0, ProblemaReinasBT.numeroDeReinas)
				.filter((int y) -> {
					Reina r = Reina.create(tablero.getNumDeReinas(), y);
					return !tablero.getDiagonalesPrincipalesOcupadas().contains(r.getDiagonalPrincipal())
							&& !tablero.getDiagonalesSecundariasOcupadas().contains(r.getDiagonalSecundaria())
							&& !tablero.getYOcupadas().contains(r.getY());
				}).boxed();
		return Stream2.create(s).toList();
	}

	@Override
	public List<Reina> getSolucion() {
		return tablero.getReinas();
	}

	@Override
	public Double getObjetivo() {
		return -this.tablero.getObjetivo();
	}

	@Override
	public Double getObjetivoEstimado(Integer a) {
		return Double.MAX_VALUE;
	}

	
}

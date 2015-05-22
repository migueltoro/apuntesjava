package us.lsi.sa.reinas;

import us.lsi.bt.reinas.Reina;
import us.lsi.bt.reinas.TableroDeReinas;
import us.lsi.common.*;
import us.lsi.math.Math2;
import us.lsi.sa.EstadoSA;
import us.lsi.stream.Stream2;

import java.util.*;

public class EstadoReinasSA implements EstadoSA<EstadoReinasSA, List<Reina>, ParInteger> {

	public static EstadoReinasSA create(TableroDeReinas tablero) {
		return new EstadoReinasSA(tablero);
	}

	public TableroDeReinas tablero;
	
	private EstadoReinasSA(TableroDeReinas tablero) {
		super();
		this.tablero = tablero;
	}

	@Override
	public EstadoReinasSA next(ParInteger a) {
		return create(tablero.intercambia(a.p1, a.p2));
	}

	@Override
	public ParInteger getAlternativa() {		
		return Math2.getParAleatorioYDistinto(0, tablero.getNumDeReinas());
	}

	@Override
	public List<Reina> getSolucion() {
		return tablero.getReinas();
	}

	@Override
	public boolean condicionDeParada() {
		return tablero.getObjetivo() == 0.;
	}

	@Override
	public EstadoReinasSA copia() {
		return create(tablero.copia());
	}

	@Override
	public double getObjetivo() {
		return tablero.getObjetivo();
	}
	
	@Override
	public boolean esSolucion(List<Reina> s) {
		List<Integer> ls = Stream2.create(s.stream().mapToInt((Reina r)->r.getY()).boxed()).toList();
		return TableroDeReinas.create(ls).getObjetivo() == 0;
	}
	
	public static void main(String[] args) {
		EstadoReinasSA e = ProblemaReinasSA.create().getEstadoInicial();
		System.out.println(e.getObjetivo());
		System.out.println(e.getSolucion());
		System.out.println(e.tablero.getObjetivo());
		System.out.println(e.tablero.getReinas());
		System.out.println(e.tablero.getYOcupadas());
		System.out.println(e.tablero.getDiagonalesPrincipalesOcupadas());
		System.out.println(e.tablero.getDiagonalesSecundariasOcupadas());
		ParInteger a = Math2.getParAleatorioYDistinto(0, e.tablero.getNumDeReinas());
		System.out.println(a);
		EstadoReinasSA ne = e.next(a);
		System.out.println(ne.tablero.getObjetivo());
		System.out.println(ne.tablero.getReinas());
		System.out.println(ne.tablero.getYOcupadas());
		System.out.println(ne.tablero.getDiagonalesPrincipalesOcupadas());
		System.out.println(ne.tablero.getDiagonalesSecundariasOcupadas());		
	}
	
}

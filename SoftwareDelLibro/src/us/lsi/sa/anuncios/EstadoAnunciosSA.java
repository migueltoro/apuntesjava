package us.lsi.sa.anuncios;


import java.util.List;

import us.lsi.bt.anuncios.ListaDeAnunciosAEmitir;
import us.lsi.bt.anuncios.ProblemaAnuncios;
import us.lsi.common.*;
import us.lsi.math.Math2;
import us.lsi.sa.EstadoSA;

public class EstadoAnunciosSA implements EstadoSA<EstadoAnunciosSA,ListaDeAnunciosAEmitir,AlternativaAnuncios>{
	
	public static EstadoAnunciosSA create(EstadoAnunciosSA e) {
		return new EstadoAnunciosSA(ListaDeAnunciosAEmitir.create(e.lista));
	}
	
	public static EstadoAnunciosSA create(ListaDeAnunciosAEmitir lista) {
		return new EstadoAnunciosSA(lista);
	}

	public static EstadoAnunciosSA create() {
		return new EstadoAnunciosSA(ListaDeAnunciosAEmitir.create());
	}
	
	private ListaDeAnunciosAEmitir lista;

	private EstadoAnunciosSA(ListaDeAnunciosAEmitir lista) {
		super();
		this.lista = ListaDeAnunciosAEmitir.create(lista);
	}

	@Override
	public EstadoAnunciosSA next(AlternativaAnuncios a) {
		ListaDeAnunciosAEmitir e = null;;
		switch(a.opcion){
		case Insertar :
			e = this.lista.insertar(a.p1, a.p2);
			break;
		case Eliminar :
			e = this.lista.eliminar(a.p1);
			break;
		case Intercambiar :
			e = this.lista.intercambiar(a.p1,a.p2);
		}	
		return EstadoAnunciosSA.create(e);
	}
	

	@Override
	public AlternativaAnuncios getAlternativa() {		
		List<AlternativaAnuncios.Opcion> ls = lista.getTiposDeOpcionesAlternativasPosibles();
		AlternativaAnuncios.Opcion op;
		AlternativaAnuncios a = null;
		int e;
		if(ls.size()==1){
			e = 0;
		} else if(ls.size() == 2){
			e = Math2.escogeEntre(0.5,0.5);
		} else {
			e = Math2.escogeEntre(0.4,0.1,0.5);
		}
		op = ls.get(e);
		switch(op){
		case Insertar:
			ParInteger p = this.lista.getAlternativaInsertar();
			a = AlternativaAnuncios.createInsertar(p.p1, p.p2);
			break;
		case Eliminar:
			Integer e2 = this.lista.getAlternativaEliminar();
			a = AlternativaAnuncios.createEliminar(e2);
			break;
		case Intercambiar:
			ParInteger p2 = this.lista.getAlternativaIntercambiar();
			a = AlternativaAnuncios.createIntercambiar(p2.p1, p2.p2);
			break;
		}
		return a;
	}
	
	@Override
	public ListaDeAnunciosAEmitir getSolucion() {
		return lista;
	}

	@Override
	public boolean condicionDeParada() {
		return false;
	}

	@Override
	public double getObjetivo() {
		return -lista.getValor();
	}

	@Override
	public EstadoAnunciosSA copia() {
		return EstadoAnunciosSA.create(this);
	}

	@Override
	public boolean esSolucion(ListaDeAnunciosAEmitir s) {
		return true;
	}
	
	@Override
	public String toString() {
		return "EstadoAnunciosSA [lista=" + lista + "]";
	}	
	
	public static void main(String[] args) {
		ProblemaAnuncios.tiempoTotal = 40;
		ProblemaAnuncios.leeYOrdenaAnuncios("anuncios.txt");
		EstadoAnunciosSA e = ProblemaAnunciosSA.create().getEstadoInicial();
		System.out.println(e);		
		AlternativaAnuncios a = e.getAlternativa();
		System.out.println("2 "+a);
		EstadoAnunciosSA e2 = e.next(a);
		System.out.println("2 "+e2);
		System.out.println("2 "+e2.getSolucion());
		a = e2.getAlternativa();
		System.out.println("3 "+a);
		EstadoAnunciosSA e3 = e2.next(a);
		System.out.println("3 "+e3);
		System.out.println("3 "+e3.getSolucion());
		System.out.println(ProblemaAnuncios.tiempoTotal);
	}
}

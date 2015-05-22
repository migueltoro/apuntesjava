package us.lsi.bt.anuncios;


import us.lsi.bt.ProblemaBT;

public class ProblemaAnunciosBT extends ProblemaAnuncios implements ProblemaBT<ListaDeAnunciosAEmitir, Integer>{

	public static ProblemaAnunciosBT create() {
		return new ProblemaAnunciosBT();
	}

	private ProblemaAnunciosBT() {
		super();
	}
	
	@Override
	public EstadoAnunciosBT getEstadoInicial() {
		return EstadoAnunciosBT.create();
	}

	@Override
	public Tipo getTipo() {
		return Tipo.Max;
	}

	
}

package us.lsi.sa.anuncios;


import us.lsi.bt.anuncios.ListaDeAnunciosAEmitir;
import us.lsi.bt.anuncios.ProblemaAnuncios;
import us.lsi.sa.ProblemaSA;


public class ProblemaAnunciosSA extends ProblemaAnuncios implements ProblemaSA<EstadoAnunciosSA,ListaDeAnunciosAEmitir,AlternativaAnuncios>{

	public static ProblemaAnunciosSA create() {
		return new ProblemaAnunciosSA();
	}

	private ProblemaAnunciosSA() {
		super();
	}
	
	@Override
	public EstadoAnunciosSA getEstadoInicial() {
		ListaDeAnunciosAEmitir ls = ListaDeAnunciosAEmitir.getSolucionVoraz();
		return EstadoAnunciosSA.create(ls);
	}
	
}

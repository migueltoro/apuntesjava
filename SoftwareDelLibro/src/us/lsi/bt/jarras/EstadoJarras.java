package us.lsi.bt.jarras;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import us.lsi.bt.EstadoBT;
import us.lsi.common.PairInteger;
import us.lsi.pd.jarras.Accion;
import us.lsi.pd.jarras.ProblemaJarrasPD;
import us.lsi.pd.jarras.SolucionJarras;

public class EstadoJarras implements EstadoBT<SolucionJarras, Accion> {
	
	public static EstadoJarras create() {
		return new EstadoJarras();
	}

	private Integer cantidadEnJ1;
	private Integer cantidadEnJ2;

	private SolucionJarras sol;
	private List<PairInteger> incrementosEnAvanza;
	
	private EstadoJarras() {
		super();
		this.cantidadEnJ1 = 0;
		this.cantidadEnJ2 = 0;
		this.sol = SolucionJarras.create();
		this.incrementosEnAvanza = new ArrayList<>();
	}
	
	public Integer getJ1() {
		return cantidadEnJ1;
	}

	public Integer getJ2() {
		return cantidadEnJ2;
	}

	public void setCantidades(Integer j1,Integer j2) {
		this.cantidadEnJ1 = j1;
		this.cantidadEnJ2 = j2;
	}

	public SolucionJarras getSol() {
		return sol;
	}

	@Override
	public Tipo getTipo() {
		return Tipo.Min;
	}

	@Override
	public EstadoBT<SolucionJarras, Accion> getEstadoInicial() {
		return create();
	}
	
	@Override
	public List<Accion> getAlternativas() {
		return IntStream.range(0,Accion.acciones.size())
				.boxed()
				.map(x->Accion.acciones.get(x))
				.filter(x->x.isAplicable(this.cantidadEnJ1,this.cantidadEnJ2))
				.collect(Collectors.toList());
	}

	
	@Override
	public EstadoJarras avanza(Accion a) {
		this.sol.add(a);
		PairInteger p = a.ejecuta(PairInteger.create(this.cantidadEnJ1,this.cantidadEnJ2));
		PairInteger inc = PairInteger.create(p.getV1()-this.cantidadEnJ1, p.getV2()-this.cantidadEnJ2);
		this.cantidadEnJ1 = p.v1;
		this.cantidadEnJ2 = p.v2;
		this.incrementosEnAvanza.add(inc);
		return this;
	}

	@Override
	public EstadoJarras retrocede(Accion a) {
		this.sol.removeLast();	
//		Una solución alternativa
//		PairInteger p = Accion.ejecuta(PairInteger.create(0, 0), sol.getListaAcciones());		
//		this.cantidadEnJ1 = p.p1;
//		this.cantidadEnJ2 = p.p2;
		PairInteger p = this.incrementosEnAvanza.remove((int) this.sol.getNumAcc());
		this.cantidadEnJ1 = this.cantidadEnJ1-p.v1;
		this.cantidadEnJ2 = this.cantidadEnJ2-p.v2;
		return this;
	}

	@Override
	public int size() {
		return ProblemaJarrasPD.numMaxAcciones-this.sol.getNumAcc();
	}


	@Override
	public boolean esCasoBase() {
		return ProblemaJarrasPD.cantidadFinalEnJarra1.equals(this.cantidadEnJ1)
				&& ProblemaJarrasPD.cantidadFinalEnJarra2.equals(this.cantidadEnJ2);
	}
	
	@Override
	public boolean estaFueraDeRango(){
		return this.sol.getNumAcc() > ProblemaJarrasPD.numMaxAcciones;
	}

	@Override
	public SolucionJarras getSolucion() {
		return SolucionJarras.create(this.sol.getListaAcciones());
	}	

	@Override
	public Double getObjetivoEstimado(Accion a) {
		return (double) this.sol.getNumAcc();
	}

	@Override
	public String toString() {
		return "[J1=" + cantidadEnJ1 + ",J2="
				+ cantidadEnJ2 + "]";
	}

	

}

package us.lsi.pd.jarras;


import java.util.List;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;



import us.lsi.common.PairInteger;
import us.lsi.pd.AlgoritmoPD.Sp;
import us.lsi.pd.ProblemaPD;
import us.lsi.pd.ProblemaPDR;

public class ProblemaJarrasPD implements ProblemaPDR<SolucionJarras, Accion> {

	public static Integer numMaxAcciones;
	public static Integer capacidadJarra2;
	public static Integer capacidadJarra1;
	public static Integer cantidadFinalEnJarra2;
	public static Integer cantidadFinalEnJarra1;
	
	public static ProblemaJarrasPD objetivo;	
	
	public static ProblemaJarrasPD create() {
		return new ProblemaJarrasPD();
	}

	public static ProblemaJarrasPD create(Integer cantidadEnJ1,Integer cantidadEnJ2, Integer numOp) {
		return new ProblemaJarrasPD(cantidadEnJ1, cantidadEnJ2, numOp);
	}

	private Integer cantidadEnJ1;
	private Integer cantidadEnJ2;
	private Integer numOp;
	
	
	private ProblemaJarrasPD() {
		super();
		this.cantidadEnJ1 = 0;
		this.cantidadEnJ2 = 0;		
		this.numOp =0;
	}

	private ProblemaJarrasPD(Integer cantidadEnJ1, Integer cantidadEnJ2, int numOp) {
		super();
		this.cantidadEnJ1 = cantidadEnJ1;
		this.cantidadEnJ2 = cantidadEnJ2;
		this.numOp = numOp;
	}

	public Integer getCantidadEnJ1() {
		return cantidadEnJ1;
	}

	public Integer getCantidadEnJ2() {
		return cantidadEnJ2;
	}

	@Override
	public Tipo getTipo() {
		return Tipo.Min;
	}

	@Override
	public int size() {
		return ProblemaJarrasPD.numMaxAcciones-this.numOp;
	}

	@Override
	public boolean esCasoBase() {
		return (ProblemaJarrasPD.cantidadFinalEnJarra1.equals(cantidadEnJ1)
				&& ProblemaJarrasPD.cantidadFinalEnJarra2.equals(cantidadEnJ2));
	}

	@Override
	public boolean estaFueraDeRango() {
		return 	this.numOp > ProblemaJarrasPD.numMaxAcciones;
	}
	
	@Override
	public Sp<Accion> getSolucionParcialCasoBase() {
		return  Sp.create(null, 0.);
	}

	@Override
	public Sp<Accion> getSolucionParcial(List<Sp<Accion>> ls) {
		return ls.stream().min(Comparator.naturalOrder()).get();
	}

	@Override
	public ProblemaPD<SolucionJarras, Accion> getSubProblema(Accion a) {
		PairInteger r = a.ejecuta(PairInteger.create(this.cantidadEnJ1,this.cantidadEnJ2));	
		ProblemaJarrasPD p = ProblemaJarrasPD.create(r.v1,r.v2,this.numOp+1);
		return p;
	}

	@Override
	public Sp<Accion> getSolucionParcialPorAlternativa(Accion a, Sp<Accion> r) {
		return Sp.create(a, r.propiedad+1);
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
	public SolucionJarras getSolucionReconstruidaCasoBase(Sp<Accion> sp) {
		return SolucionJarras.create();
	}

	@Override
	public SolucionJarras getSolucionReconstruidaCasoRecursivo(Sp<Accion> sp, SolucionJarras r) {
		r.addFirst(sp.alternativa);
		return r;
	}

	@Override
	public Double getObjetivoEstimado(Accion a) {
		return (double)this.numOp+1;
	}

	@Override
	public Double getObjetivo() {
		Double r = null;		
		if (esCasoBase()) {
			r = (double) this.numOp;
		} 
		return r;
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((cantidadEnJ1 == null) ? 0 : cantidadEnJ1.hashCode());
		result = prime * result
				+ ((cantidadEnJ2 == null) ? 0 : cantidadEnJ2.hashCode());
		result = prime * result + ((numOp == null) ? 0 : numOp.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ProblemaJarrasPD))
			return false;
		ProblemaJarrasPD other = (ProblemaJarrasPD) obj;
		if (cantidadEnJ1 == null) {
			if (other.cantidadEnJ1 != null)
				return false;
		} else if (!cantidadEnJ1.equals(other.cantidadEnJ1))
			return false;
		if (cantidadEnJ2 == null) {
			if (other.cantidadEnJ2 != null)
				return false;
		} else if (!cantidadEnJ2.equals(other.cantidadEnJ2))
			return false;
		if (numOp == null) {
			if (other.numOp != null)
				return false;
		} else if (!numOp.equals(other.numOp))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "[J1=" + cantidadEnJ1
				+ ",J2=" + cantidadEnJ2 + ", numOp=" + numOp + "]";
	}

	
	
}

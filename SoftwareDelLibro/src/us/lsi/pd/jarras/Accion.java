package us.lsi.pd.jarras;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

import us.lsi.common.PairInteger;
import us.lsi.stream.Stream2;

public class Accion {

	
	public static Accion create(String s){
		return new Accion(s);
	}
	
	public static Accion create(Integer codigo, String operacion, String descripcion){
		return new Accion(codigo, operacion, descripcion);
	}

	public static List<Accion> acciones;
	
	private Integer codigo;
	private String descripcionCorta;
	private String descripcion;
	public BiPredicate<Integer,Integer> aplicable;
	public BiFunction<Integer,Integer,PairInteger> actualiza;
	private static PairInteger pair = PairInteger.create(0, 0);

	public Accion(String s){
		String[] v = s.split(",");
		Integer ne = v.length;
		if(ne != 3) throw new IllegalArgumentException("Formato no adecuado en línea  "+s);	
		this.codigo = new Integer(v[0]);
		this.descripcionCorta = v[1];
		this.descripcion = v[2];		
	}
	
	public Accion(Integer codigo, String descripcionCorta, String descripcion) {
		super();
		this.codigo = codigo;
		this.descripcionCorta = descripcionCorta;
		this.descripcion = descripcion;
	}

	public static  void iniciaAcciones(String fileOperaciones) {
		Accion.acciones = Stream2.fromFile(fileOperaciones)
				.map((String s) -> Accion.create(s))
				.collect(Collectors.toList());
		
		Accion.acciones.get(0).aplicable = (x1,x2)->x1>0 ;
		Accion.acciones.get(1).aplicable = (x1,x2)->x1>0 ;
		Accion.acciones.get(2).aplicable = (x1,x2)->x1>0 ;
		Accion.acciones.get(3).aplicable = (x1,x2)->x1 < ProblemaJarrasPD.capacidadJarra1 ;
		Accion.acciones.get(4).aplicable = (x1,x2)->x2 < ProblemaJarrasPD.capacidadJarra1 ;
		Accion.acciones.get(5).aplicable = (x1,x2)->x2>0 ;
		Accion.acciones.get(6).aplicable = (x1,x2)->x2>0 ;
		Accion.acciones.get(7).aplicable = (x1,x2)->x2>0 ;
		
		Accion.acciones.get(0).actualiza = (x1,x2)-> PairInteger.create(0, x2);
		Accion.acciones.get(1).actualiza = (x1,x2)-> PairInteger.create(0, Math.min(x1+x2,ProblemaJarrasPD.capacidadJarra2)) ;
		Accion.acciones.get(2).actualiza = (x1,x2)-> 
				PairInteger.create(Math.max(0,x1+x2-ProblemaJarrasPD.capacidadJarra2), Math.min(x1+x2,ProblemaJarrasPD.capacidadJarra2));
		Accion.acciones.get(3).actualiza = (x1,x2)-> PairInteger.create(ProblemaJarrasPD.capacidadJarra1, x2);
		Accion.acciones.get(4).actualiza = (x1,x2)-> PairInteger.create(x1, ProblemaJarrasPD.capacidadJarra2);
		Accion.acciones.get(5).actualiza = (x1,x2)-> PairInteger.create(x1, 0);;
		Accion.acciones.get(6).actualiza = (x1,x2)-> PairInteger.create(Math.min(x1+x2,ProblemaJarrasPD.capacidadJarra1),0) ; 
		Accion.acciones.get(7).actualiza = (x1,x2)->  
				PairInteger.create(Math.min(x1+x2,ProblemaJarrasPD.capacidadJarra1), Math.max(0,x1+x2-ProblemaJarrasPD.capacidadJarra1));		
	}

	public Integer getCodigo() {
		return codigo;
	}

	public String getDescripcionCorta() {
		return descripcionCorta;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public Boolean isAplicable(Integer j1, Integer j2) {
		return aplicable.test(j1, j2);
	}

	public PairInteger ejecuta(PairInteger p) {
		pair = this.actualiza.apply(p.v1, p.v2);
		return pair;
	}
	
	public static PairInteger ejecuta(PairInteger p, List<Accion> ls){
		pair = PairInteger.create(p);
		ls.stream().forEach(a->a.ejecuta(pair));
		return pair;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		result = prime * result
				+ ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime
				* result
				+ ((descripcionCorta == null) ? 0 : descripcionCorta.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Accion))
			return false;
		Accion other = (Accion) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		if (descripcion == null) {
			if (other.descripcion != null)
				return false;
		} else if (!descripcion.equals(other.descripcion))
			return false;
		if (descripcionCorta == null) {
			if (other.descripcionCorta != null)
				return false;
		} else if (!descripcionCorta.equals(other.descripcionCorta))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "["+codigo + ","+descripcionCorta+ "]";
	}

	
}

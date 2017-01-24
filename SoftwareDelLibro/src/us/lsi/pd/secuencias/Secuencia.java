package us.lsi.pd.secuencias;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import us.lsi.graphs.VirtualVertex;


public class Secuencia implements VirtualVertex<Secuencia, SecuenciaEdge> {

	public static Secuencia create(String cadena, int index) {
		return new Secuencia(cadena, index);
	}

	public static Secuencia create(String origen) {
		return new Secuencia(origen, 0);
	}
	
	private String cadena;
	private Integer tamCadena;
	private int index;
	public static String transformada;
	public static Integer tamTransformada;
	
	
	private Secuencia(String cadena, int index) {
		super();
		this.cadena = cadena;
		this.index = index;
		this.tamCadena = this.cadena.length();
	}

	public static enum Accion{Añade, Cambia, Elimina, Avanza, Nada};
	
	
	public Integer getTamCadena() {
		return tamCadena;
	}

	public int getIndex() {
		return index;
	}

	@Override
	public Set<Secuencia> getNeighborListOf() {
		return this.edgesOf().stream().map(x->x.getTarget()).collect(Collectors.toSet());
	}

	@Override
	public Set<SecuenciaEdge> edgesOf() {
		return Arrays.stream(Secuencia.Accion.values())
				.filter(x->testAccion(x))
				.map(x->SecuenciaEdge.create(this,getNeighbor(x), x))
				.collect(Collectors.toSet());
	}

	@Override
	public boolean isNeighbor(Secuencia e) {
		return this.getNeighborListOf().contains(e);
	}

	
	
	public Secuencia getNeighbor(Accion a){
		StringBuilder s = new StringBuilder(cadena);
		int index = this.index;
		switch(a){
		case Cambia: s.setCharAt(index, transformada.charAt(index));  index++; break;
		case Elimina: s = s.deleteCharAt(index); break;
		case Añade: s = s.append(transformada.charAt(index));  index++ ; break;
		case Avanza: index++; break;
		case Nada:  break;
		}
		String r = s.toString();
		return Secuencia.create(r,index);
	}
	

	private boolean testAccion(Accion a){
		boolean r = true;
		switch(a){
		case Cambia: r = index < tamCadena &&  index < tamTransformada? true : false ; break;
		case Elimina: r = index < tamCadena? true : false ; break;
		case Añade: r = index == tamCadena &&  index < tamTransformada? true : false ; break;
		case Avanza:  r = index < tamCadena &&  index < tamTransformada && cadena.charAt(index) == transformada.charAt(index) ? true : false ; break;
		case Nada:  r = false; break;
		}
		return r;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cadena == null) ? 0 : cadena.hashCode());
		result = prime * result + index;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Secuencia))
			return false;
		Secuencia other = (Secuencia) obj;
		if (cadena == null) {
			if (other.cadena != null)
				return false;
		} else if (!cadena.equals(other.cadena))
			return false;
		if (index != other.index)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Secuencia [cadena=" + cadena + ", index=" + index + "]";
	}

	@Override
	public boolean isValid() {
		return true;
	}
	
	
}

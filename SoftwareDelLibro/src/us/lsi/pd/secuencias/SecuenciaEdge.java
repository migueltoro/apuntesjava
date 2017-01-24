package us.lsi.pd.secuencias;

import us.lsi.graphs.SimpleEdge;
import us.lsi.pd.secuencias.Secuencia.Accion;

public class SecuenciaEdge extends SimpleEdge<Secuencia> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static SecuenciaEdge create(Secuencia c1, Secuencia c2, Accion a) {
		return new SecuenciaEdge(c1, c2, a);
	}

	private Accion a;
	private Double valor;
	
	private SecuenciaEdge(Secuencia c1, Secuencia c2, Accion a) {
		super(c1, c2);
		this.a = a;
		switch(a){
		case Cambia: valor = 1.; break;
		case Elimina: valor = 1.; break;
		case Añade: valor = 1.; break;
		case Avanza: valor = 0.; break;
		case Nada: valor = 0.;
		}
	}

	public Accion getAccion() {
		return a;
	}

	public Double getValor() {
		return valor;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((a == null) ? 0 : a.hashCode());
		result = prime * result + ((valor == null) ? 0 : valor.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof SecuenciaEdge))
			return false;
		SecuenciaEdge other = (SecuenciaEdge) obj;
		if (a != other.a)
			return false;
		if (valor == null) {
			if (other.valor != null)
				return false;
		} else if (!valor.equals(other.valor))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SecuenciaEdge [a=" + a + ", valor=" + valor + ", getSource()="
				+ getSource() + ", getTarget()=" + getTarget() + "]";
	}	

}

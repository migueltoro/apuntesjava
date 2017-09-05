package us.lsi.ag.fiesta;

public class Actividad {
	private Double valoracion;
	private Double coste;
	private Integer id;
	
	public Actividad(Double valoracion, Double coste) {
		super();
		this.valoracion = valoracion;
		this.coste = coste;
	}
	public Double getValoracion() {
		return valoracion;
	}
	public Double getCoste() {
		return coste;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((coste == null) ? 0 : coste.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((valoracion == null) ? 0 : valoracion.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Actividad))
			return false;
		Actividad other = (Actividad) obj;
		if (coste == null) {
			if (other.coste != null)
				return false;
		} else if (!coste.equals(other.coste))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (valoracion == null) {
			if (other.valoracion != null)
				return false;
		} else if (!valoracion.equals(other.valoracion))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Actividad [valoracion=" + valoracion + ", coste=" + coste
				+ ", id=" + id + "]";
	}
	
	
	
}

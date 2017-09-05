package us.lsi.graphs.examples;

public class Ciudad  {

	public static Ciudad create(String[] formato) {
		return new Ciudad(formato);
	}

	public static Ciudad create(String nombre) {
		return new Ciudad(nombre);
	}

	private String nombre;
	private Double habitantes;

	private Ciudad(String nombre) {
		super();
		this.nombre = nombre;
		this.habitantes = null;
	}

	private Ciudad(String[] formato){
		super();
		this.nombre = formato[0];
		this.habitantes = new Double(formato[1]);
	}
	
	public Double getHabitantes() {
		return habitantes;
	}
	public String getNombre() {
		return nombre;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ciudad other = (Ciudad) obj;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return this.nombre;
	}
}

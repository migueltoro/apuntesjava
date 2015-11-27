package us.lsi.pd.floyd;


import org.jgrapht.graph.DefaultWeightedEdge;

public class Carretera extends DefaultWeightedEdge {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static Carretera create(Ciudad c1, Ciudad c2) {
		return new Carretera();
	}

	public static Carretera create(Ciudad c1, Ciudad c2, String[] formato) {
		return new Carretera(c1,c2,formato);
	}

	private static int ultimo = 0;
	private String nombre;
	private int id;
	private double km;
	private Ciudad source;
	private Ciudad target;

	public Carretera(Ciudad c1, Ciudad c2, String[] nombre) {
		super();
		this.source = c1;
		this.target = c2;
		this.nombre = nombre[2];
		this.km = new Double(nombre[3]);
		this.id = ultimo;
		ultimo++;
	}

	public String getNombre() {
		return nombre;
	}

	public int getId() {
		return id;
	}

	public double getKm() {
		return km;
	}

	
	public Ciudad getSource(){
		return source;
	}
	
	public Ciudad getTarget(){
		return target;
	}
	
	public double getWeight(){
		return km;
	}
		
	public Carretera() {
		super();
		this.nombre = "";
		this.id = ultimo;
		ultimo++;
	}

	@Override
	public String toString() {
		return this.nombre+","+this.km;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		Carretera other = (Carretera) obj;
		if (id != other.id)
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		return true;
	}
}

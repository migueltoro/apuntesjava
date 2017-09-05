package us.lsi.graphs.examples;


import org.jgrapht.graph.DefaultWeightedEdge;

public class Carretera extends DefaultWeightedEdge {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static Carretera create(Ciudad c1, Ciudad c2) {
		return new Carretera(c1,c2);
	}

	public static Carretera create(Ciudad c1, Ciudad c2, String[] formato) {
		return new Carretera(c1,c2,formato);
	}

	private static int num =0;
	private Ciudad source;
	private Ciudad target;
	private String nombre;
	private double km;
	private int id;

	public Carretera(Ciudad c1, Ciudad c2) {
		super();
		this.source = c1;
		this.target = c2;
		this.nombre = "";
		this.km = 0.;
		this.id = num;
		num++;
	}
	
	public Carretera(Ciudad c1, Ciudad c2, String[] nombre) {
		super();
		this.source = c1;
		this.target = c2;
		this.nombre = nombre[2];
		this.km = new Double(nombre[3]);
		this.id = num;
		num++;
	}

	public String getNombre() {
		return nombre;
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

	@Override
	public String toString() {
		return "("+super.getSource()+","+super.getTarget()+","+this.nombre+","+super.getWeight()+")";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Carretera))
			return false;
		Carretera other = (Carretera) obj;
		if (id != other.id)
			return false;
		return true;
	}

	

	
}

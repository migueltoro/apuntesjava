package us.lsi.astar.vuelos;


public class Vuelo {
	
	private String from;
	private String to;
	private double horaDeSalida;
	private double duracion;
	
	public static  Vuelo create(String from, String to, String[] s) {
		return new Vuelo(from, to, new Double(s[2]),new Double(s[3]));
	}
	
	public static Vuelo create(String c1, String c2) {
		return create(c1,c2,0.0, 1.0);
	}
	
	
	Vuelo(String from, String to, double horaDeSalida, double duracion) {
		super();
		this.from = from;
		this.to = to;
		this.horaDeSalida = horaDeSalida;
		this.duracion = duracion;
	}

	
	public Vuelo() {
		super();
		// TODO Auto-generated constructor stub
	}



	public String getFrom() {
		return from;
	}



	public String getTo() {
		return to;
	}



	public double getHoraDeSalida() {
		return horaDeSalida;
	}



	public double getDuracion() {
		return duracion;
	}

	public double getHoraDeLlegada() {
		return horaDeSalida+duracion;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((from == null) ? 0 : from.hashCode());
		long temp;
		temp = Double.doubleToLongBits(horaDeSalida);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((to == null) ? 0 : to.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Vuelo))
			return false;
		Vuelo other = (Vuelo) obj;
		if (from == null) {
			if (other.from != null)
				return false;
		} else if (!from.equals(other.from))
			return false;
		if (Double.doubleToLongBits(horaDeSalida) != Double
				.doubleToLongBits(other.horaDeSalida))
			return false;
		if (to == null) {
			if (other.to != null)
				return false;
		} else if (!to.equals(other.to))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Vuelo [from=" + from + ", to=" + to + ", horaDeSalida="
				+ horaDeSalida + ", duracion=" + duracion + "]";
	}
	
	
	public static Vuelo create(String from, String to, double timeSalida,double duracion) {
		return new Vuelo(from, to, timeSalida, duracion);
	}

	

	
}


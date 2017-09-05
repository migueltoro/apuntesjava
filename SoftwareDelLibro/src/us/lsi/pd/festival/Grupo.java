package us.lsi.pd.festival;

public class Grupo{

	private Integer codigo;
	private String nombre;
	private Integer votos;
	private Double precio;
	private String dia;
	private String hora;
	private boolean cerca;
	private boolean nuevo;
	
	private Grupo(Integer codigo, String nombre, Integer votos, Double precio,
			String dia, String hora, boolean cerca, boolean nuevo) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
		this.votos = votos;
		this.precio = precio;
		this.dia = dia;
		this.hora = hora;
		this.cerca = cerca;
		this.nuevo = nuevo;
	}
	
	private Grupo(int codigo, String[] fm) {
		super();
		this.codigo = codigo;
		this.nombre = fm[0];
		this.votos = new Integer(fm[1]);
		this.precio = new Double(fm[2]);
		this.dia = fm[3];
		this.hora = fm[4];
		this.cerca = new Boolean(fm[5]);
		this.nuevo = new Boolean(fm[6]);
	}
	
	public static Grupo create(Integer codigo, String nombre, Integer votos, Double precio,
			String dia, String hora, boolean cerca, boolean nuevo) {
		return new Grupo(codigo, nombre, votos, precio, dia, hora, cerca, nuevo);
	}
	
	public static Grupo create(int codigo, String[] fm) {
		return new Grupo(codigo, fm);
	}
	
	public Integer getCodigo() {
		return codigo;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public Integer getVotos() {
		return votos;
	}
	
	public Double getPrecio() {
		return precio;
	}
	
	public String getDia() {
		return dia;
	}
	
	public String getHora() {
		return hora;
	}

	public boolean esCerca() {
		return cerca;
	}
	
	public boolean esNuevo() {
		return nuevo;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
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
		Grupo other = (Grupo) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return nombre;
	}

}


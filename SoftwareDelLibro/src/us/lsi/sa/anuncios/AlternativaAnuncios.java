package us.lsi.sa.anuncios;


public class AlternativaAnuncios {
	
	public enum Opcion {Insertar, Eliminar, Intercambiar}
	

	public Opcion opcion;
	public Integer p1;
	public Integer p2;
	
	public static AlternativaAnuncios createInsertar(Integer p1,Integer p2) {
		return new AlternativaAnuncios(Opcion.Insertar, p1, p2);
	}
	
	public static AlternativaAnuncios createEliminar(Integer p1) {
		return new AlternativaAnuncios(Opcion.Eliminar, p1, null);
	}
	
	public static AlternativaAnuncios createIntercambiar(Integer p1, Integer p2) {
		return new AlternativaAnuncios(Opcion.Intercambiar, p1, p2);
	}
	
	private AlternativaAnuncios(Opcion opcion, Integer p1, Integer p2) {
		super();
		this.opcion = opcion;
		this.p1 = p1;
		this.p2 = p2;
	}

	@Override
	public String toString() {
		String s;
		if(this.opcion.equals(Opcion.Insertar) || this.opcion.equals(Opcion.Intercambiar)){
			s = "AlternativaAnuncios [opcion=" + this.opcion + "," + this.p1 + ","+ this.p2 +"]";
		} else {
			s = "AlternativaAnuncios [opcion=" + this.opcion + "," + this.p1+"]";
		}
		return s;
	}
}

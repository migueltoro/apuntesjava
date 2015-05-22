package us.lsi.graphs.examples;

import us.lsi.graphs.StringVertexFactory;

public class Ciudad {

	public String nombre;
	public static StringVertexFactory<Ciudad> factoria = new Factoria();

	public Ciudad(String nombre) {
		super();
		this.nombre = nombre;
	}
	
	
	@Override
	public String toString() {
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
		if (!(obj instanceof Ciudad))
			return false;
		Ciudad other = (Ciudad) obj;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		return true;
	}


	private static class Factoria implements StringVertexFactory<Ciudad> {

		public Factoria() {
			super();
			// TODO Auto-generated constructor stub
		}
		
		@Override
		public Ciudad createVertex(String[] formato) {
			// TODO Auto-generated method stub
			return new Ciudad(formato[0]);
		}
		
	}
}

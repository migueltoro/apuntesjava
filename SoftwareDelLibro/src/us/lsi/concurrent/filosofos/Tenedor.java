package us.lsi.concurrent.filosofos;


public class Tenedor extends AbstractModelObject {
	
	public enum Estado {Libre, Ocupado};
	
	private Estado estado = Estado.Libre;
	private int i;
	
	public Tenedor(int i) {
		this.i = i;
	}

	public void setEstado(Estado estado) {
		Estado oldEstado = this.estado;
		boolean oldDisponible = (this.estado == Estado.Libre);
		this.estado = estado;
		firePropertyChange("estado", oldEstado, estado);
		firePropertyChange("disponible", oldDisponible, isDisponible());
		System.out.println("Tenedor  "+ i+","+isDisponible());
	}

	public Estado getEstado() {
		return estado;
	}
	
	public boolean isDisponible(){	
		return estado == Estado.Libre;
	}

	public int getNumero() {
		return i;
	}
	
	
}

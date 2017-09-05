package us.lsi.pd.festival;

import java.util.*;

public class Festival {

	private List<Grupo> listaGrupos;
	private Integer votos = 0;	
	private Double coste = 0.0;	

	public static Festival create() {
		return new Festival();
	}

	private Festival(){
		this.listaGrupos = new ArrayList<Grupo>();
	}
	
	public static Festival create(List<Grupo> lista) {
		return new Festival(lista);
	}

	private Festival(List<Grupo> lista){
		this.listaGrupos = new ArrayList<Grupo>();
		this.listaGrupos.addAll(lista);
		calculaPropiedadesDerivadas();		
	}
	
	public void add(Grupo c){
		this.listaGrupos.add(c);
		calculaPropiedadesDerivadas();		
	}
	
	public void remove(Grupo c){
		this.listaGrupos.remove(c);
		calculaPropiedadesDerivadas();		
	}

	private void calculaPropiedadesDerivadas(){
		this.votos = 0;
		this.coste = 0.;
		for (Grupo c: listaGrupos){
			this.coste = this.coste + c.getPrecio();
			this.votos = this.votos + c.getVotos();
		}
	}
	
	public List<Grupo> getLista() {
		return listaGrupos;
	}

	public Double getCoste() {
		return coste;
	}
	
	public Integer getVotos() {
		return votos;
	}
	
	@Override
	public String toString() {
		return "ListaGrupos [votos=" + votos + ", coste=" + coste
				+ ", lista=" + listaGrupos + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((listaGrupos == null) ? 0 : listaGrupos.hashCode());
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
		Festival other = (Festival) obj;
		if (listaGrupos == null) {
			if (other.listaGrupos != null)
				return false;
		} else if (!listaGrupos.equals(other.listaGrupos))
			return false;
		return true;
	}
	
}


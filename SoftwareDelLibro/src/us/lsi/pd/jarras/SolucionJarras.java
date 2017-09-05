package us.lsi.pd.jarras;

import java.util.List;
import java.util.stream.Collectors;

import us.lsi.bt.SolucionBT;

import com.google.common.collect.Lists;

public class SolucionJarras implements SolucionBT {

	public static SolucionJarras create() {
		return new SolucionJarras();
	}

	public static SolucionJarras create(List<Accion> m) {
		return new SolucionJarras(m);
	}

	private List<Accion> listaAcciones;

	private SolucionJarras(List<Accion> ls) {
		super();
		this.listaAcciones = Lists.newArrayList(ls);
	}

	private SolucionJarras() {
		super();
		this.listaAcciones = Lists.newArrayList();
	}

	public void add(Accion op) {
		listaAcciones.add(op);
	}

	public void addFirst(Accion op) {
		listaAcciones.add(0,op);
	}
	
	public Accion removeLast() {
		if (listaAcciones.isEmpty())
			return null;
		return listaAcciones.remove(listaAcciones.size() - 1);
	}

	public Integer getNumAcc() {
		return listaAcciones.size();
	}

	public List<Accion> getListaAcciones() {
		return listaAcciones;
	}

	public void setListaOperaciones(List<Accion> ls) {
		this.listaAcciones = ls;
	}	

	@Override
	public Double getObjetivo() {
		return (double) this.getNumAcc();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((listaAcciones == null) ? 0 : listaAcciones.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof SolucionJarras))
			return false;
		SolucionJarras other = (SolucionJarras) obj;
		if (listaAcciones == null) {
			if (other.listaAcciones != null)
				return false;
		} else if (!listaAcciones.equals(other.listaAcciones))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SolucionJarra [numOp=" + this.getNumAcc() + "\n" + 
	listaAcciones.stream().map(x->x.toString()).collect(Collectors.joining("\n")) + "]";
	}	

}


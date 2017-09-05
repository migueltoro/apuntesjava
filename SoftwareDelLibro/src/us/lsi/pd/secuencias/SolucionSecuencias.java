package us.lsi.pd.secuencias;

import java.util.ArrayList;
import java.util.List;

import us.lsi.common.Tuple2;
import us.lsi.pd.secuencias.Secuencia.Accion;

public class SolucionSecuencias {
	
	
	public static SolucionSecuencias create() {
		return new SolucionSecuencias();
	}

	List<Tuple2<Accion,Integer>> acciones;
	
	private SolucionSecuencias() {
		super();
		this.acciones = new ArrayList<>();
	}

	public List<Tuple2<Accion, Integer>> getAcciones() {
		return acciones;
	}

	public void addFirst(Tuple2<Accion,Integer> p) {
		acciones.add(0,p);
	}
	
	@Override
	public String toString() {
		return "SolucionSecuencias [acciones=" + acciones + "]";
	}
	
	
}

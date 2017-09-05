package us.lsi.bt.reinas;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import us.lsi.bt.SolucionBT;

public class SolucionReinas implements SolucionBT {

	public static SolucionReinas create(List<Reina> reinas) {
		return new SolucionReinas(reinas);
	}


	private List<Reina> reinas;
	private int numeroDeConflictos;	
	
	
	private SolucionReinas(List<Reina> reinas) {
		super();
		this.reinas = reinas;
		this.numeroDeConflictos = SolucionReinas.getNumeroDeConflictos(reinas);
	}

	public List<Reina> getReinas() {
		return reinas;
	}

	public static Integer getNumeroDeConflictos(List<Reina> ls){
		Set<Integer> diagonalesPrincipalesOcupadas = new HashSet<>();
		Set<Integer> diagonalesSecundariasOcupadas = new HashSet<>();
		for(Reina r:ls){
			diagonalesPrincipalesOcupadas.add(r.getY()-r.getX());
			diagonalesSecundariasOcupadas.add(r.getY()+r.getX());
		}
		return 2*Reina.numeroDeReinas 
				- diagonalesPrincipalesOcupadas.size()
				-diagonalesSecundariasOcupadas.size();
	}
	public int getNumeroDeConflictos() {
		return numeroDeConflictos;
	}


	@Override
	public Double getObjetivo() {
		return (double) numeroDeConflictos;
	}

	@Override
	public String toString() {
		return "NumeroDeConflictos="
				+ numeroDeConflictos + ","+ "Reinas=" + reinas +"]";
	}

	
}

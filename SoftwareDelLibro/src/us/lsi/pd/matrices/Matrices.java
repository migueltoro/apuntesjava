package us.lsi.pd.matrices;

import java.util.List;

import us.lsi.pd.AlgoritmoPD.Sp;
import us.lsi.pd.ProblemaPD;

public class Matrices implements ProblemaPD<String, Integer> {

	public static List<Matriz> matrices;
	public static Integer n = matrices.size();
	
	private static void leeMatrices(String fichero){
		
	}
	
	public static Matrices create(String fichero) {
		leeMatrices(fichero);
		return new Matrices(0, n);
	}
	
	public static Matrices create(Integer i, Integer j) {
		return new Matrices(i, j);
	}

	private Integer i;
	private Integer j;

	private Matrices(Integer i, Integer j) {
		super();
		this.i = i;
		this.j = j;
	}

	@Override
	public us.lsi.pd.ProblemaPD.Tipo getTipo() {
		return Tipo.Min;
	}

	@Override
	public int size() {
		return j-i;
	}

	@Override
	public boolean esCasoBase() {
		return false;
	}

	@Override
	public Sp<Integer> getSolucionParcialCasoBase() {
		return null;
	}

	@Override
	public Sp<Integer> getSolucionParcial(List<Sp<Integer>> ls) {
		return null;
	}

	@Override
	public ProblemaPD<String, Integer> getSubProblema(Integer a, int np) {
		return null;
	}

	@Override
	public Sp<Integer> getSolucionParcialPorAlternativa(Integer a, List<Sp<Integer>> ls) {
		return null;
	}

	@Override
	public List<Integer> getAlternativas() {
		return null;
	}

	@Override
	public int getNumeroSubProblemas(Integer a) {
		return 0;
	}

	@Override
	public String getSolucionReconstruidaCasoBase(Sp<Integer> sp) {
		return null;
	}

	@Override
	public String getSolucionReconstruidaCasoRecursivo(Sp<Integer> sp, List<String> ls) {
		return null;
	}
}

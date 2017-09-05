package us.lsi.dyv.ejemplos;


import java.util.Comparator;
import java.util.List;

import us.lsi.common.Tuple2;
import us.lsi.dyv.ProblemaDyV;
import us.lsi.dyv.problemasdelistas.ProblemasDeListas;
import us.lsi.math.Math2;

public class SortList<T> implements ProblemaDyV<Void, Void>{


	public static <T> SortList<T> create(List<T> ls, Comparator<T> ord) {
		return new SortList<T>(ls, ord, 0, ls.size());
	}

	public static <T> SortList<T> create(List<T> ls, Comparator<T> ord, int i, int j) {
		return new SortList<T>(ls, ord, i, j);
	}
	
	
	private List<T> ls;
	private Comparator<T> ord;	
	private int i;
	private int j;
	private int a;
	private int b;
		
	
	
	private SortList(List<T> ls, Comparator<T> ord, int i, int j) {
		super();
		this.ls = ls;
		this.ord = ord;
		this.i = i;
		this.j = j;
		if (!this.esCasoBase()) {
			calculaDerivadas(i, j);
		}
	}

	protected void calculaDerivadas(int i, int j) {
		int p = Math2.getEnteroAleatorio(i, j);
		T pivote = this.ls.get(p);
		Tuple2<Integer, Integer> par = ProblemasDeListas
				.reordenaMedianteBanderaHolandesa(this.ls, pivote, i, j, this.ord);
		this.a = par.v1;
		this.b = par.v2;
	}	

	@Override
	public int size() {
		return this.j-this.i;
	}

	@Override
	public boolean esCasoBase() {
		return this.size() <=4;
	}

	@Override
	public Void getSolucionCasoBase() {
		ProblemasDeListas.ordenaBase(this.ls, this.i, this.j, this.ord);
		return null;
	}

	@Override
	public Void combina(List<Void> ls) {	
		return null;
	}

	@Override
	public ProblemaDyV<Void, Void> getSubProblema(int np) {
		ProblemaDyV<Void, Void> p = null;
		if(np==0)
			p = SortList.create(this.ls,this.ord,this.i, this.a);
		else if(np==1)
			p = SortList.create(this.ls,this.ord,b, j);
		return p;
	}

	@Override
	public int getNumeroDeSubProblemas() {
		return 2;
	}

	
	@Override
	public Void getSolucion(Void e) {
		return null;
	}

}

package us.lsi.pd.floyd;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.jgrapht.GraphPath;

import us.lsi.graphs.GraphPaths;
import us.lsi.graphs.GraphView;
import us.lsi.pd.AlgoritmoPD.Sp;
import us.lsi.pd.ProblemaPD;

public class FloydPD<V, E> implements ProblemaPD<GraphPath<V, E>, FloydPD.Alternativa> {

	public static enum Alternativa{Yes, No};
	
	public static <V, E> FloydPD<V, E> create(int i, int j, GraphView<V, E> grafo) {
		return new FloydPD<V, E>(i, j, 0, grafo);
	}
	
	public static <V, E> FloydPD<V, E> create(int i, int j, int k, GraphView<V, E> grafo) {
		return new FloydPD<V, E>(i, j, k, grafo);
	}
	
	private int i;
	private int j;
	private int k;
	private GraphView<V,E> grafo;
	
	
	private FloydPD(int i, int j, int k, GraphView<V, E> grafo) {
		super();
		this.i = i;
		this.j = j;
		this.k = k;
		this.grafo = grafo;
	}
	
	@Override
	public ProblemaPD.Tipo getTipo() {
		return Tipo.Min;
	}
	
	@Override
	public int size() {
		return 0;
	}
	@Override
	public boolean esCasoBase() {
			return grafo.isEdge(i,j)  ||  k == grafo.getNumVertices();
	}
	@Override
	public Sp<Alternativa> getSolucionParcialCasoBase() {
		Sp<Alternativa> r = null;
		if(grafo.isEdge(i, j)){
			Double w = grafo.getWeight(i, j);
			r = Sp.<Alternativa>create(null,w);
		}
		return r;
	}
	
	@Override
	public Sp<Alternativa> getSolucionParcial(List<Sp<Alternativa>> ls) {
		return ls.stream().min(Comparator.naturalOrder()).orElse(null);
	}
	@Override
	public ProblemaPD<GraphPath<V, E>, Alternativa> getSubProblema(Alternativa a, int np) {
		ProblemaPD<GraphPath<V, E>, Alternativa> r=null;
		switch(a){
		case No : r = FloydPD.create(i, j, k+1,grafo); break;
		case Yes : 
			switch(np){
				case 0 : r = FloydPD.create(i, k, k+1,grafo); break;
				case 1 : r = FloydPD.create(k, j, k+1,grafo); break;
				default : throw new IllegalArgumentException("El número del subproblema no puede ser "+np);
			}
		}
		return r;
	}
	@Override
	public Sp<Alternativa> getSolucionParcialPorAlternativa(Alternativa a,List<Sp<Alternativa>> ls) {
		Double r = null;
		Sp<Alternativa> r0  = ls.get(0);
		switch(a){
		case No : r = r0.propiedad; break;
		case Yes : 
			Sp<Alternativa> r1  = ls.get(1);
			r = r0.propiedad+r1.propiedad;
		}
		return Sp.create(a, r);
	}
	@Override
	public List<Alternativa> getAlternativas() {
		return Arrays.asList(Alternativa.values());
	}
	@Override
	public int getNumeroSubProblemas(Alternativa a) {
		return a.equals(Alternativa.No)?1:2;
	}
	
	@Override
	public GraphPath<V, E> getSolucionReconstruidaCasoBase(Sp<Alternativa> sp) {
		E e = grafo.getEdge(i, j);
		return GraphPaths.create(grafo.getGrafo(), grafo.getVertice(i), grafo.getVertice(j), e, grafo.getWeight(i, j));
	}
		
	@Override
	public GraphPath<V, E> getSolucionReconstruidaCasoRecursivo(Sp<Alternativa> sp, List<GraphPath<V, E>> ls) {
		GraphPath<V, E> r = null;
		switch(sp.alternativa){
		case No: r = ls.get(0); break;
		case Yes: r = GraphPaths.addGraphPath(ls.get(0),ls.get(1)); break;	
		}		
		return r;
	}	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + i;
		result = prime * result + j;
		result = prime * result + k;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof FloydPD))
			return false;
		FloydPD<?,?> other = (FloydPD<?,?>) obj;
		if (i != other.i)
			return false;
		if (j != other.j)
			return false;
		if (k != other.k)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "(" + i + "," + j + "," + k + ")";
	}	
}

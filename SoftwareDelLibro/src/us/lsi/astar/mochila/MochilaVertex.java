package us.lsi.astar.mochila;

import java.util.List;
import java.util.Set;
import java.util.stream.*;

import us.lsi.graphs.*;
import us.lsi.pd.mochila.ObjetoMochila;
import us.lsi.pd.mochila.ProblemaMochila;
import us.lsi.pd.mochila.SolucionMochila;

public class MochilaVertex implements VirtualVertex<MochilaVertex, SimpleEdge<MochilaVertex>> {

	public static MochilaVertex create() {
		return new MochilaVertex(ProblemaMochila.create());
	}
	
	public static MochilaVertex create(ProblemaMochila problema) {
		return new MochilaVertex(problema);
	}

	private ProblemaMochila problema;
	
	private MochilaVertex(ProblemaMochila problema) {
		super();
		this.problema = problema;
	}
	
	public static SolucionMochila getSolucion(List<MochilaVertex> ls){
		SolucionMochila s = SolucionMochila.create();		
		for(int i = 0; i < ls.size()-1; i++){
			int index1 = ls.get(i).problema.getIndex();
			ProblemaMochila p1 = ls.get(i).problema;
			ProblemaMochila p2 = ls.get(i+1).problema;
			ObjetoMochila ob = ProblemaMochila.getObjeto(index1);
			Integer a = p1.getAlternativa(p2);
			if(a>0){
				s = s.add(ob, a);
			}
		}
		return s;
	}

	@Override
	public Set<MochilaVertex> getNeighborListOf() {
		return problema.getAlternativas()
				.<ProblemaMochila>mapToObj((int a)-> this.problema.getSubProblema(a))
				.<MochilaVertex>map((ProblemaMochila p)->MochilaVertex.create(p))
				.collect(Collectors.<MochilaVertex>toSet());
	}

	@Override
	public Set<SimpleEdge<MochilaVertex>> edgesOf() {
		return getNeighborListOf()
				.stream()
				.map(x->SimpleEdge.create(this, x))
				.collect(Collectors.toSet());
	}

	@Override
	public boolean isNeighbor(MochilaVertex e) {
		return this.problema.esSubproblema(e.problema);
	}

	public ProblemaMochila getProblema() {
		return problema;
	}

	@Override
	public String toString() {
		return problema.toString();
	}

	@Override
	public boolean isValid() {
		return true;
	}	
	
	
}

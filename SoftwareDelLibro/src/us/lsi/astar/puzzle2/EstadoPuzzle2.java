package us.lsi.astar.puzzle2;

import java.util.*;

import com.google.common.collect.Lists;

import us.lsi.astar.puzzle.ProblemaPuzzle;
import us.lsi.common.*;
import us.lsi.graphs.ActionVirtualVertex;

/**
 * 
 * * Estado de un Puzzle como implementación de AlternativeVirtualVertex. Esta versión simplifica el código con repecto 
 * a la aversión que implementa VirtualVertex directamente.
 * 
 * @author Miguel Toro
 *
 */
public class EstadoPuzzle2 extends ActionVirtualVertex<PairInteger,EstadoPuzzle2> {
	
	public static EstadoPuzzle2 create(ProblemaPuzzle problema) {
		return new EstadoPuzzle2(problema);
	}

	public static EstadoPuzzle2 create(Integer... d) {
		return new EstadoPuzzle2(ProblemaPuzzle.create(d));
	}
	
	public static EstadoPuzzle2 create(Integer[][] datos, int i0, int j0) {
		return new EstadoPuzzle2(ProblemaPuzzle.create(datos,i0,j0));
	}
	
	private ProblemaPuzzle problema;
		
	
	private EstadoPuzzle2(ProblemaPuzzle problema) {
		super();
		this.problema = problema;
	}

	
	public ProblemaPuzzle getProblema() {
		return problema;
	}


	@Override
	public boolean isValid() {
		return problema.isValid();
	}
	
	private static List<PairInteger> values = null;
	

	@Override
	public EstadoPuzzle2 neighbor(PairInteger p) {
		return new EstadoPuzzle2(problema.getVecino(p.v1, p.v2));
	}


	@Override
	public boolean isApplicable(PairInteger p) {
		PairInteger np = PairInteger.create(p.v1+problema.getI0(), p.v2+problema.getJ0());	
		return np.v1>=0 && np.v1<ProblemaPuzzle.numFilas && np.v2>=0 && np.v2<ProblemaPuzzle.numFilas;
	}


	@Override
	public List<PairInteger> acciones() {
		if(EstadoPuzzle2.values==null){
			EstadoPuzzle2.values = Lists.newArrayList(PairInteger.create(1,0),PairInteger.create(0,1),PairInteger.create(-1,0),PairInteger.create(0,-1));
		}
		return EstadoPuzzle2.values;
	}


	@Override
	protected EstadoPuzzle2 getThis() {
		return this;
	}
	
	Integer getNumDiferentes(EstadoPuzzle2 e){
		return problema.getNumDiferentes(e.problema);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((problema == null) ? 0 : problema.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof EstadoPuzzle2))
			return false;
		EstadoPuzzle2 other = (EstadoPuzzle2) obj;
		if (problema == null) {
			if (other.problema != null)
				return false;
		} else if (!problema.equals(other.problema))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return problema.toString();
	}
	
	
}

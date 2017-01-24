package us.lsi.astar.puzzle;

import java.util.*;
import java.util.stream.Collectors;





import com.google.common.collect.Lists;
import us.lsi.common.*;
import us.lsi.graphs.SimpleEdge;
import us.lsi.graphs.VirtualVertex;

/**
 * Estado de un Puzzle como implementación de VirtualVertex
 * 
 * 
 * @author Miguel Toro
 *
 */
public class EstadoPuzzle implements VirtualVertex<EstadoPuzzle,SimpleEdge<EstadoPuzzle>> {
	
	/**
	 * @param d Lista de valores del puzzle dados por filas de abajo arriba
	 * @return Un EstadoPuzzle
	 */
	public static EstadoPuzzle create(Integer... d) {
		return new EstadoPuzzle(d);
	}
	
	private Integer[][] datos; 
	private int i0;
	private int j0;
	public static int numFilas = 3;
	
	private EstadoPuzzle(Integer... d) {
		super(); 
		this.datos = new Integer[EstadoPuzzle.numFilas][EstadoPuzzle.numFilas];
		Set<Integer> s = new HashSet<Integer>();	
		int i=0,j=0;
		for (int e:d) {							
					if(e<0 || e> (EstadoPuzzle.numFilas*EstadoPuzzle.numFilas-1)){
						throw new IllegalArgumentException();
					}
					if(e==0){
						this.i0 =i;
						this.j0 =j;
					}
					s.add(e);
					this.datos[i][j] = e;
					if(j== EstadoPuzzle.numFilas-1){
						i++;
						j=0;
					}else{
						j++;
					}
				
		}
		if(d.length!= EstadoPuzzle.numFilas*EstadoPuzzle.numFilas || s.size()!= EstadoPuzzle.numFilas*EstadoPuzzle.numFilas)
			throw new IllegalArgumentException();
	}


	public EstadoPuzzle(Integer[][] datos, int i0, int j0) {
		super();
		this.datos = datos;
		this.i0 = i0;
		this.j0 = j0;
	}

	public Integer getDato(int i, int j) {
		if(i<0 || i>=EstadoPuzzle.numFilas || j<0 || j>=EstadoPuzzle.numFilas)
			throw new IllegalArgumentException();
		return datos[i][j];
	}
	
	public EstadoPuzzle getVecino(int f, int c){
		if(f<0 || f>=EstadoPuzzle.numFilas || c<0 || c>=EstadoPuzzle.numFilas)
			throw new IllegalArgumentException();
		Integer[][] dd = new Integer[EstadoPuzzle.numFilas][EstadoPuzzle.numFilas];
		for (int i = 0; i < EstadoPuzzle.numFilas; i++) {
			for (int j = 0; j < EstadoPuzzle.numFilas; j++) {
				dd[i][j] = datos[i][j];
			}
		}
		dd[f][c] = datos[this.i0][this.j0];
		dd[this.i0][j0] = datos[f][c];
		return new EstadoPuzzle(dd,f,c);
	}
	
	@Override
	public Set<EstadoPuzzle> getNeighborListOf() {
		List<ParInteger> ls = Lists.newArrayList(ParInteger.create(1,0),ParInteger.create(0,1),ParInteger.create(-1,0),ParInteger.create(0,-1));
		return ls.stream()
				 .<ParInteger> map((ParInteger x)-> ParInteger.create(x.p1+this.i0, x.p2+this.j0))
				 .filter((ParInteger x)-> x.p1>=0 && x.p1<EstadoPuzzle.numFilas && x.p2>=0 && x.p2<EstadoPuzzle.numFilas)
				 .<EstadoPuzzle>map((ParInteger x)-> this.getVecino(x.p1,x.p2))
				 .collect(Collectors.<EstadoPuzzle>toSet());
	}
	
	public Set<SimpleEdge<EstadoPuzzle>> edgesOf(){
		return this.getNeighborListOf().stream()
		   .map(x->SimpleEdge.create(this,x))
		   .collect(Collectors.<SimpleEdge<EstadoPuzzle>>toSet());
	}
	
	@Override
	public boolean isNeighbor(EstadoPuzzle e) {
		return this.getNumDiferentes(e)==2 && Math.abs(this.i0-e.i0)+Math.abs(this.j0-e.j0)==1;
	}
	
	@Override
	public boolean isValid() {
		Set<Integer> s = new HashSet<Integer>();	
		for (int j = 0; j < EstadoPuzzle.numFilas; j++) {
			for (int i = 0; i < EstadoPuzzle.numFilas; i++) {
				s.add(datos[i][j]);
			}
		}
		return s.size()== EstadoPuzzle.numFilas*EstadoPuzzle.numFilas;
	}
	
	public Integer getNumDiferentes(EstadoPuzzle e){
		Integer s = 0;
		for (int i = 0; i < EstadoPuzzle.numFilas; i++) {
			for (int j = 0; j < EstadoPuzzle.numFilas; j++) {
				if (!this.datos[i][j].equals(e.datos[i][j])) {
					s++;
				}
			}
		}
		return s;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.deepHashCode(datos);
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof EstadoPuzzle))
			return false;
		EstadoPuzzle other = (EstadoPuzzle) obj;
		if (!Arrays.deepEquals(datos, other.datos))
			return false;
		return true;
	}


	@Override
	public String toString() {
		String s ="\n";
		
		for (int i = datos.length-1; i >= 0; i--) {
			for (int j = 0; j < datos.length; j++) {
				if (j<datos.length-1) {
					s = s + datos[i][j]+",";
				}else {
					s = s + datos[i][j]+"\n";
				}
			}
		}
		return s;
	}
	
	
}

package us.lsi.astar.puzzle;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ProblemaPuzzle {

	/**
	 * @param d Lista de valores del puzzle dados por filas de abajo arriba
	 * @return Un EstadoPuzzle
	 */
	public static ProblemaPuzzle create(Integer... d) {
		return new ProblemaPuzzle(d);
	}
	
	public static ProblemaPuzzle create(Integer[][] datos, int i0, int j0) {
		return new ProblemaPuzzle(datos, i0, j0);
	}
	
	private Integer[][] datos; 
	private int i0;
	private int j0;
	public static int numFilas = 3;
	
	private ProblemaPuzzle(Integer... d) {
		super(); 
		this.datos = new Integer[ProblemaPuzzle.numFilas][ProblemaPuzzle.numFilas];
		Set<Integer> s = new HashSet<Integer>();	
		int i=0,j=0;
		for (int e:d) {							
					if(e<0 || e> (ProblemaPuzzle.numFilas*ProblemaPuzzle.numFilas-1)){
						throw new IllegalArgumentException();
					}
					if(e==0){
						this.i0 =i;
						this.j0 =j;
					}
					s.add(e);
					this.datos[i][j] = e;
					if(j== ProblemaPuzzle.numFilas-1){
						i++;
						j=0;
					}else{
						j++;
					}
				
		}
		if(d.length!= ProblemaPuzzle.numFilas*ProblemaPuzzle.numFilas || s.size()!= ProblemaPuzzle.numFilas*ProblemaPuzzle.numFilas)
			throw new IllegalArgumentException();
	}


	private ProblemaPuzzle(Integer[][] datos, int i0, int j0) {
		super();
		this.datos = datos;
		this.i0 = i0;
		this.j0 = j0;
	}

	public Integer getDato(int i, int j) {
		if(i<0 || i>=ProblemaPuzzle.numFilas || j<0 || j>=ProblemaPuzzle.numFilas)
			throw new IllegalArgumentException();
		return datos[i][j];
	}

	
	public int getI0() {
		return i0;
	}


	public int getJ0() {
		return j0;
	}


	public static int getNumFilas() {
		return numFilas;
	}

	public boolean isValid() {
		Set<Integer> s = new HashSet<Integer>();	
		for (int j = 0; j < ProblemaPuzzle.numFilas; j++) {
			for (int i = 0; i < ProblemaPuzzle.numFilas; i++) {
				s.add(datos[i][j]);
			}
		}
		return s.size()== ProblemaPuzzle.numFilas*ProblemaPuzzle.numFilas;
	}
	
	public Integer getNumDiferentes(ProblemaPuzzle e){
		Integer s = 0;
		for (int i = 0; i < ProblemaPuzzle.numFilas; i++) {
			for (int j = 0; j < ProblemaPuzzle.numFilas; j++) {
				if (!this.datos[i][j].equals(e.datos[i][j])) {
					s++;
				}
			}
		}
		return s;
	}
	
	public boolean isNeighbor(ProblemaPuzzle e) {
		return this.getNumDiferentes(e)==2 && Math.abs(this.i0-e.i0)+Math.abs(this.j0-e.j0)==1;
	}
	
	public ProblemaPuzzle getVecino(int incx, int incy){
		int f = this.i0+incx;
		int c = this.j0+incy;
		if(f<0 || f>=ProblemaPuzzle.numFilas || c<0 || c>=ProblemaPuzzle.numFilas)
			throw new IllegalArgumentException();
		Integer[][] dd = new Integer[ProblemaPuzzle.numFilas][ProblemaPuzzle.numFilas];
		for (int i = 0; i < ProblemaPuzzle.numFilas; i++) {
			for (int j = 0; j < ProblemaPuzzle.numFilas; j++) {
				dd[i][j] = datos[i][j];
			}
		}
		dd[f][c] = datos[this.i0][this.j0];
		dd[this.i0][j0] = datos[f][c];
		return create(dd, f, c);
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
		if (!(obj instanceof ProblemaPuzzle))
			return false;
		ProblemaPuzzle other = (ProblemaPuzzle) obj;
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

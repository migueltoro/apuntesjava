package us.lsi.bt.sudoku;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

public class CuadroSudoku {

	
	public static CuadroSudoku create() {
		return new CuadroSudoku();
	}
	
	public static CuadroSudoku create(CuadroSudoku cuadro) {
		return new CuadroSudoku(cuadro);
	}
	
	private Integer[][] datos;
	private final Integer numFilas;
	private List<Set<Integer>> valoresOcupadosEnX;
	private List<Set<Integer>> valoresOcupadosEnY;
	private List<Set<Integer>> valoresOcupadosEnSubCuadro;
	private Integer objetivo;
	
	private CuadroSudoku() {
		super();
		this.numFilas = ProblemaSudoku.numeroDeFilas;
		this.datos = new Integer[numFilas][numFilas];
		
		for(int i=0;i<numFilas;i++){
			for(int j=0;j<numFilas;j++){
				if(!ProblemaSudoku.estaLibre(i, j)){
					datos[i][j] = ProblemaSudoku.getInfoDeOcupadas(i, j);
				}else{
					datos[i][j] = 0;
				}
			}
		}
		calculaPropiedadesDerivadas();
	}
	
	
	private CuadroSudoku(CuadroSudoku cuadro){
		this.numFilas = cuadro.numFilas;
		this.datos = new Integer[numFilas][numFilas];
		for (int i = 0; i < this.numFilas; i++) {
			for (int j = 0; j < this.numFilas; j++) {
				this.datos[i][j] = cuadro.datos[i][j];
			}
		}
		this.valoresOcupadosEnX = Lists.newArrayList(cuadro.valoresOcupadosEnX);
		this.valoresOcupadosEnY = Lists.newArrayList(cuadro.valoresOcupadosEnY);
		this.valoresOcupadosEnSubCuadro = Lists.newArrayList(cuadro.valoresOcupadosEnSubCuadro);
		this.objetivo = cuadro.objetivo;
	}
	
	private void calculaPropiedadesDerivadas(){
		
		this.valoresOcupadosEnX = Lists.newArrayList();
		this.valoresOcupadosEnY = Lists.newArrayList();
		this.valoresOcupadosEnSubCuadro = Lists.newArrayList();
		
		for(Integer i=0; i< this.numFilas;i++){
			this.valoresOcupadosEnX.add(Sets.<Integer>newHashSet());
			this.valoresOcupadosEnY.add(Sets.<Integer>newHashSet());
			this.valoresOcupadosEnSubCuadro.add(Sets.<Integer>newHashSet());
		}
		
		for(int i=0;i<numFilas;i++){
			for(int j=0;j<numFilas;j++){
				this.valoresOcupadosEnX.get(i).add(this.getInfo(i,j));
				this.valoresOcupadosEnY.get(j).add(this.getInfo(i,j));
				this.valoresOcupadosEnSubCuadro.get(ProblemaSudoku.getSubCuadro(i,j)).add(this.getInfo(i,j));
			}
		}
		
		for(Integer i=0; i< this.numFilas;i++){
			this.valoresOcupadosEnX.get(i).remove(0);
			this.valoresOcupadosEnY.get(i).remove(0);
			this.valoresOcupadosEnSubCuadro.get(i).remove(0);
		}
		
		this.objetivo = 3*numFilas*numFilas;
		
		for(int i=0;i<numFilas;i++){
			this.objetivo = this.objetivo - this.valoresOcupadosEnX.get(i).size();
			this.objetivo = this.objetivo - this.valoresOcupadosEnY.get(i).size();
			this.objetivo = this.objetivo - this.valoresOcupadosEnSubCuadro.get(i).size();
		}
				
	}
	
	
	public Set<Integer> getValoresOcupadosEnPos(Integer pos){
		Casilla casilla = ProblemaSudoku.getCasilla(ProblemaSudoku.posicionesLibres.get(pos));
		Set<Integer> ocupados = Sets.newHashSet();
		ocupados.addAll(this.getValoresOcupadosEnX().get(casilla.x));
		ocupados.addAll(this.getValoresOcupadosEnY().get(casilla.y));
		ocupados.addAll(this.getValoresOcupadosEnSubCuadro().get(casilla.sc));
		return ocupados;
	}
	
	public Integer getInfo(Integer x, Integer y) {
		return datos[x][y];
	}

	public Integer getInfo(Integer p) {
		Casilla casilla = ProblemaSudoku.getCasilla(p);
		return getInfo(casilla.x,casilla.y);
	}
	
	public Integer getObjetivo() {
		return objetivo;
	}

	public void setInfo(int x, int y, Integer info){
		this.datos[x][y] = info;
		calculaPropiedadesDerivadas();
	}
	
	public void setInfo(int pos, Integer info){
		Casilla casilla = ProblemaSudoku.getCasilla(pos);
		setInfo(casilla.x,casilla.y,info);
	}
	
	public void intercambia(int p1, int p2){
		Casilla c1 = ProblemaSudoku.getCasilla(p1);
		Casilla c2 = ProblemaSudoku.getCasilla(p2);
		intercambia(c1.x,c1.y,c2.x,c2.y);
	}
	
	public void intercambia(int x1, int y1, int x2, int y2){
		Integer oldY1 = this.getInfo(x1, y1);
		Integer oldY2 = this.getInfo(x2, y2);
		this.datos[x1][y1] = oldY2;
		this.datos[x2][y2] = oldY1;
		calculaPropiedadesDerivadas();
	}
	
	public void show() {
		String s = "";
		for(int y=ProblemaSudoku.numeroDeFilas-1;y>=0;y--){
			for(int x=0;x < ProblemaSudoku.numeroDeFilas;x++){
					s = s+" "+this.getInfo(x,y);		
			}
			s = s+"\n";
		}
		System.out.println(s);
		System.out.println("Objetivo ="+this.objetivo);
	}

	public Integer[][] getDatos() {
		return datos;
	}

	public Integer getNumFilas() {
		return numFilas;
	}

	public List<Set<Integer>> getValoresOcupadosEnY() {
		return valoresOcupadosEnY;
	}

	public List<Set<Integer>> getValoresOcupadosEnSubCuadro() {
		return valoresOcupadosEnSubCuadro;
	}

	public List<Set<Integer>> getValoresOcupadosEnX() {
		return valoresOcupadosEnX;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(datos);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof CuadroSudoku))
			return false;
		CuadroSudoku other = (CuadroSudoku) obj;
		if (!Arrays.deepEquals(datos, other.datos))
			return false;
		return true;
	}
	
	
}

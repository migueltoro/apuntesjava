package us.lsi.sa.sudoku;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.Comparator;

import us.lsi.bt.SolucionBT;
import us.lsi.common.Lists2;
import us.lsi.common.StringExtensions2;
import us.lsi.stream.Stream2;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Lists;
import com.google.common.collect.Multiset;
import com.google.common.collect.Sets;
import com.google.common.collect.Table;

import java.util.stream.IntStream;

public class CuadroSudoku implements SolucionBT{

	/**
	 * Tamaño de un subcuadro
	 */
	public static Integer tamSubCuadro = 3;
	/**
	 * Número de filas
	 */
	public static Integer numeroDeFilas = tamSubCuadro*tamSubCuadro;
	/**
	 * Numero de casillas
	 */
	public static Integer numeroDeCasillas = numeroDeFilas*numeroDeFilas;
	/**
	 * Casillas ocupadas
	 */
	public static Table<Integer,Integer,Integer> casillasOcupadas;
	/**
	 * Listas de posiciones libres inicialmente
	 */
	public static List<Integer> posicionesLibres;
	/**
	 * Listas de posiciones ocupadas inicialmente
	 */
	public static List<Integer> posicionesOcupadas;
	/**
	 * Numero de posiciones libres inicialmente
	 */
	public static Integer numPosicionesLibres;
	
	/**
	 * Lista de posiciones libres en cada fila
	 */
	public static List<List<Integer>> posicionesLibresEnfilas;
	
	/**
	 * Comienzo de cada fila en la lista de posicines libres
	 */
	public static List<Integer> comienzoDeFilas;
	

	/**
	 * Lista de valores en las casillas. Inicialmente con cero en las posiciones libres
	 */
	private static List<Integer> valores;
	
	private List<Integer> listDatos;
	private List<Set<Integer>> valoresOcupadosEnX;
	private List<Set<Integer>> valoresOcupadosEnY;
	private List<Set<Integer>> valoresOcupadosEnSubCuadro;
	
	private List<Integer> valoresEnPosicionesLibres;
	
	private Integer objetivo;
	
	
	
	/**
	 * @return Crea un cuadro rellenando las casillas libres con cero.
	 */
	public static CuadroSudoku create() {
		List<Integer> valoresEnPosicionesLibres = Lists2.nCopias(CuadroSudoku.numPosicionesLibres, 0);
		return new CuadroSudoku(valoresEnPosicionesLibres);
	}	
	
	/**
	 * @return Crea un cuadro completando cada una de las posiciones libres con un valor todavía no asignado
	 */
	public static CuadroSudoku createCompletadoPorPosicionesLibres() {
		Multiset<Integer> ms = HashMultiset.create();
		ms.addAll(CuadroSudoku.casillasOcupadas.values());
		List<Integer> valoresEnPosicionesLibres = IntStream.rangeClosed(1,CuadroSudoku.numeroDeFilas)
				.boxed()
				.map(x->Lists2.nCopias(CuadroSudoku.numeroDeFilas-ms.count(x),x).stream())
				.flatMap(x->x)
				.collect(Collectors.toList());
		return new CuadroSudoku(valoresEnPosicionesLibres);
	}
	
	/**
	 * @return Crea un cuadro completando las casillas libres de cada fila con los valores no fijados en la misma
	 */
	public static CuadroSudoku createCompletadoPorFilas() {
		CuadroSudoku c = CuadroSudoku.create();
		List<Integer> valoresEnPosicionesLibres = Lists.newArrayList();
		CuadroSudoku.comienzoDeFilas = Lists.newArrayList();
		Set<Integer> s = IntStream.rangeClosed(1, CuadroSudoku.numeroDeFilas).boxed().collect(Collectors.toSet());
		int n = 0;
		for (int i = 0; i < CuadroSudoku.numeroDeFilas; i++) {
			CuadroSudoku.comienzoDeFilas.add(n);
			Set<Integer> libres = Sets.newHashSet(s);
			libres.removeAll(c.valoresOcupadosEnY.get(i));
			valoresEnPosicionesLibres.addAll(libres);
			n = n + libres.size();
		}
		return new CuadroSudoku(valoresEnPosicionesLibres);
	}
	
	/**
	 * @param valoresEnPosicionesLibres Valores asignados a las posiciones libres en la lista CuadroSudoku.posicionesLibres
	 * @return El cuadro creado
	 */
	public static CuadroSudoku create(List<Integer> valoresEnPosicionesLibres) {
		return new CuadroSudoku(valoresEnPosicionesLibres);
	}
	
	/**
	 * Ordena las posiciones libres con respecto al número de valores libres en esa posición para el cuadro inicial
	 */
	public static void sortPosicionesLibresPorNumVal() {
		CuadroSudoku c = CuadroSudoku.create();
		CuadroSudoku.posicionesLibres = CuadroSudoku.posicionesLibres
				.stream()
				.sorted(Comparator.comparingInt(x->c.getValoresLibresEnPos(x).size()))
				.collect(Collectors.toList());
	}
	
	/**
	 * Ordena las posiciones libres con respecto al número de su posición en el cuadro 
	 */
	public static void sortPosicionesLibresPorPos() {
		CuadroSudoku.posicionesLibres = CuadroSudoku.posicionesLibres
				.stream()
				.sorted()
				.collect(Collectors.toList());
	}
	
	/**
	 * @return Valores libres en la posición que se guarda en la correspondiente casilla de CuadroSudokuAG.posicionesLibres
	 */
	public static List<List<Integer>> valoresLibres() {
		CuadroSudoku c = CuadroSudoku.create();
		return CuadroSudoku.posicionesLibres.stream()
				.map(x->c.getValoresLibresEnPos(x).stream().collect(Collectors.toList()))
				.collect(Collectors.toList());
	}
	
	/**
	 * Asigna valores a las casillas que sólo pueden tener un valor y reduce el número de casillas libres
	 */
	public static void asignaValoresUnicos() {
		CuadroSudoku.sortPosicionesLibresPorNumVal();
		List<List<Integer>> lv = CuadroSudoku.valoresLibres();
		List<List<Integer>> lv2 = lv.stream().filter(x->x.size()==1).collect(Collectors.toList());
		int n = lv2.size();
		while (n>0) {
			for (int i = 0; i < n; i++) {
				Casilla c = Casilla.create(CuadroSudoku.posicionesLibres.get(i));
				CuadroSudoku.casillasOcupadas.put(c.x, c.y, lv.get(i).get(0));				
			}	
			CuadroSudoku.iniLibresYOcupadas();
			CuadroSudoku.sortPosicionesLibresPorNumVal();
			lv = CuadroSudoku.valoresLibres();
			lv2 = lv.stream().filter(x -> x.size() == 1).collect(Collectors.toList());
			n = lv2.size();
		}
	}
	
	private CuadroSudoku(List<Integer> valoresEnPosicionesLibres) {
		super();
		this.valoresEnPosicionesLibres = Lists.newArrayList(valoresEnPosicionesLibres);
		this.listDatos = Lists.newArrayList(CuadroSudoku.valores);
		this.completaValores(valoresEnPosicionesLibres);		
		this.calculaPropiedadesDerivadas();
	}
	
	/**
	 * @param valoresEnPosicionesLibres Valores en las posiciones libres
	 * @pos Rellena las posiciones libres con los valores dados
	 */
	private void completaValores(List<Integer> valoresEnPosicionesLibres) {		
		for (int i = 0; i < valoresEnPosicionesLibres.size(); i++) {
			int p = CuadroSudoku.posicionesLibres.get(i);
			int val = valoresEnPosicionesLibres.get(i);		
			this.listDatos.set(p,val);
		}		
	}
	/**
	 * @param x Coordenada x de la casilla
	 * @param y Coordenada y de la casilla
	 * @return El valor de la casilla
	 */
	public Integer get(int x, int y) {
		Casilla c = Casilla.create(x, y);
		return this.listDatos.get(c.p);
	}

	/**
	 * @return El objetivo a maximizar
	 */
	public Integer getObjetivoMax() {
		return objetivo;
	}
	
	/**
	 * @return El objetivo a minimizar. Si es cero tenemos un Sudoku legal
	 */
	public Integer getObjetivoMin() {
		return 3*CuadroSudoku.numeroDeFilas*CuadroSudoku.numeroDeFilas -this.objetivo;		
	}
	
	/**
	 * @return Una lista con todos los datos del cuadro recorridos por filas
	 */
	public List<Integer> getListDatos() {
		return listDatos;
	}
	
	/**
	 * @return Valores en las posiciones libres
	 */
	public List<Integer> getValoresEnPosicionesLibres() {
		return valoresEnPosicionesLibres;
	}

	private void calculaPropiedadesDerivadas(){
		
		this.valoresOcupadosEnX = Lists.newArrayList();
		this.valoresOcupadosEnY = Lists.newArrayList();
		this.valoresOcupadosEnSubCuadro = Lists.newArrayList();
		
		for(Integer i=0; i< CuadroSudoku.numeroDeFilas;i++){
			this.valoresOcupadosEnX.add(Sets.<Integer>newHashSet());
			this.valoresOcupadosEnY.add(Sets.<Integer>newHashSet());
			this.valoresOcupadosEnSubCuadro.add(Sets.<Integer>newHashSet());
		}	
		
		for (int p = 0; p < CuadroSudoku.numeroDeCasillas; p++) {
			Casilla c = Casilla.create(p);
			Integer val = this.listDatos.get(p);
			this.valoresOcupadosEnX.get(c.x).add(val);
			this.valoresOcupadosEnY.get(c.y).add(val);
			this.valoresOcupadosEnSubCuadro.get(c.sc).add(val);
		}
		
		this.objetivo = 0;
		
		for(int i=0;i<CuadroSudoku.numeroDeFilas;i++){
			this.valoresOcupadosEnX.get(i).remove(0);
			this.objetivo = this.objetivo + this.valoresOcupadosEnX.get(i).size();
			this.valoresOcupadosEnY.get(i).remove(0);
			this.objetivo = this.objetivo + this.valoresOcupadosEnY.get(i).size();
			this.valoresOcupadosEnSubCuadro.get(i).remove(0);
			this.objetivo = this.objetivo + this.valoresOcupadosEnSubCuadro.get(i).size();			
		}
		
//		this.objetivo = 3*CuadroSudokuAG.numeroDeFilas*CuadroSudokuAG.numeroDeFilas -this.objetivo;		
	}
	
	
	
	/**
	 * Inicializa las posiciones libres y ocupadas
	 */
	public static void iniLibresYOcupadas() {
		CuadroSudoku.posicionesLibres = Lists.newArrayList();
		CuadroSudoku.posicionesOcupadas = Lists.newArrayList();
		CuadroSudoku.posicionesLibresEnfilas = Lists.newArrayList();		
		for (int i = 0; i < CuadroSudoku.numeroDeFilas; i++) {
			CuadroSudoku.posicionesLibresEnfilas.add(Lists.newArrayList());
		}
		CuadroSudoku.valores = Lists2.nCopias(CuadroSudoku.numeroDeCasillas, 0);
		for (int p = 0; p < CuadroSudoku.numeroDeCasillas; p++) {
			Casilla c = Casilla.create(p);
			if(c.isFree){
				CuadroSudoku.posicionesLibres.add(p);
				CuadroSudoku.posicionesLibresEnfilas.get(c.y).add(p);
			} else {
				CuadroSudoku.posicionesOcupadas.add(p);
				CuadroSudoku.valores.set(p, CuadroSudoku.casillasOcupadas.get(c.x, c.y));
			}				
		}	
		CuadroSudoku.numPosicionesLibres = CuadroSudoku.posicionesLibres.size();
	}
	
	/**
	 * @param p Una posición en el cuadrado
	 * @return El conjunto de valores ocupados en esa posición
	 */
	public Set<Integer> getValoresOcupadosEnPos(Integer p){
		Casilla casilla = Casilla.create(p);
		Set<Integer> ocupados = Sets.newHashSet();
		ocupados.addAll(this.valoresOcupadosEnX.get(casilla.x));
		ocupados.addAll(this.valoresOcupadosEnY.get(casilla.y));
		ocupados.addAll(this.valoresOcupadosEnSubCuadro.get(casilla.sc));
		return ocupados;
	}
	
	/**
	 * @param p Una posición en el cuadrado
	 * @return El conjunto de valores libres en esa posición
	 */
	public Set<Integer> getValoresLibresEnPos(Integer p) {
		if(p>=CuadroSudoku.numeroDeCasillas) return Sets.newHashSet();
		Set<Integer> s = IntStream.rangeClosed(1,CuadroSudoku.numeroDeFilas)
				.boxed().collect(Collectors.toSet());
		s.removeAll(this.getValoresOcupadosEnPos(p));
		return s;
	}
	
	/**
	 * @param nf Fichero de datos
	 * @pos Inicializa las variables del tipo
	 */
	public static void iniDatos(String nf) {
		CuadroSudoku.casillasOcupadas = HashBasedTable.<Integer,Integer,Integer>create();
		Stream2.fromFile(nf)
				.<String[]> map(s -> StringExtensions2.toArray(s, "[, ]"))
				.forEach(x -> CuadroSudoku.casillasOcupadas.put(new Integer(
								x[0]), new Integer(x[1]), new Integer(x[2])));	
		CuadroSudoku.iniLibresYOcupadas();
	}
	
	@Override
	public Double getObjetivo() {
		return (double) 3*CuadroSudoku.numeroDeFilas*CuadroSudoku.numeroDeFilas -this.objetivo;
	}
	
	/**
	 * Muestra en cuadrado en forma de cadena
	 */
	public void show() {
		String s = "";
		for(int y=CuadroSudoku.numeroDeFilas-1;y>=0;y--){
			for(int x=0;x < CuadroSudoku.numeroDeFilas;x++){
					s = s+" "+get(x,y);		
			}
			s = s+"\n";
		}
		System.out.println(s);
		System.out.println("Objetivo ="+this.objetivo);
		System.out.println("Objetivo Min="+this.getObjetivoMin());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((listDatos == null) ? 0 : Arrays.hashCode(listDatos.toArray()));
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
		if (listDatos == null) {
			if (other.listDatos != null)
				return false;
		} else if (!Arrays.deepEquals(listDatos.toArray(),other.listDatos.toArray()))
			return false;
		return true;
	}

	
	
	
}

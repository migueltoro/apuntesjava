package us.lsi.bt.sudoku;

import java.util.List;
import java.util.stream.IntStream;

import com.google.common.base.Preconditions;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Lists;
import com.google.common.collect.Table;

import us.lsi.stream.Stream2;
import us.lsi.common.*;

public class ProblemaSudoku  {

	public static Integer tamSubCuadro = 3;
	public static Integer numeroDeFilas = tamSubCuadro*tamSubCuadro;
	public static Integer numeroDeCasillas = numeroDeFilas*numeroDeFilas;
	public static Table<Integer,Integer,Integer> casillasOcupadas;
	public static List<Integer>	posicionesOcupadas;
	public static List<Integer>	posicionesLibres;
	public static List<List<Integer>> posicionesLibresEnX;
	public static List<List<Integer>> posicionesLibresEnY;
	public static List<List<Integer>> posicionesLibresEnSubCuadro;
	
	public ProblemaSudoku(String nf) {
		super();
		leeDatos(nf);
	}

	public static Integer getInfoDeOcupadas(Integer x, Integer y) {
		Preconditions.checkArgument(!ProblemaSudoku.estaLibre(x, y));
		return casillasOcupadas.get(x, y);
	}

	public static Integer getInfoDeOcupadas(Integer pos) {
		Preconditions.checkArgument(!ProblemaSudoku.estaLibre(pos));
		Casilla casilla = ProblemaSudoku.getCasilla(pos);
		return casillasOcupadas.get(casilla.x, casilla.y);
	}
	
	public static boolean estaLibre(Integer x, Integer y) {
		return !casillasOcupadas.contains(x, y);
	}

	public static boolean estaLibre(Integer pos){
		Integer y = pos/numeroDeFilas;
		Integer x = pos%numeroDeFilas;		
		return ProblemaSudoku.estaLibre(x, y);
	}
	
	public static Integer getPos(Integer x, Integer y){
		Preconditions.checkArgument(0 <= x && x < ProblemaSudoku.numeroDeFilas);
		Preconditions.checkArgument(0 <= y && y < ProblemaSudoku.numeroDeFilas);
		return y*ProblemaSudoku.numeroDeFilas+x;
	}
	
	public static Casilla getCasilla(Integer pos) {
		Preconditions.checkArgument(0<=pos && pos < ProblemaSudoku.numeroDeCasillas);
		Integer y = pos/numeroDeFilas;
		Integer x = pos%numeroDeFilas;
		Integer tm = ProblemaSudoku.tamSubCuadro;
		Integer sc = x/tm+tm*(y/tm);
		boolean isFree = ProblemaSudoku.estaLibre(x, y);
		Integer info = isFree ? 0 : ProblemaSudoku.getInfoDeOcupadas(x, y);	
		return Casilla.create(x, y, sc, isFree, info, pos);
	}
	
	public static Integer getSubCuadro(Integer x, Integer y){
		Integer tm = tamSubCuadro;
		return x/tm+tm*(y/tm);
	}

	private static void asignaLibres(Integer p) {
		ProblemaSudoku.posicionesLibres.add(p);
		Casilla casilla = ProblemaSudoku.getCasilla(p);
		ProblemaSudoku.posicionesLibresEnX.get(casilla.x).add(p);
		ProblemaSudoku.posicionesLibresEnY.get(casilla.y).add(p);
		ProblemaSudoku.posicionesLibresEnSubCuadro.get(casilla.sc).add(p);
	}
	
	private static void asignaOcupadas(Integer p){
		ProblemaSudoku.posicionesOcupadas.add(p);
	}
	
	private static void iniList() {
		posicionesLibres = Lists.newArrayList();
		posicionesOcupadas = Lists.newArrayList();
		posicionesLibresEnX = Lists.newArrayList();
		posicionesLibresEnY = Lists.newArrayList();
		posicionesLibresEnSubCuadro = Lists.newArrayList();		
		List<Integer> s;
		for(int i = 0; i< ProblemaSudoku.numeroDeFilas; i++) {
	    	s = Lists.newArrayList();
	    	ProblemaSudoku.posicionesLibresEnX.add(s);
	    	s = Lists.newArrayList();
	    	ProblemaSudoku.posicionesLibresEnY.add(s);
	    	s = Lists.newArrayList();
	    	ProblemaSudoku.posicionesLibresEnSubCuadro.add(s);
	    }
	}
	
	private static void calculaPosicionesLibresYOcupadas() {
		iniList();
		IntStream.range(0, ProblemaSudoku.numeroDeCasillas)
				.filter(p -> ProblemaSudoku.estaLibre(p))
				.forEach(p -> ProblemaSudoku.asignaLibres(p));
		IntStream.range(0, ProblemaSudoku.numeroDeCasillas)
				.filter(p -> !ProblemaSudoku.estaLibre(p))
				.forEach(p -> ProblemaSudoku.asignaOcupadas(p));
	}
	
	private static void leeDatos(String nf) {
		casillasOcupadas = HashBasedTable.<Integer,Integer,Integer>create();
		Stream2.fromFile(nf)
				.<String[]> map(s -> StringExtensions2.toArray(s, "[, ]"))
				.forEach(x -> ProblemaSudoku.casillasOcupadas.put(new Integer(
								x[0]), new Integer(x[1]), new Integer(x[2])));
		calculaPosicionesLibresYOcupadas();		
	}

	public static boolean esSolucion(CuadroSudoku cuadro) {
		boolean r = true;
		for (int i = 0; i < ProblemaSudoku.numeroDeFilas; i++) {
			r = r & cuadro.getValoresOcupadosEnX().get(i).size() == ProblemaSudoku.numeroDeFilas;
			r = r & cuadro.getValoresOcupadosEnY().get(i).size() == ProblemaSudoku.numeroDeFilas;
			r = r & cuadro.getValoresOcupadosEnSubCuadro().get(i).size() == ProblemaSudoku.numeroDeFilas;
		}
		for(Integer p: posicionesOcupadas){
			r = r && cuadro.getInfo(p) == ProblemaSudoku.getInfoDeOcupadas(p);
		}
		return r;
	}
	
}

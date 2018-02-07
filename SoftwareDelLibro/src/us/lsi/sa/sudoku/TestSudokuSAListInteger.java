package us.lsi.sa.sudoku;

import us.lsi.ag.agchromosomes.ChromosomeFactory;
import us.lsi.algoritmos.Algoritmos;
import us.lsi.sa.AlgoritmoSA;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

public class TestSudokuSAListInteger {

	public static void main(String[] args) {
		AlgoritmoSA.setFile("C:\\Users\\Boss\\Desktop\\Ficheros\\traza.txt");	
		AlgoritmoSA.temperaturaInicial = 30000;
		AlgoritmoSA.alfa = 0.92;
		AlgoritmoSA.numeroDeIteracionesPorIntento = 20000;
		AlgoritmoSA.numeroDeIteracionesALaMismaTemperatura = 1;
		AlgoritmoSA.numeroDeIntentos = 10;
		CuadroSudoku.tamSubCuadro = 3;		
		CuadroSudoku.iniDatos("sudoku.txt");
		CuadroSudoku.asignaValoresUnicos();
		ProblemaSudokuAGListInteger p = new ProblemaSudokuAGListInteger();	
		AlgoritmoSA a = Algoritmos.createSA(ChromosomeFactory.ChromosomeType.ListInteger ,p);	
//		System.out.println(CuadroSudoku.comienzoDeFilas+","+CuadroSudoku.numPosicionesLibres);
		a.ejecuta();
		System.out.println("Mejor Solución =");
		CuadroSudoku c = CuadroSudoku.create(ChromosomeFactory.asListInteger(a.mejorSolucionEncontrada).decode());
		c.show();
		System.out.println(c.getObjetivoMin());
		Multiset<Integer> ms = HashMultiset.create();
		ms.addAll(c.getListDatos());
		System.out.println(ms);	
	}

}

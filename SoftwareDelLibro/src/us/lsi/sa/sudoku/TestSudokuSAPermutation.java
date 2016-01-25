package us.lsi.sa.sudoku;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

import us.lsi.ag.agchromosomes.ChromosomeFactory;
import us.lsi.algoritmos.Algoritmos;
import us.lsi.sa.AlgoritmoSA;


/**
 * Ajuste del Algoritmo 
 * 
 * 
 * 
 * @author Miguel Toro
 *
 */
public class TestSudokuSAPermutation {

	public static void main(String[] args) {
		
		
		AlgoritmoSA.setFile("C:\\Users\\Boss\\Desktop\\Ficheros\\traza.txt");	
		AlgoritmoSA.temperaturaInicial = 30000;
		AlgoritmoSA.alfa = 0.9;
		AlgoritmoSA.numeroDeIteracionesPorIntento = 20000;
		AlgoritmoSA.numeroDeIteracionesALaMismaTemperatura = 1;
		AlgoritmoSA.numeroDeIntentos = 10;
		CuadroSudoku.tamSubCuadro = 3;		
		CuadroSudoku.iniDatos("sudoku.txt");
		CuadroSudoku.asignaValoresUnicos();
		ProblemaSudokuAGPermutation p = new ProblemaSudokuAGPermutation();	
		AlgoritmoSA a = Algoritmos.createSA(ChromosomeFactory.ChromosomeType.IndexPermutation ,p);		
		a.ejecuta();
		System.out.println("Mejor Solución =");
		CuadroSudoku c = CuadroSudoku.create(ChromosomeFactory.asIndex(a.mejorSolucionEncontrada.asChromosome()).decode());
		c.show();
		Multiset<Integer> ms = HashMultiset.create();
		ms.addAll(c.getListDatos());
		System.out.println(ms);		
	}

}

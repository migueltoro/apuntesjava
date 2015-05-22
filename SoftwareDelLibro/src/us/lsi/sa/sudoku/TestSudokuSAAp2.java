package us.lsi.sa.sudoku;

import us.lsi.algoritmos.Algoritmos;
import us.lsi.bt.sudoku.CuadroSudoku;
import us.lsi.common.ParInteger;
import us.lsi.sa.AlgoritmoSA;

public class TestSudokuSAAp2 {

	public static void main(String[] args) {
		
		AlgoritmoSA.setFile("C:\\Users\\Boss\\Desktop\\Ficheros\\traza.txt");	
		AlgoritmoSA.temperaturaInicial = 15;
		AlgoritmoSA.alfa = 0.99;
		AlgoritmoSA.numeroDeIteracionesPorIntento = 50000;
		AlgoritmoSA.numeroDeIteracionesALaMismaTemperatura = 1;
		AlgoritmoSA.numeroDeIntentos = 50;
	
		AlgoritmoSA<EstadoSudokuSAAp2,CuadroSudoku,ParInteger> a = Algoritmos.createSA(new ProblemaSudokuSAAp2("sudoku.txt"));
		
		a.ejecuta();
		
		System.out.println("Mejor Solución = \n");
		a.mejorSolucionEncontrada.cuadro.show();
	}

}

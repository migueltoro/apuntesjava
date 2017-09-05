package us.lsi.bt.sudoku;

import us.lsi.algoritmos.Algoritmos;
import us.lsi.bt.AlgoritmoBT;
import us.lsi.sa.sudoku.CuadroSudoku;

public class TestSudokuBT{

	

	public static void main(String[] args) {
		
		AlgoritmoBT.isRandomize = false;
		AlgoritmoBT.numeroDeSoluciones = 4;
		CuadroSudoku.tamSubCuadro = 3;
		CuadroSudoku.iniDatos("sudoku.txt");
		CuadroSudoku.asignaValoresUnicos();
		EstadoSudokuBT e = EstadoSudokuBT.create();
		e.getAlternativas();
		AlgoritmoBT<CuadroSudoku,Integer> a = Algoritmos.createBT(e);
		
		a.ejecuta();

		System.out.println("Num de Soluciones = "+a.getSoluciones().size()+"\n\n");
		for(CuadroSudoku c: a.getSoluciones()){
			System.out.println("El valor siguiente es \n\n");
			c.show();
		}

	}

}

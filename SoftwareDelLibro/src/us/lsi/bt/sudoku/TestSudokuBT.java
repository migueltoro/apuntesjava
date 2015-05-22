package us.lsi.bt.sudoku;



import us.lsi.algoritmos.Algoritmos;
import us.lsi.bt.AlgoritmoBT;


public class TestSudokuBT {

	public static String ruta = "C:\\Users\\Boss\\Desktop\\Ficheros\\";
	
	public static void main(String[] args) {	
		
		
		AlgoritmoBT.setFile("C:\\Users\\Boss\\Desktop\\Ficheros\\traza.txt");
		AlgoritmoBT.soloLaPrimeraSolucion = true;
		AlgoritmoBT.isRandomize = false;
		AlgoritmoBT.numeroDeSoluciones = 1;
		AlgoritmoBT<CuadroSudoku,Integer> a = Algoritmos.createBT(new ProblemaSudokuBT(AlgoritmoBT.getRaiz()+"sudoku.txt"));
		
		a.ejecuta();

		System.out.println("Num de Soluciones = "+a.soluciones.size()+"\n\n");
		for(CuadroSudoku c: a.soluciones){
			System.out.println("El valor siguiente es solución "+ProblemaSudoku.esSolucion(c)+"\n\n");
			c.show();
		}
	
	}

		
}

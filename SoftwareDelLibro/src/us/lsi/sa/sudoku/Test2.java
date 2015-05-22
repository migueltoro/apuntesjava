package us.lsi.sa.sudoku;


public class Test2 {

	public static void main(String[] args) {
		EstadoSudokuSAAp1 e = new ProblemaSudokuSAAp1("sudoku.txt").getEstadoInicial();
		e.cuadro.show();
		for(int i=0; i< 100;i++){
			double obj = e.getObjetivo();
			e = e.next(e.getAlternativa());
			System.out.println(obj-e.getObjetivo());
		}
/*		for(int x=0; x < ProblemaSudoku.numeroDeFilas; x++){
			System.out.println(e.cuadro.getValoresOcupadosEnX().get(x).size());
		}
*/	}

}

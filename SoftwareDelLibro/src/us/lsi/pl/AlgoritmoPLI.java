package us.lsi.pl;

import lpsolve.LpSolve;
import lpsolve.LpSolveException;

public class AlgoritmoPLI implements SolutionPL {

	public static AlgoritmoPLI create(IProblemaPL p, String fichero) {
		p.toStringConstraints(fichero);
		return new AlgoritmoPLI(fichero);
	}
	
	public static AlgoritmoPLI create(String fichero) {
		return new AlgoritmoPLI(fichero);
	}
	
	private double[] solutionPoint;
	private double solutionValue;
	
	private String fichero;
	
	private LpSolve solver;

	private AlgoritmoPLI(String fichero) {
		super();
		this.fichero = fichero;
	}
	
	/**
	 * @return El coste total del objetivo
	 */
	public double getObjetivo(){
		return solutionValue;
	}

	/**
	 * @return El punto solución
	 */
	public double[] getSolucion() {
		return solutionPoint;
	}

	
	
	
	public void ejecuta(){
		try {
			solver = LpSolve.readLp(this.fichero, 1, "Problema");

			solver.solve();
			solutionPoint = solver.getPtrVariables();
			solutionValue = solver.getObjective();

			// delete the problem and free memory
			solver.deleteLp();
		} catch (LpSolveException e) {
			e.printStackTrace();
		}

	}
	
	
	
}

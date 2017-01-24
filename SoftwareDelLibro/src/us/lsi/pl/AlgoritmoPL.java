package us.lsi.pl;

import java.util.Collection;

import org.apache.commons.math3.optim.PointValuePair;
import org.apache.commons.math3.optim.linear.LinearConstraint;
import org.apache.commons.math3.optim.linear.LinearConstraintSet;
import org.apache.commons.math3.optim.linear.LinearObjectiveFunction;
import org.apache.commons.math3.optim.linear.NonNegativeConstraint;
import org.apache.commons.math3.optim.linear.SimplexSolver;
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;

import us.lsi.flowgraph.NoSeEncuentraSolucion;
import us.lsi.pl.ProblemaPL.TipoDeOptimizacion;
/**
 * @author Miguel Toro
 * 
 * <p> Un algoritmo de Programación Lineal. El algoritmo parte de una definición del problema recogida en un tipo 
 *  {@link us.lsi.pl.ProblemaPL ProblemaPL}  y calcula la solución. El algoritmo solo admite variables reales. </p>
 * 
 * 
 * <p> La implementación reutiliza el algoritmo SimplexSolver de Apache. </p>
 */
public class AlgoritmoPL implements SolutionPL {

	public static AlgoritmoPL create(IProblemaPL p){
		return new AlgoritmoPL(p.getObjectiveFunction(), p.getConstraints(), p.getTipo());
	}

	public static AlgoritmoPL create(IProblemaPL p, String fichero){
		p.toStringConstraints(fichero);
		return new AlgoritmoPL(p.getObjectiveFunction(), p.getConstraints(), p.getTipo());
	}
	
	private double[] solutionPoint;
	private double solutionValue;
	
	private TipoDeOptimizacion tipo;
	private LinearObjectiveFunction objectiveFunction;
	private Collection<LinearConstraint> collection;
	
	
	private AlgoritmoPL(LinearObjectiveFunction objectiveFunction, Collection<LinearConstraint> collection, 
			TipoDeOptimizacion tipo) {
		super();
		this.tipo = tipo;
		this.objectiveFunction = objectiveFunction;
		this.collection = collection;
	}
	
	private AlgoritmoPL(LinearObjectiveFunction objectiveFunction, Collection<LinearConstraint> collection) {
		super();
		this.tipo = TipoDeOptimizacion.Min;
		this.objectiveFunction = objectiveFunction;
		this.collection = collection;
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

	/**
	 * Calcula la solución
	 * @exception NoSeEncuentraSolucion Si no existe solución o el algoritmo no puede encontrarla
	 */
	public void ejecuta(){
		SimplexSolver ss = new SimplexSolver();	
		LinearConstraintSet lcs = new LinearConstraintSet(collection);
		PointValuePair p = null;
		try {
			p = ss.optimize(objectiveFunction, lcs,tipo.equals(TipoDeOptimizacion.Min)?GoalType.MINIMIZE:GoalType.MAXIMIZE,
					new NonNegativeConstraint(true));
		} catch (Exception e) {
			throw new NoSeEncuentraSolucion();
		}		
		solutionPoint = p.getPoint();
		solutionValue = p.getValue();
	}

	public TipoDeOptimizacion getTipo() {
		return tipo;
	}

	public LinearObjectiveFunction getObjectiveFunction() {
		return objectiveFunction;
	}


	public Collection<LinearConstraint> getCollection() {
		return collection;
	}
	
	
}

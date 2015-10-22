package us.lsi.pl;

import java.util.List;
import java.util.function.Function;

import com.google.common.collect.Lists;

import us.lsi.algoritmos.AbstractAlgoritmo;
import us.lsi.common.StringExtensions2;
import lpsolve.LpSolve;
import lpsolve.LpSolveException;

public class AlgoritmoPLI  extends AbstractAlgoritmo implements SolutionPL{

	public static AlgoritmoPLI create(IProblemaPL p, String fichero) {
		p.toStringConstraints(fichero);
		return new AlgoritmoPLI(fichero);
	}
	
	public static AlgoritmoPLI create(String fichero) {
		return new AlgoritmoPLI(fichero);
	}
	
	public static AlgoritmoPLI create() {
		return new AlgoritmoPLI("intermedio.txt");
	}
	
	private double[] solutionPoint;
	private double solutionValue;
	private List<String> names;
	
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

	/**
	 * @return La solución de la variable i
	 */
	public double getSolucion(int i) {
		return solutionPoint[i];
	}
		
	public List<String> getNames() {
		return names;
	}

	public String getName(int i) {
		return names.get(i);
	}
	
	public int getNumVar() {
		return solutionPoint.length;
	}
	
	
	public String getFichero() {
		return fichero;
	}

	public void setConstraints(String r){
		StringExtensions2.toFile(r,this.getFichero());
	}
	
	public void ejecuta(){
		
		
		try {
			
			solver = LpSolve.readLp(this.fichero, 1, "Problema");

			solver.solve();
			solutionPoint = solver.getPtrVariables();
			solutionValue = solver.getObjective();
			names = Lists.newArrayList();
			for (int j = 1; j <= solutionPoint.length; j++) {
				names.add(solver.getOrigcolName(j));
			}
			solver.deleteLp();
			
		} catch (LpSolveException e) {
			throw new IllegalStateException("Se ha producido una excepción en LpSolve");
		}

	}

	public static String getFactor(Integer num, String symbol){
		return num+"*"+symbol;
	}

	public static String getSumVariable(String name, int i1, int i2){
		String r = "";
		for(int i =i1;i<i2;i++){
			if (i!=i1) r = r+"+";
			r = r +AlgoritmoPLI.getVariable(name,i);
		}	
		return r;
	}
	
	public static String getSumFactor(Function<Integer,Double> f, String name, int i1, int i2){
		String r = "";
		for(int i =i1;i<i2;i++){
			if (i!=i1) r = r+"+";
			r = r +f.apply(i)+"+"+AlgoritmoPLI.getVariable(name,i);
		}	
		return r;
	}
	
	public static String getVariable(String name, int i, int j, int k){
		return name+i+"_"+j+"_"+k;
	}

	public static String getVariable(String name, int i, int j){
		return name+i+"_"+j;
	}

	public static String getVariable(String name, int index){
		return name+index;
	}
	
	public static String intVariables(String name, int i1, int i2) {
		String r = "";
		r = r +"int ";
		for(int i =i1;i<i2;i++){
			if (i!=0) r = r+",";
			r = r +AlgoritmoPLI.getVariable("x",i);
		}
		r = r+";\n\n";
		return r;
	}
	
}

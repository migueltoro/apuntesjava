package us.lsi.pl;

import java.util.List;

import com.google.common.collect.Lists;

import us.lsi.algoritmos.AbstractAlgoritmo;
import us.lsi.common.StringExtensions2;
import lpsolve.LpSolve;
import lpsolve.LpSolveException;

/**
 * @author Miguel Toro
 * 
 * <p> Un algoritmo de Programación Lineal Entera. El algoritmo lee un fichero en formato 
 * <a href="http://lpsolve.sourceforge.net/5.5/" target="_blank">LpSolve</a> y calcula la solución. 
 * Se pueden declarar tres tipos de variables: enteras (int), binarias (bin) y reales (por defecto).
 * Hay otros tipos de variables posibles (libres, semicontinuas) que pueden consultarse en la documentación de LpSolve. </p>
 * 
 * <p> La clase proporciona algunos métodos para facilitar la generación del fichero de entrada como 
 * getFactor, getVariable, getSumVariables,... </p>
 * 
 * <p> La implementación reutiliza el algortimo LpSolve. </p>
 */
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
	 * @param i El valor de la variable i
	 * @return La solución de la variable i
	 */
	public double getSolucion(int i) {
		return solutionPoint[i];
	}
		
	public List<String> getNames() {
		return names;
	}

	/**
	 * @param i Un índice de variable en el rango 0..getNumVar()-1
	 * @return Elvalor de la variable i
	 */
	public String getName(int i) {
		return names.get(i);
	}
	
	/**
	 * @return Número de variables
	 */
	public int getNumVar() {
		return solutionPoint.length;
	}
	
	
	/**
	 * @return El fichero de entrada
	 */
	public String getFichero() {
		return fichero;
	}
	
	/**
	 * Ejecuta el algoritmo
	 */
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

	/**
	 * @param num Un número
	 * @param symbol Una cadena
	 * @return Un factor construido de los elementos anteriores. Es decir de la forma: num*symbol
	 */
	public static String getFactor(Integer num, String symbol){
		return num+"*"+symbol;
	}

	/**
	 * @param name Un nombre de variable
	 * @param i1 Límite inferior del índice de la variable
	 * @param i2 Límite superior del índice de la variable
	 * @return El sumatorio de las variables cuyos índices están en el rango indicado
	 */
	public static String getSumVariable(String name, int i1, int i2){
		String r = "";
		for(int i =i1;i<i2;i++){
			if (i!=i1) r = r+"+";
			r = r +AlgoritmoPLI.getVariable(name,i);
		}	
		return r;
	}
	
	/**
	 * @param name Un nombre de una variable
	 * @param i Un primer índice
	 * @param j Un segundo índice
	 * @param k Un tercer índice
	 * @return La variable indexada de la forma namei_j_k
	 */
	public static String getVariable(String name, int i, int j, int k){
		return name+i+"_"+j+"_"+k;
	}
	/**
	 * @param name Un nombre de una variable
	 * @param i Un primer índice
	 * @param j Un segundo índice
	 * @return La variable indexada de la forma namei_j
	 */
	public static String getVariable(String name, int i, int j){
		return name+i+"_"+j;
	}
	/**
	 * @param name Un nombre de una variable
	 * @param i Un primer índice
	 * @return La variable indexada de la forma namei
	 */
	public static String getVariable(String name, int i){
		return name+i;
	}
	
	/**
	 * @param name Un nombre de variable 
	 * @param i1 Límite inferior del rango 
	 * @param i2 Limite superior del rango
	 * @return Declaracion de que las varaibles son enteras de la forma: int namei1,...,namei2;
	 */
	public static String intVariables(String name, int i1, int i2) {
		String r = "";
		r = r +"int ";
		for(int i =i1;i<i2;i++){
			if (i!=0) r = r+",";
			r = r +AlgoritmoPLI.getVariable(name,i);
		}
		r = r+";\n\n";
		return r;
	}

	public void setConstraints(String r) {
		StringExtensions2.toFile(r, this.fichero);
	}
	
	
}

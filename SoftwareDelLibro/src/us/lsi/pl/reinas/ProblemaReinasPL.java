package us.lsi.pl.reinas;

import org.apache.commons.math3.optim.linear.Relationship;

import us.lsi.algoritmos.Algoritmos;
import us.lsi.pl.AlgoritmoPLI;
import us.lsi.pl.ProblemaPL;
import us.lsi.pl.ProblemaPL.TipoDeOptimizacion;
import us.lsi.pl.ProblemaPL.TipoDeVariable;

public class ProblemaReinasPL {

	public static int numeroDeReinas = 8;
	
	public static int getPos(int i, int j){
		return i+j*numeroDeReinas;
	}
	
	public static ProblemaPL getProblema(){
		int n = numeroDeReinas;
		int nV = numeroDeReinas*numeroDeReinas;
		ProblemaPL problema = ProblemaPL.create(nV, TipoDeOptimizacion.Max);
		double[] fo = new double[nV];
		for (int i = 0; i < nV; i++) {
			fo[i] = 1.;
		}
		problema.setObjectiveFunction(fo, 0);
		
		for (int i = 0; i < n; i++) {
			double[] columnas = new double[nV];
			for (int j = 0; j < n; j++) {
				int p = getPos(i,j);
				columnas[p] = 1;
			}
			problema.addConstraint(columnas, Relationship.EQ, 1);
		}
		
		for (int j = 0; j < n; j++) {
			double[] filas = new double[nV];
			for (int i = 0; i < n; i++) {
				int p = getPos(i,j);
				filas[p] = 1;
			}
			problema.addConstraint(filas, Relationship.EQ, 1);
		}
		
		for (int k = -n+1; k < n; k++) {
			double[] dp = new double[nV];
			for (int i = -n+1; i < n; i++) {
				int j = i+k;
				if (i>=0 && i<n && j>=0 && j<n) {
					int p = getPos(i, j);
					dp[p] = 1;
				}
			}
			problema.addConstraint(dp, Relationship.LEQ, 1);
		}
		
		for (int k = 0; k <= 2*n-2; k++) {
			double[] ds = new double[nV];
			for (int i = 0; i <= 2*n-2; i++) {
				int j = -i+k;
				if (i>=0 && i<n && j>=0 && j<n) {
					int p = getPos(i, j);
					ds[p] = 1;
				}
			}
			problema.addConstraint(ds, Relationship.LEQ, 1);
		}
		
		
		problema.setTipoDeTodasLasVariables(TipoDeVariable.Binaria);
		return problema;
	}
	
	
	public static void main(String[] args) {
		numeroDeReinas = 100;
		ProblemaPL p = ProblemaReinasPL.getProblema();
		p.toStringConstraints("ficheros\\reinas.txt");
		AlgoritmoPLI a = Algoritmos.createPLI("ficheros\\reinas.txt");
		a.ejecuta();		
		System.out.println("________");
		System.out.println(Math.round(a.getObjetivo()));
		System.out.println("________");
		System.out.println("________");
		String s ="";
		for (int pos = 0; pos < p.getNumOfVariables(); pos++) {
			int i = pos%numeroDeReinas;
			double x = a.getSolucion()[pos];
			if (Math.round(x)==1) {
				s= s+ " X ";
			}else{
				s= s+ "   ";
			}
			if(i==numeroDeReinas-1){
				s = s+"\n";
			}
		}
		System.out.println(s);
		System.out.println("________");
	}
}

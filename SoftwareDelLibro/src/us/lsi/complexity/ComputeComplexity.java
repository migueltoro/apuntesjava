package us.lsi.complexity;

import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;

import us.lsi.algoritmos.AbstractAlgoritmo;
import us.lsi.common.Files2;
import us.lsi.dyv.ejemplos.EjemplosRecursivoIterativos;
import us.lsi.math.Math2;

public class ComputeComplexity {
	
	public static List<Double[]> getData(){
		List<Double[]> r = new ArrayList<>();
		for(double x=10; x<10000; x=x+100){
			Double[] a = new Double[2];
			a[0] = 2.*Math.pow(x,2.5)*Math.pow(Math.log(x),1.2);
			a[1] = x;
			r.add(a);
		}
		return r;
	}
	private static BigInteger quickSort(Integer n){
		Map<Integer,BigInteger> m = new HashMap<>();
		return quickSort(n,m);
	}
	private static BigInteger quickSort(Integer n, Map<Integer,BigInteger> m){
		BigInteger s = BigInteger.ZERO;
		Integer n2 = n/2;
		if (n<2) {
			s = BigInteger.ONE;
		} else if(m.containsKey(n)){
			s = m.get(n);
		} else {
			for (int i = 0; i < n2; i++) {
				s = s.add(quickSort(i,m));			
			}
			s = s.multiply(new BigInteger("2")).divide(new BigInteger(n.toString())).add(new BigInteger(n.toString()));
			m.put(n, s);
		}
		return s;
	}
	
	public static List<Double[]> getDataQuickSort(){
		List<Double[]> r = new ArrayList<>();
		for(int n=500; n<10000; n=n+200){
			Double[] a = new Double[2];
			a[0] = quickSort(n).doubleValue();
			a[1] = (double) n;
			r.add(a);
		}
		return r;
	}
	
	public static List<Double[]> getDataPot(){
		List<Double[]> r = new ArrayList<>();
		int b = 5;
		for(int n=500; n<10000; n=n+100){
			Double[] a = new Double[2];
			long ti = System.nanoTime();
			EjemplosRecursivoIterativos.potI2(b,n);	
			long tf = System.nanoTime();
			a[0] = (double)(tf-ti);
			a[1] = (double) n;
			r.add(a);
		}
		return r;
	}
	
	public static List<Double[]> getDataFib(){
		List<Double[]> r = new ArrayList<>();
		BigInteger b = new BigInteger("25");
		for(int n=1000; n<50000; n=n+100){
			Double[] a = new Double[2];
			long ti = System.nanoTime();
			b.pow(n);
			long tf = System.nanoTime();
			a[0] = (double)(tf-ti)/1000.;
			a[1] = (double) n;
			r.add(a);
		}
		return r;
	}
	
	public static List<Double[]> getDataFib2(){
		List<Double[]> r = new ArrayList<>();
		for(int n=1000; n<50000; n=n+100){
			Double[] a = new Double[2];
			long ti = System.nanoTime();
			Math2.fibonacci5(n);
			long tf = System.nanoTime();
			a[0] = (double)(tf-ti);
			a[1] = (double) n;
			r.add(a);
		}
		return r;
	}
	
	private static void generateFile(
			String fileExpLog, 
			String fileExp, 
			String fileLog, 
			String fileTime, 
			List<Double[]> r){
		PrintWriter p1 = Files2.getWriter(fileExpLog);
		PrintWriter p2 = Files2.getWriter(fileExp);
		PrintWriter p3 = Files2.getWriter(fileLog);
		PrintWriter p4 = Files2.getWriter(fileTime);
		for(Double[] d: r){
			p1.printf("%f,%f,%f\n", Math.log(d[0]),Math.log(d[1]),Math.log(Math.log(d[1])));
			p2.printf("%f,%f\n", Math.log(d[0]),Math.log(d[1]));
			p3.printf("%f,%f\n", Math.log(d[0]),Math.log(Math.log(d[1])));
			p4.printf("%.1f,%.1f\n", d[0],d[1]);
		}
		p1.close();
		p2.close();
		p3.close();
		p4.close();
	}
	
	private static Double[] toDouble(String[] s){
		Double[] r = new Double[s.length];
		for(int i=0;i<s.length;i++){
			r[i] = new Double(s[i]);
		}
		return r;
	}
	
	private static List<Double[]> readFile(String file){
		return Files2.getLines(file)
				.stream()
				.map(x->x.split(","))
				.map(x->toDouble(x))
				.collect(Collectors.toList());
	}
	
	private static void calculateParameters(String file){
		List<Double[]> ld = readFile(file);
		int no = ld.size();
		int nv = ld.get(0).length-1;
		double[] y = new double[no];
		double[][] x = new double[no][nv];
		for(int i =0; i<no;i++){
			Double[] dt = ld.get(i);
			y[i] = dt[0];
			for (int j = 1; j < dt.length; j++) {
				x[i][j - 1] = dt[j];
			}
		}	
		OLSMultipleLinearRegression regression = new OLSMultipleLinearRegression();
		regression.newSampleData(y, x);
		double[] beta = regression.estimateRegressionParameters();
		beta[0] = Math.exp(beta[0]);
		System.out.println("Error ="+regression.estimateRegressionStandardError());
		System.out.println("R2 ="+regression.calculateAdjustedRSquared());
		double[] err = regression.estimateRegressionParametersStandardErrors();
		if (beta.length == 3) {			
			System.out.println(String.format("Errors of a = %.2f, b = %.2f, c = %.2f", err[0],
					err[1], err[2]));
			System.out.println(String.format("Values a = %.2f,b = %.2f, c = %.2f", beta[0],
					beta[1], beta[2]));
		} else {
			System.out.println(String.format("Errors of a = %.2f, b = %.2f", err[0],
					err[1]));
			System.out.println(String.format("Values a = %.2f, b = %.2f", beta[0],
					beta[1]));
		}	
	}
	
	public static void main(String[] args) {
		List<Double[]> r = getDataFib2();
		generateFile(
				AbstractAlgoritmo.raiz+"__xxx1.csv",
				AbstractAlgoritmo.raiz+"__xxx2.csv",
				AbstractAlgoritmo.raiz+"__xxx3.csv",
				AbstractAlgoritmo.raiz+"__tiempos.csv",r);
		calculateParameters(AbstractAlgoritmo.raiz+"__xxx1.csv");
		calculateParameters(AbstractAlgoritmo.raiz+"__xxx2.csv");
		calculateParameters(AbstractAlgoritmo.raiz+"__xxx3.csv");
				
//		double[] y = new double[]{11.0, 12.0, 13.0, 14.0, 15.0, 16.0};
//		double[][] x = new double[6][];
//		x[0] = new double[]{0, 0, 0, 0, 0};
//		x[1] = new double[]{2.0, 0, 0, 0, 0};
//		x[2] = new double[]{0, 3.0, 0, 0, 0};
//		x[3] = new double[]{0, 0, 4.0, 0, 0};
//		x[4] = new double[]{0, 0, 0, 5.0, 0};
//		x[5] = new double[]{0, 0, 0, 0, 6.0};          
////		y - the [n,1] array representing the y sample
////		x - the [n,k] array representing the x sample
//		regression.newSampleData(y, x);
////		The [k,1] array representing b
//		double[] beta = regression.estimateRegressionParameters();       
//
//		double[] residuals = regression.estimateResiduals();
//
//		double[][] parametersVariance = regression.estimateRegressionParametersVariance();
//
//		double regressandVariance = regression.estimateRegressandVariance();
//
//		double rSquared = regression.calculateRSquared();
//
//		double sigma = regression.estimateRegressionStandardError();
//		System.out.println(String.format("%.2f,%.2f",
//				beta[0],beta[1]));
	}

}

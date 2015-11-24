package us.lsi.math;

import java.math.*;
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import us.lsi.common.ParInteger;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;



public class Math2 {

	
	/**
	 * @param d Un Double
	 * @return Representación simplificada de un Double 
	 */
	public static String simplify(Double d){
		String s = "";
		if(d==1.){
			s+="+";
		}else if(d==-1.){
			s+="-";
		}else if(d>0.){
			s+="+"+d.toString();
		}else if(d!=0.){
			s+=d.toString();
		}
		return s;
	}
	
	/**
	 * @pre n &gt; = 0
	 * @param n Un entero
	 * @return El número n de Fibonacci calculado reduciendo el problema a una potenciación de matrices
	 * y en una versión recursiva
	 */
	public static BigInteger fibonacci4(Integer n){
		MatrizFibonacci m = powMatrizFibonacci(n);
		return m.getA();
	}
	
	private static MatrizFibonacci powMatrizFibonacci(Integer n){
		MatrizFibonacci r;		
		if(n==0){
			r = MatrizFibonacci.getOne();
		}else if(n==1){
			r = MatrizFibonacci.getBase();
		}else {
			r = powMatrizFibonacci(n/2);
			r = r.getSquare();
			if(n%2!=0){
				r = r.getMultiplyBase();
			}
		}
		return r;
	}
	
	/**
	 * @pre n &gt; = 0
	 * @param n Un Entero
	 * @return El número n de Fibonacci calculado reduciendo el problema a una potenciación de matrices
	 * y en una versión iterativa
	 */
	public static BigInteger fibonacci5(Integer n){
		BigInteger ar,br;
		BigInteger au,bu;
		BigInteger at,bt;
		BigInteger dos = new BigInteger("2");
		ar = BigInteger.ONE;
		br = BigInteger.ZERO;
		au = BigInteger.ZERO;
		bu = BigInteger.ONE;
		while(n>0){
			if(n%2==1){
				at = bu.multiply(ar).add(au.multiply(ar)).add(au.multiply(br));
				bt = au.multiply(ar).add(bu.multiply(br));
				au = at;
				bu = bt;
			}
			at = ar.multiply(ar).add(dos.multiply(ar).multiply(br));
			bt = ar.multiply(ar).add(br.multiply(br));
			ar = at;
			br = bt;
			n =n/2;
		}
		return au;
	}
	
	/**
	 * @pre n &gt; = 0
	 * @param n Un Entero
	 * @return El número n de Fibonacci calculado reduciendo el problema a una potenciación de matrices
	 * y en una versión iterativa y usando Long en vez de BigInteger
	 */
	public static Long fibonacci3(Integer n){
		Long ar,br;
		Long au,bu;
		Long at,bt;
		ar = 1L;
		br = 0L;
		au = 0L;
		bu = 1L;
		while(n>0){
			if(n%2==1){
				at = bu*ar+au*ar+au*br;
				bt = au*ar+bu*br;
				au = at;
				bu = bt;
			}
			at = ar*ar+2*ar*br;
			bt = ar*ar+br*br;
			ar = at;
			br = bt;
			n =n/2;
		}
		return au;
	}
	
	
	/**
	 * @pre base &gt; 0
	 * @param base Base de la potencia
	 * @param n Exponente de la potencia
	 * @return base &#94; n en una versión iterativa de complejidad log(n)
	 */
	public static Double powi(Double base, Integer n){
		Double r, u;
		r = base;
		u = 1.;
		while( n > 0){
	       if(n%2==1){
			     u = u * r;
			}
			r = r * r;
			n = n/2;
		}
		return u;

	}
	
	/**
	 * @pre base &gt; 0
	 * @param base Base de la potencia
	 * @param n Exponente de la potencia
	 * @return base &#94; n en una versión iterativa de complejidad log(n)
	 */
	public static Double powr(Double base, Integer n){
		Double r;		
		if(n==0){
			r = 1.;
		}else if(n==1){
			r = base;
		}else {
			r = powr(base,n/2);
			r = r*r;
			if(n%2!=0){
				r = r*base;
			}
		}
		return r;
	}
	
	/**
	 * @param base Base de la potencia
	 * @param n Exponente de la potencia
	 * @return base &#94; n en una versión iterativa de complejidad log(n)
	 */
	public static Double pow(Double base, Integer n){
		return powr(base,n);
	}
	
	
	/**
	 * @param a Un entero
	 * @param b Un segundo entero
	 * @return Calcula el máximo común divisor de los valores ablsolutos de los parámetros
	 */
	public static int mcd(int a, int b){		
	       int u = Math.abs(a);
	       int v = Math.abs(b);
	       int r;
	       while (v != 0) {
	            r = u % v;
	            u = v;
	            v = r;
	       }
		    return u;
	}
	
    public static Random rnd   = new Random(System.nanoTime());
	    
	
    /**
     * @return Un objeto de tipo Random
     * 
     * @see java.util.Random
     */
    public static Random getRandom() {
		return rnd;
	}

	/**
	 * @pre b &gt; a
	 * @param a Límite inferior
	 * @param b Límte Superior
	 * @return Un entero aleatorio r tal que a &lt; = r &lt; b
	 */
	public static Integer getEnteroAleatorio(Integer a, Integer b){   	
	    	Integer valor;
			Preconditions.checkArgument(b>a,a+","+b);
	    	if(b-a == 1){
	    		valor = a;
	    	}else{
	    		valor = a+rnd.nextInt(b-a);
	    	}
	    	return valor;
	}

	/**
	 * @return Un entero aleatorio
	 * @see java.util.Random#nextLong()
	 */
	public static Long getLongAleatorio(){   	
    	return rnd.nextLong();
	}
	
	/**
	 * @pre b &gt; a
	 * @param a Límite inferior
	 * @param b Límte Superior
	 * @return Un par aleatorio cuyos elementos son distintos y están en el intervalo  a &lt; = r &lt; b
	 */
	public static ParInteger getParAleatorioYDistinto(Integer a, Integer b){   	
    	Preconditions.checkArgument(b-a>=2,a+","+b);
    	Integer c1 = getEnteroAleatorio(a,b-1);
    	Integer c2 = getEnteroAleatorio(c1+1,b);
		return ParInteger.create(c1, c2);
	}
	
	/**
	 * @pre b &gt; a
	 * @param a Límite inferior
	 * @param b Límte Superior
	 * @return Un double aleatorio que  está en el intervalo  a &lt; = r &lt; b
	 */
	public static Double getDoubleAleatorio(Double a, Double b){   	
	    	Preconditions.checkArgument(b>a,a+","+b);
	    	Double r = a+(b-a)*rnd.nextDouble();
	    	return r;
	}
	
	
	/**
	 * @param increment El incremento &delta;
	 * @param t Temperatura
	 * @return Si &delta; &lt; 0 devuelve 1, si t = 0 devuelve 0, en otro caso devuelve e &#94; (- &delta;/t) 
	 */
	public static double boltzmann(double increment, double t){
		double r;
		if(increment < 0.){
			r = 1.;
		} else if(t==0.){
			r = 0.;
		} else {
			r = Math.exp(-increment/t);
		}
		return r;
	}
	
	/**
	 * @param increment El incremento &delta;
	 * @param t Temperatura 
	 * @return Verdadero si e &#94; (- &delta;/t) &gt; r. Donde r es un real aleatorio 0 &lt; = r &lt; = 1
	 */
	public static boolean aceptaBoltzmann(double increment, double t) {
			double rd = Math2.getDoubleAleatorio(0., 1.);
			double rd2 =  Math2.boltzmann(increment,t);
			return rd2 > rd;
	}
	
	
	/**
	 * @param probabilities Es una distribución de probabilidades para una variables aleatoria 
	 * con valores 0 hasta probabilidades.size() no incluido.
	 * @return Un entero entre 0 y probabilidades.size(), no incluido, con las probababilidades proporcionadas
	 */
	public static Integer escogeEntre(List<Double> probabilities){
		Preconditions.checkArgument(!probabilities.isEmpty());
		Double ppa = 0.;
		Integer r = 0;
		double na = Math2.getDoubleAleatorio(0., 1.);
		for(Double p:probabilities){
			ppa = ppa+p;
			if(ppa >= na){
				break;
			}
			r++;
		}
		return r;
	}
	
	/**
	 * @param first Primera probabilidad
	 * @param rest Resto de la probabilidades
	 * @return Si se foerma una lista ls con todos los parámetros el método devuelve un entero 
	 * entre 0 y ls.size(), no incluido, con las probababilidades proporcionadas en la lista
	 */
	public static Integer escogeEntre(Double first, Double...rest){
		return escogeEntre(Lists.asList(first, rest));
	}
	 
	/**
	 * @param a Un entero
	 * @return Si es par
	 */
	public static boolean esPar(Integer a){
		return a%2 == 0;
	}
		
	/**
	 * @param a Un entero 
	 * @return Si es impar
	 */
	public static boolean esImpar(Integer a){
		return !esPar(a);
	}
	
	/**
	 * @param a Un entero
	 * @param b Un segundo entero
	 * @return Si a es divisible por b
	 */
	public static boolean esDivisible(Integer a, Integer b){
		return (a%b) == 0;
	}
	
	public static boolean esDivisible(Long a, Long b){
		return (a%b) == 0;
	}
	/**
	 * @param a Un entero
	 * @return Si a es primo
	 */
	public static boolean esPrimo(Integer a){
		Integer sqrt = (int)Math.sqrt((double)a);
		return IntStream.rangeClosed(2, sqrt).noneMatch(x->Math2.esDivisible(a, x));
	}
	
	public static boolean esPrimo(Long a){
		Long sqrt = (long)Math.sqrt((double)a);
		return LongStream.rangeClosed(2, sqrt).noneMatch(x->Math2.esDivisible(a, x));
	}
	
	public static boolean esPrimo(BigInteger a){
		return a.isProbablePrime(100);
	}
		
	/**
	 * @param a Un entero
	 * @return Siguiente primo
	 */
	
	public static Integer siguientePrimo(Integer a){
		a = (a+1)%2==0?a+2:a+1;
		return Stream.iterate(a, x->x+2).filter(x->Math2.esPrimo(x)).findFirst().get();
	}
	
	public static Long siguientePrimo(Long a){
		a = (a+1)%2==0?a+2:a+1;
		return Stream.iterate(a, x->x+2).filter(x->Math2.esPrimo(x)).findFirst().get();
	}
	
	public static BigInteger siguientePrimo(BigInteger a){
		return a.nextProbablePrime();
	}
	
	/**
	 * @param a Un entero
	 * @return El signo de a: +1,0,-1
	 */
	public static int sgn(Integer a) {
		int r = 0;
		if (a != 0)
			r = a >= 0 ? 1 : -1;
		return r;
	}

	
	/**
	 * @param a Un double
	 * @return El signo de a: +1,0,-1
	 */
	public static int sgn(Double a) {
		int r = 0;
		if (r != 0)
			r = a >= 0 ? 1 : -1;
		return r;
	}
	
	/**
	 * @param n Un entero
	 * @param a Un double 
	 * @param b Un segundo double
	 * @return Un lista de tamaño n con números reales en el intervalo a &lt; = r &lt; b 
	 */
	public static List<Double> getListDoubleAleatoria(int n, double a, double b){
		List<Double> lista = Lists.newArrayList();
		for(int i =0; i<n;i++){
			lista.add(getDoubleAleatorio(a,b));
		}
		return lista;
	}
	
	/**
	 * @param e Un entero positivo
	 * @return El número de btis necesario para poder codificarlo en binario
	 */
	public static Integer numeroDeBits(Integer e){
		      int bits_necesarios = 0;
		      while(e > 0) {
		            bits_necesarios++;
		            e = e/2; // Desplazo bits (división por 2)
		      }
		      return bits_necesarios;
	}
	
	/**
	 * @pre Todos los parámetros son positivos. El valor de e debe ser menor que maxEscala
	 * @param e Un entero
	 * @param maxEscala Un entero 
	 * @param num Un entero
	 * @return Devuelve un valor en el rango 0..num-1 con la expresión e*num/maxEscala
	 */
	public static Integer escala(Integer e, Integer maxEscala, Integer num){
	     int a = e*num/maxEscala;
	     return a;
	}
	
	
	/**
	 * @pre Todos los parámetros son positivos. El valor de e debe ser menor que maxEscala
	 * @param e Un entero
	 * @param maxEscala Un entero 
	 * @param num Un entero
	 * @return Devuelve un valor en el rango 0..num con la expresión e*(num+1)/maxEscala
	 */
	public static Integer escalaIncluded(Integer e, Integer maxEscala, Integer num){
	     int a = e*(num+1)/maxEscala;
	     return a;
	}
}

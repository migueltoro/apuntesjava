package us.lsi.dyv.ejemplos;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.apache.commons.math3.Field;
import org.apache.commons.math3.FieldElement;
import org.apache.commons.math3.fraction.BigFraction;
import org.apache.commons.math3.fraction.BigFractionField;
import org.apache.commons.math3.linear.FieldMatrix;

import us.lsi.common.Lists2;
import us.lsi.common.Tuple2;
import us.lsi.common.Tuple3;
import us.lsi.iterativo.Matrices;


public class EjemplosRecursivoIterativos {
	
	
	/**
	 * @param ls Una lista
	 * @param e Un elemento a buscar
	 * @param <T> El tipo de los elementos
	 * @return Posicion del elemento e en la lista ls o -1 si no está
	 */
	public static <T> int buscaPosicionJ7(List<T> ls, T e){
		return ls.indexOf(e);
	}
	
	/**
	 * @param ls Una lista
	 * @param e Un elemento a buscar
	 * @param i Un indice en la lista
	 * @param <T> El tipo de los elementos
	 * @return Posicion del elemento e en la sublista ls[i,ls.size()] o -1 si no está
	 */
	public static <T> int buscaPosicionR(List<T> ls, T e, int i){
		int r;
		if(ls.size()-i == 0 ){
			r = -1;
		} else if (ls.get(i).equals((e))){
			r = i;			
		} else {
			r = buscaPosicionR(ls,e,i+1);
		}
		return r;
	}
	/**
	 * @param ls Una lista
	 * @param e Un elemento a buscar
	 * @param <T> El tipo de los elementos
	 * @return Posicion del elemento e en la lista ls o -1 si no está
	 */
	public static <T> int buscaPosicionI(List<T> ls, T e){
		int a = -1;
		int i = 0;
		while(ls.size()-i > 0 ){
			if (ls.get(i).equals((e))){
				a = i;
				if(a >= 0) break;
			}			
			i++;
		}
		return a;
	}
	/**
	 * @param ls Una lista
	 * @param e Un elemento a buscar
	 * @param <T> El tipo de los elementos
	 * @return Posicion del elemento e en la lista ls o -1 si no está
	 */
	public static <T> int buscaPosicionIJ(List<T> ls, T e){
		Tuple2<Integer,T> def = Tuple2.create(-1, null);
		return IntStream.range(0,ls.size())
				.mapToObj(x->Tuple2.create(x, ls.get(x)))
				.filter(x->x.v2.equals(e))
				.findFirst()
				.orElse(def)
				.v1;
	}
	/**
	 * @param ls Una lista
	 * @param e Un elemento a buscar
	 * @param i Un indice en la lista
	 * @param j Un índice en la lista
	 * @param <T> El tipo de los elementos
	 * @return Posicion del elemento e en la sublista ls[i,j] o -1 si no está
	 */
	public static <T> int buscaPosicionR(List<T> ls, T e, int i, int j){
		int r;
		if(j-i == 0){
			r = -1;
		} else if(j-i == 1  && ls.get(i).equals((e))){
			r = i;			
		} else if (j-i == 1  && !ls.get(i).equals((e))){
			r = -1;			
		} else {
			int k =(i+j)/2;
			int r1 = buscaPosicionR(ls,e,i,k);
			int r2 = buscaPosicionR(ls,e,k,j);
			if(r1 <0 && r2<0){
				r = -1;
			} else if(r1>=0){
				r = r1;
			} else {  // (r2>=0){
				r = r2;
			}
		}
		return r;
	}
	/**
	 * @pre i &le; j
	 * @pre La lista ls está ordenada con el orden cmp
	 * @param ls Una lista
	 * @param e Un elemento a buscar
	 * @param cmp Un Comparator
	 * @param i Un indice en la lista
	 * @param j un indice en la lista
	 * @param <T> El tipo de los elementos
	 * @return Posicion del elemento e en la sublista ls[i,ls.size()] o -1 si no está
	 */	
	public static <T> int buscaPosicionR(List<T> ls, T e, Comparator<T> cmp, int i, int j){
		int r;
		if(j-i == 0){
			r = -1;		
		} else {
			int k =(i+j)/2;
			int r1 = cmp.compare(e,ls.get(k));
			if(r1==0){
				r = k;
			}else if(r1<0){
				r = buscaPosicionR(ls,e,cmp,i,k);
			} else {
				r = buscaPosicionR(ls,e,cmp,k+1,j);
			}		
		}
		return r;
	}
	
	/**
	 * @pre La lista ls está ordenada con el orden cmp
	 * @param ls Una lista
	 * @param e Un elemento a buscar
	 * @param cmp Un Comparator
	 * @param <T> El tipo de los elementos
	 * @return Posicion del elemento e en la lista ls o -1 si no está
	 */	
	public static <T> int buscaPosicionI(List<T> ls, T e, Comparator<T> cmp){
		int r = -1;
		int i = 0;
		int j = ls.size();
		while(j-i>0){
			int k =(i+j)/2;
			int r1 = cmp.compare(e,ls.get(k));
			if(r1==0){
				r = k;
				if(r>=0) break;
			}else if(r1 < 0){
				j = k;
			} else {
				i = k+1;
			}	
		}
		return r;
	}
	
	private static <T> Tuple2<Integer,Integer> nextBuscaPosicionIJ(List<T> ls, T e,Comparator<T> cmp, Tuple2<Integer,Integer> t){
		int i = t.v1;
		int j = t.v2;
		int k =(i+j)/2;
		Tuple2<Integer,Integer> r;
		int r1 = cmp.compare(e,ls.get(k));
		if(r1==0){
			r = Tuple2.create(k, k+1);
		}else if(r1 < 0){
			r = Tuple2.create(i, k);
		} else {
			r = Tuple2.create(k+1, j);
		}
		return r;
	}
	
	/**
	 * @pre La lista ls está ordenada con el orden cmp
	 * @param ls Una lista
	 * @param e Un elemento a buscar
	 * @param cmp Un Comparator
	 * @param <T> El tipo de los elementos
	 * @return Posicion del elemento e en la lista ls o -1 si no está
	 */		
	public static <T> int buscaPosicionIJ(final List<T> ls, final T e, final Comparator<T> cmp){
		Tuple2<Integer,Integer> inicial = Tuple2.create(0, ls.size());
		Optional<Tuple2<Integer,Integer>> op = Stream.iterate(inicial,x->nextBuscaPosicionIJ(ls,e,cmp,x))
					 .filter(x->x.v2-x.v2==0 || ls.get(x.v1).equals(e))
					 .findFirst();
		int r = op.get().v1;
		if(!ls.get(r).equals(e)) r = -1;
	    return r;
	}
	
	/**
	 * @param ls Una lista
	 * @param cmp Un Comparator
	 * @param <T> El tipo de los elementos
	 * @return Si la lista está ordenada con cmp
	 */
	public static <T> Boolean estaOrdenadaI(List<T> ls, Comparator<T> cmp){
		Boolean a = true;
		int i = 0;
		int n = ls.size();
		while(n-i>1){
			a = cmp.compare(ls.get(i),ls.get(i+1))<=0;
			if(!a) break;
			i++;			
		}
		return a;
	}
	
	/**
	 * @param ls Una lista
	 * @param cmp Un Comparator
	 * @param <T> El tipo de los elementos
	 * @return Si la lista está ordenada con cmp
	 */
	public static <T> Boolean estaOrdenadaIJ(List<T> ls, Comparator<T> cmp){
		return IntStream.range(0, ls.size()-1)
				        .allMatch(i->cmp.compare(ls.get(i),ls.get(i+1))<=0);
	}
	
	/**
	 * @param ls Una lista
	 * @param cmp Un Comparator
	 * @param i Un índice de la lista
	 * @param <T> El tipo de los elementos
	 * @return Si la sublista ls[i,ls.size()] está ordenada con respecto a cmp
	 */
	public static <T> Boolean estaOrdenadaR(List<T> ls, Comparator<T> cmp, int i){
		Boolean r;
		if(ls.size()-i <= 1){
			r = true;
		} else if(!(cmp.compare(ls.get(i),ls.get(i+1))<=0)){
			r = false;
		} else {
			r = estaOrdenadaR(ls,cmp,i+1);
		}
		return r;
	}
	
	/**
	 * @param ls Una lista
	 * @return Si la lista es una progresión aritmética
	 */
	public static Boolean esProgresionAritmeticaI(List<Double> ls){
		Boolean a = true;		
		int n = ls.size();
		if(n<=2) return  false;
		int i = 0;
		Double r = ls.get(1)-ls.get(0);
		while(n-i>1){
			a = (ls.get(i)+r) == (ls.get(i+1));
			if(!a) break;
			i++;			
		}
		return a;
	}
	
	/**
	 * @param ls Una lista
	 * @return Si la lista es una progresión aritmética
	 */
	public static Boolean esProgresionAritmeticaIJ(List<Double> ls){
		int n = ls.size();
		if(n<=2) return  false;
		Double r = ls.get(1)-ls.get(0);
		return IntStream.range(0, n-1)
				        .allMatch(i->(ls.get(i)+r) == (ls.get(i+1)));
	}
	
	/**
	 * @param ls Una lista
	 * @return Si la lista es una progresión aritmética
	 */
	public static Boolean esProgresionAritmeticaR(List<Double> ls){
		int n = ls.size();
		if(n<=2) return  false;
		Double r = ls.get(1)-ls.get(0);
		return esProgresionAritmeticaR2(ls, r, 0);
	}
	
	/**
	 * @param ls Una lista
	 * @param i Un índice de la lista
	 * @param r La razón
	 * @return Si la sublista ls[i,ls.size()] es una progresión aritmética de razón r
	 */
	public static Boolean esProgresionAritmeticaR(List<Double> ls, Double r, int i){
		Boolean s;
		int n = ls.size();
		if(n-i <= 1){
			s = true;
		} else if(!(ls.get(i)+r == ls.get(i+1))){
			s = false;
		} else {
			s = esProgresionAritmeticaR(ls,r,i+1);
		}
		return s;
	}
	
	/**
	 * @param ls Una lista
	 * @param i Un índice de la lista
	 * @param r La razón
	 * @return Si la sublista ls[i,ls.size()] es una progresión aritmética de razón r
	 */
	public static Boolean esProgresionAritmeticaR2(List<Double> ls, Double r, int i){
		Boolean s;
		int n = ls.size();
		if(n-i <= 1){
			s = true;
		} else {
			s = (ls.get(i)+r == ls.get(i+1)) && esProgresionAritmeticaR2(ls,r,i+1);
		}
		return s;
	}

	/**
	 * @param ls Una lista
	 * @param i Un índice en la lista
	 * @return Si son impares los elementos de i hasta el final de la lista
	 */
	public static Boolean sonImparesR(List<Integer> ls, int i) {
		Boolean u;
		if(ls.size()-i==0){
			return true;
		} else if(ls.get(i)%2==0){
			return false;
		} else {
			u = sonImparesR(ls,i+1);
		}
		return u;
	}
	
	/**
	 * @param ls Una lista
	 * @return Si son impares los elementos de la lista
	 */
	public static Boolean sonImparesI(List<Integer> ls) {
		Boolean r = true;
		int i = 0;
		int n = ls.size();
		while(n-i>0){
			if(ls.get(i)%2==0){
				r = false;
				if(!r) break;
			}
			i++;
		}
		return r;
	}
	/**
	 * @param ls Una lista
	 * @return Si son impares los elementos de la lista
	 */
	public static Boolean sonImparesIJ(List<Integer> ls) {
		return ls.stream().allMatch(x->x%2==1);
	}
	
	/**
	 * @param c Un caracter 0..9
	 * @return El correspondiente entero
	 */
	public static int toInt(char c){
		return c-'0';
	}
	
	/**
	 * El cálculo se hace comenzando por los digitos menos significativos
	 * @param d Lista de digitos
	 * @param r Base
	 * @return El numero de base r representado por d
	 */
	public static Long toNumLM(String d, int r){
		Long a = 0L;
		Long b = 1L;
		int i = d.length()-1;
		while(i >= 0){
			a = a+toInt(d.charAt(i))*b;
			b = b*r;
			i--;
		}
		return a;
	}
	
	/**
	 * El cálculo se hace comenzando por los digitos más significativos
	 * @param d Lista de digitos
	 * @param r Base
	 * @return El numero de base r representado por d
	 */
	public static Long toNumML(String d, int r){
		Long a = 0L;
		int i = 0;
		while(i < d.length()){
			a = toInt(d.charAt(i))+a*r;
			i++;
		}
		return a;
	}
	
	
	/**
	 * @param b Base 
	 * @param n Entero
	 * @return Representacion de n en base b calculado de forma recursiva
	 */
	public static String toBaseR(Integer n, int b){
		String r;
		if(n<b){
			r = n.toString();
		} else {
			r = toBaseR(n/b,b)+""+n%b;
		}
		return r;
	}
	
	/**
	 * @param b Base 
	 * @param n Entero
	 * @return Representacion de n en base b calculado de forma iterativa
	 */
	public static String toBaseI(int n, int b){
		String a = "";
		while(n>b){			
			a = n%b+""+a;
			n = n/b;
		}
		a = n+""+a;
		return a;
	}
	
	public static Tuple2<Integer,String> nextToBaseIJ(Integer b, Tuple2<Integer,String> t){
		return Tuple2.create(t.v1/b, t.v1%b+t.v2);
	}
	
	/**
	 * @param b Base 
	 * @param n Entero
	 * @return Representacion de n en base b calculado de forma iterativa
	 */
	public static String toBaseIJ(int n, int b){
		Tuple2<Integer,String> inicial = Tuple2.create(n,"");
		Tuple2<Integer,String> r =Stream.iterate(inicial,x->nextToBaseIJ(b,x))
				     .filter(t->t.v1<b)
				     .findFirst()
				     .get();
	    return r.v1+r.v2;
	}
	
	/**
	 * @param b Base 
	 * @param n Entero
	 * @return Suma de los digitos de n en base b calculado de forma recursiva
	 */
	public static Integer sumaDigitosR(int n, int b){
		Integer r;
		if(n<b){
			r = n;
		} else {
			r = sumaDigitosR(n/b,b)+n%b;
		}
		return r;
	}
	
	/**
	 * @param b Base 
	 * @param n Entero
	 * @return Suma de los digitos de n en base b de forma iterativa
	 */
	public static Integer sumaDigitosI(int n, int b){
		Integer a = 0;
		while(n>b){			
			a = a+n%b;
			n = n/b;
		}
		return a+n;
	}
	
	public static Tuple2<Integer,Integer> nextSumaDigitosIJ(Integer b, Tuple2<Integer,Integer> t){
		return Tuple2.create(t.v1/b,t.v2+t.v1%b);
	}
	
	/**
	 * @param b Base 
	 * @param n Entero
	 * @return Suma de los digitos de n en base b de forma iterativa
	 */
	public static Integer sumaDigitosIJ(int n, int b){
		Tuple2<Integer,Integer> inicial = Tuple2.create(n,0);
		Tuple2<Integer,Integer> r =Stream.iterate(inicial,x->nextSumaDigitosIJ(b,x))
				     .filter(t->t.v1<b)
				     .findFirst()
				     .get();
	    return r.v2+r.v1;
	}
	/**
	 * @param b Base 
	 * @param n Exponente
	 * @return Resultado de elevar b a n calculado de forma recursiva
	 */
	public static Long potR(int b, int n){
		Long r;
		if(n==0){
			r = 1L;
		} else {
			r = potR(b,n-1)*b;
		}
		return r;
	}
	
	/**
	 * @param b Base 
	 * @param n Exponente
	 * @return Resultado de elevar b a n calculado de forma iterativa
	 */
	public static Long potI(int b, int n){
		Long a = 1L;
		while(n>0){			
			a = a*b;
			n = n-1;
		}
		return a;
	}
	
	/**
	 * @param b Base 
	 * @param n Exponente
	 * @return Resultado de elevar b a n calculado de forma iterativa
	 */
	public static Long potIJ(int b, int n){
		return IntStream.range(0, n)
						.map(x->n-x)
						.mapToLong(x->b)
						.reduce(1L,(long x,long y)-> x*y);
	}
	
	/**
	 * @param b Base 
	 * @param n Exponente
	 * @return Resultado de elevar b a n calculado de forma recursiva
	 */
	public static Long potR2(int b, int n){
		Long r;
		if(n==0){
			r = 1L;
		} else if(n==1){
			r = (long)b;
		} else if(n%2==1){
			r = potR2(b,n/2);
			r = r*r*b;
		}else{
			r = potR2(b,n/1);
			r = r*r;
		}
		return r;
	}
	
	/**
	 * @param b Base 
	 * @param n Exponente
	 * @return Resultado de elevar b a n calculado de forma iterativa
	 */
	public static long potI2(int b,int n){
		int e = b;
		int a = 1;
		while( n > 0){
	        if(n%2==1){
			     a = a * e;
			}
			e = e * e;
			n = n/2;
		}
		return a;
	}
	
	/**
	 * @param b Base 
	 * @param n Exponente
	 * @param <T> Tipo de los elementos de la matriz
	 * @param f El campo al que pertenecen los elementos de la matriz
	 * @return Resultado de elevar b a n calculado de forma iterativa
	 */
	public static <T extends FieldElement<T>> FieldMatrix<T> potI2(FieldMatrix<T> b, int n, Field<T> f){
		FieldMatrix<T> e = b;			
		FieldMatrix<T> a = Matrices.getId(b.getRowDimension(),f);
		while( n > 0){
	        if(n%2==1){
			     a = a.multiply(e);
			}
			e = e.multiply(e);
			n = n/2;
		}
		return a;
		
	}
		
	public static Tuple3<Integer,Integer,Integer> nextPotI2J(int b,Tuple3<Integer,Integer,Integer> t){
		return Tuple3.create(t.v1*t.v1,t.v2/2,t.v2%2==1?t.v3*t.v1:t.v3);
	}
	
	/**
	 * @param b Base 
	 * @param n Exponente
	 * @return Resultado de elevar b a n calculado de forma iterativa
	 */
	public static long potI2J(int b,int n){
		Tuple3<Integer,Integer,Integer> inicial = Tuple3.create(b,n,1);
		Tuple3<Integer,Integer,Integer> r =Stream.iterate(inicial,x->nextPotI2J(b,x))
				     .filter(t->t.v2 == 0)
				     .findFirst()
				     .get();
	    return r.v3;
	}
	
	/**
	 * 
	 * @param n Término
	 * @return Valor del n-esimo número de Fibonacci calculado de forma recursiva
	 */
	public static BigInteger fibR(Integer n){
		BigInteger r;
		if(n<=1){
			r = new BigInteger(n.toString());
		}else {
			r = fibR(n-1).add(fibR(n-2));		
	    }
		return r;
	}
	
	/**
	 * 
	 * @param n Término
	 * @return Valor del n-esimo número de Fibonacci calculado de forma recursiva con memoria
	 */
	public static BigInteger fibRM(Integer n){
		return fibRM(n,new HashMap<>());
	}
	
	public static BigInteger fibRM(Integer n, Map<Integer,BigInteger> m){
		BigInteger r;
		if(m.containsKey(n)){
			r = m.get(n);
		}else if(n<=1){
			r = new BigInteger(n.toString());
			m.put(n, r);
		}else {
			r = fibR(n-1).add(fibR(n-2));
			m.put(n, r);
	    }
		return r;
	}
	
	/**
	 * 
	 * @param n Término
	 * @return Valor del n-esimo número de Fibonacci calculado de forma iterativa bottom up
	 */
	public static BigInteger fibI(int n){
			int i = 0;
		    BigInteger a = BigInteger.ONE;
			BigInteger b = BigInteger.ZERO;
			while(i < n){
				i = i+1;
				BigInteger a0 = a;
				a = a0.add(b);
				b = a0;
			}
			return b;
	}
	
	
	/**
	 * 
	 * @param n Término
	 * @return Valor del n-esimo número de Fibonacci calculado de forma iterativa con matrices
	 */
	public static BigInteger fibIM(Integer n){
		Integer[] cf = {1,1};
		Integer[] vi = {1,0};
		FieldMatrix<BigFraction> e = Matrices.getBase(2,cf);			
		FieldMatrix<BigFraction> a = Matrices.getId(2,BigFractionField.getInstance());
		FieldMatrix<BigFraction> c = Matrices.getColumn(vi);
		a = potI2(e,n,BigFractionField.getInstance());
		FieldMatrix<BigFraction> r = a.multiply(c);
		return r.getEntry(1, 0).getNumerator();
	}

	/**
	 * @param lista Una lista
	 * @param pivote Un pivote 
	 * @param cmp Un comparator
	 * @param <T> El tipo de los elementos de la lista
	 * @return Los limites de los intervalos en la lista reordenada de forma iterativa
	 */
	public static <T> Tuple2<Integer,Integer> reordenaMedianteBanderaHolandesaI(List<T> lista, T pivote, Comparator<? super T> cmp){
		int a, b, c;
		a = 0;	
		b = 0;	
		c = lista.size();	
		while (c-b>0) {
		    T elem =  lista.get(b);
		    if (cmp.compare(elem, pivote)<0) {
		    	Lists2.intercambia(lista, a,b);
				a++;
				b++;
		    } else if (cmp.compare(elem, pivote)>0) {
		    	Lists2.intercambia(lista,b,c-1);
				c--;	
		    } else if (elem.equals(pivote)) {
		    	b++;
		    }
		}
		return Tuple2.create(a, b);
	}

	public static <T> Tuple3<Integer,Integer,Integer> nextReordenaMedianteBanderaHolandesaIJ(List<T> lista, T pivote, Comparator<? super T> cmp, Tuple3<Integer,Integer,Integer> t){
		T elem =  lista.get(t.v2);
		Tuple3<Integer,Integer,Integer> r = null;
	    if (cmp.compare(elem, pivote)<0) {
	    	Lists2.intercambia(lista,t.v1,t.v2);
	    	r = Tuple3.create(t.v1+1, t.v2+1, t.v3);
	    } else if (cmp.compare(elem, pivote)>0) {
	    	Lists2.intercambia(lista,t.v2,t.v3-1);
	    	r = Tuple3.create(t.v1, t.v2, t.v3-1);	
	    } else if (elem.equals(pivote)) {
	    	r = Tuple3.create(t.v1, t.v2+1, t.v3);
	    }
	    return r;
	}
	/**
	 * @param lista Una lista
	 * @param pivote Un pivote 
	 * @param cmp Un comparator
	 * @param <T> El tipo de los elementos de la lista
	 * @return Los limites de los intervalos en la lista reordenada de forma iterativa en Java 8
	 */
	public static <T> Tuple2<Integer,Integer> reordenaMedianteBanderaHolandesaIJ(List<T> lista, T pivote, Comparator<? super T> cmp){
		Tuple3<Integer,Integer,Integer> inicial = Tuple3.create(0,0,lista.size());
		Tuple3<Integer,Integer,Integer> r =Stream
				.iterate(inicial,x->nextReordenaMedianteBanderaHolandesaIJ(lista,pivote,cmp,x))
				.filter(t->t.v3 -t.v2 == 0)
				.findFirst()
				.get();
	    return Tuple2.create(r.v1, r.v2);
	}
	
	public static <T> Tuple2<Integer,Integer> reordenaMedianteBanderaHolandesaR(List<T> lista, T pivote, Comparator<? super T> cmp){
		return reordenaMedianteBanderaHolandesaR(lista,pivote,cmp, 0, 0, lista.size());
	}
	/**
	 * @param lista Una lista
	 * @param pivote Un pivote 
	 * @param cmp Un comparator
	 * @param <T> El tipo de los elementos de la lista
	 * @param a Limite superior de los elementos menores al pivote
	 * @param b Limite superior de los elementos iguales al pivote
	 * @param c Limite inferior de los elementos mayores que el pivote
	 * @return Los limites de los intervalos en la lista reordenada de forma recursiva
	 */
	public static <T> Tuple2<Integer,Integer> reordenaMedianteBanderaHolandesaR(List<T> lista, T pivote, Comparator<? super T> cmp, int a, int b, int c){
		Tuple2<Integer,Integer> r = null;
		if(c-b == 0){
			r = Tuple2.create(a, b);
		} else {
			T elem =  lista.get(b);
		    if (cmp.compare(elem, pivote)<0) {
		    	Lists2.intercambia(lista, a,b);
				r = reordenaMedianteBanderaHolandesaR(lista,pivote,cmp,a+1,b+1,c);
		    } else if (cmp.compare(elem, pivote)>0) {
		    	Lists2.intercambia(lista,b,c-1);
				r = reordenaMedianteBanderaHolandesaR(lista,pivote,cmp,a,b,c-1);
		    } else if (elem.equals(pivote)) {
		    	r = reordenaMedianteBanderaHolandesaR(lista,pivote,cmp,a,b+1,c);
		    }
		}
		return r;
	}
	
	
	
	public static void main(String[] args) {
//		Integer[] d = {34,57,23,-34,567,89};
//		List<Integer> ls = Arrays.asList(d);
//		boolean ord1 = estaOrdenadaI(ls,Comparator.naturalOrder());
//		boolean ord2 = estaOrdenadaIJ(ls,Comparator.naturalOrder());
//		boolean ord3 = estaOrdenadaR(ls,Comparator.naturalOrder(),0);
//		System.out.println(ord1+","+ord2+","+ord3);
//		Collections.sort(ls);
//		System.out.println(ls);
//		ord1 = estaOrdenadaI(ls,Comparator.naturalOrder());
//		ord2 = estaOrdenadaIJ(ls,Comparator.naturalOrder());
//		ord3 = estaOrdenadaR(ls,Comparator.naturalOrder(),0);
//		System.out.println(ord1+","+ord2+","+ord3);
//		int r1 = buscaPosicionI(ls,-34);
//		int r2 = buscaPosicionR(ls,-34,0);
//		int r3 = buscaPosicionR(ls,-34,0,ls.size());
//		int r4 = buscaPosicionR(ls,-34,Comparator.naturalOrder(),0,ls.size());
//		int r5 = buscaPosicionI(ls,-34,Comparator.naturalOrder());
//		int r6 = buscaPosicionJ7(ls,-34);
//		int r7 = buscaPosicionIJ(ls,-34);
//		int r8 = buscaPosicionIJ(ls,-34,Comparator.naturalOrder());
//		System.out.println(r1+","+r2+","+r3+","+r4+","+r5+","+r6+","+r7+","+r8);
//		r1 = buscaPosicionI(ls,55);
//		r2 = buscaPosicionR(ls,55,0);
//		r3 = buscaPosicionR(ls,55,0,ls.size());
//		r4 = buscaPosicionR(ls,55,Comparator.naturalOrder(),0,ls.size());
//		r5 = buscaPosicionI(ls,55,Comparator.naturalOrder());
//		r6 = buscaPosicionJ7(ls,55);
//		r7 = buscaPosicionIJ(ls,55);
//		r8 = buscaPosicionIJ(ls,55,Comparator.naturalOrder());
//		System.out.println(r1+","+r2+","+r3+","+r4+","+r5+","+r6+","+r7+","+r8);
//		ls = Arrays.asList(3,8,7,11);
//		Boolean r10 = sonImparesI(ls);
//		Boolean r11 = sonImparesIJ(ls);
//		Boolean r12 = sonImparesR(ls,0);		
//		System.out.println(r10+","+r11+","+r12);
//		System.out.println(toBaseR(1234567,10)+","+toBaseI(1234567,10)+","+toBaseIJ(1234567,10));
//		System.out.println(sumaDigitosR(1234567,10)+","+sumaDigitosI(1234567,10)+","+sumaDigitosIJ(1234567,10));
//		System.out.println(potR(3,7)+","+potI(3,7)+","+potIJ(3,7)+","+potI2(3,7)+","+potI2J(3,7)+","+potR2(3,7));
//		Long t1 = System.nanoTime();
//		BigInteger r = fibR(100);
//		Long t2 = System.nanoTime()-t1;
//		System.out.println("Recursivo ="+t2+"---"+r);
//		Long t1 = System.nanoTime();
//		BigInteger r = fibRM(50);
//		Long t2 = System.nanoTime()-t1;
//		System.out.println("Recursivo con Memoria ="+t2+"---"+r);
//		Long t1 = System.nanoTime();
//		BigInteger r = fibI(100000);
//		Long t2 = System.nanoTime()-t1;
//		System.out.println("Iterativo ="+t2+"---");
//		System.out.println(r.toString());
//		t1 = System.nanoTime();
//		r = fibIM(100000);
//		t2 = System.nanoTime()-t1;
//		System.out.println("Iterativo con matriz ="+t2+"---");
//		System.out.println(r.toString());
//		t1 = System.nanoTime();
//		r = Math2.fibonacci5(100000);
//		t2 = System.nanoTime()-t1;
//		System.out.println("Iterativo directo ="+t2+"---");
//		System.out.println(r.toString());
//		ls = Arrays.asList(d);
//		Tuple2<Integer,Integer> r20 = reordenaMedianteBanderaHolandesaR(ls,35, Comparator.naturalOrder());
//		System.out.println(r20);
//		ls = Arrays.asList(d);
//		r20 = reordenaMedianteBanderaHolandesaI(ls,35, Comparator.naturalOrder());
//		System.out.println(r20);
//		ls = Arrays.asList(d);
//		r20 = reordenaMedianteBanderaHolandesaIJ(ls,35, Comparator.naturalOrder());
//		System.out.println(r20);	
		
//		System.out.println(toNumLM("903457",10));
//		System.out.println(toNumML("903457",10));
		List<Double> ls = Arrays.asList(2.,4.,7.,8.);
		System.out.println(EjemplosRecursivoIterativos.esProgresionAritmeticaR(ls));
	}

}

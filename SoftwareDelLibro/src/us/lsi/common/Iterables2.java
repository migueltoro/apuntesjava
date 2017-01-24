package us.lsi.common;

import java.io.*;
import java.util.*;

import us.lsi.math.Math2;

import com.google.common.base.*;
import com.google.common.collect.*;

public class Iterables2 {
	
	public static <F> String toString(Iterable<F> fromIterable){
		List<F> ls = Lists.newArrayList(fromIterable);
		return  ls.toString();
	}
	
	public static <F> void modify(Iterable<F> fromIterable, Function<? super F,Void> function){
		for(F e:fromIterable){
			function.apply(e);
		}	
	}
	
	public static <T> Iterable<T> emptyIterable(){
	    List<T> lis = new ArrayList<T>();
	    return lis;
	}
	
	public static <T> Iterable<T> unitaryRandomIterable(Iterable<T> it){	    
		Preconditions.checkNotNull(it);
		Iterable<T> r = it;		
		if(Iterables.size(it) > 1){	
			List<T> lis = new ArrayList<T>();
			int pos = Math2.getEnteroAleatorio(0, Iterables.size(it));
			lis.add(Iterables.get(it, pos));
			r = lis;
		}
	    return r;
	}
	
	public static <T> T elementRandom(Iterable<T> it){	    
		T r = null;
		Preconditions.checkNotNull(it);
		if(!Iterables.isEmpty(it)){
				int pos = Math2.getEnteroAleatorio(0, Iterables.size(it));
				r = Iterables.get(it, pos);
		}
		return r;
	}
	
	public static Iterable<String> fromFile(String nombreFichero){	
		Iterable<String> is = Iterables.transform(new FlujoEntrada(nombreFichero), 
				new Function<String,String>(){
			@Override
			public String apply(String s) {
				// TODO Auto-generated method stub
				return s.trim();
			}});
		return is;
	}
	
	public static Iterable<String> fromFile(String nombreFichero, String delim){
		Iterable<String> is = Iterables.transform(new FlujoEntrada(nombreFichero,delim), 
				new Function<String,String>(){
			@Override
			public String apply(String s) {
				// TODO Auto-generated method stub
				return s.trim();
			}});
		return is;
	}
	
	public static Iterable<String> fromString(String cadena, String delim){
		Splitter sp = Splitter.on(CharMatcher.anyOf(delim));
		Iterable<String> it = sp.omitEmptyStrings().split(cadena);
		return it;
	}
	
	public static <N extends Number> Iterable<N> fromArithmeticSequence(N a, N b, N c){
		return new SecuenciaAritmetica<N>(a,b,c);
	}
	
	public static <N extends Number> Iterable<Par<N,N>> fromPairsSequence(N a1, N a2, N b1, N b2){
		return new IterableDePares<N>(a1,a2,b1,b2);
	}
	
   public static <T> Iterable<T> fromArray(T[] a){
		return Arrays.asList(a);
	}
	
	public static <T> Iterable<T> fromDomain(Function<T,T> fl, T v0, Predicate<T> domain){
		return new IterableListaVirtualDomain<T>(fl,v0,domain);
	}
	
	public static <T> Iterable<T> fromToLast(Function<T,T> fl, T v0, Predicate<T> esElUltimo){
		return new IterableListaVirtualToLast<T>(fl,v0,esElUltimo);
	}
	
	public static <T> Iterable<T> fromIndex(Function<Integer,T> fl, Integer n){
		return new IterableListaVirtualFromIndex<T>(fl,n);
	}	
	
	private static class IterableListaVirtualDomain<T> implements  Iterable<T> {
		private Function<T,T> f;
		private T v0;
		private Predicate<T> domain;
		
		
		public IterableListaVirtualDomain(Function<T, T> fl, T v0, Predicate<T> domain) {
			super();
			this.f = fl;
			this.v0 = v0;
			this.domain = domain;
		}


		@Override
		public Iterator<T> iterator() {
			// TODO Auto-generated method stub
			return new IteratorListaVirtualDomain();
		}
		
		private class IteratorListaVirtualDomain extends UnmodifiableIterator<T> implements Iterator<T>{
			private T a;
			
			public IteratorListaVirtualDomain() {
				super();
				// TODO Auto-generated constructor stub
				a = v0;
			}

			@Override
			public boolean hasNext() {
				// TODO Auto-generated method stub
				return domain.apply(a);
			}

			@Override
			public T next() {
				// TODO Auto-generated method stub
				Preconditions.checkState(hasNext());   
				T oldA = a;
				a = f.apply(a);
				return oldA;
				
			}
			
		}
	}
	
	private static class IterableListaVirtualToLast<T> implements  Iterable<T> {
		private Function<T,T> f;
		private T v0;
		private Predicate<T> esElUltimo;
		
		
		public IterableListaVirtualToLast(Function<T, T> fl, T v0, Predicate<T> esElUltimo) {
			super();
			this.f = fl;
			this.v0 = v0;
			this.esElUltimo = esElUltimo;
		}


		@Override
		public Iterator<T> iterator() {
			// TODO Auto-generated method stub
			return new IteratorListaVirtualToLast();
		}
		
		private class IteratorListaVirtualToLast extends UnmodifiableIterator<T> implements Iterator<T>{
			private T a;
			
			public IteratorListaVirtualToLast() {
				super();
				// TODO Auto-generated constructor stub
				a = v0;
			}

			@Override
			public boolean hasNext() {
				// TODO Auto-generated method stub
				return a!=null;
			}

			@Override
			public T next() {
				// TODO Auto-generated method stub
				Preconditions.checkState(hasNext());   							
				T oldA = a;
				if(esElUltimo.apply(a)){
					a = null;
				}else{
					a = f.apply(a);
				}
				return oldA;
				
			}
			
		}
	}
	
	private static class IterableListaVirtualFromIndex<T> implements  Iterable<T> {
		
		private Function<Integer,T> f;
		private Integer n;
		
		
		public IterableListaVirtualFromIndex(Function<Integer, T> fl, Integer n) {
			super();
			this.f = fl;
			this.n =n;
		}


		@Override
		public Iterator<T> iterator() {
			// TODO Auto-generated method stub
			return new IteratorListaVirtual1();
		}
		
		private class IteratorListaVirtual1 extends UnmodifiableIterator<T> implements Iterator<T>{
			private Integer a;
			
			public IteratorListaVirtual1() {
				super();
				// TODO Auto-generated constructor stub
				a = 0;
			}

			@Override
			public boolean hasNext() {
				// TODO Auto-generated method stub
				return a<n;
			}

			@Override
			public T next() {
				// TODO Auto-generated method stub
				Preconditions.checkState(hasNext());   
				Integer oldA = a;
				a = a+1;
				return f.apply(oldA);
				
			}
			
		}
	}
	
	private static class FlujoEntrada  implements Iterable<String>{
		private String nf;
		private String delimiter;
		public FlujoEntrada(String nombreFich) {
			nf = nombreFich;
			delimiter = "\\n+";
		}
		public FlujoEntrada(String nombreFich, String delim) {
			nf = nombreFich;
			delimiter = delim;
//			System.out.println(delimiter);
		}
		public Iterator<String> iterator() {
			Iterator<String> itor = null;
			try {
				itor = new Scanner(new File(nf)).useDelimiter(delimiter);
			} catch (FileNotFoundException e) {
				throw new IllegalStateException();
			}
			return itor;
		}
	}
	
	private static class SecuenciaAritmetica<N extends Number> implements Iterable<N> {

		private Double primero,ultimo,incremento;
		private Double dif;
		private Class<N> type;
		
		@SuppressWarnings("unchecked")
		public SecuenciaAritmetica(N a, N b, N c){
			type = (Class<N>) a.getClass();
			primero =    a.doubleValue();
			ultimo =     b.doubleValue();
			incremento=  c.doubleValue();	
			dif = ultimo-primero;
			Preconditions.checkArgument(dif*incremento > 0, "No se puede generar secuencia aritmética con "+a+","+b+","+c);
		}
		
		public Iterator<N> iterator(){
			return new IteradorSecuenciaAritmetica();
		}

		private class IteradorSecuenciaAritmetica  extends UnmodifiableIterator<N> implements Iterator<N> {
			private Double actual;
			
			public IteradorSecuenciaAritmetica(){
				this.actual=primero; 
	  		}
			public N next() { 
				    Preconditions.checkState(hasNext());   
	        		Double r=actual; 
	        		actual = actual + incremento;
					return type.cast(r);
	  		}    
	  		public boolean hasNext() { 
	        		return (ultimo-actual)*dif > 0.; 
	  		} 
	 	} 
	} 
	

	private static class IterableDePares<N extends Number> implements Iterable<Par<N, N>> {
		private Double a1;
		private Double a2;
		private Double b1;
		private Double b2;
		private Class<N> type;
		
		@SuppressWarnings("unchecked")
		public IterableDePares(N ma1, N ma2, N mb1, N mb2) {
			a1 = ma1.doubleValue();
			a2 = ma2.doubleValue();
			b1 = mb1.doubleValue();
			b2 = mb2.doubleValue();
			type = (Class<N>) ma1.getClass();
			if(!((a2>=a1)|| (b2>=b1)))  throw new IllegalArgumentException("No se permiten esos parámetros");
			
		}
		
		public Iterator<Par<N, N>> iterator() {
			return new IteradorDePares();
		}
		
		private class IteradorDePares extends UnmodifiableIterator<Par<N,N>> implements Iterator<Par<N,N>>{
			private Double ia;
			private Double ib;
				  
			public IteradorDePares() {
				  ia = a1;
				  ib = b1;
			}

			public boolean hasNext() {
				  return ia < a2;
			}

			public Par<N,N> next() {
				 	Preconditions.checkState(hasNext());   
					Double oia = ia;
					Double oib = ib;
					if(ib < b2-1) 
						ib = ib +1;
					else {
						ia = ia+1;
						ib = b1;
					}
					return Par.create(type.cast(oia), type.cast(oib));
			}

		}
	}


	public static <S> boolean esIterable(S object){
		boolean r = false;
		if(object instanceof Iterable)
			r = true;
		return r;
	}
	

}


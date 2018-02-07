package us.lsi.dyv.problemasdelistas;

import java.util.Comparator;
import java.util.List;
import java.util.stream.*;
import us.lsi.common.Lists2;
import us.lsi.common.Tuple2;
import us.lsi.math.*;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;


public class ProblemasDeListas {
	
	
	public static <E extends Comparable<? super E>> int binarySearch(List<E> lista, E key){
		Comparator<E> ord = Comparator.naturalOrder();
		return bSearch(lista,0,lista.size(),key,ord);	
	}
	
	public static <E> int binarySearch(List<E> lista, E key, Comparator<? super E> cmp){
		return bSearch(lista,0,lista.size(),key,cmp);	
	}
	
	private static <E> int bSearch(List<E> lista, int i,int j, E key, Comparator<? super E> ord){
		Preconditions.checkArgument(j>=i);
		int r;
		int k;
		if(j-i == 0){
			r = -1;
		}else{
			k = (i+j)/2;
			int r1 = ord.compare(key,lista.get(k));
			if(r1==0){
				r = k;
			}else if(r1<0){
				r = bSearch(lista,i,k,key,ord);
			}else{
				r = bSearch(lista,k+1,j,key,ord);
			}
		}
		return r;
	}
	
	public static <E extends Comparable<? super E>> void sort(List<E> lista){
		Comparator<? super E> ord = Comparator.naturalOrder();
		quickSort(lista,0,lista.size(),ord);	
	}
	
	public static <E> void sort(List<E> lista, Comparator<? super E> cmp){
		quickSort(lista,0,lista.size(),cmp);	
	}
	
	public static <T> Tuple2<Integer,Integer> reordenaMedianteBanderaHolandesa(List<T> lista, T pivote, Integer i, Integer j,  Comparator<? super T> cmp){
		int a, b, c;
		a = i;	
		b = i;	
		c = j;	
		while (c-b>0) {
		    T elem =  lista.get(b);
		    if (cmp.compare(elem, pivote)<0) {
		    	Lists2.intercambia(lista, a,b);
				a++;
				b++;
		    } else if (cmp.compare(elem, pivote)>0) {
		    	Lists2.intercambia(lista,b,c-1);
				c--;	
		    } else {
		    	b++;
		    }
		}
		return Tuple2.create(a, b);
	}

	public static <T> Integer reordenaSobrePivote(List<T> lista, T pivote, Integer i, Integer j,  Comparator<? super T> cmp){
		int a, b; 
		a = i;  
		b = j; 
		while (b-a>0) {
			T elem =  lista.get(a);
			 if (cmp.compare(elem, pivote)<0){
				 a++; 
			 } else {
				 Lists2.intercambia(lista, a,b-1);
				 b--;
			 }
		}
		return a;
	}
	
	
	private static <E> void quickSort(List<E> lista, int i, int j, Comparator<? super E> ord){
		Preconditions.checkArgument(j>=i);
		if(j-i <= 4){
			ProblemasDeListas.ordenaBase(lista, i, j, ord);
		}else{
			E pivote = escogePivote(lista, i, j);
			Tuple2<Integer,Integer> p = ProblemasDeListas.reordenaMedianteBanderaHolandesa(lista, pivote, i, j, ord);
			quickSort(lista,i,p.getV1(),ord);
			quickSort(lista,p.getV2(),j,ord);			
		}
	}

	public static <E extends Comparable<? super E>> void mergeSort(List<E> lista){
		Comparator<? super E> ord = Comparator.naturalOrder();
		List<E> ls = Lists.newArrayList(lista);
		mgSort(lista,0,lista.size(),ord,ls);	
	}
	
	public static <E> void mergeSort(List<E> lista, Comparator<? super E> ord){
		List<E> ls = Lists.newArrayList(lista);
		mgSort(lista,0,lista.size(),ord,ls);	
	}
	
	private static <E> void mgSort(List<E> lista, int i, int j, Comparator<? super E> ord, List<E> ls){
		if(j-i > 1){
			int k = (j+i)/2;
			mgSort(lista,i,k,ord,ls);
			mgSort(lista,k,j,ord,ls);
			mezcla(lista,i,k,lista,k,j,ls,i,j,ord);
			copia(lista,i,j,ls);
		}
	}
	
	private static <E> void mezcla(List<E> l1, int i1, int j1, List<E> l2, int i2, int j2,List<E> l3, int i3, int j3, Comparator<? super E> ord){
		int k1= i1;
		int k2= i2;
		int k3= i3;
		while(k3<j3){
			if(k1<j1 && k2<j2){
				if(ord.compare(l1.get(k1), l2.get(k2))<=0){
					l3.set(k3, l1.get(k1));
					k1++;
					k3++;
				}else{
					l3.set(k3, l2.get(k2));
					k2++;
					k3++;
				}
			}else if(k2==j2){
				l3.set(k3, l1.get(k1));
				k1++;
				k3++;
			}else{
				l3.set(k3, l2.get(k2));
				k2++;
				k3++;
			}
		}
	}
	
	private static <E> void copia(List<E> lista, int i, int j, List<E> ls){
		for(int k = i; k<j; k++){
			lista.set(k, ls.get(k));
		}
	}
	
	private static <E> E escogePivote(List<E> lista, int i, int j) {
		E pivote = lista.get(Math2.getEnteroAleatorio(i, j));
		return pivote;
	}
	
	public static <E extends Comparable<? super E>> E getKesimo(List<E> lista, int k){
		Preconditions.checkElementIndex(k, lista.size());
		Comparator<? super E> ord = Comparator.naturalOrder();
		return escogeKesimo(lista,0,lista.size(),k,ord);	
	}
	
	public static <E> E getKesimo(List<E> lista, int k, Comparator<? super E> cmp){
		Preconditions.checkElementIndex(k, lista.size());
		return escogeKesimo(lista,0,lista.size(),k,cmp);
	}
	
	private static <E> E escogeKesimo(List<E> lista, int i, int j, int k, Comparator<? super E> ord){
		Preconditions.checkArgument(j>=i);
		E r = null;
		if(j-i == 0){
			
		} else if(j-i == 1){
			r = lista.get(i);
		}else{
			E pivote = escogePivote(lista, i, j);
			Tuple2<Integer,Integer> p = ProblemasDeListas.reordenaMedianteBanderaHolandesa(lista, pivote, i, j, ord);
			if(k < p.getV1()){
				r = escogeKesimo(lista,i,p.getV1(),k,ord);
			}else if(k >= p.getV2()){
				r = escogeKesimo(lista,p.getV2(),j,k,ord);
			}else{
				r = lista.get(k);
			}					
		}
		return r;
	}	
	
	public static SubSecuencia getSubSecuenciaMaxima(List<Double> lista){
		Comparator<SubSecuencia> ord = Comparator.naturalOrder();
		return getSubSecuenciaMaxima(lista,0,lista.size(),ord);
	}
	
	private static SubSecuencia getSubSecuenciaMaxima(List<Double> lista, int i, int j, Comparator<SubSecuencia> ord){
		SubSecuencia r = null;	
		if(j-i <= 1){
			r = new SubSecuencia(lista,i,j);
		}else{
			int k = (i+j)/2;
			SubSecuencia s1 = getSubSecuenciaMaxima(lista, i, k, ord);
			SubSecuencia s2 = getSubSecuenciaMaxima(lista, k, j, ord);
			SubSecuencia s3 = getSubSecuenciaMaximaCentrada(lista,i,j,k);
			r = Stream.of(s1, s2, s3).max(ord).get();
		}
		return r;
	}	
	
	private static SubSecuencia getSubSecuenciaMaximaCentrada(List<Double> lista, int a, int b, int k){
		Double sumaMaxima = Double.MIN_VALUE;
		Double suma = 0.;
		int i1 = k;
		int j1 = k;
		int from = i1;
		int to = j1;
		for(i1 = k-1;i1 >= a; i1--){
			suma = suma + lista.get(i1);  
			if(suma > sumaMaxima){
				sumaMaxima = suma;
				from = i1;
			}
		}
		suma = sumaMaxima;
		for(j1=k;j1<b;j1++){
			suma = suma + lista.get(j1);  
			if(suma > sumaMaxima){
				sumaMaxima = suma;
				to = j1+1;
			}
		}
		SubSecuencia sm = new SubSecuencia(lista,from,to);
		return sm;
	}
	
	public static class SubSecuencia implements Comparable<SubSecuencia>{
		private List<Double> lista;
		private Integer from;
		private Integer to;
		
		
		public SubSecuencia(List<Double> lista, Integer from, Integer to) {
			super();
			this.lista = lista;
			this.from = from;
			this.to = to;
		}

		public Integer getFrom() {
			return from;
		}

		public Integer getTo() {
			return to;
		}

		public Double getSuma() {
			Double suma = 0.;
			for(int i= from; i<to;i++){
				suma = suma+lista.get(i);
			}
			return suma;
		}

		@Override
		public int compareTo(SubSecuencia s) {
			// TODO Auto-generated method stub
			return this.getSuma().compareTo(s.getSuma());
		}

		@Override
		public String toString() {
			return "SubSecuencia [from=" + from + ", to=" + to + ", getSuma()="
					+ getSuma() + "]";
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((from == null) ? 0 : from.hashCode());
			result = prime * result + ((lista == null) ? 0 : lista.hashCode());
			result = prime * result + ((to == null) ? 0 : to.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			SubSecuencia other = (SubSecuencia) obj;
			if (from == null) {
				if (other.from != null)
					return false;
			} else if (!from.equals(other.from))
				return false;
			if (lista == null) {
				if (other.lista != null)
					return false;
			} else if (!lista.equals(other.lista))
				return false;
			if (to == null) {
				if (other.to != null)
					return false;
			} else if (!to.equals(other.to))
				return false;
			return true;
		}
		
	}

	public static <T> void ordenaBase(List<T> lista, Integer inf, Integer sup, Comparator<? super T> ord) {		
		for (int i = inf; i < sup; i++) {
		      for(int j = i+1; j < sup; j++){
		    	  if(ord.compare(lista.get(i),lista.get(j))>0){
		    		  Lists2.intercambia(lista, i, j);
		    	  }
		      }
		}
	}
}

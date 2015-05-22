package us.lsi.algoritmos;

import java.util.*;
import com.google.common.collect.*;


/**
 * Un agregado de elementos ordenados
 *  
 * @author Miguel Toro 
 *
 * @param <S> El tipo de los elementos
 */
public class Soluciones<S> {
	
	
	private SortedSet<S> soluciones;

    private Soluciones(Comparator<S> cmp){
    	soluciones = Sets.<S>newTreeSet(cmp);
    }

    public static <S extends Comparable<? super S>> Soluciones<S> create() {
		return new Soluciones<S>(Comparator.<S>naturalOrder());
	}
    
    public static <S> Soluciones<S> create(Comparator<S> cmp) {
		return new Soluciones<S>(cmp);
	}
    
    public boolean isEmpty(){
    	return soluciones.isEmpty();
    }
    
    public int size(){
    	return soluciones.size();
    }
    
    public boolean add(S s){
    	boolean r = soluciones.add(s);
    	return r;
    }

	public S getMejorSolucion() {
		S r = null;
		if(!soluciones.isEmpty()){
			r =soluciones.first();
		}
		return r;
	}
	
	public SortedSet<S> getSoluciones() {
		return soluciones;
	}
	
	public String toString(){
		String r = "El número de Soluciones es = "+soluciones.size()+".  Las Soluciones son: \n";
		for(S s : soluciones){
			r=r+s+"\n";
		}
		return r;
	}
}

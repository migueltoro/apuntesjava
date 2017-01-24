package us.lsi.pd.mochila;

import java.util.Set;

import com.google.common.collect.*;

/**
 * <p> Esta clase implementa el tipo SolucionMochila. </p>
 * <p> Las propiedades de estos objetos son: </p>
 * <ul>
 * <li> Objetos en la mochila (básica, de tipo Multiset&lt;ObjetoMochila&gt;)
 * <li> Valor (derivada)
 * <li> Peso (derivada)
 * </ul> 
 * 
 * 
 * 
 * @author Miguel Toro
 *
 */
public class SolucionMochila {
	
	public static SolucionMochila create() {
		return new SolucionMochila();
	}
	
	public static SolucionMochila create(Multiset<ObjetoMochila> m) {
		return new SolucionMochila(m);
	}

	private Multiset<ObjetoMochila> m;	

	private SolucionMochila() {
		super();
		this.m = HashMultiset.create();
	}
	
	private SolucionMochila(Multiset<ObjetoMochila> m) {
		super();
		this.m = m;
	}
		
	public SolucionMochila add(ObjetoMochila ob, int nu) {
		Multiset<ObjetoMochila> m = getM();
		m.add(ob, nu);	
		return create(m);
	}
	
	public Multiset<ObjetoMochila> getM() {
		return HashMultiset.create(m);
	}
	
	public int count(ObjetoMochila ob){
		return m.count(ob);
	}
	
	public Set<ObjetoMochila> elements(){
		return m.elementSet();
	}
	
	public Integer getValor() {	
		Integer r = 0;
		for(ObjetoMochila e : m){
				r = r+e.getValor();
		}
		return r;
	}
	
	public Integer getPeso() {
		Integer r = 0;
		for(ObjetoMochila e : m){
			r = r+e.getPeso();
		}
		return r;
	}
	
	public int compareTo(SolucionMochila s) {
		int r = getValor().compareTo(s.getValor());
		if(r == 0){
			r = this.toString().compareTo(s.toString());
		}
		return r;
	}
	
	
	public boolean equals(Object arg0) {
		return m.equals(arg0);
	}

	public int hashCode() {
		return m.hashCode();
	}
	
	public String toString() {
		return m.toString()+","+getValor()+","+getPeso();
	}
	
}

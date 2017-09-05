package us.lsi.bt.anuncios;

import java.util.*;

import us.lsi.bt.SolucionBT;
import us.lsi.common.Lists2;
import us.lsi.common.PairInteger;
import us.lsi.math.Math2;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

public class ListaDeAnunciosAEmitir implements SolucionBT {

	public enum Opcion {Insertar,Eliminar, Intercambiar};
	
	private List<Integer> anunciosDecididosParaEmitir;
	private Set<Integer> anunciosDecididosParaEmitirSet;
	private Integer tiempoConsumido;
	private Integer tiempoRestante;
	private Double valor;	
	private SortedSet<Integer> anunciosDisponibles;
	private Integer numAnunciosIncompatibles;
	private Integer numAnunciosRepetidos;	

	public Integer getNumAnunciosIncompatibles() {
		return numAnunciosIncompatibles;
	}

	public Integer getNumAnunciosRepetidos() {
		return numAnunciosRepetidos;
	}

	public static ListaDeAnunciosAEmitir create(List<Integer> anunciosDecididosParaEmitir) {
		return new ListaDeAnunciosAEmitir(anunciosDecididosParaEmitir);
	}

	public static ListaDeAnunciosAEmitir createValido(List<Integer> anunciosDecididosParaEmitir) {
		ListaDeAnunciosAEmitir la = new ListaDeAnunciosAEmitir(anunciosDecididosParaEmitir);
		if (!la.cumpleRestricciones()) {
			throw new IllegalArgumentException("Estado No válido");
		}
		return la;
	}
	
	public static ListaDeAnunciosAEmitir create() {
		return new ListaDeAnunciosAEmitir();
	}
	
	private  ListaDeAnunciosAEmitir(){	
		this(Lists.<Integer>newArrayList());
	}
	
	private  ListaDeAnunciosAEmitir(List<Integer> anunciosDecididosParaEmitir){
		this.anunciosDecididosParaEmitir = Lists.newArrayList(anunciosDecididosParaEmitir);
		this.anunciosDecididosParaEmitirSet = Sets.newHashSet(anunciosDecididosParaEmitir);
		calculaPropiedadesDerivadas();		
		calculaAnunciosDisponibles();
	}
	
	public static ListaDeAnunciosAEmitir create(ListaDeAnunciosAEmitir ls) {
		return new ListaDeAnunciosAEmitir(ls.anunciosDecididosParaEmitir);
	}

	public boolean cumpleRestricciones() {
		return this.numAnunciosIncompatibles == 0 && this.numAnunciosRepetidos == 0 && this.tiempoRestante >=0 ;		
	}

	private void calculaPropiedadesDerivadas(){			
		this.tiempoConsumido = 0;
		this.valor = 0.;
		for(int i=0; i< anunciosDecididosParaEmitir.size();i++){
			Integer a = anunciosDecididosParaEmitir.get(i);
			this.valor = this.valor+ProblemaAnuncios.getAnuncio(a).getPrecio(this.tiempoConsumido+1);
			this.tiempoConsumido=this.tiempoConsumido+ProblemaAnuncios.getAnuncio(a).getDuracion();
		}
		this.tiempoRestante = ProblemaAnuncios.tiempoTotal-this.tiempoConsumido;	
		this.numAnunciosIncompatibles = 0;				
		for(PairInteger p: ProblemaAnuncios.restricciones){
			if(this.anunciosDecididosParaEmitirSet.contains(p.v1) && this.anunciosDecididosParaEmitirSet.contains(p.v2)){
				this.numAnunciosIncompatibles = this.numAnunciosIncompatibles +1;
			}
		}
		this.numAnunciosRepetidos =  this.anunciosDecididosParaEmitir.size() - this.anunciosDecididosParaEmitirSet.size();	
	}
	
	
	private void calculaAnunciosDisponibles(){		
		Set<Integer> disponibles = Sets.newHashSet(ProblemaAnuncios.todosLosAnuncios);	
		disponibles.removeAll(this.anunciosDecididosParaEmitirSet);
		for(PairInteger p: ProblemaAnuncios.restricciones){
			if(this.anunciosDecididosParaEmitirSet.contains(p.v1)){
				disponibles.remove(p.v2);
			}
		}
		Set<Integer> quitar = Sets.newHashSet();
		for(Integer e : disponibles){
			if(ProblemaAnuncios.getAnuncio(e).getDuracion()>this.tiempoRestante){
				quitar.add(e);
			}
		}
		disponibles.removeAll(quitar);
		Comparator<Integer> cmp = Comparator.<Integer,Anuncio>comparing(x->ProblemaAnuncios.getAnuncio(x), Comparator.<Anuncio>reverseOrder());
		this.anunciosDisponibles = Sets.newTreeSet(cmp);
		this.anunciosDisponibles.addAll(disponibles);
	}
	
	public ListaDeAnunciosAEmitir insertar(int pos, Integer e){
		Preconditions.checkPositionIndex(pos, this.anunciosDecididosParaEmitir.size());
		Preconditions.checkArgument(!this.anunciosDecididosParaEmitirSet.contains(e));
		List<Integer> ls = Lists.newArrayList(this.anunciosDecididosParaEmitir);
		ls.add(pos, e);
		return create(ls);
	}
	
	public ListaDeAnunciosAEmitir insertarUltimo(Integer e){
		return insertar(this.anunciosDecididosParaEmitir.size(),e);
	}
	
	public ListaDeAnunciosAEmitir eliminar(int pos){
		Preconditions.checkElementIndex(pos, this.anunciosDecididosParaEmitir.size());
		List<Integer> ls = Lists.newArrayList(this.anunciosDecididosParaEmitir);
		ls.remove(pos);
		return create(ls);
	}
	
	public ListaDeAnunciosAEmitir eliminarUltimo(){
		Preconditions.checkArgument(!this.anunciosDecididosParaEmitir.isEmpty());
		return eliminar(this.anunciosDecididosParaEmitir.size());
	}

	public ListaDeAnunciosAEmitir intercambiar(int i, int j){
		Preconditions.checkElementIndex(i, this.anunciosDecididosParaEmitir.size());
		Preconditions.checkElementIndex(j, this.anunciosDecididosParaEmitir.size());
		Preconditions.checkArgument(i!=j);
		List<Integer> ls = Lists.newArrayList(this.anunciosDecididosParaEmitir);
		Lists2.intercambia(ls, i, j);
		return create(ls);
	}
	
	public List<Anuncio> getAnunciosDecididosParaEmitir() {
		List<Anuncio> ls = Lists.newArrayList();
		for(Integer e: this.anunciosDecididosParaEmitir){
			ls.add(ProblemaAnuncios.getAnuncio(e));
		}
		return ls;
	}

	public List<Integer> getAnunciosDecididosParaEmitirInt() {
		return this.anunciosDecididosParaEmitir;
	}
	
	public Set<Integer> getAnunciosDecididosParaEmitirSet() {
		return anunciosDecididosParaEmitirSet;
	}

	public Integer getTiempoConsumido() {
		return tiempoConsumido;
	}

	public Integer getTiempoRestante() {
		return tiempoRestante;
	}

	public Double getValor() {
		return valor;
	}
	
	@Override
	public Double getObjetivo() {
		return valor;
	}
	public Integer getNumAnunciosAEmitir(){
		return this.anunciosDecididosParaEmitir.size();
	}
	
	public SortedSet<Integer> getAnunciosDisponibles(){
		return this.anunciosDisponibles;
	}
	
	public int getNumAnunciosDisponibles(){
		return this.anunciosDisponibles.size();
	}
	
	public Integer getAlternativaEliminar(){
		return Math2.getEnteroAleatorio(0, this.anunciosDecididosParaEmitir.size());
	}	
	
	public PairInteger getAlternativaInsertar() {
		Preconditions.checkState(!this.anunciosDisponibles.isEmpty());
		Integer pos = Math2.getEnteroAleatorio(0,this.anunciosDecididosParaEmitir.size() + 1);
		List<Integer> ls = Lists.newArrayList(this.anunciosDisponibles);
		Integer r = Math2.getEnteroAleatorio(0,ls.size());
		return PairInteger.create(pos, ls.get(r));		
	}

	public PairInteger getAlternativaIntercambiar(){
		return Math2.getParAleatorioYDistinto(0, this.anunciosDecididosParaEmitir.size());	
	}

	public List<Opcion> getTiposDeOpcionesAlternativasPosibles(){
		List<Opcion> ls = Lists.newArrayList();
		for(Opcion op : Opcion.values()){
			switch(op){
			case Insertar :
				if(!this.getAnunciosDisponibles().isEmpty()){
					ls.add(op);
				}
				break;
			case Eliminar :
				if(!this.getAnunciosDecididosParaEmitir().isEmpty()){
					ls.add(op);
				}
				break;
			case Intercambiar :
				if(this.getAnunciosDecididosParaEmitir().size() >=2){
					ls.add(op);
				}
				break;
			}			
		}
		return ls;
	}
	
	public static ListaDeAnunciosAEmitir getSolucionVoraz(){
		ListaDeAnunciosAEmitir e = ListaDeAnunciosAEmitir.create();
		while(!e.getAnunciosDisponibles().isEmpty()){
			Integer a = e.getAnunciosDisponibles().first();
			e = e.insertarUltimo(a);
		}
		return e;
	}
	
	@Override
	public String toString() {
		return anunciosDecididosParaEmitir+ "\n Valor=" + valor 
				+ "\n TiempoRestante=" + tiempoRestante
				+"\n NumAnunciosIncompatibles ="+ this.numAnunciosIncompatibles
				+"\n NumAnuncios Repetidos = "+ this.numAnunciosRepetidos
				+ "\n AnunciosDisponibles=" + anunciosDisponibles + "]";
	}
	
	public static void main(String[] args) {
		ProblemaAnuncios.tiempoTotal = 30;
		ProblemaAnuncios.leeYOrdenaAnuncios("anuncios.txt");
		for(Anuncio a:ProblemaAnuncios.todosLosAnunciosDisponibles){
			System.out.println(a.getCodigo()+","+a.getPrecioUnitario());
		}		
		System.out.println(ProblemaAnuncios.todosLosAnunciosDisponibles);
		System.out.println(ProblemaAnuncios.restricciones);
		ListaDeAnunciosAEmitir ls = ListaDeAnunciosAEmitir.getSolucionVoraz();
		System.out.println(ls);
	}
}

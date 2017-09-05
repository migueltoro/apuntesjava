package us.lsi.bt.afinidad;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;









import us.lsi.bt.EstadoBT;
import us.lsi.common.Lists2;
import us.lsi.pd.afinidad.Cliente;
import us.lsi.pd.afinidad.ProblemaAfinidad;
import us.lsi.pd.afinidad.SolucionAfinidad;

public class EstadoAfinidadBT implements EstadoBT<SolucionAfinidad,Integer>{


	public static EstadoAfinidadBT create() {
		return new EstadoAfinidadBT();
	}

	private int indexCliente;
	private List<Integer> numClientesPorTrabajador;	
	private Map<Integer, Set<Integer>> trabajadorOcupadosEnFranja;	
	private int afinidadAcum;	
	private List<Integer> alternativas;
	
	
	private EstadoAfinidadBT() {
		this.indexCliente=0;
		this.numClientesPorTrabajador= Lists2.nCopias(ProblemaAfinidad.trabajadores.size(), 0);
		this.trabajadorOcupadosEnFranja=new HashMap<>();
		ProblemaAfinidad.clientes.stream()
			.forEach(x->this.trabajadorOcupadosEnFranja.put(x.franjaHoraria,new HashSet<>()));
		this.alternativas= new ArrayList<>();
		
	}
	
	private Cliente getCliente(int index){
		return ProblemaAfinidad.clientes.get(index);
	}
	
	private Set<Integer> getTrabajadoresAfinesACliente(int index){
		return ProblemaAfinidad.clientes.get(index).trabajadoresAfines;
	}	
	
	private Set<Integer> getTrabajadoresOcupadosEnFranjaDeCliente(int index){
		int franja=this.getCliente(indexCliente).franjaHoraria;
		return this.trabajadorOcupadosEnFranja.get(franja);
	}
	
	@Override
	public EstadoBT<SolucionAfinidad, Integer> getEstadoInicial() {		
		return create();
	}
	
	@Override
	public Tipo getTipo() {	
		return Tipo.Max;
	}	
	
	@Override
	public EstadoAfinidadBT avanza(Integer a) {
		
		//Actualiza la afinidad. +1 si el trabajador a es afin al cliente index 
		this.afinidadAcum+=(this.getTrabajadoresAfinesACliente(indexCliente).contains(a)?1:0);		
		
		//añade al trabajador en la franja del cliente
		
		this.getTrabajadoresOcupadosEnFranjaDeCliente(indexCliente).add(a);
		
		//añade al trabajador a un cliente más
		this.numClientesPorTrabajador.set(a,numClientesPorTrabajador.get(a)+1);
		this.alternativas.add(a);
		this.indexCliente++;
		
		return this;
		
	}

	@Override
	public EstadoAfinidadBT retrocede(Integer a) {
		indexCliente--;
		
		//Elimina la afinidad si es que se introdujo
		afinidadAcum-=(this.getTrabajadoresAfinesACliente(indexCliente).contains(a)?1:0);
								
		//saca al trabajador de la franja en la que se introdujo
		this.getTrabajadoresOcupadosEnFranjaDeCliente(indexCliente).remove(a);
		
		//Le quita un cliente al trabajador
		numClientesPorTrabajador.set(a,numClientesPorTrabajador.get(a)-1);
		
		//Quita la alternativa
		alternativas.remove(alternativas.size()-1);	
		
		return this;
	}

	@Override
	public int size() {		
		return ProblemaAfinidad.clientes.size()-indexCliente;
	}

	@Override
	public boolean esCasoBase() {
		return indexCliente==ProblemaAfinidad.clientes.size();
	}

	@Override
	public List<Integer> getAlternativas() {

		List<Integer> ret= IntStream.range(0, ProblemaAfinidad.trabajadores.size())
				.filter(x -> cumpleRestricciones(x))
				.boxed()
				.collect(Collectors.toList());		
		
		return ret;
	}
	
	private boolean cumpleRestricciones(int x){	
		return this.numClientesPorTrabajador.get(x) <= 2 && 
			  !this.getTrabajadoresOcupadosEnFranjaDeCliente(indexCliente).contains(x) ;		
	}

	@Override
	public SolucionAfinidad getSolucion() {
		Map<String, String> sol= new HashMap<>();
		for(int i=0; i< ProblemaAfinidad.clientes.size();i++){
			sol.put(this.getCliente(i).nombre, ProblemaAfinidad.trabajadores.get(alternativas.get(i)));
		}
		return SolucionAfinidad.create(sol, afinidadAcum);
	}
	
	@Override
	public Double getObjetivoEstimado(Integer a) {
		return (double) afinidadAcum + 
				        (this.getTrabajadoresAfinesACliente(indexCliente).contains(a)?1:0)+
				        (ProblemaAfinidad.clientes.size()-indexCliente-1);
			
	}

}


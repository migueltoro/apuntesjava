package us.lsi.pd.afinidad;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import us.lsi.common.Lists2;
import us.lsi.pd.AlgoritmoPD.Sp;
import us.lsi.pd.ProblemaPD;
import us.lsi.pd.ProblemaPDR;

public class ProblemaAfinidadPD  implements ProblemaPDR<SolucionAfinidad, Integer> {
	
	
	public static ProblemaAfinidadPD create() {
		return new ProblemaAfinidadPD();
	}

	public static ProblemaAfinidadPD create(int indexCliente,
			List<Integer> numClientesPorTrabajador,
			Map<Integer, Set<Integer>> trabajadorOcupadosEnFranja,
			int afinidadAcum) {
		return new ProblemaAfinidadPD(indexCliente, numClientesPorTrabajador,
				trabajadorOcupadosEnFranja, afinidadAcum);
	}

	private int indexCliente;
	private List<Integer> numClientesPorTrabajador;	
	private Map<Integer, Set<Integer>> trabajadoresOcupadosEnFranja;	
	private int afinidadAcum;
	
	private ProblemaAfinidadPD() {
		this.indexCliente=0;
		this.numClientesPorTrabajador= Lists2.nCopias(ProblemaAfinidad.trabajadores.size(), 0);
		this.trabajadoresOcupadosEnFranja=new HashMap<>();
		ProblemaAfinidad.clientes.stream()
			.forEach(x->this.trabajadoresOcupadosEnFranja.put(x.franjaHoraria,new HashSet<>()));	
	}
	
	private ProblemaAfinidadPD(int indexCliente, List<Integer> numClientesPorTrabajador,
			Map<Integer,Set<Integer>> trabajadorOcupadosEnFranja, int afinidadAcum) {
		this.indexCliente = indexCliente;
		this.numClientesPorTrabajador = numClientesPorTrabajador;		
		this.trabajadoresOcupadosEnFranja = trabajadorOcupadosEnFranja;
		this.afinidadAcum = afinidadAcum;
	}
	
	private Cliente getCliente(int index){
		return ProblemaAfinidad.clientes.get(index);
	}
	
	private Set<Integer> getTrabajadoresAfinesACliente(int index){
		return ProblemaAfinidad.clientes.get(index).trabajadoresAfines;
	}	
	
	private Set<Integer> getTrabajadoresOcupadosEnFranjaDeCliente(int index){
		int franja=this.getCliente(indexCliente).franjaHoraria;
		return this.trabajadoresOcupadosEnFranja.get(franja);
	}
	
	private void addTrabajadorOcupadoEnFranjaDeCliente(int index, 
			Map<Integer, Set<Integer>> trabajadoresOcupadosEnFranja, Integer a){
		int franja=this.getCliente(indexCliente).franjaHoraria;
		Set<Integer> s = new HashSet<>(trabajadoresOcupadosEnFranja.get(franja));
		s.add(a);
		trabajadoresOcupadosEnFranja.put(franja, s);
	}
	
	public Tipo getTipo() {	
		return Tipo.Max;
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
	public Sp<Integer> getSolucionParcialCasoBase() {		
		return Sp.create(null, 0.);
	}

	@Override
	public Sp<Integer> getSolucionParcial(List<Sp<Integer>> ls) {		
		Sp<Integer> s = Collections.max(ls);
		return s;
	}

	@Override
	public ProblemaPD<SolucionAfinidad, Integer> getSubProblema(Integer a) {		
		//Actualiza la afinidad. +1 si el trabajador a es afin al cliente index 
		 Integer afinidadAcum = this.afinidadAcum +(this.getTrabajadoresAfinesACliente(indexCliente).contains(a)?1:0);		
				
		//añade al trabajador en la franja del cliente				
		Map<Integer, Set<Integer>> trabajadoresOcupadosEnFranja= new HashMap<>(this.trabajadoresOcupadosEnFranja);
		addTrabajadorOcupadoEnFranjaDeCliente(indexCliente,trabajadoresOcupadosEnFranja,a);
				
		//añade al trabajador a un cliente más
		List<Integer> numClientes=new ArrayList<>(numClientesPorTrabajador);
		numClientes.set(a,numClientes.get(a)+1);
		Integer indexCliente = this.indexCliente+1;
				
		return ProblemaAfinidadPD.create(indexCliente, numClientes, trabajadoresOcupadosEnFranja, afinidadAcum);
	}

	

	@Override
	public Sp<Integer> getSolucionParcialPorAlternativa(Integer a, Sp<Integer> sp) {	
		return Sp.create(a, 
				sp.propiedad +
				(this.getTrabajadoresAfinesACliente(indexCliente).contains(a)?1:0));
	}

	@Override
	public List<Integer> getAlternativas() {		
		List<Integer> ret=IntStream.range(0, ProblemaAfinidad.trabajadores.size())
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
	public SolucionAfinidad getSolucionReconstruidaCasoBase(Sp<Integer> sp) {		
		return SolucionAfinidad.create(new HashMap<>(),0);
	}

	@Override
	public SolucionAfinidad getSolucionReconstruidaCasoRecursivo(Sp<Integer> sp, SolucionAfinidad s) {

		Map<String, String> msol = s.getAsignacion();
		msol.put(ProblemaAfinidad.clientes.get(indexCliente).nombre,
				ProblemaAfinidad.trabajadores.get(sp.alternativa));

		return SolucionAfinidad.create(msol, sp.propiedad.intValue());
	}
	
	
	@Override
	public Double getObjetivoEstimado(Integer a) {
		return (double)afinidadAcum+(this.getTrabajadoresAfinesACliente(indexCliente).contains(a)?1:0)
				+ (ProblemaAfinidad.clientes.size()-indexCliente-1);	
	}

	@Override
	public Double getObjetivo() {
		Double r = null;
		if (this.esCasoBase()) {
			r = (double) afinidadAcum;
		}
		return r;
	}	



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + afinidadAcum;
		result = prime * result + indexCliente;
		result = prime
				* result
				+ ((numClientesPorTrabajador == null) ? 0
						: numClientesPorTrabajador.hashCode());
		result = prime
				* result
				+ ((trabajadoresOcupadosEnFranja == null) ? 0
						: trabajadoresOcupadosEnFranja.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ProblemaAfinidadPD))
			return false;
		ProblemaAfinidadPD other = (ProblemaAfinidadPD) obj;
		if (afinidadAcum != other.afinidadAcum)
			return false;
		if (indexCliente != other.indexCliente)
			return false;
		if (numClientesPorTrabajador == null) {
			if (other.numClientesPorTrabajador != null)
				return false;
		} else if (!numClientesPorTrabajador
				.equals(other.numClientesPorTrabajador))
			return false;
		if (trabajadoresOcupadosEnFranja == null) {
			if (other.trabajadoresOcupadosEnFranja != null)
				return false;
		} else if (!trabajadoresOcupadosEnFranja
				.equals(other.trabajadoresOcupadosEnFranja))
			return false;
		return true;
	}

	//	@Override
//	public String toString() {
//		return "["+indexCliente + ", "+ numClientesPorTrabajador + ", " + afinidadAcum + ", "+ trabajadoresOcupadosEnFranja +"]";
//	}
	@Override
	public String toString() {
		return "["+indexCliente + ", "+ afinidadAcum +"]";
	}
}


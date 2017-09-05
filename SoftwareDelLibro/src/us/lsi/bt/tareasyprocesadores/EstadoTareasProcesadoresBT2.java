package us.lsi.bt.tareasyprocesadores;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.google.common.collect.Lists;

import us.lsi.bt.EstadoBT;
import us.lsi.common.Lists2;
import us.lsi.pd.tareasprocesadores.Tarea;

public class EstadoTareasProcesadoresBT2 implements EstadoBT<SolucionTareasProcesadores, Integer> {

	public static Integer numeroDeProcesadores;
	public static Integer numeroDeTareas;
	public static EstadoTareasProcesadoresBT2 inicial;
	
	public static EstadoTareasProcesadoresBT2 create(String fichero, Integer np) {
		Tarea.leeTareas(fichero);
		numeroDeProcesadores = np;
		numeroDeTareas = Tarea.tareas.size();
		inicial = new EstadoTareasProcesadoresBT2(fichero,np);
		return inicial;
	}
	
	private int index;
	private List<Double> cargaProcesadores;
	private Map<Integer,List<Tarea>> asignacion;
	private Double tiempoDelMasCargado;
	
	private EstadoTareasProcesadoresBT2(String fichero, Integer np){
		Tarea.leeTareas(fichero);
		numeroDeProcesadores = np;
		numeroDeTareas = Tarea.tareas.size();
		this.index = 0;		
		this.cargaProcesadores = Lists2.nCopias(np, 0.);
		this.asignacion = new HashMap<>();
		IntStream.range(0, np).boxed().forEach(p->this.asignacion.put(p, Lists.newArrayList()));
		this.tiempoDelMasCargado = this.getTiempoDelMasCargado();
	}

	@Override
	public Tipo getTipo() {
		return Tipo.Min;
	}

	@Override
	public int size() {
		return numeroDeTareas - index;
	}

	@Override
	public EstadoBT<SolucionTareasProcesadores, Integer> getEstadoInicial() {
		return inicial; 
	}
	
	private Double getTiempoDelMasCargado(){
		return this.cargaProcesadores.stream().max(Comparator.naturalOrder()).get();
	}
	
	@Override
	public boolean esCasoBase() {
		return index == numeroDeTareas;
	}
	
	@Override
	public SolucionTareasProcesadores getSolucion() {
		Map<Integer,List<Tarea>> asignacion = new HashMap<>();
		IntStream.range(0, numeroDeProcesadores).boxed()
			.forEach(x->asignacion.put(x, Lists.newArrayList(this.asignacion.get(x))));
		return SolucionTareasProcesadores.create(asignacion,tiempoDelMasCargado);
	}

	@Override
	public List<Integer> getAlternativas() {
		return IntStream.range(0,numeroDeProcesadores)
				.boxed()
				.sorted(Comparator.comparing(x->cargaProcesadores.get(x)))
				.collect(Collectors.toList());
	}
	
	@Override
	public Double getObjetivoEstimado(Integer a){
		List<Double> ls = Lists.newArrayList(cargaProcesadores);
		ls.set(a, ls.get(a)+Tarea.getDuracion(index));
		return ls.stream().max(Comparator.naturalOrder()).get();
	}

	@Override
	public EstadoBT<SolucionTareasProcesadores, Integer> avanza(Integer a) {
		cargaProcesadores.set(a, cargaProcesadores.get(a)+Tarea.getDuracion(index));
		asignacion.get(a).add(Tarea.getTarea(index));
		tiempoDelMasCargado = this.getTiempoDelMasCargado();
		this.index++;
		return this;
	}

	@Override
	public EstadoBT<SolucionTareasProcesadores, Integer> retrocede(Integer a) {
		this.index--;
		cargaProcesadores.set(a, cargaProcesadores.get(a)-Tarea.getDuracion(index));
		asignacion.get(a).remove(Tarea.getTarea(index));
		tiempoDelMasCargado = this.getTiempoDelMasCargado();	
		return this;
	}
	
	@Override
	public String toString() {
		return "(" + index
				+this.getTiempoDelMasCargado()+ "," + cargaProcesadores+","+","+asignacion
				+ ")";
	}

}

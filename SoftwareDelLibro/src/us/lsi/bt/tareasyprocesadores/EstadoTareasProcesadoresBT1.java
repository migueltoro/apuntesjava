package us.lsi.bt.tareasyprocesadores;

import java.util.Comparator;
import java.util.List;

import us.lsi.bt.EstadoBT;
import us.lsi.pd.tareasprocesadores.CargaDeProcesadores;
import us.lsi.pd.tareasprocesadores.Tarea;

import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class EstadoTareasProcesadoresBT1 implements EstadoBT<SolucionTareasProcesadores, Integer> {

	public static Integer numeroDeProcesadores;
	public static Integer numeroDeTareas;
	public static EstadoTareasProcesadoresBT1 inicial;
	
	public static EstadoTareasProcesadoresBT1 create(String fichero, Integer np) {
		Tarea.leeTareas(fichero);
		numeroDeProcesadores = np;
		numeroDeTareas = Tarea.tareas.size();
		CargaDeProcesadores cargaProcesadoresAcumulada = CargaDeProcesadores.create(np);
		inicial = new EstadoTareasProcesadoresBT1(0, cargaProcesadoresAcumulada);
		return inicial;
	}
	
	public static EstadoTareasProcesadoresBT1 create(int index, CargaDeProcesadores cargaProcesadoresAcumulada) {
		return new EstadoTareasProcesadoresBT1(index, cargaProcesadoresAcumulada);
	}
	
	private int index;
	private CargaDeProcesadores cargaProcesadoresAcumulada;
	
	private EstadoTareasProcesadoresBT1(String fichero, Integer np){
		Tarea.leeTareas(fichero);
		numeroDeProcesadores = np;
		numeroDeTareas = Tarea.tareas.size();
		this.index = 0;		
		this.cargaProcesadoresAcumulada = CargaDeProcesadores.create(np);
	}

	private EstadoTareasProcesadoresBT1(int index, CargaDeProcesadores cargaProcesadoresAcumulada) {
		super();
		this.index = index;
		this.cargaProcesadoresAcumulada = cargaProcesadoresAcumulada;
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
	
	@Override
	public boolean esCasoBase() {
		return index == numeroDeTareas;
	}
	
	@Override
	public SolucionTareasProcesadores getSolucion() {
		return SolucionTareasProcesadores.create(
				this.cargaProcesadoresAcumulada.getAsignacion(),
				this.cargaProcesadoresAcumulada.getTiempoDelMasCargado());
	}

	@Override
	public List<Integer> getAlternativas() {
		return IntStream.range(0,numeroDeProcesadores)
				.boxed()
				.sorted(Comparator.comparing(x->cargaProcesadoresAcumulada.getCargaProcesadores().get(x)))
				.collect(Collectors.toList());
	}
	
	@Override
	public Double getObjetivoEstimado(Integer a){
		CargaDeProcesadores carga = this.cargaProcesadoresAcumulada.addTareaAProcesador(a, index);
		return carga.getTiempoDelMasCargado();
	}

	@Override
	public EstadoBT<SolucionTareasProcesadores, Integer> avanza(Integer a) {
		CargaDeProcesadores carga = this.cargaProcesadoresAcumulada.addTareaAProcesador(a, index);
		return EstadoTareasProcesadoresBT1.create(index+1, carga);
	}

	@Override
	public EstadoBT<SolucionTareasProcesadores, Integer> retrocede(Integer a) {
		CargaDeProcesadores carga = this.cargaProcesadoresAcumulada.removeTareaAProcesador(a,index-1);
		return EstadoTareasProcesadoresBT1.create(index-1, carga);
	}
	
	@Override
	public String toString() {
		return "(" + index
				+ "," + cargaProcesadoresAcumulada
				+ ")";
	}

	
}

package us.lsi.concurrent.filosofos;

import us.lsi.concurrent.filosofos.Tenedor.Estado;

import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.Monitor;
import com.google.common.util.concurrent.Monitor.Guard;


public class Tenedores {
	
	private static int nt = Filosofos.numeroDeFilosofos;
	private static Tenedor[] tenedores;
	private static Monitor monitor[];
	private static Monitor.Guard[] disponibles;
	
	public static void init() {
		tenedores = new Tenedor[nt];
		monitor = new Monitor[nt];
		disponibles = new Monitor.Guard[nt];
		
		for(int i =0;i <nt; i++){
			tenedores[i] = new Tenedor(i);
		}
		
		for(int i =0;i <nt; i++){
			monitor[i] = new Monitor();
		}
		
		for(int i =0;i < nt; i++){
			disponibles[i] = newGuarda(i);
		}
	}

	private static Guard newGuarda(final int i) {
		return new Monitor.Guard(monitor[i]) {			
			@Override
			public boolean isSatisfied() {
				return tenedores[i].isDisponible() && tenedores[(i+1)%nt].isDisponible();
			}
		};
	}
	
	public static int getNumeroDeTenedores() {
		return nt;
	}
	
	public static Tenedor[] getTenedores() {
		return tenedores;
	}
	
	public static void obtenerTenedores(int i) {
		Preconditions.checkElementIndex(i, nt);
		monitor[i].enterWhenUninterruptibly(disponibles[i]);
		tenedores[i].setEstado(Estado.Ocupado);
		tenedores[(i+1)%nt].setEstado(Estado.Ocupado);	
	}
	
	public static void liberarTenedores(int i){
		Preconditions.checkElementIndex(i, nt);
		monitor[i].leave();
		tenedores[i].setEstado(Estado.Libre);
		tenedores[(i+1)%nt].setEstado(Estado.Libre);	
	}

	
}

package us.lsi.concurrent.filosofos;

import us.lsi.math.Math2;

public class Filosofo extends AbstractModelObject implements Runnable {
	
	public enum Estado {Pensando,Comiendo,Esperando,Fin};
	
	public static Long escala = 10L;
		
	private Estado estado;
	private boolean fin = false;
	
	int i;
	private Thread threat;

	public Filosofo(int i) {
		this.estado = Estado.Esperando;
		this.i = i;
	}

	@Override
	public void run() {	
		while(!fin){
			esperar();
			try {
				Tenedores.obtenerTenedores(i);
				comer();
			} finally {
				Tenedores.liberarTenedores(i);
			}
			pensar();		
		}	
		setEstado(Estado.Fin);
	}
	
	public void start(){
		this.threat = new Thread(this);
		this.threat.start();
	}
	public void stop(){
		fin = true;
	}
	public Estado getEstado() {
		return estado;
	}
	
	public void setEstado(Estado estado) {
		Estado oldEstado = this.estado;
		this.estado = estado;
		firePropertyChange("estado", oldEstado, estado);
		System.out.println("Filosofo  "+ i+","+getEstado());
	}
	
	public void esperar(){
		setEstado(Estado.Esperando);
	}
	
	public void comer(){
		setEstado(Estado.Comiendo);
		System.out.println("Comiendo  "+i);
		try {
			Thread.sleep(escala*Math2.getEnteroAleatorio(0, 1000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void pensar(){
		setEstado(Estado.Pensando);
		System.out.println("Pensando  "+i);
		try {
			Thread.sleep(escala*Math2.getEnteroAleatorio(0, 1000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}	
	
	
}

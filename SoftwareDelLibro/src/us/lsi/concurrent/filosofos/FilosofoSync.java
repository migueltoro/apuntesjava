package us.lsi.concurrent.filosofos;


import us.lsi.math.Math2;


public class FilosofoSync  implements Runnable {
	
	public enum Estado {Pensando,Comiendo,Esperando,Fin};
	
	public static Long escala = 10L;
		
	private Estado estado;
	private boolean fin = false;
	
	Tenedor t1; 
	Tenedor t2 ;
	
	int i;
	private Thread threat;

	public FilosofoSync(int i) {
		this.estado = Estado.Esperando;
		this.i = i;
		int i1 = i;
		int i2 = (i+1)% FilosofosSync.numeroDeFilosofos;
		if(i1 < i2){
			this.t1= TenedoresSync.getTenedor(i1);
			this.t2 = TenedoresSync.getTenedor(i2);
		} else{				
			this.t1= TenedoresSync.getTenedor(i2);
			this.t2=TenedoresSync.getTenedor(i1);
		}
	}

	@Override
	public void run() {	
		while(!fin){
			esperar();
			synchronized(t1){
				synchronized(t2){
					t1.setEstado(Tenedor.Estado.Ocupado);
					t2.setEstado(Tenedor.Estado.Ocupado);
					comer();
					t1.setEstado(Tenedor.Estado.Libre);
					t2.setEstado(Tenedor.Estado.Libre);
				}
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
		this.estado = estado;
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void pensar(){
		setEstado(Estado.Pensando);
		System.out.println("Pensando  "+i);
		try {
			Thread.sleep(escala*Math2.getEnteroAleatorio(0, 1000));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	
	
}

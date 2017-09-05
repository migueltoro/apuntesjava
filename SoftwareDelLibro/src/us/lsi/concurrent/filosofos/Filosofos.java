package us.lsi.concurrent.filosofos;

public class Filosofos {

	public static int numeroDeFilosofos = 6;
	private static Filosofo[] filosofos;
	
	public static void init() {
		filosofos = new Filosofo[numeroDeFilosofos];
		
		for(int i =0;i <numeroDeFilosofos; i++){
			filosofos[i] = new Filosofo(i);
		}		
	}

	public static void start() {
		for(int i =0;i <numeroDeFilosofos; i++){
			filosofos[i].start();
		}
	}
	
	public static void stop() {
		for(int i =0;i <numeroDeFilosofos; i++){
			filosofos[i].stop();
		}
	}
	
	public static Filosofo[] getFilosofos() {
		return filosofos;
	}
}

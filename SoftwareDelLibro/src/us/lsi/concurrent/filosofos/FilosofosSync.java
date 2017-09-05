package us.lsi.concurrent.filosofos;

public class FilosofosSync {
	
	public static int numeroDeFilosofos = 6;
	private static FilosofoSync[] filosofos;
	
	public static void init() {
		filosofos = new FilosofoSync[numeroDeFilosofos];
		
		for(int i =0;i <numeroDeFilosofos; i++){
			filosofos[i] = new FilosofoSync(i);
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
	
	public static FilosofoSync[] getFilosofos() {
		return filosofos;
	}
}

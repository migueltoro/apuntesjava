package us.lsi.concurrent.filosofos;


import com.google.common.base.Preconditions;


public class TenedoresSync {
	
	private static int nt = FilosofosSync.numeroDeFilosofos;
	private static Tenedor[] tenedores;
	
	public static void init() {
		tenedores = new Tenedor[nt];
		
		for(int i =0;i <nt; i++){
			tenedores[i] = new Tenedor(i);
		}
	}
	
	public static int getNumeroDeTenedores() {
		return nt;
	}
	
	public static Tenedor[] getTenedores() {
		return tenedores;
	}
	
	public static Tenedor getTenedor(int i) {
		Preconditions.checkElementIndex(i, nt);
		return tenedores[i];
	}

}

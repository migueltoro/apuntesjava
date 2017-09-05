package us.lsi.concurrent.filosofos;

public class TestFilosofosSync {

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TenedoresSync.init();
		FilosofosSync.init();	
		FilosofosSync.start();
	}

}

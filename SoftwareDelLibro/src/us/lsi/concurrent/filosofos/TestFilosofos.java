package us.lsi.concurrent.filosofos;

public class TestFilosofos {

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Filosofos.init();
		Tenedores.init();
		Filosofos.start();
	}

}

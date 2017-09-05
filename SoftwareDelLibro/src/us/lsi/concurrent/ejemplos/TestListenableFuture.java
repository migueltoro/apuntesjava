package us.lsi.concurrent.ejemplos;

import com.google.common.util.concurrent.*;
import java.util.concurrent.*;

import us.lsi.math.Math2;

public class TestListenableFuture {

	
	public static void main(String[] args) {        
		
		      
		ListeningExecutorService exec = MoreExecutors.listeningDecorator(Executors.newSingleThreadExecutor());        
		
		ListenableFuture<Integer> lf = exec.submit(new Callable<Integer>(){
			@Override
			public Integer call() throws Exception {				
				Integer n = Math2.getEnteroAleatorio(0, 1000);
				System.out.println("Entero = "+n); 
				Thread.sleep(1000); 
				if(n%2==0){
						throw new Exception();
				} else {
					return n;  
				}
			}}); 	
		
		lf.addListener(new Runnable(){
			@Override
			public void run() {                 
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} 
				System.out.println("Ha estado haciendo otra tarea"); 
			}}, exec);   		
		
		Futures.<Integer>addCallback(lf, new FutureCallback<Integer>(){
			@Override
			public void onFailure(Throwable e) {
				System.out.println("Se ha producido una excepción"); 
			}
			@Override
			public void onSuccess(Integer e) {
				Long e2 = (long) e*e;
				System.out.println("El cuadrado es = "+e2); 
			}});
		
		
		try {
			exec.awaitTermination(5,TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		exec.shutdown();
	}
}

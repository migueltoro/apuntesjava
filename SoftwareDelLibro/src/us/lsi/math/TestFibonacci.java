package us.lsi.math;
import java.math.*;

public class TestFibonacci {

	
	public static void main(String[] args) {
		for(int i =0; i<20;i++){
				BigInteger m = Math2.fibonacci4(i);
				System.out.println(i+","+m);
		}
	
	}
}

package us.lsi.ag.expression;

public class GenerateTest {

	public static void main(String[] args) {
	
		for(int i= 0;i<1000;i+=50){
			System.out.print(Math.sqrt(i)+4*i+7+",");
//			System.out.print(i*1.+",");
		}
	}

}

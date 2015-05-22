package us.lsi.math;

import org.apache.commons.math3.fraction.Fraction;
import org.apache.commons.math3.linear.*;




public class TestMatrices {

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Fraction[][] r = {{new Fraction(1,2),new Fraction(1,3),new Fraction(1,8), new Fraction(1,8)},
						  {new Fraction(-1,7),new Fraction(7,1),new Fraction(6,-1), new Fraction(1,8)},
						  {new Fraction(1,2),new Fraction(1,5),new Fraction(1,7), new Fraction(1,8)},
						  {new Fraction(1,2),new Fraction(1,5),new Fraction(1,7), new Fraction(1,8)}
						 };
		FieldMatrix<Fraction> m =  new Array2DRowFieldMatrix<Fraction>(r);
		System.out.println(m);
		System.out.println(m.multiply(m));
		System.out.println(m.multiply(m).getRow(2));
		System.out.println(m.multiply(m));
		//Matriz.TAMSTRING = 20;
		Fraction r2 = m.multiply(m).getTrace();
		System.out.println(Fraction.getReducedFraction(r2.getNumerator(),r2.getDenominator()));
	
	}
}

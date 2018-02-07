package us.lsi.iterativo;

import org.apache.commons.math3.Field;
import org.apache.commons.math3.FieldElement;
import org.apache.commons.math3.fraction.BigFraction;
import org.apache.commons.math3.fraction.BigFractionField;
import org.apache.commons.math3.linear.Array2DRowFieldMatrix;
import org.apache.commons.math3.linear.FieldMatrix;

public class Matrices {

	/**
	 * @param v Un array
	 * @return Un vector columna con los datos del array
	 */
	public static FieldMatrix<BigFraction> getColumn(Integer[] v){
		BigFraction[] r = new BigFraction[v.length];
		for(int i = 0;i< v.length;i++){
			r[i] = new BigFraction(v[i]);
		}
		return  new Array2DRowFieldMatrix<>(BigFractionField.getInstance(),r);
	}

	/**
	 * @param k La dimensión
	 * @param f El campo de los elementos de la matriz
	 * @param <T> El tipo de los elmentos de la matriz
	 * @return La matriz unidad de esa dimensión
	 */
	public static  <T extends FieldElement<T>> FieldMatrix<T> getId(int k, Field<T> f){
		FieldMatrix<T> r = new Array2DRowFieldMatrix<>(f,k,k);
	
		for (int i = 0; i < k; i++) {
			for (int j = 0; j < k; j++) {
				if (i == j) {
					r.setEntry(i,j,f.getOne());
				} else {
					r.setEntry(i,j,f.getZero());
				}
			}
		}
		return  r;
	}

	/**
	 * @param data Los datos de la matriz por filas
	 * @return La matriz con esos datos
	 */
	public static FieldMatrix<BigFraction> getMatriz(BigFraction[][] data){
		return new Array2DRowFieldMatrix<>(data);
	}
	
	/**
	 * @param k Dimensión
	 * @param cf Coeficientes de la regresión
	 * @return La base de la potencia que nos dará la solución de la regresión
	 */
	public static FieldMatrix<BigFraction> getBase(int k, Integer[] cf){
		FieldMatrix<BigFraction> r = new Array2DRowFieldMatrix<>(BigFractionField.getInstance(),k,k);	
		for (int i = 0; i < k; i++) {
			for (int j = 0; i < k; i++) {
					r.setEntry(i,j,BigFraction.ZERO);
			}
		}
		for (int j = 0; j < k; j++) {
			r.setEntry(0,j,new BigFraction(cf[j]));
		}
		for (int i = 1; i <k; i++) {
			int j = i-1;
			r.setEntry(i,j,BigFraction.ONE);
		}
		return  r;
	}

	/**
	 * @param m Una matriz
	 * @return La representación como cadena. Las filas se indexan con i y las columnas con j. Los índices de las filas aumentan hacia abajo. La
	 * casilla superior izquierda tiene índices (0,0) y la inferior derecha (k-1,k-1) siendo k la dimensión.
	 */
	public static String show(FieldMatrix<BigFraction> m){
		String r = "";
		for (int i = 0; i< m.getRowDimension(); i++) {
			for (int j = 0; j < m.getColumnDimension(); j++) {
					if(j!=0) r = r +",";
					r = r + m.getEntry(i,j);
			}
			r = r + "\n";
		}
		return r;		
	}
	
	public static void main(String[] args) {
		FieldMatrix<BigFraction> e = Matrices.getId(3,BigFractionField.getInstance());
		System.out.println(Matrices.show(e));
		Integer[] cf = {1,2,3};
		e = Matrices.getBase(3,cf);
		System.out.println(Matrices.show(e));
		e = Matrices.getColumn(cf);
		System.out.println(Matrices.show(e));
		BigFraction[][] id = {{BigFraction.ONE,BigFraction.ZERO},{BigFraction.ZERO,BigFraction.ONE}};
		System.out.println(Matrices.show(getMatriz(id)));
		System.out.println(Matrices.show(getMatriz(id).power(5)));
		BigFraction[][] b = {{BigFraction.ONE,BigFraction.ONE},{BigFraction.ONE,BigFraction.ZERO}};
		System.out.println(Matrices.show(getMatriz(b)));
		System.out.println(Matrices.show(getMatriz(b).power(10)));
	}

}

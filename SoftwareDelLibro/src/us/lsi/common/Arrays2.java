package us.lsi.common;

public class Arrays2 {
	
		public  static double[] getArrayDouble(int n, double v){
			double[] r = new double[n];
			for(int i=0;i<r.length;i++){
				r[i]= v;
			}
			return r;
		}

}

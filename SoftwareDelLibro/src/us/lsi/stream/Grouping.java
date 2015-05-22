package us.lsi.stream;

public interface Grouping {
	
	public static String nIndex(int n){
		StringBuffer s = new StringBuffer();
		for(int i =0; i<n;i++){
			s.append("     ");
		}
		return s.toString();
	}
	
	
}

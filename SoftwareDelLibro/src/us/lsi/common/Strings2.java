package us.lsi.common;

import com.google.common.base.Strings;

public class Strings2 {

	public static String nBlancos(int n){
		return Strings.padStart("", 2*n, ' ');
	}

	
}

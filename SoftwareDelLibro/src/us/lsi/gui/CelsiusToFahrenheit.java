package us.lsi.gui;

public class CelsiusToFahrenheit {
	
	public static int convert(int celsius){
		 int tempFahr = (int)(celsius* 1.8 + 32);
		 return tempFahr;
	}
}

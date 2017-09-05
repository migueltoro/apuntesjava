package us.lsi.basictypes;


public class TokenizeString {

	public static TokenizeString create(String s) {
		return new TokenizeString(s);
	}

	public static String spaces = " \t\n";
	private int index;
	private final String s;
	
	private TokenizeString(String s) {
		super();
		this.s = s;
		this.index = 0;
	}
	
	
	
	public Character getChar() {
		return this.s.charAt(this.index);
	}

	public int getIndex() {
		return this.index;
	}

	public String getS() {
		return s;
	}

	public Character nextCharIgnoringSpaces(){
		return nextCharIgnoring(spaces);
	}

	public Character nextCharIgnoring(String cr){
		Character c = null;
		while(this.index < this.s.length()) {
			c = this.s.charAt(this.index);
			if(cr.indexOf(c)<0) break; 
			this.index++;
		}
		return c;
	}
	
	public void saltarSpaces(){
		saltarCaracteresEn(spaces);
	}
	
	public void saltarCaracteresEn(String cr) {
		Character c;
		while(this.index < this.s.length()) {
			c = this.s.charAt(this.index);
			if(cr.indexOf(c)<0) break; 
			this.index++;
		}
	}

	public Character matchCaracter(Character cr) {
		Character c = null;
		if (this.index < s.length()) {
			c = s.charAt(this.index);
			if (!c.equals(cr))
				throw new RuntimeException("Se espera un " + cr
						+ " en posición " + this.index + " en vez de " + c);
			this.index++;
		} else {
			throw new RuntimeException("Indice fuera de rango = " + this.index + ">=" + this.s.length());
		}
		return c;
	}
	
	public String copiarHasta(Character cr) {
		StringBuilder r = new StringBuilder();
		Character c = this.s.charAt(this.index);
		while(!c.equals(cr)) {
			r.append(c);
			this.index++;
			c = s.charAt(this.index);
		}
		this.index++;
		return r.toString();
	}

	@Override
	public String toString() {
		return "[index=" + index + ", s=" + s + "]";
	}
	
	
}

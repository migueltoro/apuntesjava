package us.lsi.basictypes;

public class TestTokenize {

	public static final String s = "([3,llenar J1]!([0,vaciar J1]!([3,llenar J1]!([2,echar J1 en J2]!([4,llenar J2]!([3,llenar J1]!([7,echar J2 en J1]!([6,volcar J2 en J1]!([4,llenar J2]!([1,volcar J1 en J2]!([4,llenar J2]!( ))))))))))))";
	
	

	public static void main(String[] args) {
		TokenizeString ts = TokenizeString.create(s);
		ts.saltarCaracteresEn(" ");
		System.out.println(ts);
		Character c = ts.nextCharIgnoring(" ");
		System.out.println(c);
		System.out.println(ts);
		ts.matchCaracter('(');
		System.out.println(c);
		System.out.println(ts);
		String r = ts.copiarHasta('!');
		System.out.println(r+","+ts.getChar());
		System.out.println(ts);
	}

}

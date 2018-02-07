package us.lsi.regularexpressions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import us.lsi.common.Preconditions;
import us.lsi.common.Sets2;
import us.lsi.common.Tuple3;

public class Tokenizer {

	public enum TokenType{Integer,Double,VariableIdentifier,FunctionIdentifier,ReservedWord,Operator,Separator};
	public static Set<String> separators = Sets2.newSet("{", "}", ",", ";", "(", ")");
	private static String space = "\\s+";
	private static String number = "[0-9]+(\\.[0-9]*)?";
	private static String identifier = "@?[a-zA-Z][a-zA-Z0-9]*";
	private static String operatorOrSeparator =  
			"\\*=?|\\+=?|\\-=?|/=?|!=?|(==?)|(<=?)|(>=?)|\\?\\:?|[\\^\\(\\)\\{\\}\\,\\;]";
	private static Pattern pSpace = Pattern.compile(space);
	private static Pattern pNumber = Pattern.compile(number);
	private static Pattern pIdentifier = Pattern.compile(identifier);
	private static Pattern pOperatorOrSeparator = Pattern.compile(operatorOrSeparator);
	
	
	private String text;	
	private List<Tuple3<String,TokenType,Integer>> tokens;
	private int index;
	private Set<String> functions;
	private Set<String> reservedWords;
	private int end;
	private int start;
	
	public static Tokenizer create(String text, Set<String> functions, Set<String> reservedWords) {
		return new Tokenizer(text,functions,reservedWords);
	}
	
	public static Tokenizer create(String text) {
		return new Tokenizer(text,null,null);
	}
	
	private Tokenizer(String text,Set<String> functions, Set<String> reservedWords) {
		super();
		this.text = text;
		if (functions != null) this.functions = functions;
		else this.functions = Sets2.newSet();
		if (reservedWords != null) this.reservedWords = reservedWords;
		else this.reservedWords = Sets2.newSet();
		this.index=0;
		this.start = 0;
		this.end = this.text.length();
		this.tokens = tokens();
	}

	public String getText() {
		return text;
	}
	
	public String getSufix(){
		return text.substring(tokens.get(index).v3);
	}
	
	public String getToken() {
		return tokens.get(index).v1;
	}

	public TokenType getTokenType() {
		return tokens.get(index).v2;
	}
	
	public Integer getPosition(){
		return tokens.get(index).v3;
	}

	public Set<String> getFunctions() {
		return functions;
	}

	public Set<String> getReservedWords() {
		return reservedWords;
	}
	
	public List<Tuple3<String, TokenType, Integer>> getTokens() {
		return tokens;
	}

	public String nextToken(){	
		String r = getToken();
		index = index+1;
		return 	r;	
	}
	
	public boolean hasMoreTokens() {
		return index < tokens.size();
	}
	
	public String previousToken(){
		Preconditions.checkState(index>0, "No existe estado previo");
		index = index-1;
		return getToken();	
	}
	
	private List<Tuple3<String,TokenType,Integer>> tokens(){
		List<Tuple3<String,TokenType,Integer>> r = new ArrayList<>();
		while(hasMore()){
			r.add(next());
		}
		return r;
	}
	
	private Tuple3<String,TokenType,Integer> next() {
		Matcher matcher = null;		
		String token = null;
		TokenType tokenType = null;
		int inc = 0;
		Character c = text.charAt(start);
		boolean space = false;
		boolean match = false;
		if (Character.isSpaceChar(c)) {
			matcher = pSpace.matcher(text.subSequence(start, end));
			match = matcher.find();
			inc = matcher.end();
			space = true;
		} else if (Character.isDigit(c)) {
			matcher = pNumber.matcher(text.subSequence(start, end));
			match = matcher.find();
			inc = matcher.end();
			token= text.subSequence(start,start+inc).toString();
			if (token.contains(".")) {
				tokenType = TokenType.Double;
			}else {
				tokenType = TokenType.Integer;
			}
		} else if (Character.isLetter(c) || c.equals('@')) {
			matcher = pIdentifier.matcher(text.subSequence(start, end));
			match = matcher.find();
			inc = matcher.end();
			token= text.subSequence(start,start+inc).toString();
			if (reservedWords.contains(token)) {
				tokenType = TokenType.ReservedWord;
			}else if(functions.contains(token)){
				tokenType = TokenType.FunctionIdentifier;
			}else {
				tokenType = TokenType.VariableIdentifier;
			}
		} else {
			matcher = pOperatorOrSeparator.matcher(text.subSequence(start, end));
			match = matcher.find();
			if (match) {
				inc = matcher.end();
				token= text.subSequence(start,start+inc).toString();
				if (separators.contains(token)) {
					tokenType = TokenType.Separator;
				}else{
					tokenType = TokenType.Operator;
				}
			} else {
				Preconditions.checkState(false, String.format("Caracter %c no reconocido en la posición %d",c,start));
			}			
		}		
		int oldStart = start;
		start = start + inc;
		if(space && hasMore()){
			return next();
		} else {
			return Tuple3.create(token,tokenType,oldStart);
		}
	}

	private boolean hasMore() {
		return start < end;
	}

	public TokenType seeNextTokenType() {
		TokenType tp = null;
		if (hasMoreTokens()) {
			tp = tokens.get(index).v2;
		} else {
			Preconditions.checkState(false, "Cadena acabada inesperadamente");
		}
		return tp;
	}
	
	public String seeNextToken() {
		String s = null;
		if (hasMoreTokens()) {
			s = tokens.get(index).v1;
		} else {
			Preconditions.checkState(false, "Cadena acabada inesperadamente");
		}
		return s;
	}
	
	public String matchTokenTypes(TokenType... s){
		List<TokenType> sl = Arrays.asList(s);
		String r = null;
		if(hasMoreTokens()){
			r = getToken();
			Preconditions.checkState(sl.contains(getTokenType()), 
					String.format("Se esperaba %s y se ha encontrado %s en la posición \n   %s",
							sl.toString(),getToken(),text.substring(tokens.get(index).v3)));
			nextToken();
		}else {
			Preconditions.checkState(false, "Cadena acabada inesperadamente");
		}
		return r;
	}

	public String matchTokens(String... s){
		List<String> sl = Arrays.asList(s);
		String r = null;
		if(hasMoreTokens()){
			r = getToken();
			Preconditions.checkState(sl.contains(getToken()), 
					String.format("Se esperaba %s y se ha encontrado %s en la posición \n  %s",
							sl.toString(),getToken(),text.substring(tokens.get(index).v3)));
			nextToken();
		}else {
			Preconditions.checkState(false, "Cadena acabada inesperadamente");
		}
		return r;
	}

	public boolean isTokenType(TokenType... s){
		List<TokenType> r = Arrays.asList(s);
		return r.contains(getTokenType());
	}
	
	public boolean isToken(String... s){
		List<String> r = Arrays.asList(s);
		return r.contains(getToken());
	}
	
	public static void main(String[] args) {
		String ex = "while{d==!=(int)23.4   *y+5+((double)4*x+2.)^3  -sqrt(45.6+2.*@a23),;+45.6/(45<=z*7}";
		System.out.println(Tokenizer.operatorOrSeparator);
		Set<String> functions = Sets2.newSet("sqrt");
		Set<String> reservedWords = Sets2.newSet("while");
		Tokenizer t = Tokenizer.create(ex,functions,reservedWords);
		String s = t.tokens.stream().map(x->x.toString()).collect(Collectors.joining("\n"));
		System.out.println(s);
		System.out.println(t.tokens.size());
	}

	
	
}

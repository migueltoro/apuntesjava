package us.lsi.tiposrecursivos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import us.lsi.common.Lists2;
import us.lsi.common.Maps2;
import us.lsi.common.Preconditions;
import us.lsi.regularexpressions.Tokenizer;
import us.lsi.regularexpressions.Tokenizer.TokenType;
import us.lsi.tiposrecursivos.Exp.ExpType;

public class ExpParser {

	public static <R> Exp<R> scan(String s) {
		Tokenizer tk = Tokenizer.create(s, Operator.functions, Operator.reservedWords);
		Map<String, Exp<?>> vars = new HashMap<>();
		@SuppressWarnings("unchecked")
		Exp<R> r = (Exp<R>) ExpParser.scanExp(tk, vars);
		return r;
	}
	
	@SuppressWarnings("unchecked")
	public static <R> Exp<R> scan(String s, Map<String, Exp<?>> vars) {
		Tokenizer tk = Tokenizer.create(s, Operator.functions, Operator.reservedWords);
		Map<String, Exp<?>> nVars = new HashMap<>(vars);
	    vars.values().stream().map(e->e.getVars()).forEach(m->nVars.putAll(m));		
	    Exp<R> r = (Exp<R>) ExpParser.scanExp(tk, nVars);
		return r;
	}
	
	protected static Exp<?> scanExp(Tokenizer tk, Map<String,Exp<?>> vars){
		Exp<?> r = scanParticleMultiply(tk,vars);
		String s;
		while (tk.hasMoreTokens() && 
				tk.seeNextTokenType().equals(TokenType.Operator) &&
				Operator.arities.get(tk.seeNextToken()) == 2) {
			s = tk.matchTokenTypes(TokenType.Operator);
			Exp<?> exp = ExpParser.scanParticleMultiply(tk, vars);
			r = Operator.getBinary(s,r.getExpType(),exp.getExpType()).exp(r,exp);
		}
		return r;
	}
	
	protected static Exp<?> scanParticleMultiply(Tokenizer tk, Map<String,Exp<?>> vars){
		Exp<?> r = scanParticlePot(tk,vars);
		String s;
		while (tk.hasMoreTokens() && 
				tk.seeNextTokenType().equals(TokenType.Operator) &&
				Operator.arities.get(tk.seeNextToken()) == 4) {
			s = tk.matchTokenTypes(TokenType.Operator);
			Exp<?> exp = ExpParser.scanParticlePot(tk, vars);
			r = Operator.getBinary(s,r.getExpType(),exp.getExpType()).exp(r,exp);
		}
		return r;
	}
	
	protected static Exp<?> scanParticlePot(Tokenizer tk, Map<String,Exp<?>> vars){
		Exp<?> r = scanParticleBasic(tk,vars);
		String s;
		if (tk.hasMoreTokens() && 
				tk.seeNextTokenType().equals(TokenType.Operator) &&
				Operator.arities.get(tk.seeNextToken()) == 6) {
			s = tk.matchTokenTypes(TokenType.Operator);
			Exp<?> exp = ExpParser.scanParticleBasic(tk, vars);
			r = Operator.getBinary(s,r.getExpType(),exp.getExpType()).exp(r,exp);
		}
		return r;
	}
	protected static Exp<?> scanParticleBasic(Tokenizer tk, Map<String,Exp<?>> vars){
		Exp<?> r = null;
		String s;
		TokenType tt = tk.seeNextTokenType();
		switch (tt) {	
		case Integer:
			r = ExpParser.scanConstantInteger(tk);
			break;
		case Double:
			r = ExpParser.scanConstantDouble(tk);
			break; 
		case VariableIdentifier:
			if (tk.seeNextToken().charAt(0) == ('@')) {
				r = ExpParser.scanPlaceHolder(tk, vars);
			} else {
				r = ExpParser.scanVariable(tk, vars);
			}
			break;
		case Operator:
		case FunctionIdentifier:
			s = tk.matchTokenTypes(TokenType.Operator,TokenType.FunctionIdentifier);			
			tk.matchTokens("(");	
			r = scanParameters(s,tk,vars);
			tk.matchTokens(")");
			break;
		case Separator:	
			tk.matchTokens("(");
			if(Lists2.newList("int","double").contains(tk.seeNextToken())){
				r = scanCastType(tk, vars);
			} else {				
				r = scanExp(tk, vars);
				tk.matchTokens(")");
			}			
			break;
		default:
			Preconditions.checkState(false, 
					String.format("No se esperaba %s en la posición \n   %s",
							tk.getToken(),tk.getSufix()));			
		}			
		return r;
	}

	private static Exp<?> scanCastType(Tokenizer tk, Map<String, Exp<?>> vars){
		String  op = tk.matchTokens("int","double");
		tk.matchTokens(")");
		Exp<?> exp = scanExp(tk, vars);
		String name = "("+op+")";
		return Operator.getUnary(name, exp.getExpType()).exp(exp);
	}
	
	private static Exp<?> scanParameters(String name, Tokenizer tk,
			Map<String, Exp<?>> vars) {
		List<Exp<?>> exps = new ArrayList<>();
		Exp<?> exp = ExpParser.scanExp(tk, vars);
		exps.add(exp);
		while (tk.seeNextToken().equals(",")) {
			tk.matchTokens(",");
			exp = ExpParser.scanExp(tk, vars);
			exps.add(exp);
		}
		Exp<?> r = null;
		int arity = exps.size();
		boolean sameType = IntStream.range(0, exps.size() - 1).allMatch(
				i -> exps.get(i).getExpType()
						.equals(exps.get(i + 1).getExpType()));
		if (sameType) {
			switch (arity) {
			case 1:r = Operator.getUnary(name, exp.getExpType()).exp(exp);break;
			case 2:r = Operator.getBinary(name, exps.get(0).getExpType(),
						exps.get(1).getExpType()).exp(exps.get(0), exps.get(1));break;
			case 3:r = Operator.getTernary(name, exps.get(0).getExpType(),
						exps.get(1).getExpType(), exps.get(2).getExpType())
						.exp(exps.get(0), exps.get(1), exps.get(2));break;
			default:
				r = Operator.getAccumulator(name, exp.getExpType()).exp(exps);
			}
		} else {
			switch (arity) {
			case 1:
				r = Operator.getUnary(name, exp.getExpType()).exp(exp);
				break;
			case 2:
				r = Operator.getBinary(name, exps.get(0).getExpType(),
						exps.get(1).getExpType()).exp(exps.get(0), exps.get(1));
				break;
			case 3:
				r = Operator.getTernary(name, exps.get(0).getExpType(),
						exps.get(1).getExpType(), exps.get(2).getExpType())
						.exp(exps.get(0), exps.get(1), exps.get(2));
				break;
			default:
				Preconditions.checkState(false, String.format(
						"No hay disponibles operadores de aridad %d", arity));
			}
		}
		return r;

	}
	
	public static VariableExp<?> scanVariable(Tokenizer tk, Map<String,Exp<?>> vars){		
		String s = tk.matchTokenTypes(TokenType.VariableIdentifier);
		VariableExp<?> r=null;	
		if(vars.containsKey(s)){
	        r = (VariableExp<?>) vars.get(s);
		} else {
			ExpType expType = Operator.getVariableType(s);
			switch(expType){
			case Boolean: r = Exp.<Boolean>variable(s, ExpType.Boolean);break;
			case Integer: r = Exp.<Integer>variable(s, ExpType.Integer);break;
			case Double:  r = Exp.<Double>variable(s, ExpType.Double);				
			}
			vars.put(s, r);
		}
		return r;
	}

	public static Exp<?> scanPlaceHolder(Tokenizer tk, Map<String,Exp<?>> vars){						
		String s = tk.matchTokenTypes(TokenType.VariableIdentifier);
		Exp<?> r=null;	
		if(vars.containsKey(s)){
	        r = vars.get(s);
		} else {
			ExpType expType = Operator.getVariableType(s.substring(1));
			switch(expType){
			case Boolean: r = Exp.<Boolean>placeHolder(s, ExpType.Boolean);break;
			case Integer: r = Exp.<Integer>placeHolder(s, ExpType.Integer);break;
			case Double:  r = Exp.<Double>placeHolder(s, ExpType.Double);				
			}
			vars.put(s, r);
		}
		return r;
	}

	public static ConstantExp<Double> scanConstantDouble(Tokenizer tk){		
		String s = tk.matchTokenTypes(TokenType.Double);
		return Exp.constant(new Double(s),ExpType.Double);
	}

	public static ConstantExp<Integer> scanConstantInteger(Tokenizer tk){		
		String s = tk.matchTokenTypes(TokenType.Integer);
		return Exp.constant(new Integer(s),ExpType.Integer);  
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		String s1 = "((sqrt((x*3.2))^3)+(x+y+sqrt((x*3.2))+(sqrt((x*3.2))^3)+(x+y)))";
		String s2 = "(x+y+sqrt((x*3.2))+(sqrt((x*3.2))^3)+(x+y))";		
		String s3 = "((((x+3.2)*y)+x)*sqrt((3.2^3)))";
		String s4 = "((((x+3.2)*y)+x)*5.7243340223994625)";
		String s5 = "((sqrt((x*3.2))^3)+(v+y+sqrt((@z*3.2))+(sqrt((x*3.2))^3)+(x+y)))";
		String s6 = "iff(x==y,x+3.2*y+x*sqrt(3.2^3),sqrt(x*3.2)^3+x+y+sqrt(x*3.2)+sqrt(x*3.2)^3+x+y)";
		String s8 = "iff(x==y,x+3.2*y+x*sqrt(3.2^3),sqrt(x*3.2)^3+ +(x,y,sqrt(x*3.2),sqrt(x*3.2)^3,x+y))";
		String s9 = "iff(x==y,(int)(((x+3.2)*y+x)*sqrt(3.2^3)),(int)(sqrt(x*3.2)^3+ min(x,y,sqrt(x*3.2),sqrt(x*3.2)^3,x+y)))";
		String s10 = "(int)(x^3+5.6)+9";
		System.out.println(Operator.operators);
		Exp<Double> ex1 = ExpParser.scan(s1);
		System.out.println("ex1 ="+ex1+","+ex1.getVars());
		Map<String,Exp<?>> vars = Maps2.newHashMap("@z", ex1);
		System.out.println(vars);
		Exp<Double> exp = ExpParser.scan(s5,vars);
		System.out.println("exp ="+exp+","+exp.getVars());
		
		exp.get("x").<Double>asVariable().setValue(4.5);
		exp.get("y").<Double>asVariable().setValue(45.7);
		exp.get("v").<Double>asVariable().setValue(5.7);
		System.out.println(exp.simplify());
		
		System.out.println(exp.val());
		
	}
	
}

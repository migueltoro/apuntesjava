package us.lsi.tiposrecursivos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import us.lsi.common.Preconditions;
import us.lsi.regularexpressions.Tokenizer;
import us.lsi.regularexpressions.Tokenizer.TokenType;

public class SentenceParser {

	public static Sentence scan(String s) {
		System.out.println(Operator.functions);
		System.out.println(Operator.reservedWords);
		Tokenizer tk = Tokenizer.create(s, Operator.functions,
				Operator.reservedWords);
		Map<String, Exp<?>> vars = new HashMap<>();
		Sentence r = scanSentence(tk, vars);
		return r;
	}

	private static Sentence scanSentence(Tokenizer tk, Map<String, Exp<?>> vars) {
		Sentence r = null;
		switch (tk.seeNextTokenType()) {
		case VariableIdentifier:
			VariableExp<?> var;
			String s1 = tk.seeNextToken();
			Preconditions.checkState(s1.charAt(0) != ('@'), s1
					+ " debe ser una variable");
			var = ExpParser.scanVariable(tk, vars);
			tk.matchTokens("=");
			Exp<?> exp = ExpParser.scanExp(tk, vars);
			tk.matchTokens(";");
			Preconditions.checkState(var.getExpType().equals(exp.getExpType()),
					"Typos no iguales en la asignación");
			r = Sentence.assign(var, exp);
			break;
		case ReservedWord:
			String s2 = tk.seeNextToken();
			switch (s2) {
			case "if":
				tk.matchTokens("if");
				tk.matchTokens("(");
				@SuppressWarnings("unchecked")
				Exp<Boolean> condition1 = (Exp<Boolean>) ExpParser.scanExp(tk,
						vars);
				tk.matchTokens(")");
				Sentence consequent = scanSentence(tk, vars);
				tk.matchTokens("else");
				Sentence alternative = scanSentence(tk, vars);
				r = Sentence.ifThenElse(condition1, consequent, alternative);
				break;
			case "while":
				tk.matchTokens("while");
				tk.matchTokens("(");
				@SuppressWarnings("unchecked")
				Exp<Boolean> condition2 = (Exp<Boolean>) ExpParser.scanExp(tk,vars);
				tk.matchTokens(")");
				Sentence block = scanSentence(tk, vars);
				r = Sentence.whileSentence(condition2, block);
				break;
			default:
				tk.matchTokens("if", "while");
			}
			break;
		case Separator:
			tk.matchTokens("{");
			r = scanSentences(tk, vars);
			tk.matchTokens("}");
			break;
		default:
			tk.matchTokenTypes(TokenType.VariableIdentifier,
					TokenType.Separator, TokenType.VariableIdentifier);
		}
		return r;
	}

	private static Sentence scanSentences(Tokenizer tk, Map<String, Exp<?>> vars) {
		List<Sentence> r = new ArrayList<>();
		while (!tk.seeNextToken().equals("}")) {
			Sentence s = scanSentence(tk, vars);
			r.add(s);
		}
		return Sentence.sentences(r);
	}

	public static void main(String[] args) {
		String s1 = "{x=0.;y=3.;while(x+y==5.){x=y+2.;if(x==0.){x = 2.+3.;}else{y = x^2;}}}";
		Sentence s = SentenceParser.scan(s1);
		s.toDOT("Program.gv", "Program");
		System.out.println(s);
	}
}

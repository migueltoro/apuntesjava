package us.lsi.tiposrecursivos;

import java.util.List;

public abstract class Sentence extends Element {

	public static <E> Assignment<E> assignment(VariableExp<E> var, Exp<E> exp) {
		return new Assignment<>(var, exp);
	}

	@SuppressWarnings("unchecked")
	public static <E> Assignment<E> assign(Object var, Object exp){
		VariableExp<E> var1 = (VariableExp<E>) var;
		Exp<E> exp1 = (Exp<E>) exp;	
		return Sentence.assignment(var1, exp1);
	}
	
	public static IfThenElse ifThenElse(Exp<Boolean> condition,
			Sentence consequent, Sentence alternative) {
		return new IfThenElse(condition, consequent, alternative);
	}

	public static Sentences sentences(List<Sentence> sentences) {
		return new Sentences(sentences);
	}

	public static While whileSentence(Exp<Boolean> condition, Sentence block) {
		return new While(condition, block);
	}

	/**
	 * @return El nombre del operador de la expresión
	 */
	public abstract String getName(); 
	public abstract void execute();
	
	/**
	 * @return Copia profunda de la expresión
	 */
	public abstract Sentence copy();

	/**
	 * @return Igualdad
	 */
	public abstract boolean equals(Object other);
	/**
	 * @return Igualdad
	 */
	public abstract int hashCode();
	
	
	protected abstract String getId();
	
	
	protected abstract void toDOT(String file);

	
}

package us.lsi.common;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;


import java.util.function.Function;
import java.util.function.UnaryOperator;

import us.lsi.common.TernaryExp.TriFunction;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;


/**
 * <p> Tipo que modela una expresión
 * 
 * @author Miguel Toro
 *
 * @param <R> Tipo del resultado de la expresión
 */
public abstract class Exp<R> {	
	
	public static <R> VariableExp<R> createVariable(R value, String name) {
		return new VariableExp<R>(value, name);
	}
	public static <R> VariableExp<R> createVariable(String name) {
		return new VariableExp<R>(name);
	}
	public static <R, T1, T2> BinaryExp<R, T1, T2> createBinary(Exp<T1> left, Exp<T2> right, BiFunction<T1, T2, R> operator, String symbol) {
		return new BinaryExp<R, T1, T2>(left, right, operator, symbol);
	}
	public static <R, T1, T2> BinaryExp<R, T1, T2> createBinary(BiFunction<T1, T2, R> operator, String symbol) {
		return new BinaryExp<R, T1, T2>(operator, symbol);
	}
	public static <R> ConstantExp<R> createConstant(R value) {
		return new ConstantExp<R>(value);
	}
	public static <R, T> UnaryExp<R, T> createUnary(Exp<T> op, Function<T, R> operator, String symbol,UnaryExp.Tipo tipo) {
		return new UnaryExp<R, T>(op, operator, symbol,tipo);
	}
	public static <R, T> UnaryExp<R, T> createUnary(Function<T, R> operator, String symbol, UnaryExp.Tipo tipo) {
		return new UnaryExp<R, T>(operator, symbol,tipo);
	}	
	public static <R, T1, T2, T3> TernaryExp<R, T1, T2, T3> createTernary(Exp<T1> op1, Exp<T2> op2, Exp<T3> op3, TriFunction<T1, T2, T3, R> operator, String symbol) {
		return new TernaryExp<R, T1, T2, T3>(op1, op2, op3, operator, symbol);
	}
	public static <R, T1, T2, T3> TernaryExp<R, T1, T2, T3> createTernary(TriFunction<T1, T2, T3, R> operator, String symbol) {
		return new TernaryExp<R, T1, T2, T3>(operator, symbol);
	}
	
	public static <R> NaryExp<R> createNary(List<Exp<R>> ops,BinaryOperator<R> operator, String symbol) {
		return new NaryExp<R>(ops, operator, symbol);
	}
	public static <R> UnaryExpS<R> createUnaryS(Exp<R> op, UnaryOperator<R> operator, String symbol, UnaryExp.Tipo tipo) {
		return new UnaryExpS<R>(op, operator, symbol,tipo);
	}
	public static <R> UnaryExpS<R> createUnaryS(UnaryOperator<R> operator, String symbol, UnaryExp.Tipo tipo) {
		return new UnaryExpS<R>(operator, symbol,tipo);
	}
	public static <R> BinaryExpS<R> createBinaryS(Exp<R> left, Exp<R> right, BinaryOperator<R> operator, String symbol) {
		return new BinaryExpS<R>(left, right, operator, symbol);
	}
	public static <R> BinaryExpS<R> createBinaryS(BinaryOperator<R> operator, String symbol) {
		return new BinaryExpS<R>(operator, symbol);
	}
	public static <R> TernaryExpS<R> createTernaryS(Exp<R> op1, Exp<R> op2, Exp<R> op3, TriFunction<R, R, R, R> operator, String symbol) {
		return new TernaryExpS<R>(op1, op2, op3, operator, symbol);
	}
	public static <R> TernaryExpS<R> createTernaryS(TriFunction<R, R, R, R> operator, String symbol) {
		return new TernaryExpS<R>(operator, symbol);
	}
	
	public Exp() {}
	/**
	 * @return Número de operandos de la expresión
	 */
	public abstract Integer getArity(); 
	/**
	 * @return Valor devuelto por la expresión
	 */
	public abstract R eval();
	
	/**
	 * @return Copia profunda de la expresión
	 */
	public abstract Exp<R> clone();
		
	/**
	 * @return Vista de la expresión como una variable si es posible.
	 */
	public VariableExp<R> asVariable() {
		throw new IllegalStateException("Not a subtype");
	}
	/**
	 * @return Vista de la expresión como una constante si es posible.
	 */
	public ConstantExp<R> asConstant() {
		throw new IllegalStateException("Not a subtype");
	}
	/**
	 * @return Vista de la expresión como una expresión unaria si es posible.
	 */
	public UnaryExpS<R> asUnary() {
		throw new IllegalStateException("Not a subtype");
	}
	/**
	 * @return Vista de la expresión como una expresión binaria si es posible.
	 */
	public BinaryExpS<R> asBinary() {
		throw new IllegalStateException("Not a subtype");
	}
	/**
	 * @return Vista de la expresión como una expresión ternaria si es posible.
	 */
	public TernaryExpS<R> asTernary() {
		throw new IllegalStateException("Not a subtype");
	}
	
	
	
	/**
	 * @param <S> El tipo de la expresión 
	 * @param items Una lista de enteros cada uno de los cuales indexa una variable,
	 * una constante o un operador de la lista <code> expressions </code>
	 * @param operatorIndex El primer índice dónde se encuentran los operadores
	 * @param expressions Una lista de expresiones elementales de las que se construirá la expresión resultante.
	 * Primero las variables, luego las constantes y por último los operadores
	 * @return Una lista con los niveles de un árbol que representa una expresión. 
	 * Cada nivel es una lista de elementos del árbol. La expresión completa está en el elemneto 0 del nivel 0.
	 */
	public static <S> List<List<Exp<S>>> levels(List<Integer> items, Integer operatorIndex, List<Exp<S>> expressions) {
		List<Exp<S>> nodeListLast;
		List<List<Exp<S>>> levels = Lists.newArrayList();
		int pos = 0;
		List<Exp<S>> nodeList = Lists.newArrayList();
		nodeList.add(Exp.getExpCopy(items.get(pos), operatorIndex, expressions));
		pos++;
		levels.add(nodeList);
		int level = levels.size()-1;
		int aritySum = nodeList.stream().mapToInt(x->x.getArity()).sum();		
		while (aritySum>0) {			
			nodeListLast = levels.get(level);
			nodeList = Lists.newArrayList();			
			for (int i = 0; i < nodeListLast.size(); i++) {
				Exp<S> parent = nodeListLast.get(i);
				pos = Exp.subExp(pos,items,parent,nodeList,operatorIndex, expressions);
			}
			levels.add(nodeList);
			level = levels.size()-1;
			aritySum = nodeList.stream().mapToInt(x->x.getArity()).sum();	
		}
		return levels;
	}
	
	/**
	 * @param pos 
	 * @param items Una lista de enteros
	 * @param parent Una vértice de la expresión cuyos hijos queremos actualizar
	 * @param nodeList Una lista que se va actualizando con el nivel siguiente al de parent
	 * @param operatorIndex El primer índice dónde se encuentran los operadores
	 * @param expressions Una lista de expresiones elementales. Primero las variables, luego las constantes y por último los operadores
	 * @return Devuelve la nueva posición en la lista expression
	 */
	private static <S> Integer  subExp(int pos, List<Integer> items, Exp<S> parent, List<Exp<S>> nodeList, int operatorIndex, List<Exp<S>> expressions){
		Exp<S> op1 = null;
		Exp<S> op2 = null;
		Exp<S> op3 = null;
		switch(parent.getArity()){
		case 0: break;
		case 1: op1 = Exp.getExpCopy(items.get(pos), operatorIndex, expressions);
				nodeList.add(op1);
				UnaryExpS<S> p1 = parent.asUnary();
				p1.setOp(op1);
				break;
		case 2: op1 = Exp.getExpCopy(items.get(pos), operatorIndex, expressions);
				nodeList.add(op1);
				op2 = Exp.getExpCopy(items.get(pos+1), operatorIndex, expressions);
				nodeList.add(op2);
				BinaryExpS<S> p2 = parent.asBinary();
				p2.setOp(op1,op2);
				break;
		case 3: op1 = Exp.getExpCopy(items.get(pos), operatorIndex, expressions);
				nodeList.add(op1);
				op2 = Exp.getExpCopy(items.get(pos+1), operatorIndex, expressions);
				nodeList.add(op2);
				op3 = Exp.getExpCopy(items.get(pos+2), operatorIndex, expressions);
				nodeList.add(op3);
				TernaryExpS<S> p = parent.asTernary();
				p.setOp(op1,op2,op3);
				break;
		default:
				Preconditions.checkState(parent.getArity()<=3);
		}
		return pos+parent.getArity();
	}
	
	
	/**
	 * @param i Un indice en la lista expressions
	 * @param operatorIndex La primera posición de los operadores en la lista 
	 * @param expressions Una lista de expresiones elementales. Primero las variables, luego las constantes y por último los operadores
	 * @return Una copia del elemento i de expresión o el mismo elemento si es variable o constante
	 */
	private static <S> Exp<S> getExpCopy(int i, int operatorIndex, List<Exp<S>> expressions) {
		Exp<S> r = null;
		if (i < operatorIndex) {
			r = expressions.get(i);
		} else {
			r = expressions.get(i).clone();
		}
		return r;
	}
	
	
	
}

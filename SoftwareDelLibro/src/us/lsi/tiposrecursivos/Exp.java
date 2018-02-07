package us.lsi.tiposrecursivos;

import java.util.Arrays;
import java.util.List;
import java.util.Map;




import us.lsi.common.Preconditions;


/**
 * <p> Tipo que modela una expresión
 * 
 * @author Miguel Toro
 *
 * @param <R> Tipo del resultado de la expresión
 */
public abstract class Exp<R> extends Element {	
	
	/**
	 * @param <R> El tipo de la expresión
	 * @param ls Una lista de operadores ordenada por niveles y de izquierda a derecha
	 * @return La expresión resultante
	 */
	public static <R> Exp<R> exp(List<Operator> ls){
		return Operator.byLevelsToExp(Operator.toLevels(ls));
	}
	
	public static <R> Exp<R> string(String s){
		return ExpParser.scan(s);
	}
	
	public static <R> Exp<R> string(String s, Map<String, Exp<?>> vars){
		return ExpParser.scan(s, vars);
	}
	
	public static <R> VariableExp<R> variable(String name, R value, ExpType expType) {
		return new VariableExp<R>(name, value, expType);
	}
	
	public static <R> VariableExp<R> variable(String name, ExpType expType) {
		return new VariableExp<R>(name, null, expType);
	}
	
	public static <R> VariableExp<R> variable(String name, R value) {
		return new VariableExp<R>(name, value, null);
	}
	
	public static <R> VariableExp<R> variable(String name) {
		return new VariableExp<R>(name, null, null);
	}
	
	public static <T1, T2, R> BinaryExp<T1, T2, R> binary(Exp<T1> left,Exp<T2> right,BinaryOperatorExp<T1,T2,R> operator){
		return new BinaryExp<T1,T2,R>(left,right,operator);
	}
	
	public static <R> ConstantExp<R> constant(String name, R value, ExpType expType) {
		return new ConstantExp<R>(name,value,expType);
	}
	
	public static <R> ConstantExp<R> constant(String name) {
		return new ConstantExp<R>(name, null, null);
	}
	
	public static <R> ConstantExp<R> constant(R value) {
		return new ConstantExp<R>(null,value,null);
	}
	
	public static <R> ConstantExp<R> constant(R value,ExpType expType) {
		return new ConstantExp<R>(null,value,expType);
	}
	
	public static <T,R> UnaryExp<T,R> unary(Exp<T> r1,UnaryOperatorExp<T, R> unaryOperatorExp) {
		return new UnaryExp<T,R>(r1, unaryOperatorExp);
	}		
	
	public static <T1, T2, T3, R> TernaryExp<T1, T2, T3, R> ternary(
			Exp<T1> op1, Exp<T2> op2, Exp<T3> op3,TernaryOperatorExp<T1,T2,T3,R> operator) {
		return new TernaryExp<>(op1, op2, op3, operator);
	}
	
	public static <T,R> NaryExp<T,R> nary(List<Exp<T>> ops,AccumulatorExp<T,R> accumulator) {
		return new NaryExp<T,R>(ops,accumulator);
	}
	
	@SafeVarargs
	public static <T,R> NaryExp<T,R> nary(AccumulatorExp<T,R> accumulator, Exp<T>... ops) {
		return new NaryExp<T,R>(Arrays.asList(ops),accumulator);
	}
	
	public static <R> PlaceHolderExp<R> placeHolder(String name, ExpType expType) {
		return new PlaceHolderExp<R>(name,expType);
	}
	
	public static <R> PlaceHolderExp<R> placeHolder(String name) {
		return new PlaceHolderExp<R>(name,null);
	}
	
	public enum ExpType{Integer,Double,Boolean};
	
	
	public Exp() { 
		super();
	}
	
	/**
	 * @return Número de operandos de la expresión
	 */
	public abstract Integer getArity(); 
	/**
	 * @return El nombre del operador de la expresión
	 */
	public abstract String getName(); 
	/**
	 * @return El operador de la expresión
	 */
	public abstract Operator getOperator(); 
	/**
	 * @return Valor devuelto por la expresión
	 */
	public abstract R val();
	
	/**
	 * @return Copia profunda de la expresión
	 */
	public abstract Exp<R> copy();

	
	/**
	 * @return Igualdad
	 */
	public abstract boolean equals(Object other);
	/**
	 * @return Igualdad
	 */
	public abstract int hashCode();	
	
	public abstract Boolean match(Exp<?> pattern);
	
	public Exp<R> ifMatchTransform(String pattern, String result){
		Exp<R> e = Exp.string(pattern);		
		return ifMatchTransform(e,this.getVars(),result);
	}
	
	public abstract Exp<R> ifMatchTransform(Exp<?> pattern, Map<String,Exp<?>> vars,String result);
		
	public abstract ExpType getExpType();
	
	protected abstract String getId();
	
	protected abstract void toDOT(String file);
	
	public abstract Exp<R> simplify();
	
	private Map<String, Exp<?>> vars;
	
	protected abstract Map<String, Exp<?>> vars();
	
	@SuppressWarnings("unchecked")
	public <S> Exp<S> get(String s){
		if(vars==null){
			vars = vars();
		}
		Preconditions.checkArgument(getVars().keySet().contains(s),String.format("La expresión no tiene la varaible %s",s));
		return (Exp<S>) vars().get(s);
	}
	
	public Map<String, Exp<?>> getVars() {
		if(vars==null){
			vars = vars();
		}
		return vars;
	}
	
	public abstract Integer getPriority();
	
	/**
	 * @return Si es una variable
	 */
	public Boolean isVariable() {
		return false;
	}
	/**
	 * @return Si es constante
	 */
	public Boolean isConstant() {
		return false;
	}
	/**
	 * @return Si es una expresión unaria.
	 */
	public Boolean isUnary() {
		return false;
	}
	/**
	 * @return Si es una expresión binaria
	 */
	public Boolean isBinary() {
		return false;
	}
	/**
	 * @return Si es una expresión ternaria
	 */
	public Boolean isTernary() {
		return false;
	}
	
	/**
	 * @return Si es una expresión naria
	 */
	public Boolean isNary() {
		return false;
	}
	
	/**
	 * @return Si es PlaceHolder
	 */
	public Boolean isPlaceHolderExp() {
		return false;
	}
	/**
	 * @return Conversión a  variable
	 */
	@SuppressWarnings("unchecked")
	public <S> VariableExp<S> asVariable() {
		return (VariableExp<S>) this;
	}
	/**
	 * @return Conversión a  constante
	 */
	
	@SuppressWarnings("unchecked")
	public <S> ConstantExp<S> asConstant() {
		return (ConstantExp<S>) this;
	}
	/**
	 * @return Conversión a  expresión unaria.
	 */
	@SuppressWarnings("unchecked")
	public <T,S> UnaryExp<T,S> asUnary() {
		return (UnaryExp<T,S>) this;
	}
	/**
	 * @return Conversión a expresión binaria
	 */
	@SuppressWarnings("unchecked")
	public <T1,T2,S> BinaryExp<T1,T2,S> asBinary() {
		return (BinaryExp<T1,T2,S>) this;
	}
	/**
	 * @return Conversión a expresión ternaria
	 */
	@SuppressWarnings("unchecked")
	public <T1,T2,T3,S> TernaryExp<T1,T2,T3,S> asTernary() {
		return (TernaryExp<T1,T2,T3,S>) this;
	}
	
	/**
	 * @return Conversión a  expresión naria
	 */
	@SuppressWarnings("unchecked")
	public <T,S> NaryExp<T,S> asNary() {
		return (NaryExp<T,S>) this;
	}
	
	/**
	 * @return Conversión a  PlaceHolderExp
	 */
	@SuppressWarnings("unchecked")
	public <T> PlaceHolderExp<T> asPlaceHolderExp() {
		return (PlaceHolderExp<T>) this;
	}
	
	public static void main(String[] args) {
		Operator.initial();
		System.out.println(Operator.operators.keySet().size());
		VariableExp<Double> x = Exp.variable("x",1.0);
		VariableExp<Double> y = Exp.variable("y",2.1);
		ConstantExp<Double> c = Exp.constant(3.2);		
		BinaryExp<Double,Double,Double> plus = Exp.binary(x,y,Operator.getBinary("+", ExpType.Double, ExpType.Double));
		BinaryExp<Double,Double,Double> multiply = Exp.binary(x,c,Operator.getBinary("*", ExpType.Double, ExpType.Double));
		UnaryExp<Double, Double> sqrt = Exp.unary(multiply,Operator.getUnary("sqrt", ExpType.Double));
		UnaryExp<Double, Double> pot1 = Exp.unary(sqrt,Operator.getUnary("^2",ExpType.Double));
		UnaryExp<Double, Double> pot = Exp.unary(sqrt,Operator.getUnary("^3",ExpType.Double));
		NaryExp<Double,Double> s = Exp.nary(Operator.getAccumulator("+",ExpType.Double),x,y,sqrt,pot,plus);
		BinaryExp<Double,Double,Double> rr = Exp.binary(pot, s,Operator.getBinary("+",ExpType.Double, ExpType.Double));
		System.out.println(pot1);
		System.out.println(pot1.val());
		System.out.println(pot1.simplify());
		System.out.println(pot1.simplify().val());
		System.out.println(pot);
		System.out.println(pot.val());
		System.out.println(s);
		System.out.println(s.val());
		PlaceHolderExp<Double> p = Exp.placeHolder("@1");
		Boolean r2 = rr.match(Exp.binary(pot,p,Operator.getBinary("+",ExpType.Double,ExpType.Double)));
		System.out.println(rr);
		System.out.println(r2+","+p.getExp());
		List<Operator> lo = Arrays.asList(
				Operator.getBinary("*", ExpType.Double, ExpType.Double),
				Operator.getBinary("+", ExpType.Double, ExpType.Double),
				Operator.getUnary("sqrt", ExpType.Double),
				Operator.getBinary("*", ExpType.Double, ExpType.Double),
				x,
				Operator.getUnary("^3", ExpType.Double),				
				Operator.getBinary("+", ExpType.Double, ExpType.Double),				
				y,
				c,
				x,
				c);
		System.out.println(lo);

		Exp<Double> e = Exp.exp(lo);
		System.out.println(e);
		System.out.println(e.simplify());
		e.toDOT("Exprs.gv", "Expresion");
		Exp<Boolean> b = Exp.binary(x,y,Operator.getBinary("==",ExpType.Double,ExpType.Double));
		Exp<Double> r = Exp.ternary(b, e, rr,
				Operator.getTernary("iff", ExpType.Boolean,ExpType.Double,ExpType.Double));
		System.out.println(r);
		
		Exp<Double> e2 = Exp.string("0.+3.^(2+5)+x^2+sqrt(4.+x)+x");
		System.out.println(e2);
		Exp<Double> exp = e2.ifMatchTransform("0.+@y", "@y");
		exp.get("x").<Double>asVariable().setValue(4.5);
		System.out.println(exp.simplify());
		System.out.println(exp.val());
	}
	

}


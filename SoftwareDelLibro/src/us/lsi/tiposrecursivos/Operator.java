package us.lsi.tiposrecursivos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.google.common.base.Preconditions;

import us.lsi.common.Sets2;
import us.lsi.common.Tuple2;
import us.lsi.common.Tuple3;
import us.lsi.common.Tuple4;
import us.lsi.common.Tuple5;
import us.lsi.tiposrecursivos.Exp.ExpType;

public interface Operator {
	
	public static Map<Object,Operator> operators = new HashMap<>();
	static Set<String> functions = new HashSet<>();
	public static Set<String> reservedWords = Sets2.newSet("while","if","else","int","double");
	public static Map<String,Integer> arities = new HashMap<>();
    public static ExpType getVariableType(String s){
    	ExpType r;
    	Character c = s.charAt(0);
    	if(c<='d') r = ExpType.Boolean;
		else if(c<='k') r = ExpType.Integer;
		else r = ExpType.Double;
		return r;
    }
    
	public static int initial(){
    	AccumulatorExp.add();
    	BinaryOperatorExp.add();
    	UnaryOperatorExp.add();
    	TernaryOperatorExp.add();
    	return operators.size();
    }
	
	public static int operatorsNumber = initial();
	
	public static Operator get(String s){
		Tuple2<String,Integer> t = Tuple2.create(s,0);
		Preconditions.checkArgument(operators.containsKey(t),
				String.format("No existe el operador %s",s));
		return operators.get(t);
	}
	
	public static boolean containsOperator(String s) {
		Tuple2<String,Integer> t = Tuple2.create(s,0);
		return operators.containsKey(t);
	}
	
	public static void add(String s, Operator op) {
		Tuple2<String,Integer> t = Tuple2.create(s,0);
		operators.put(t,op);
	}
	
	public static Operator get(String s, int a, ExpType e){
		Tuple3<String,Integer,ExpType> t = Tuple3.create(s, a, e);
		Preconditions.checkArgument(operators.containsKey(t),
				String.format("No existe el operador %s de tipo %s, aridad %d",s,e!=null?e.toString():null
						,a));
		return operators.get(t);
	}
	
	public static boolean containsOperator(String s, ExpType e){
		Tuple3<String,Integer,ExpType> t = Tuple3.create(s, 1, e);
		return operators.containsKey(t);
	}
	
	public static boolean containsAccumulator(String s, ExpType e){
		Tuple3<String,Integer,ExpType> t = Tuple3.create(s, -1, e);
		return operators.containsKey(t);
	}
	
	@SuppressWarnings("unchecked")
	public static <T,R> UnaryOperatorExp<T,R> getUnary(String s, ExpType e){
		return (UnaryOperatorExp<T, R>) get(s,1,e);
	}
	
	@SuppressWarnings("unchecked")
	public static <T,R> AccumulatorExp<T,R> getAccumulator(String s, ExpType e){
		return (AccumulatorExp<T, R>) get(s,-1,e);
	}
	
	public static void add(String s, ExpType e1, Operator op) {
		Tuple3<String,Integer,ExpType> t = Tuple3.create(s, op.getArity(),e1);
		operators.put(t,op);
	}
	
	public static Operator get(String s, int a, ExpType e1, ExpType e2){
		Tuple4<String,Integer,ExpType,ExpType> t  = Tuple4.create(s, a,e1, e2);
		Preconditions.checkArgument(operators.containsKey(t),
				String.format("No existe el operador %s de tipo %s, %s, aridad %d",s,
						e1!=null?e1.toString():null,
						e2!=null?e2.toString():null,
					    a));
		return (BinaryOperatorExp<?, ?, ?>) operators.get(t);
	}
	
	public static boolean containsOperator(String s, ExpType e1, ExpType e2){
		Tuple4<String,Integer,ExpType,ExpType> t  = Tuple4.create(s, 2,e1, e2);
		return operators.containsKey(t);
	}
	
	@SuppressWarnings("unchecked")
	public static <T1,T2,R> BinaryOperatorExp<T1,T2,R> getBinary(String s, ExpType e1, ExpType e2){
		return (BinaryOperatorExp<T1, T2,R>) get(s,2,e1,e2);
	}
	
	public static void add(String s, ExpType e1, ExpType e2, Operator op) {
		Tuple4<String,Integer,ExpType,ExpType> t = Tuple4.create(s,op.getArity(),e1,e2);
		operators.put(t,op);
	}
	
	public static Operator get(String s, Integer a, ExpType e1, ExpType e2, ExpType e3){
		Tuple5<String,Integer,ExpType,ExpType,ExpType> t  = Tuple5.create(s,a,e1,e2,e3);
		Preconditions.checkArgument(operators.containsKey(t),
				String.format("No existe el operador %s de tipo %s, %s, %s, aridad %d",
						s,
						e1!=null?e1.toString():null,
						e2!=null?e2.toString():null,
						e3!=null?e3.toString():null,
						a));
		return operators.get(t);
	}
	
	public static boolean containsOperator(String s, ExpType e1, ExpType e2, ExpType e3){
		Tuple5<String,Integer,ExpType,ExpType,ExpType> t  = Tuple5.create(s,3,e1,e2,e3);
		return operators.containsKey(t);
	}
	
	
	@SuppressWarnings("unchecked")
	public static <T1,T2,T3, R> TernaryOperatorExp<T1,T2,T3,R> getTernary(String s, ExpType e1, ExpType e2, ExpType e3){
		return (TernaryOperatorExp<T1, T2, T3, R>) get(s,3,e1,e2,e3);
	}
	
	public static void add(String s, ExpType e1, ExpType e2, ExpType e3, TernaryOperatorExp<?,?,?,?> op) {
		Tuple5<String,Integer,ExpType,ExpType,ExpType> t = Tuple5.create(s,op.getArity(),e1,e2,e3);
		operators.put(t,op);
	}
	
	Integer getArity();
	
	String getName();
	
	String getShortName();
	
	String getId();
	
	Integer getPriority();
	
	/**
	 * @param <R> El tipo de la expresión
	 * @param lo Una lista de operadores por niveles
	 * @return La expresión resultante
	 */
	@SuppressWarnings("unchecked")
	static <R> Exp<R> byLevelsToExp(List<List<Operator>> lo){
		int n = lo.size();
		List<Exp<R>> level = lo.get(n-1).stream().map(x->(Exp<R>)x).collect(Collectors.toList());
		List<Exp<R>> nLevel;
		for(int i=n-2;i>=0;i--){
			int k = 0;
			nLevel = new ArrayList<>();
			for(Operator op :lo.get(i)){
				switch(op.getArity()){
				case 0: nLevel.add((Exp<R>)op); break;
				case 1: nLevel.add(Exp.unary(level.get(k),(UnaryOperatorExp<R,R>)op)); 
						k = k+1; break;
				case 2: nLevel.add(Exp.binary(level.get(k), level.get(k+1),(BinaryOperatorExp<R,R,R>)op)); 
					    k = k+2; break;
				case 3: nLevel.add(Exp.ternary(level.get(k), level.get(k+1),level.get(k+2),(TernaryOperatorExp<R,R,R,R>)op)); 
			    		k = k+3; break;
				default: Preconditions.checkState(false, "Aridad no posible");
				}
			}
			level = nLevel;
		}
		return level.get(0);
	}

	/**
	 * @param <R> El tipo de la expresión
	 * @param ls Una lista de operadores
	 * @return Una lista con los operadores en cada nivel agrupados según sus aridades
	 */
	static <R> List<List<Operator>> toLevels(List<Operator> ls){
		List<List<Operator>> r = new ArrayList<>();
		List<Operator> lv = Arrays.asList(ls.get(0));
		int i = 1;
		int nh;
		while(lv.size()>0){	
			r.add(lv);	
			nh = lv.stream().mapToInt(x->x.getArity()).sum();
			lv = IntStream.range(i,i+nh).mapToObj(x->ls.get(x)).collect(Collectors.toList());					
			i = i + nh;	
		}
		return r;
	}

	public static void main(String[] args) {
		Operator.initial();
		System.out.println(Tuple5.create(1, 2,3, 4, 5));
		String s = operators.entrySet()
				.stream()
				.map(x->"("+x.getKey()+","+x.getValue()+")")
				.collect(Collectors.joining("\n"));
		System.out.println(s);
		Operator op = Operator.getBinary("*",ExpType.Double,ExpType.Double);
		System.out.println(op);
		Operator op2 = Operator.getUnary("sqrt",ExpType.Double);
		System.out.println(op2);
		Operator op1 = Operator.getTernary("if", ExpType.Boolean,ExpType.Double,ExpType.Double);
		System.out.println(op1);
	}
}

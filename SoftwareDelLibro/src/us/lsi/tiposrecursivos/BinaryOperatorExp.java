package us.lsi.tiposrecursivos;
import java.util.function.BiFunction;

import us.lsi.math.Math2;
import us.lsi.tiposrecursivos.Exp.ExpType;

public class BinaryOperatorExp<T1,T2,R> implements Operator {

	private BiFunction<T1,T2,R> code = null;
	private String name = null;
	private String format = null;
	private String shortName = null;
	private ExpType[] expType;
	private int priority;
	private int id;
	private static int lastId = 0;
	
	public BinaryOperatorExp(BiFunction<T1, T2, R> operator, String name,
			String format,String shortName, ExpType[] expType, Integer priority) {
		super();
		this.code = operator;
		this.name = name;
		this.format = format;
		this.shortName = shortName;
		this.expType = expType;
		this.priority = priority;
		this.id = lastId;
		lastId++;
	}

	public BiFunction<T1, T2, R> getCode() {
		return code;
	}

	public String getSymbol() {
		return name;
	}

	public String getFormat() {
		return format;
	}
	
	@Override
	public Integer getArity() {
		return 2;
	}
	
	public String getId() {
		return "BO_"+id;
	}
	
	public String getName() {
		return name;
	}
	
	
	public ExpType[] getExpType() {
		return expType;
	}

	@SuppressWarnings("unchecked")
	public BinaryExp<T1,T2,R> exp(Object obj1, Object obj2){
		Exp<T1> op1 = (Exp<T1>) obj1;
		Exp<T2> op2 = (Exp<T2>) obj2;
		return Exp.binary(op1, op2, this);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof BinaryOperatorExp))
			return false;
		BinaryOperatorExp<?,?,?> other = (BinaryOperatorExp<?,?,?>) obj;
		if (id != other.id)
			return false;
		return true;
	}


	public static int add() {
		
			BinaryOperatorExp<?,?,?> op;
			ExpType[] expType1 = {ExpType.Integer,ExpType.Integer,ExpType.Integer};
			op = new BinaryOperatorExp<Integer,Integer,Integer>((x,y)->x+y,"B_PlusInteger","%s+%s","+",expType1,2);
			Operator.add("+",ExpType.Integer,ExpType.Integer,op);
			Operator.arities.put("+",2);
			
			ExpType[] expType3  = {ExpType.Double,ExpType.Double,ExpType.Double};
			op = new BinaryOperatorExp<Double,Double,Double>((x,y)->x+y,"B_PlusDouble","%s+%s","+",expType3,2);
			Operator.add("+",ExpType.Double,ExpType.Double,op); 
			
			ExpType[] expType11 = {ExpType.Integer,ExpType.Integer,ExpType.Integer};
			op = new BinaryOperatorExp<Integer,Integer,Integer>((x,y)->x-y,"B_MinusInteger","%s-%s","-",expType11,2);
			Operator.add("-",ExpType.Integer,ExpType.Integer,op);
			Operator.arities.put("-",2);
			
			ExpType[] expType31  = {ExpType.Double,ExpType.Double,ExpType.Double};
			op = new BinaryOperatorExp<Double,Double,Double>((x,y)->x-y,"B_MinusDouble","%s-%s","-",expType31,2);
			Operator.add("-",ExpType.Double,ExpType.Double,op);
			
			ExpType[] expType2 = {ExpType.Integer,ExpType.Integer,ExpType.Integer};
			op = new BinaryOperatorExp<Integer,Integer,Integer>((x,y)->x*y,"B_MultiplyInteger","%s*%s","*",expType2,4);
			Operator.add("*",ExpType.Integer,ExpType.Integer,op);
			Operator.arities.put("*",4);

			ExpType[] expType4  = {ExpType.Double,ExpType.Double,ExpType.Double};
			op = new BinaryOperatorExp<Double,Double,Double>((x,y)->x*y,"B_MultiplyDouble","%s*%s","*",expType4,4);
			Operator.add("*",ExpType.Double,ExpType.Double,op);
	
			ExpType[] expType5  = {ExpType.Double,ExpType.Double,ExpType.Double};
			op = new BinaryOperatorExp<Double,Double,Double>((x,y)->Math.pow(x,y),"B_PotDouble","%s^%s","^",expType5,6);
			Operator.add("^",ExpType.Double,ExpType.Double,op);
			Operator.arities.put("^",6);
	
			ExpType[] expType8  = {ExpType.Double,ExpType.Integer,ExpType.Double};
			op = new BinaryOperatorExp<Double,Integer,Double>((x,y)->Math2.pow(x,y),"B_PotDoubleInteger","%s^%s","^",expType8,6);
			Operator.add("^",ExpType.Double,ExpType.Integer,op);
			
			ExpType[] expType9  = {ExpType.Integer,ExpType.Integer,ExpType.Integer};
			op = new BinaryOperatorExp<Integer,Integer,Integer>((x,y)->Math2.pow(x,y).intValue(),"B_PotInteger","%s^%s","^",expType9,6);
			Operator.add("^",ExpType.Integer,ExpType.Integer,op);
			
			ExpType[] expType6  = {ExpType.Double,ExpType.Double,ExpType.Boolean};
			op = new BinaryOperatorExp<>((x,y)->x.equals(y),"B_IqualDouble","%s==%s","==",expType6,8);
			Operator.add("==",ExpType.Double,ExpType.Double,op);
			Operator.arities.put("==",8);
			
			ExpType[] expType61  = {ExpType.Integer,ExpType.Integer,ExpType.Boolean};
			op = new BinaryOperatorExp<>((x,y)->x.equals(y),"B_IqualInteger","%s==%s","==",expType61,8);
			Operator.add("==",ExpType.Integer,ExpType.Integer,op);
			
			
			
			return 1;
	}
	
	@Override
	public String toString() {
		return name;
	}

	@Override
	public String getShortName() {
		return shortName;
	}
	
	@Override
	public Integer getPriority() {
		return priority;
	}
	
}

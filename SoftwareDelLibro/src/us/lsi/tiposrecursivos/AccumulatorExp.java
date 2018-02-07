package us.lsi.tiposrecursivos;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import us.lsi.tiposrecursivos.Exp.ExpType;

public class AccumulatorExp<T,R> implements Operator {

	private Collector<T,?,R> collectorOperator = null;
	private String name;
	private String shortName = null;
	private ExpType[] expType;
	private int id;
	private static int lastId = 0;
	
	
	public AccumulatorExp(Collector<T,?,R> collector, String name, String shortName, 
			ExpType[] expType) {
		super();
		this.collectorOperator = collector;
		this.shortName = shortName;
		this.name = name;
		this.expType = expType;
		this.id = lastId;
		lastId++;
	}
	
	public Collector<T,?,R> getCode() {
		return collectorOperator;
	}

	@Override
	public Integer getArity() {
		return -1;
	}	
	
	@Override
	public String toString() {
		return name;
	}

	public String getId() {
		return "A_"+id;
	}

	public String getName() {
		return name;
	}
	
	@SuppressWarnings("unchecked")
	public NaryExp<T,R> exp(List<Exp<?>> ls){
		List<Exp<T>> ops = ls.stream().map(x->(Exp<T>)x).collect(Collectors.toList());
		return Exp.nary(ops, this);
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
		if (!(obj instanceof AccumulatorExp))
			return false;
		AccumulatorExp<?,?> other = (AccumulatorExp<?,?>) obj;
		if (id != other.id)
			return false;
		return true;
	}



	public static int add() {
			
			ExpType[] expType1  = {ExpType.Integer,ExpType.Integer};
			AccumulatorExp<Integer,Integer> op1 = new AccumulatorExp<>(Collectors.reducing(0,(x,y)->x+y), "A_PlusInteger","+",expType1);
			Operator.add("+",ExpType.Integer,op1);
			
			ExpType[] expType2  = {ExpType.Double,ExpType.Double};
			AccumulatorExp<Double,Double> op2 = new AccumulatorExp<>(Collectors.reducing(0.,(x,y)->x+y), "A_PlusDouble", "+",expType2);
			Operator.add("+",ExpType.Double,op2);
			
			ExpType[] expType3  = {ExpType.Integer,ExpType.Integer};
			op1 = new AccumulatorExp<>(Collectors.reducing(0,(x,y)->x*y), "A_MultiplyInteger","*",expType3);
			Operator.add("*",ExpType.Integer,op1);
			
			ExpType[] expType4  = {ExpType.Double,ExpType.Double};
			AccumulatorExp<Double,Double> op4 = new AccumulatorExp<>(Collectors.reducing(0.,(x,y)->x*y), "A_MultiplyDouble","*",expType4);
			Operator.add("*",ExpType.Double,op4);
			
			ExpType[] expType5  = {ExpType.Integer,ExpType.Integer};
			AccumulatorExp<Integer,Integer> op5 = new AccumulatorExp<>(Collectors.reducing(Integer.MAX_VALUE,(x,y)->Integer.min(x,y)), "A_MinInteger","min",expType5);
			Operator.add("min",ExpType.Integer,op5);
			functions.add("min");
			
			ExpType[] expType6  = {ExpType.Integer,ExpType.Integer};
			AccumulatorExp<Integer,Integer> op6 = new AccumulatorExp<>(Collectors.reducing(Integer.MIN_VALUE,(x,y)->Integer.max(x,y)), "A_MaxInteger","max",expType6);
			Operator.add("max",ExpType.Integer,op6);
			functions.add("max");
			
			ExpType[] expType7  = {ExpType.Double,ExpType.Double};
			AccumulatorExp<Double,Double> op7 = new AccumulatorExp<>(Collectors.reducing(Double.MAX_VALUE,(x,y)->Double.min(x,y)), "A_MinDouble","min",expType7);
			Operator.add("min",ExpType.Double,op7);
			
			ExpType[] expType8  = {ExpType.Double,ExpType.Double};
			AccumulatorExp<Double,Double> op8 = new AccumulatorExp<>(Collectors.reducing(Double.MIN_VALUE,(x,y)->Double.max(x,y)), "A_MaxDouble","max",expType8);
			Operator.add("max",ExpType.Double,op8);
	
		
			return 1;
	}

	@Override
	public String getShortName() {
		return shortName;
	}

	public ExpType[] getExpType() {
		return expType;
	}
	
	@Override
	public Integer getPriority() {
		return 0;
	}
}

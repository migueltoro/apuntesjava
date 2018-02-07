package us.lsi.tiposrecursivos;

import us.lsi.common.TriFunction;
import us.lsi.tiposrecursivos.Exp.ExpType;

public class TernaryOperatorExp<T1, T2, T3, R> implements Operator{

	private TriFunction<T1,T2,T3,R> code = null;
	private String name = null;
	private String format = null;
	private String shortName = null;
	private int id;
	private static int lastId = 0;
	
	
	public TernaryOperatorExp(TriFunction<T1, T2, T3, R> operator, 
			String name,String format, String shortName) {
		super();
		this.code = operator;
		this.name = name;
		this.format = format;
		this.shortName = shortName;
		this.id = lastId;
		lastId++;
	}

	@SuppressWarnings("unchecked")
	public TernaryExp<T1,T2,T3,R> exp(Object obj1, Object obj2, Object obj3){
		Exp<T1> op1 = (Exp<T1>) obj1;
		Exp<T2> op2 = (Exp<T2>) obj2;
		Exp<T3> op3 = (Exp<T3>) obj3;
		return Exp.ternary(op1, op2, op3, this);
	}
	
	public TriFunction<T1, T2, T3, R> getCode() {
		return code;
	}

	public String getFormat() {
		return format;
	}
	
	@Override
	public Integer getArity() {
		return 3;
	}	
	
	@Override
	public String toString() {
		return name;
	}

	public String getId() {
		return "TO_"+id;
	}
	
	public String getName() {
		return name;
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
		if (!(obj instanceof TernaryOperatorExp))
			return false;
		TernaryOperatorExp<?,?,?,?> other = (TernaryOperatorExp<?,?,?,?>) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	public static int add() {
		TernaryOperatorExp<Boolean,Double,Double,Double> op1 = new TernaryOperatorExp<>((x,y,z)->x?y:z,"T_IfDouble","iff(%s,%s,%s)","iff");
		Operator.add("iff",ExpType.Boolean,ExpType.Double,ExpType.Double,op1);
		functions.add("iff");
		
		TernaryOperatorExp<Boolean,Integer,Integer,Integer> op2 = new TernaryOperatorExp<>((x,y,z)->x?y:z,"T_IfInteger","iff(%s,%s,%s)","iff");
		Operator.add("iff",ExpType.Boolean,ExpType.Integer,ExpType.Integer,op2);
	
		return 1;
	}
	
	@Override
	public String getShortName() {
		return this.shortName;
	}
	
	@Override
	public Integer getPriority() {
		return 0;
	}
}

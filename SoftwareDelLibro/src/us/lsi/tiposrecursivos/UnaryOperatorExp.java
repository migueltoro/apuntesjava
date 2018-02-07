package us.lsi.tiposrecursivos;
import java.util.function.Function;

import us.lsi.tiposrecursivos.Exp.ExpType;

public class UnaryOperatorExp<T,R> implements Operator {

	private Function<T,R> code = null;
	private String name = null;
	private String sortName = null;
	private String format = null;
	private ExpType[] expType = null;
	private int id;
	private static int lastId = 0;
	
	
	public UnaryOperatorExp(Function<T, R> operator, String name,String format, String shortName, ExpType[] expType) {
		super();
		this.code = operator;
		this.name = name;
		this.format = format;
		this.sortName = shortName;
		this.expType = expType;
		this.id = lastId;
		lastId++;
		
	}

	@SuppressWarnings("unchecked")
	public UnaryExp<T,R> exp(Object obj){
		Exp<T> op = (Exp<T>) obj;
		return Exp.unary(op, this);
	}
	
	public Function<T, R> getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public String getFormat() {
		return format;
	}
	
	@Override
	public Integer getArity() {
		return 1;
	}
	
	public String getId() {
		return "UO_"+id;
	}
	
	@Override
	public String toString() {
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
		if (!(obj instanceof UnaryOperatorExp))
			return false;
		UnaryOperatorExp<?,?> other = (UnaryOperatorExp<?,?>) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public static int add() {
			UnaryOperatorExp<?,?> op;
			ExpType[] expType1 = {ExpType.Double,ExpType.Double};
			op = new UnaryOperatorExp<Double,Double>(x->Math.sqrt(x),"U_SqrtDouble","sqrt%s","sqrt",expType1);
			Operator.add("sqrt",ExpType.Double,op);
			functions.add("sqrt");
			
			ExpType[] expType2 = {ExpType.Double,ExpType.Double};
			op = new UnaryOperatorExp<Double,Double>(x->x*x,"U_SquareDouble","%s^2","^2",expType2);
			Operator.add("^2",ExpType.Double,op);
		
			ExpType[] expType3 = {ExpType.Double,ExpType.Double};
			op = new UnaryOperatorExp<Double,Double>(x->x*x*x,"U_CubeDouble","%s^3","^3",expType3);
			Operator.add("^3",ExpType.Double,op);
			
			ExpType[] expType4 = {ExpType.Double,ExpType.Integer};
			op = new UnaryOperatorExp<Double,Integer>(x->x.intValue(),"U_DoubleToInt","(int)%s","(int)",expType4);
			Operator.add("(int)",ExpType.Double,op);

			ExpType[] expType5 = {ExpType.Integer,ExpType.Double};
			op = new UnaryOperatorExp<Integer,Double>(x->x.doubleValue(),"U_IntToDouble","(double)%s","(double)",expType5);
			Operator.add("(double)",ExpType.Integer,op);
			
			return 1;
	}

	public ExpType[] getExpType() {
		return expType;
	}

	@Override
	public String getShortName() {
		return sortName;
	}

	@Override
	public Integer getPriority() {
		return 0;
	}
	
}

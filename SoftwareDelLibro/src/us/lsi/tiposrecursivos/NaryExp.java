package us.lsi.tiposrecursivos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import us.lsi.algoritmos.AbstractAlgoritmo;
import us.lsi.common.Maps2;


public class NaryExp<T,R> extends Exp<R> {

	private List<Exp<T>> ops = null;
	private AccumulatorExp<T,R> accumulator;
	private int id;
	private static int lastId = 0;
	private Boolean flag = false;

	NaryExp(List<Exp<T>> ops, AccumulatorExp<T,R> accumulator) {
		super();
		this.ops = ops;
		this.accumulator = accumulator;
		this.id = lastId;
		lastId++;
	}

	@Override
	public R val() {	
		return ops.stream().map(x->x.val()).collect(this.accumulator.getCode());
	}

	@Override
	public NaryExp<T,R> copy() {
		List<Exp<T>> ops = this.ops.stream().map(x->x.copy()).collect(Collectors.toList());
		return Exp.nary(ops, this.accumulator);
	}


	public List<Exp<T>> getOps() {
		return ops;
	}

	
	@Override
	public String toString() {
		return " "+accumulator.getShortName()+ops.stream().map(x->x.toString()).collect(
				Collectors.joining(",",
								   "(",
								   ")"));
	}

	public void setOps(List<Exp<T>> ops) {
		this.ops = ops;
	}

	@Override
	public Integer getArity() {
		return this.ops.size();
	}

	@Override
	public Operator getOperator() {
		return accumulator;
	}
	
	@Override
	public String getName() {
		return accumulator.getName();
	}
	@Override
	public Boolean isNary() {
		return true;
	}
	@Override
	public Boolean isConstant() {
		return this.ops.stream().allMatch(x->x.isConstant());
	}
	
	@Override
	public Exp<R> simplify() {
		Exp<R> r;
		if(this.isConstant()){
			r = Exp.constant(this.val());
		} else {
			List<Exp<T>> ls = this.ops.stream().map(x->x.simplify()).collect(Collectors.toList());
			r = Exp.nary(ls, accumulator);
		}
		return r;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((accumulator == null) ? 0 : accumulator.hashCode());
		result = prime * result + ((ops == null) ? 0 : ops.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof NaryExp))
			return false;
		NaryExp<?,?> other = (NaryExp<?,?>) obj;
		if (accumulator == null) {
			if (other.accumulator != null)
				return false;
		} else if (!accumulator.equals(other.accumulator))
			return false;
		if (ops == null) {
			if (other.ops != null)
				return false;
		} else if (!ops.equals(other.ops))
			return false;
		return true;
	}

	@Override
	public Boolean match(Exp<?> exp) {
		Boolean r = false;
		if(exp.isPlaceHolderExp()){
			exp.<R>asPlaceHolderExp().setExp(this);
			r = true;
		} else if(exp.isNary()){
			NaryExp<?,?> t = exp.asNary();
			if(this.ops.size() == t.ops.size()) {
				r = IntStream.range(0,this.ops.size())
						.allMatch(i->this.ops.get(i).match(t.ops.get(i)));
			}
		}
		return r;
	}

	@Override
	protected String getId() {
		return "NE_"+id;
	}

	@Override
	protected void toDOT(String file) {
		if (!flag) {
			String s1 = "    \"%s\" [label=\"%s\"];";
			AbstractAlgoritmo.getFile()
					.println(
							String.format(s1, getId(),
									this.accumulator.getShortName()));
			String s2 = "    \"%s\" -> \"%s\"  [label=\"%d\"];";
			for (int i = 0; i < ops.size(); i++) {
				AbstractAlgoritmo.getFile().println(
						String.format(s2, getId(), ops.get(i).getId(), i));
				ops.get(i).toDOT(file);
			}
		}
		flag = true;
	}
	
	@Override
	protected void setFlagFalse() {
		flag = false;
		ops.stream().forEach(x->x.setFlagFalse());
	}

	@Override
	public ExpType getExpType() {
		return accumulator.getExpType()[1];
	}
	
	@Override
	protected Map<String, Exp<?>> vars() {
		Map<String, Exp<?>> r = new HashMap<>();
		ops.stream().forEach(x->r.putAll(x.vars()));
		return r;
	}
	
	@Override
	public Integer getPriority() {
		return 12;
	}
	
	
	@Override
	public Exp<R> ifMatchTransform(Exp<?> pattern, Map<String,Exp<?>> vars,String patternResult) {
		List<Exp<T>> r1 = ops.stream()
				.map(x->x.ifMatchTransform(pattern, vars, patternResult))
				.collect(Collectors.toList());
		Exp<R> r = Exp.nary(r1,this.accumulator);
		Exp<?> copy = pattern.copy();
		if(r.match(copy)){
			Map<String,Exp<?>> m = copy.getVars();
			Map<String,Exp<?>> m2 = 
					Maps2.<String,Exp<?>,Exp<?>>newHashMap(m, 
							x->x.isPlaceHolderExp()?x.asPlaceHolderExp().getExp():x);
			m2.putAll(vars);
			r = Exp.string(patternResult,m2);
		}
		return r;
	}
	
}

package us.lsi.ag.agchromosomes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.math3.genetics.AbstractListChromosome;
import org.apache.commons.math3.genetics.BinaryChromosome;
import org.apache.commons.math3.genetics.Chromosome;
import org.apache.commons.math3.genetics.InvalidRepresentationException;

import com.google.common.collect.Lists;

import us.lsi.ag.ProblemaAG;
import us.lsi.ag.ProblemaAGExpression;
import us.lsi.common.ConstantExp;
import us.lsi.common.Lists2;
import us.lsi.common.Exp;
import us.lsi.common.NaryExp;
import us.lsi.common.VariableExp;
import us.lsi.math.Math2;

/**
 * @author Miguel Toro
 *
 * @param <T> Tipo del resultado de la expresión
 * 
 * <p> Implementa el tipo {@link us.lsi.ag.agchromosomes.IExpressionChromosome IExpressionChromosome}.
 * La implementación 
 * sigue las ideas de <a href="https://en.wikipedia.org/wiki/Gene_expression_programming"> Gene expression programming</a></p>
 */
public class ExpressionChromosome<T> extends BinaryChromosome implements IExpressionChromosome<T> {

	
	/**
	 * Número de bits para codificar cada uno de los valores enteros resultantes
	 */
	public static Integer numeroDeBits = 5;

	/**
	 * Problema a resolver
	 */
	public static ProblemaAGExpression<?,?> problem;

	/**
	 * Longitud de la cabeza de cada uno de los genes
	 */
	public static Integer headLeng;	

	private static Integer tailLeng;
	
	/**
	 * Número de Genes del cromosoma
	 */
	public static Integer numGenes;
	
	private static Integer numItemsPorGen;

	private static Integer numItems;
	
	private static Integer constantsIndex;
	
	/**
	 * Dimensión del cromosoma
	 */

	static int DIMENSION;

	/**
	 * @pos Inicializa los parámetros de la clase
	 * @param problema El problema a resolver
	 * @param <T> El tipo de la expresión que representa el cromosoma
	 */
	public static <T> void iniValues(ProblemaAG problema) {
		ExpressionChromosome.problem = (ProblemaAGExpression<?, ?>) problema;
		int maxArity = ExpressionChromosome.problem.getOperators().stream()
				.mapToInt(x -> x.getArity()).max().getAsInt();
		ExpressionChromosome.tailLeng = ExpressionChromosome.headLeng * (maxArity - 1) + 1;
		ExpressionChromosome.numItemsPorGen = ExpressionChromosome.headLeng + ExpressionChromosome.tailLeng;		
		ExpressionChromosome.numItems = ExpressionChromosome.numItemsPorGen*ExpressionChromosome.numGenes + 
				ExpressionChromosome.problem.getNumConstants();	
		ExpressionChromosome.constantsIndex = ExpressionChromosome.numItemsPorGen*ExpressionChromosome.numGenes;
		ExpressionChromosome.DIMENSION = ExpressionChromosome.numeroDeBits*ExpressionChromosome.numItems;
	}
	
	/**
	 * @param i Un indice a 
	 * @return
	 */
	private Integer getMax(int i) {		
		Integer r = null;
		if (i>=ExpressionChromosome.constantsIndex) {
			r = ExpressionChromosome.problem.getMaxValueConstant();
		} else {
			i = i%ExpressionChromosome.numItemsPorGen;
			if (i < ExpressionChromosome.headLeng) {
				r = this.getNumVariables()+this.getNumConstants()+this.getNumOperators();
			} else if (i < ExpressionChromosome.headLeng+ExpressionChromosome.tailLeng) {
				r = this.getNumVariables()+this.getNumConstants();
			} 
		}
		return r;
	}	

	private static Integer pow = Math2.pow(2., numeroDeBits).intValue();

	private List<VariableExp<T>> variables;
	private List<ConstantExp<T>> constants;
	private Exp<T> expression;
	private List<Integer> items;
	private double ft;
	private ProblemaAGExpression<?,T> prb;

	@SuppressWarnings("unchecked")
	public ExpressionChromosome(Integer[] representation)
			throws InvalidRepresentationException {
		super(representation);
		this.prb = (ProblemaAGExpression<?, T>) ExpressionChromosome.problem;		
		this.items = this.dcd();
		this.variables = this.getNumVariables(this.getNumVariables());
	    this.constants = this.getNumConstants(this.getNumConstants());
	    int operatorIndex = this.getNumVariables()+this.getNumConstants();;
	    List<Exp<T>> ls = Lists.newArrayList();
	    ls.addAll(this.variables);
	    ls.addAll(this.constants);
	    ls.addAll(this.prb.getOperators());
	    List<Exp<T>> expressions = Lists.newArrayList();
	    int index1 = 0;
		for (int i = 0; i < ExpressionChromosome.numGenes; i++) {
			int index2 = index1 +  ExpressionChromosome.numItemsPorGen;
			List<List<Exp<T>>> levels = Exp.levels(this.items.subList(index1, index2), operatorIndex, ls);
			Exp<T> expression = levels.get(0).get(0);
			index1 = index1+ ExpressionChromosome.numItemsPorGen;
			expressions.add(expression);
		}
		NaryExp<T> nExp = this.prb.getNaryExp().clone();
		nExp.setOps(expressions);
		this.expression = nExp;
		this.ft = this.calculateFt();
	}
	
	
	@SuppressWarnings("unchecked")
	public ExpressionChromosome(List<Integer> representation)
			throws InvalidRepresentationException {
		super(representation);
		this.prb = (ProblemaAGExpression<?, T>) ExpressionChromosome.problem;		
		this.items = this.dcd();
		this.variables = this.getNumVariables(this.getNumVariables());
	    this.constants = this.getNumConstants(this.getNumConstants());
	    int operatorIndex = this.getNumVariables()+this.getNumConstants();;
	    List<Exp<T>> ls = Lists.newArrayList();
	    ls.addAll(this.variables);
	    ls.addAll(this.constants);
	    ls.addAll(this.prb.getOperators());
	    List<Exp<T>> expressions = Lists.newArrayList();
	    int index1 = 0;
		for (int i = 0; i < ExpressionChromosome.numGenes; i++) {
			int index2 = index1 +  ExpressionChromosome.numItemsPorGen;
			List<List<Exp<T>>> levels = Exp.levels(this.items.subList(index1, index2), operatorIndex, ls);
			Exp<T> expression = levels.get(0).get(0);
			index1 = index1+ ExpressionChromosome.numItemsPorGen;
			expressions.add(expression);
		}
		NaryExp<T> nExp = this.prb.getNaryExp().clone();
		nExp.setOps(expressions);
		this.expression = nExp;
		this.ft = this.calculateFt();
	}

	@Override
	public AbstractListChromosome<Integer> newFixedLengthChromosome(List<Integer> ls) {
		return new ExpressionChromosome<T>(ls);
	}
	
	@Override
	public Exp<T> decode() {
		return this.getExp();
	}

	private List<Integer> dcd() {
		List<Integer> ls = super.getRepresentation();
		List<Integer> r = new ArrayList<Integer>();
		int index1 = 0;
		for (int i = 0; i < ExpressionChromosome.numItems; i++) {
			int index2 = index1 + numeroDeBits;
			Integer e = Lists2.decode(ls.subList(index1, index2));
			Integer d = Math2.escala(e, pow, this.getMax(i));
			r.add(d);
			index1 = index1 + numeroDeBits;
		}
		return r;
	}
	
	@Override
	public List<Integer> getRepresentation() {
		return super.getRepresentation();
	}
	
	public static ExpressionChromosome<?> getInitialChromosome() {
		List<Integer> ls = BinaryChromosome.randomBinaryRepresentation(ExpressionChromosome.DIMENSION);
		return new ExpressionChromosome<>(ls);
	}

	@Override
	public double fitness() {
		return ft;
	}

	private double calculateFt() {		
		return this.prb.fitnessFunction(this);
	}

	@Override
	public Exp<T> getExp() {
		return this.expression;
	}
	
	@Override
	public VariableExp<T> getVariable(int i) {
		return this.variables.get(i);
	}
	
	@Override
	public ConstantExp<T> getConstant(int i) {
		return this.constants.get(i);
	}
	
	@Override
	public Exp<T> getOperator(int i) {
		return this.prb.getOperators().get(i);
	}
	
	@Override
	public Integer getNumVariables() {
		return ExpressionChromosome.problem.getNumVariables();
	}

	@Override
	public Integer getNumConstants() {
		return ExpressionChromosome.problem.getNumConstants();
	}
	
	@Override
	public ProblemaAGExpression<?,T> getProblem() {
		return this.prb;
	}

	@Override
	public Integer getNumOperators() {
		return ExpressionChromosome.problem.getNumOperators();
	}
	
	@Override
	public Chromosome asChromosome() {
		return this;
	}	

	/**
	 * Identificadores de las variables a usar
	 */
	public static List<String> nombres = Arrays.asList("x","y","z"+"t","u","v"+"w");
			
	private List<VariableExp<T>> getNumVariables(int num){
		List<VariableExp<T>> ls = Lists.newArrayList();
		for(int i=0; i<num;i++){
			ls.add(Exp.<T>createVariable(nombres.get(i)));
		}
		return ls;
	}
	
	private List<ConstantExp<T>> getNumConstants(int num){
		List<ConstantExp<T>> ls = Lists.newArrayList();
		for(int i=0; i<num;i++){
			ls.add(Exp.<T>createConstant(this.prb.convert(this.items.get(ExpressionChromosome.constantsIndex+i))));
		}
		return ls;
	}

	
}

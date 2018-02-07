package us.lsi.ag.agchromosomes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.math3.genetics.AbstractListChromosome;
import org.apache.commons.math3.genetics.BinaryChromosome;
import org.apache.commons.math3.genetics.InvalidRepresentationException;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import us.lsi.ag.ProblemAG;
import us.lsi.ag.ExpressionProblemAG;
import us.lsi.math.Math2;
import us.lsi.tiposrecursivos.ConstantExp;
import us.lsi.tiposrecursivos.Exp;
import us.lsi.tiposrecursivos.NaryExp;
import us.lsi.tiposrecursivos.Operator;
import us.lsi.tiposrecursivos.VariableExp;


/**
 * @author Miguel Toro
 *
 * @param <T> Tipo del resultado de la expresión
 * 
 * <p> 
 * La implementación 
 * sigue las ideas de <a href="https://en.wikipedia.org/wiki/Gene_expression_programming"> Gene expression programming</a></p>
 * 
 * <p> Para formar la expresiones resultado de decodificar un cromosoma disponemos de p operadores, v variables y c constantes. 
 * Asumimos un cromosoma está formado por n genes más c constantes. 
 * Cada gen tiene r elementos separados en dos partes: la cabeza de longitud definida h 
 * y la cola de longitud t = h (ma - 1) + 1 siendo ma la maxima aridad de los operadores usados. 
 * Se cumple r = h+t. El número s de elementos del cromosoma es s = n*r+c. 
 * Asumimos, además que en la cabeza de cada gene hay operadores, numerados en el rango 0..p-1, 
 * variables numeradas en el rango 0..v-1 y constantes en el rango 0..c-1. </p>
 * 
 * <p> Modelamos un cromosoma con una lista de enteros de tamaño s que obtenemos de una de bits de tamaño s*b, 
 * siendo b el número de bits usado para representar un entero. Lo n primeros segmentos de tamaño r representan los n genes 
 * y el segmento final de m elementos las constantes. Dentro de cada gen el primer segmento de longitud h es la cabeza y estará formada 
 * por enteros en el rango 0..p+v+c-1. El segundo segmento es la cola de longitud t y formada por enteros en el rango 0..v+c+1. El segmento final 
 * de constantes de tamaño c está formado por enteros en el rango 0..c-1. Definimos sv = c, sp = c+v. 
 * Asumimos que un entero i en el rango 0..p+v+c-1 representa la constante 
 * i si 0 &le; i &lt; sv. Un entero i en el rango 0..p+v+c-1 representa la variable  
 * i-sv si sv &le; i &lt; sp. Un entero i en el rango 0..p+v+c-1 representa el operador 
 * i-sp si sp &le; i &lt; c+v+p. </p>
 * 
 */
public class ExpressionChromosome<T> extends BinaryChromosome implements us.lsi.ag.Chromosome<Exp<T>> {

	/**
	 * Número de bits para codificar cada uno de los valores enteros resultantes
	 */
	public static Integer numeroDeBits = 5;

	/**
	 * Problema a resolver
	 */
	private static ExpressionProblemAG<?,?> problem;

	/**
	 * Longitud de la cabeza de cada uno de los genes
	 */
	public static Integer headLeng;	

	/**
	 * Longitud de la cola de un gen
	 */
	private static Integer tailLeng;
	
	/**
	 * Número de Genes del cromosoma
	 */
	public static Integer numGenes;
	
	/**
	 * Numero de elmentos de un gen.  numItemsPorGen = headLeng+tailLeng
	 */
	private static Integer numItemsPorGen;

	/**
	 * Numero de Items totales en el cromosoma
	 */
	private static Integer numItemsPorCromosoma;
	
	private static Integer numConstants;
	
	private static Integer numVariables;
	
	private static Integer numOperators;
	
	/**
	 * Indice de la primera contante en el cromosoma.
	 * Las contantes están ubicadas después de los genes
	 */
	private static Integer constantsIndex;
	
	/**
	 * El indice de la primera variable en la lista allOperators
	 */
	@SuppressWarnings("unused")
	private static Integer variableIndex;
	
	/**
	 * El indice del primer operador en la lista allOperators
	 */
	@SuppressWarnings("unused")
	private static Integer operatorIndex;
		
	/**
	 * El rango de valores que puede tomar cada casilla del cromosoma.
	 * La casilla i contendrá un valor tal que 0 &lg; dcd.get(i) &lt;maxRanges.get(i)
	 */
	private static List<Integer> maxRanges;	
	
	/**
	 * Dimensión del cromosoma. dimension = numItems*numeroDeBits
	 */
	private static int dimension;
	
	/**
	 * Las variables disponibles
	 */
	private static List<VariableExp<?>> variables;
	
	/**
	 * Las constantes disponibles
	 */
	private static List<ConstantExp<?>> constants;
	
	/**
	 * Operadores disponibles
	 */
	private static List<Operator> operators;
	/**
	 * Una secuencia formada por las constantes, las variables y los operadores disponibles
	 */
	private static List<Operator> allOperators = new ArrayList<>();
	
	/**
	 * Identificadores de las variables a usar
	 */
	public static List<String> nombresDeVariables = Arrays.asList("x","y","z"+"t","u","v"+"w");
	
	
	/**
	 * Identificadores de las constantes a usar
	 */
	public static List<String> nombresDeConstantes = Arrays.asList("a","b","c"+"d","e","f"+"g");
	
	/**
	 * @pos Inicializa los parámetros de la clase
	 * @param problema El problema a resolver
	 * @param <T> El tipo de la expresión que representa el cromosoma
	 */
	public static <T> void iniValues(ProblemAG problema) {
		problem = (ExpressionProblemAG<?, ?>) problema;
		operators = problem.getOperators();
		int maxArity = problem.getOperators().stream().mapToInt(x -> x.getArity()).max().getAsInt();
		headLeng = problem.getHeadLength();
		tailLeng = headLeng * (maxArity - 1) + 1;
		numItemsPorGen = headLeng + tailLeng;	
		numConstants = problem.getNumConstants();
		numVariables = problem.getNumVariables();
		numOperators = problem.getOperators().size();	
		numGenes = problem.getNumGenes();
		numItemsPorCromosoma = numItemsPorGen*numGenes + numConstants;	
		dimension = numeroDeBits*numItemsPorCromosoma;
		constantsIndex = numGenes*numItemsPorGen;
		variableIndex = numConstants;
		operatorIndex = numConstants+numVariables;
		maxRanges = new ArrayList<>();
		maxRanges = IntStream.range(0,numItemsPorCromosoma).map(i->getMax(i)).boxed().collect(Collectors.toList());
		List<ConstantExp<T>> nConstants = getConstants(numConstants);
		constants = nConstants.stream().map(x->(ConstantExp<?>)x).collect(Collectors.toList());
		allOperators.addAll(nConstants);
		List<VariableExp<T>> nVariables = getVariables(numVariables);
		variables = nVariables.stream().map(x->(VariableExp<?>)x).collect(Collectors.toList());
		allOperators.addAll(nVariables);
		allOperators.addAll(operators);
	}
	
	/**
	 * @param i Un indice en maxRanges 
	 * @return Un valor que especifica en rango de valores en maxRanges[i]
	 */
	private static Integer getMax(int i) {		
		Integer r = null;
		if (i>=constantsIndex) {
			r = problem.getMaxValueConstant();
		} else {
			i = i%numItemsPorGen;
			if (i < headLeng) {
				r = numConstants+ numVariables + numOperators;
			} else if (i < headLeng+ tailLeng) {
				r = numConstants + numVariables;
			} 
		}
		return r;
	}	
	
	private static <R> List<VariableExp<R>> getVariables(int num){
		List<VariableExp<R>> ls = Lists.newArrayList();
		for(int i=0; i<num;i++){
			ls.add(Exp.<R>variable(nombresDeVariables.get(i)));
		}
		return ls;
	}
	
	private static <R> List<ConstantExp<R>> getConstants(int num){
		List<ConstantExp<R>> ls = Lists.newArrayList();
		for(int i=0; i<num;i++){
			ls.add(Exp.<R>constant(nombresDeConstantes.get(i)));
		}
		return ls;
	}
	
	private Exp<T> expression;
	/**
	 * Los items del cromosoma decodificado
	 */
	private List<Integer> items;
	private List<Operator> operatorsByGen;
	private double ft;

	public ExpressionChromosome(Integer[] representation) throws InvalidRepresentationException {		
		this(Arrays.asList(representation));
	}
	
	public ExpressionChromosome(List<Integer> representation) throws InvalidRepresentationException {
		super(representation);	
		this.items = this.dcd();
		List<Exp<T>> expressions = new ArrayList<>();
		Exp<T> e = null;
		for(int i=0; i<numConstants;i++){
			getConstant(i).setValue(getConstValue(i));
		}
		for(int i=0; i<numGenes;i++){
			operatorsByGen = getGen(i).stream().map(x->allOperators.get(x)).collect(Collectors.toList());
			e = Exp.exp(operatorsByGen);
		}
		expressions.add(e);
		NaryExp<T,T> nExp = Exp.nary(expressions,this.getProblem().getAccumulator());
		this.expression = nExp;
		this.ft = calculateFt();
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
		List<Integer> d = Math2.decodesInScala(ls, numItemsPorCromosoma, numeroDeBits,maxRanges);
		return d;
	}
		
	private List<Integer> getGen(int i){
		Preconditions.checkArgument(i < numGenes);
		return this.items.subList(numItemsPorGen*i,numItemsPorGen*i+numItemsPorGen);
	}
	
	private T getConstValue(int i){
		return getProblem().convert(this.items.get(constantsIndex+i));
	}
	
	@Override
	public List<Integer> getRepresentation() {
		return super.getRepresentation();
	}
	
	public static ExpressionChromosome<?> getInitialChromosome() {
		List<Integer> ls = BinaryChromosome.randomBinaryRepresentation(ExpressionChromosome.dimension);
		return new ExpressionChromosome<>(ls);
	}

	@Override
	public double fitness() {
		return ft;
	}

	private double calculateFt() {		
		return this.getProblem().fitnessFunction(this);
	}


	public Exp<T> getExp() {
		return this.expression;
	}
	
	@SuppressWarnings({ "unchecked"})
	public VariableExp<T> getVariable(int i) {
		return (VariableExp<T>)variables.get(i);
	}
	
	@SuppressWarnings("unchecked")
	public ConstantExp<T> getConstant(int i) {
		return (ConstantExp<T>)constants.get(i);
	}
	
	public Operator getOperator(int i) {
		return operators.get(i);
	}
	

	public Integer getNumVariables() {
		return problem.getNumVariables();
	}

	public Integer getNumConstants() {
		return problem.getNumConstants();
	}
	
	@SuppressWarnings("unchecked")
	public ExpressionProblemAG<?,T> getProblem() {
		return (ExpressionProblemAG<?,T>)ExpressionChromosome.problem;
	}

	public Integer getNumOperators() {
		return numOperators;
	}
	
}

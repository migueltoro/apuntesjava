package us.lsi.ag.expression;

import us.lsi.ag.AlgoritmoAG;
import us.lsi.ag.ProblemaAGExpression;
import us.lsi.ag.agchromosomes.ChromosomeFactory;
import us.lsi.ag.agchromosomes.ExpressionChromosome;
import us.lsi.ag.agchromosomes.IExpressionChromosome;
import us.lsi.ag.agstopping.StoppingConditionFactory;
import us.lsi.ag.agstopping.StoppingConditionFactory.StoppingConditionType;
import us.lsi.algoritmos.Algoritmos;
import us.lsi.common.Exp;

public class TestExpression {

	
	public static void main(String[] args) {
		
		AlgoritmoAG.ELITISM_RATE  = 0.30;
		AlgoritmoAG.CROSSOVER_RATE = 0.8;
		AlgoritmoAG.MUTATION_RATE = 0.7;
		AlgoritmoAG.POPULATION_SIZE = 100;
		
		StoppingConditionFactory.NUM_GENERATIONS = 10000;
		StoppingConditionFactory.stoppingConditionType = StoppingConditionType.SolutionsNumber;
		StoppingConditionFactory.FITNESS_MIN = -500.;
		StoppingConditionFactory.SOLUTIONS_NUMBER_MIN = 1;
		
		ChromosomeFactory.crossoverType = ChromosomeFactory.CrossoverType.OnePoint;
		
		ExpressionChromosome.headLeng = 2;
		ExpressionChromosome.numGenes = 5;
		
		ProblemaAGExpression<Exp<Double>, Double> p = new Expression();
		AlgoritmoAG ap = Algoritmos.createAG(ChromosomeFactory.ChromosomeType.Expression,p);
		ap.ejecuta();
		
		IExpressionChromosome<Double> cr = ChromosomeFactory.asExpression(ap.getBestFinal());
		System.out.println("================================");
		System.out.println(p.getSolucion(cr)+","+(cr.fitness()));
		System.out.println("================================");

	}

}

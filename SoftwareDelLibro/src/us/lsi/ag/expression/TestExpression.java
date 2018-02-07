package us.lsi.ag.expression;

import us.lsi.ag.ExpressionProblemAG;
import us.lsi.ag.agchromosomes.AlgoritmoAG;
import us.lsi.ag.agchromosomes.ChromosomeFactory;
import us.lsi.ag.agchromosomes.ExpressionChromosome;
import us.lsi.ag.agstopping.StoppingConditionFactory;
import us.lsi.ag.agstopping.StoppingConditionFactory.StoppingConditionType;
import us.lsi.algoritmos.Algoritmos;
import us.lsi.tiposrecursivos.Exp;

public class TestExpression {

	
	public static void main(String[] args) {
		
		AlgoritmoAG.ELITISM_RATE  = 0.30;
		AlgoritmoAG.CROSSOVER_RATE = 0.8;
		AlgoritmoAG.MUTATION_RATE = 0.7;
		AlgoritmoAG.POPULATION_SIZE = 50;
		
		StoppingConditionFactory.NUM_GENERATIONS = 50000;
		StoppingConditionFactory.stoppingConditionType = StoppingConditionType.SolutionsNumber;
		StoppingConditionFactory.FITNESS_MIN = -1.;
		StoppingConditionFactory.SOLUTIONS_NUMBER_MIN = 1;
		
		ChromosomeFactory.crossoverType = ChromosomeFactory.CrossoverType.OnePoint;
		
		ExpressionProblemAG<Exp<Double>, Double> p = new Expression();
		AlgoritmoAG ap = Algoritmos.createAG(ChromosomeFactory.ChromosomeType.Expression,p);
		ap.ejecuta();
		
		ExpressionChromosome<Double> cr = ChromosomeFactory.<Double>asExpression(ap.getBestFinal());
		System.out.println("================================");
		System.out.println(p.getSolucion(cr).simplify()+","+(cr.fitness()));
		System.out.println("================================");
		for(int i=0;i<cr.getNumConstants();i++){
			System.out.println(String.format("%s = %f",cr.getConstant(i).getName(),cr.getConstant(i).getValue()));
		}

	}

}

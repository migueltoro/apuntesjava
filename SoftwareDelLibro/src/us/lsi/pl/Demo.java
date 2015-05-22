package us.lsi.pl;

import lpsolve.*;

public class Demo {

  public static void main(String[] args) {
    try {
      // Create a problem with 4 variables and 0 constraints
      LpSolve solver = LpSolve.makeLp(0, 4);

      // add constraints
      double r1[] = {3, 2, 2, 1};
      double r2[] = {0, 3, 3, 1};
      double r3[] = {2, 3, -2, 3};
//      solver.strAddConstraint("3 2 2 1", LpSolve.LE, 4);
//      solver.strAddConstraint("0 4 3 1", LpSolve.LE, 3);

      solver.addConstraint(r1, LpSolve.LE, 4);
      solver.addConstraint(r2, LpSolve.GE, 3);
      // set objective function
 //     solver.strSetObjFn("2 3 -2 3");
      solver.setObjFn(r3);
      
      solver.setMaxim();;;
      // solve the problem
      solver.solve();
System.out.println("______________________");
      solver.printConstraints(0);;
      solver.printConstraints(1);
      solver.printTableau();
      solver.printSolution(0);
System.out.println("______________________");     
      // print solution
      System.out.println("Value of objective function: " + solver.getObjective());
      double[] var = solver.getPtrVariables();
      for (int i = 0; i < var.length; i++) {
        System.out.println("Value of var[" + i + "] = " + var[i]);
      }

      // delete the problem and free memory
      solver.deleteLp();
    }
    catch (LpSolveException e) {
       e.printStackTrace();
    }
  }

}

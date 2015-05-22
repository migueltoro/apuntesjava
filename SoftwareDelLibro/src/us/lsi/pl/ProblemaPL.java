package us.lsi.pl;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.math3.optim.linear.LinearConstraint;
import org.apache.commons.math3.optim.linear.LinearObjectiveFunction;
import org.apache.commons.math3.optim.linear.Relationship;

import us.lsi.common.StringExtensions2;
import us.lsi.common.Par;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

public class ProblemaPL implements IProblemaPL  {
	
	
	/**
	 * Los tipos que puede tomar una variable en el problema 
	 * 	 
	 */
	public enum TipoDeVariable{Real,Entera,Binaria}
	
	/**
	 * Si el problema es de Maximización o de Minimización
	 *
	 */
	public enum TipoDeOptimizacion{Max,Min}
	
	
	
	/**
	 * @param getNumOfVariables Número de variables
	 * @param tipo El tipo de optimización
	 * @return Un problema sin restricciones y sin función objetivo
	 */
	public static ProblemaPL create(Integer getNumOfVariables,TipoDeOptimizacion tipo) {
		return new ProblemaPL(getNumOfVariables, tipo);
	}


	private LinearObjectiveFunction objectiveFunction;
	private Collection<LinearConstraint> constraints;
	private TipoDeOptimizacion tipo;
	private Integer numOfVariables;
	private Map<Integer,TipoDeVariable> tiposDeVariables;
	private Set<Integer> variablesSemicontinuas;
	private Set<Integer> variablesLibres;
	private Set<Par<List<Integer>,Integer>> sos;
	private Map<Integer,String> nombres;

	
	private ProblemaPL(Integer numOfVariables, TipoDeOptimizacion tipo) {
		super();
		this.objectiveFunction = null;
		this.constraints = Lists.newArrayList();
		this.tipo = tipo;
		this.numOfVariables = numOfVariables;
		this.tiposDeVariables = Maps.newHashMap();
		this.variablesLibres = Sets.newHashSet();
		this.variablesSemicontinuas = Sets.newHashSet();
		this.sos = Sets.newHashSet();
		this.nombres = Maps.newHashMap();
	}
		
	
	/* (non-Javadoc)
	 * @see us.lsi.pl.IProblemaPL#getTipo()
	 */
	@Override
	public TipoDeOptimizacion getTipo() {
		return tipo;
	}


	/* (non-Javadoc)
	 * @see us.lsi.pl.IProblemaPL#getObjectiveFunction()
	 */
	@Override
	public LinearObjectiveFunction getObjectiveFunction() {
		return objectiveFunction;
	}

	
	/* (non-Javadoc)
	 * @see us.lsi.pl.IProblemaPL#setObjectiveFunction(org.apache.commons.math3.optim.linear.LinearObjectiveFunction)
	 */
	@Override
	public void setObjectiveFunction(LinearObjectiveFunction objectiveFunction) {
		Preconditions.checkArgument(objectiveFunction.getCoefficients().toArray().length == this.numOfVariables);
		this.objectiveFunction = objectiveFunction;
	}

	/* (non-Javadoc)
	 * @see us.lsi.pl.IProblemaPL#setObjectiveFunction(double[], double)
	 */
	@Override
	public void setObjectiveFunction(double[] coefficients, double constantTerm) {
		Preconditions.checkArgument(coefficients.length == this.numOfVariables);
		this.objectiveFunction = new LinearObjectiveFunction(coefficients,constantTerm);
	}

	/* (non-Javadoc)
	 * @see us.lsi.pl.IProblemaPL#getConstraints()
	 */
	@Override
	public Collection<LinearConstraint> getConstraints() {
		return constraints;
	}
	
	/* (non-Javadoc)
	 * @see us.lsi.pl.IProblemaPL#addConstraint(org.apache.commons.math3.optim.linear.LinearConstraint)
	 */
	@Override
	public void addConstraint(LinearConstraint lc){
		Preconditions.checkArgument(lc.getCoefficients().toArray().length == this.numOfVariables);
		this.constraints.add(lc);
	}

	/* (non-Javadoc)
	 * @see us.lsi.pl.IProblemaPL#addConstraint(double[], org.apache.commons.math3.optim.linear.Relationship, double)
	 */
	@Override
	public void addConstraint(double[] coefficients,Relationship relationship, double value){
		Preconditions.checkArgument(coefficients.length == this.numOfVariables);
		this.constraints.add(new LinearConstraint(coefficients,relationship,value));
	}
	
	/* (non-Javadoc)
	 * @see us.lsi.pl.IProblemaPL#addSosConstraint(java.util.List, java.lang.Integer)
	 */
	@Override
	public void addSosConstraint(List<Integer> ls, Integer nv){
		sos.add(Par.create(ls, nv));
	}
	
	/* (non-Javadoc)
	 * @see us.lsi.pl.IProblemaPL#getGetNumOfVariables()
	 */
	@Override
	public Integer getNumOfVariables() {
		return numOfVariables;
	}

	
	/* (non-Javadoc)
	 * @see us.lsi.pl.IProblemaPL#setTipoDeVariable(int, us.lsi.pl.ProblemaPL.TipoDeVariable)
	 */
	@Override
	public void setTipoDeVariable(int e, TipoDeVariable tipo){
		this.tiposDeVariables.put(e, tipo);
	}
	
	/* (non-Javadoc)
	 * @see us.lsi.pl.IProblemaPL#setTipoDeTodasLasVariables(us.lsi.pl.ProblemaPL.TipoDeVariable)
	 */
	@Override
	public void setTipoDeTodasLasVariables(TipoDeVariable tipo){	
		for (int i = 0; i < this.numOfVariables; i++) {
			this.tiposDeVariables.put(i, tipo);
		}
	}
	
	/* (non-Javadoc)
	 * @see us.lsi.pl.IProblemaPL#setVariableLibre(int)
	 */
	@Override
	public void setVariableLibre(int e){
		this.variablesLibres.add(e);
	}
	
	/* (non-Javadoc)
	 * @see us.lsi.pl.IProblemaPL#setVariableSemicontinua(int)
	 */
	@Override
	public void setVariableSemicontinua(int e){
		this.variablesSemicontinuas.add(e);
	}
	
	/* (non-Javadoc)
	 * @see us.lsi.pl.IProblemaPL#getVariablesEnteras()
	 */
	@Override
	public List<Integer> getVariablesEnteras(){
		return tiposDeVariables.keySet().stream().filter(x->tiposDeVariables.get(x).equals(TipoDeVariable.Entera)).collect(Collectors.toList());
	}
	
	/* (non-Javadoc)
	 * @see us.lsi.pl.IProblemaPL#getVariablesBinarias()
	 */
	@Override
	public List<Integer> getVariablesBinarias(){
		return tiposDeVariables.keySet().stream().filter(x->tiposDeVariables.get(x).equals(TipoDeVariable.Binaria)).collect(Collectors.toList());
	}
	
	/* (non-Javadoc)
	 * @see us.lsi.pl.IProblemaPL#setNombre(java.lang.Integer, java.lang.String)
	 */
	@Override
	public void setNombre(Integer e, String s){
		nombres.put(e, s);
	}
	
	/* (non-Javadoc)
	 * @see us.lsi.pl.IProblemaPL#getNombre(java.lang.Integer)
	 */
	@Override
	public String getNombre(Integer e){
		String r;
		if(nombres.containsKey(e)){
			r = nombres.get(e);
		} else {
			r = "x"+e;
		}
		return r;
	}
	
	/* (non-Javadoc)
	 * @see us.lsi.pl.IProblemaPL#getVariablesLibres()
	 */
	@Override
	public List<Integer> getVariablesLibres(){
		return this.variablesLibres.stream().collect(Collectors.toList());
	}
	
	/* (non-Javadoc)
	 * @see us.lsi.pl.IProblemaPL#getVariablesSemicontinuas()
	 */
	@Override
	public List<Integer> getVariablesSemicontinuas(){
		return this.variablesSemicontinuas.stream().collect(Collectors.toList());
	}
	
	public String toString(double[] datos){
		String s = "";
		for(int i =0; i< datos.length; i++){
			if(datos[i]==1.){
				s=s+" +"+this.getNombre(i);
			}else if(datos[i] == -1.){
				s=s+" -"+this.getNombre(i);
			}else if(datos[i]< 0.){
				s=s+" " +datos[i]+" "+this.getNombre(i);
			} else if(datos[i]> 0.){
				s=s+" "+"+"+datos[i]+" "+this.getNombre(i);
			}
		}
		return s;
	}
	
	public String toString(LinearConstraint lc){
		String r = "";
		r = r+toString(lc.getCoefficients().toArray());
		if(lc.getRelationship().equals(Relationship.EQ))
			r = r+" = ";
		else if(lc.getRelationship().equals(Relationship.GEQ)){
			r = r+" >= ";
		}else {
			r = r+" <= ";
		}
		r = r +lc.getValue();
		r = r + ";\n";
		return r;
	}
	
	public String toString(LinearObjectiveFunction lc){
		String s = tipo.equals(TipoDeOptimizacion.Min)?"min: ":"max: ";
		s = s+ toString(lc.getCoefficients().toArray());
		s = s + ";\n\n";
		return s;
	}
	
	public String toStringVariablesEnteras(){
		List<Integer> ls = this.getVariablesEnteras();
		String r = "";		
		if (!ls.isEmpty()) {
			r = ls.stream()
					  .map(x->this.getNombre(x))
					  .collect(Collectors.joining(" ,", "int ",  ";\n\n"));	
		}	
		return r;
	}
	
	public String toStringVariablesBinarias(){
		List<Integer> ls = this.getVariablesBinarias();
		String r = "";		
		if (!ls.isEmpty()) {
			r = ls.stream()
				  .map(x->this.getNombre(x))
				  .collect(Collectors.joining(" ,", "bin ",  ";\n\n"));	
		}		
		return r;
	}
	
	public String toStringVariablesLibres(){
		List<Integer> ls = this.getVariablesLibres();
		String r = "";		
		if (!ls.isEmpty()) {
			r = ls.stream()
					  .map(x->this.getNombre(x))
					  .collect(Collectors.joining(" ,", "free ",  ";\n\n"));
		}		
		return r;
	}
	
	public String toStringVariablesSemicontinuas(){
		List<Integer> ls = this.getVariablesSemicontinuas();
		String r = "";		
		if (!ls.isEmpty()) {
			r = ls.stream()
					  .map(x->this.getNombre(x))
					  .collect(Collectors.joining(" ,", "smc ",  ";\n\n"));			
	}		
		return r;
	}
	
	
	public String toStringSos(){
		List<Par<List<Integer>,Integer>> ls = this.sos.stream().collect(Collectors.toList());
		String r = "";		
		if (!ls.isEmpty()) {
			int[] pos = {0};
			r = ls.stream()
				  .map((Par<List<Integer>,Integer> tp)->
					          tp.getP1().stream()
							  .map(x->this.getNombre(x))
							  .collect(Collectors.joining(" ,", "SOS"+pos[0]+": "," <= " + tp.getP2()+";\n\n")))
				  .peek(x->{pos[0]=pos[0]+1;})
			      .collect(Collectors.joining("","sos \n",""));
		}		
		return r;
	}
	
	
	/* (non-Javadoc)
	 * @see us.lsi.pl.IProblemaPL#toStringConstraints()
	 */
	@Override
	public String toStringConstraints(){
		String restricciones ="";
		restricciones+=toString(this.objectiveFunction);
		for(LinearConstraint lc: this.constraints){
			restricciones+=this.toString(lc);
		}
		restricciones+="\n";
		restricciones+= toStringVariablesEnteras();
		restricciones+= toStringVariablesBinarias();
		restricciones+= toStringVariablesLibres();
		restricciones+= toStringVariablesSemicontinuas();
		restricciones+= toStringSos();
		return restricciones;
	}
	
	/* (non-Javadoc)
	 * @see us.lsi.pl.IProblemaPL#toStringConstraints(java.lang.String)
	 */
	@Override
	public void toStringConstraints(String fichero){
		StringExtensions2.toFile(this.toStringConstraints(), fichero);
	}
}

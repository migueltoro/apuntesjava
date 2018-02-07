package us.lsi.pd;

import java.util.List;

import com.google.common.collect.Lists;

import us.lsi.math.Math2;
import us.lsi.pd.AlgoritmoPD.Sp;
import us.lsi.tiposrecursivos.Tree;

public class AlgunosTestsPD<S, A> {
	
	

	
	/**
	 * Dado un problema se ejecuta el árbol de alternativas sobre el mismo
	 * consiguiendo subproblemas sucesivos y posteriormente se calcula la
	 * solución parcial y la reconstruida
	 * 
	 * @param <S> Tipo de la solución
	 * @param <A> Tipo de la alternativa
	 * @param p Un Estado inicial
	 * @param alternativas Un árbol de alternativas
	 * @return La solución parcial del problema
	 */
	public static <S, A> Sp<A> test1(ProblemaPD<S, A> p, Tree<A> alternativas) {
		Sp<A> r = null;
		System.out.println("Avanza");
		if (p.esCasoBase()) {
			System.out.println("Es Caso Base = " + p);
			r = p.getSolucionParcialCasoBase();
		} else if (p.estaFueraDeRango()) {
			System.out.println("Está Fuera de Rango = " + p);
			r = null;
		} else {
			System.out.println("Problema = " + p);
			List<A> la = p.getAlternativas();
			System.out.println("Alternativas = " + la);
			if (!la.isEmpty()) {
				A a = alternativas.getLabel();
				System.out.println("Contiene la alternativa = "
						+ la.contains(a));
				Integer np = p.getNumeroSubProblemas(a);
				List<Sp<A>> lsp = Lists.newArrayList();
				Sp<A> solp = null;
				boolean haySolucion= true;
				for (int i = 0; i < np; i++) {
					ProblemaPD<S, A> sp = p.getSubProblema(a, i);
					solp = test1(sp, alternativas.getElement(i));
					if(solp == null){
						haySolucion = false;
					}
					lsp.add(solp);
				}
				if (haySolucion) {
					r = p.getSolucionParcialPorAlternativa(a, lsp);
				}else {
					r = null;
				}
				System.out.println("Problema = " + p + ", Solucion Parcial = " + r);
			}
		}
		return r;
	}

	/**
	 * Dado un problema se ejecutan acciones aleatorias, escogidas entre las
	 * posibles para cada problema, consiguiendo subproblemas sucesivos y
	 * posteriormente se calcula la solución parcial y la reconstruida
	 * 
	 * @param <S> Tipo de la solución
	 * @param <A> Tipo de la alternativa
	 * @param p Un Estado inicial
	 * @return Un árbol de alternativas escogido aleatoriamente
	 */
	public static <S, A> Tree<A> test2(ProblemaPD<S, A> p) {
		Tree<A> r = null;
		if (p.esCasoBase() || p.estaFueraDeRango()) {
			r = Tree.empty();
		} else {
			List<A> alternativas = p.getAlternativas();
			A a = null;
			if (!alternativas.isEmpty()) {
				Integer n = Math2.getEnteroAleatorio(0, alternativas.size());
				a = alternativas.get(n);
				int np = p.getNumeroSubProblemas(a);
				r = Tree.leaf(a);
				List<Tree<A>> la = Lists.newArrayList();
				for (int i = 0; i < np; i++) {
					ProblemaPD<S,A> sp = p.getSubProblema(a, i);
					Tree<A> th = test2(sp);
					la.add(th);
				}
				r = Tree.nary(a,la);
			} else {
				r = Tree.empty();
			}
		}
		return r;
	}
}


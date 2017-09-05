package us.lsi.pd;

import java.util.List;

import com.google.common.base.Preconditions;

import us.lsi.pd.AlgoritmoPD.Sp;

/**
 * <p> Una adaptación del {@link us.lsi.pd.ProblemaPD ProblemaPD} para el caso de reducción.
 * </p>
 * 
 * <p>Una explicación puede encontrarse en <a href="../../../document/ExpPD.html" target="_blank">Programación Dinámica</a></p>
 * 
 * 
 * @author Miguel Toro
 *
 * @param <S> El tipo de la solución
 * @param <A> El tipo de la alternativa
 */

public interface ProblemaPDR<S, A> extends ProblemaPD<S, A> {
	
	@Override
	default int getNumeroSubProblemas(A a){
		return 1;
	}
	
	@Override
	default ProblemaPD<S,A> getSubProblema(A a, int np) {
		Preconditions.checkArgument(np==0);
		return getSubProblema(a);
	}
	
	ProblemaPD<S,A> getSubProblema(A a);
	
	@Override
	default Sp<A> getSolucionParcialPorAlternativa(A a , List<Sp<A>> ls){
		return getSolucionParcialPorAlternativa(a ,ls.get(0));
	}
	
	Sp<A> getSolucionParcialPorAlternativa(A a ,Sp<A> sp);
	
	@Override
	default S getSolucionReconstruidaCasoRecursivo(Sp<A> sp, List<S> ls){
		return getSolucionReconstruidaCasoRecursivo(sp,ls.get(0));
	}
	
	S getSolucionReconstruidaCasoRecursivo(Sp<A> sp, S ls);
	
	

}

package us.lsi.pd.numerodearboles;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.google.common.base.Preconditions;

import us.lsi.pd.AlgoritmoPD.Sp;
import us.lsi.pd.ProblemaPD;

public class NumeroDeArbolesNArios implements ProblemaPD<Integer, Integer> {

	public static NumeroDeArbolesNArios create(Integer n) {
		return new NumeroDeArbolesNArios(n, 1, 0);
	}
	
	public static NumeroDeArbolesNArios create(Integer n, Integer m, Integer t) {
		return new NumeroDeArbolesNArios(n, m, t);
	}

	private Integer n; //Numero de vértices
	private Integer m; //Tamaño de la lista de árboles
	private Integer t; //Nivel
	public static Integer nmh;  //Número máximo de hijos	

	private NumeroDeArbolesNArios(Integer n, Integer m, Integer t) {
		super();
		this.n = n;
		this.m = m;
		this.t = t;
	}

	@Override
	public ProblemaPD.Tipo getTipo() {
		return Tipo.Otro;
	}

	@Override
	public int size() {
		return 0;
	}

	@Override
	public boolean esCasoBase() {
		return this.n==0 || this.m==0;
	}

	@Override
	public Sp<Integer> getSolucionParcialCasoBase() {
		Sp<Integer> r = null;
		if (n==0) {
			r = Sp.create(null, 1.);
		}else if(m==0){			
			r = null;
		}
		return r;
	}

	@Override
	public Sp<Integer> getSolucionParcial(List<Sp<Integer>> ls) {
		Double s= ls.stream().mapToDouble(x->x.propiedad).sum();
		return Sp.create(null,s);
	}

	@Override
	public ProblemaPD<Integer, Integer> getSubProblema(Integer a, int np) {
		NumeroDeArbolesNArios r;
		if(np==0) {
			if (a==0) {
				r = NumeroDeArbolesNArios.create(0,1,t);
			} else{
				r = NumeroDeArbolesNArios.create(a-1,NumeroDeArbolesNArios.nmh,t+1);
			}
		} else 
			r = NumeroDeArbolesNArios.create(n-a, m-1, t);
		return r;
	}

	@Override
	public Sp<Integer> getSolucionParcialPorAlternativa(Integer a, List<Sp<Integer>> ls) {
		return Sp.create(a, ls.get(0).propiedad*ls.get(1).propiedad);
	}

	@Override
	public List<Integer> getAlternativas() {
		Preconditions.checkArgument(this.n>=0);
		return IntStream.rangeClosed(0,this.n).boxed().collect(Collectors.toList());
	}

	@Override
	public int getNumeroSubProblemas(Integer a) {
		return 2;
	}

	@Override
	public Integer getSolucionReconstruidaCasoBase(Sp<Integer> sp) {
		return null;
	}

	@Override
	public Integer getSolucionReconstruidaCasoRecursivo(Sp<Integer> sp, List<Integer> ls) {
		return null;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((m == null) ? 0 : m.hashCode());
		result = prime * result + ((n == null) ? 0 : n.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof NumeroDeArbolesNArios))
			return false;
		NumeroDeArbolesNArios other = (NumeroDeArbolesNArios) obj;
		if (m == null) {
			if (other.m != null)
				return false;
		} else if (!m.equals(other.m))
			return false;
		if (n == null) {
			if (other.n != null)
				return false;
		} else if (!n.equals(other.n))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "[n=" + n + ", m=" + m + "]";
	}

	
}

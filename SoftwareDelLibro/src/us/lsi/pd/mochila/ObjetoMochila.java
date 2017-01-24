package us.lsi.pd.mochila;

/**
 * <p> Esta clase implementa el tipo ObjetoMochila.</p>
 * <p> Las propiedades de estos problemas son: </p>
 * <ul>
 * <li> Código
 * <li> Valor
 * <li> Peso
 * <li> Número máximo de unidades
 * </ul> 
 * 
 * 
 * 
 * @author Miguel Toro
 *
 */
public class ObjetoMochila implements Comparable<ObjetoMochila>{
	
	public static ObjetoMochila create(Integer valor,Integer peso, Integer countMax) {
		return new ObjetoMochila(valor, peso, countMax);
	}

	/**
	 * @param s Una línea de un fichero de texto
	 * @return Construye un objeto mochila a partir de una línea de un fichero
	 */
	public static ObjetoMochila create(String s) {
		return new ObjetoMochila(s);
	}
	
	private static Integer nCodigo = 0;
	
	private Integer codigo;
	private Integer valor;
	private Integer peso;
	private Integer numMaxDeUnidades;
	
	ObjetoMochila(Integer valor, Integer peso, Integer countMax){
		this.codigo = nCodigo;
		nCodigo++;
		this.valor = valor;
		this.peso = peso;
		this.numMaxDeUnidades = countMax;
	}	
	ObjetoMochila(String s){		
		String[] v = s.split("[ ,]");
		Integer ne = v.length;
		if(ne != 3) throw new IllegalArgumentException("Formato no adecuado en línea  "+s);	
		valor = new Integer(v[0]);
		peso = new Integer(v[1]);
		numMaxDeUnidades = new Integer(v[2]);
		this.codigo = nCodigo;
		nCodigo++;
	}	
	public Integer getPeso() {
		return peso;
	}
	public Integer getValor() {
		return valor;
	}
	public Integer getCodigo() {
		return codigo;
	}
	public Integer getNumMaxDeUnidades() {
		return numMaxDeUnidades;
	}
	
	@Override
	public int compareTo(ObjetoMochila o) {
		int r = getRatioValorPeso().compareTo(o.getRatioValorPeso());
		if(r == 0){
			r = getCodigo().compareTo(o.getCodigo());
		}
		return r;
	}	
	public Double getRatioValorPeso() {
		return ((double)valor)/peso;
	}
	
	@Override
	public String toString() {
		return "<"+codigo+","+valor+","+peso+","+numMaxDeUnidades+">";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ObjetoMochila))
			return false;
		ObjetoMochila other = (ObjetoMochila) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
	
	
	
}

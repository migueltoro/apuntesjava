package us.lsi.algoritmos;

public class Metricas {
	private int numeroDeIteraciones =0;
	private int numeroDeLLamadasRecursivas=0;
	private int numeroDePasos=0;
	private int numeroDeUsosDeLaMemoria=0;
	private long tiempoDeEjecucionInicial;
	private long tiempoDeEjecucionFinal;
	private int numeroDeCasosBase=0;
	private int numeroDeVecesQueFiltra =0;
	private int numeroEstadosFinales = 0;
	private int numeroDeSolucionesEncontradas = 0;
	private int numeroDeMejoresSolucionesEncontradas = 0;	

	private static Metricas metricas = null;
	
	public Metricas(){		
	}
	
	public static Metricas getMetricas(){
		if(metricas == null)
			metricas = new Metricas();
		return metricas;
	}
	
	public int getNumeroDeIteraciones() {
		return numeroDeIteraciones;
	}


	/* (non-Javadoc)
	 * @see tipos.MetricasI#getNumDeLLamadasRecursivas()
	 */
	public int getNumeroDeLLamadasRecursivas() {
		return numeroDeLLamadasRecursivas;
	}

	/* (non-Javadoc)
	 * @see tipos.MetricasI#getNumDePasos()
	 */
	public int getNumeroDePasos() {
		return numeroDePasos;
	}

	/* (non-Javadoc)
	 * @see tipos.MetricasI#getNumDeUsosDeLaMemoria()
	 */
	public int getNumeroDeUsosDeLaMemoria() {
		return numeroDeUsosDeLaMemoria;
	}

	/* (non-Javadoc)
	 * @see tipos.MetricasI#getTiempoDeEjecucion()
	 */
	public long getTiempoDeEjecucion() {
		return tiempoDeEjecucionFinal-tiempoDeEjecucionInicial;
	}
	
	/* (non-Javadoc)
	 * @see tipos.MetricasI#getNumDeCasosBase()
	 */
	public int getNumeroDeCasosBase(){
		return numeroDeCasosBase;
	}

	/* (non-Javadoc)
	 * @see tipos.MetricasI#addIteracion()
	 */
	public void addIteracion() {
		numeroDeIteraciones++;
	}

	/* (non-Javadoc)
	 * @see tipos.MetricasI#addLLamadaRecursiva()
	 */
	public void addLLamadaRecursiva() {
		numeroDeLLamadasRecursivas++;
	}


	/* (non-Javadoc)
	 * @see tipos.MetricasI#addPaso()
	 */
	public void addPaso() {
		numeroDePasos++;
	}

	/* (non-Javadoc)
	 * @see tipos.MetricasI#addUsoDeLaMemoria()
	 */
	public void addUsoDeLaMemoria() {
		numeroDeUsosDeLaMemoria++;
	}

	public void setTiempoDeEjecucionInicial() {
		tiempoDeEjecucionInicial = System.nanoTime();
	}
	
	public void setTiempoDeEjecucionFinal() {
		tiempoDeEjecucionFinal = System.nanoTime();
	}
	
	/* (non-Javadoc)
	 * @see tipos.MetricasI#addCasoBase()
	 */
	public void addCasoBase(){
		numeroDeCasosBase++;
	}
	
	/* (non-Javadoc)
	 * @see tipos.MetricasI#getNumDeVecesQueFiltra()
	 */
	public int getNumeroDeVecesQueFiltra(){
		return numeroDeVecesQueFiltra;
	}
	
	/* (non-Javadoc)
	 * @see tipos.MetricasI#addUnFiltro()
	 */
	public void addUnFiltro(){
		numeroDeVecesQueFiltra++;
	}

	public int getNumeroEstadosFinales() {
		return numeroEstadosFinales;
	}
	
	public void addUnEstadoFinal() {
		numeroEstadosFinales++;
	}
	
	public int getNumeroDeSolucionesEncontradas() {
		return numeroDeSolucionesEncontradas;
	}
	
	public void addUnaSolucionEncontrada() {
		numeroDeSolucionesEncontradas++;
	}
	
	public int getNumeroDeMejoresSolucionesEncontradas() {
		return numeroDeMejoresSolucionesEncontradas;
	}
	
	public void addUnaMejorSolucionEncontrada() {
		numeroDeMejoresSolucionesEncontradas++;
	}
	
	public String toString(){
		String s = "Número de Iteraciones         = "+ numeroDeIteraciones + "\n"+
		           "Número de Llamadas Recursivas = "+ numeroDeLLamadasRecursivas+ "\n"+
		           "Número de Pasos               = "+ numeroDePasos +"\n"+
		           "Numero de Usos de la Memoria  = "+ numeroDeUsosDeLaMemoria + "\n"+
		           "Tiempo de Ejecucion en nanoseg= "+ getTiempoDeEjecucion() + "\n" +
		           "Número de Casos Base          = "+ numeroDeCasosBase + "\n" +
		           "Número de Veces que Filtra    = "+ numeroDeVecesQueFiltra+ "\n"+
		           "Numero de Estados Finales     = "+ numeroEstadosFinales+ "\n" +
		           "Número de Soluciones Encont.  = "+ numeroDeSolucionesEncontradas+ "\n"+
		           "Número de Mejores Sol. Enc.   = "+ numeroDeMejoresSolucionesEncontradas;
		return s;
	}
	
	
}

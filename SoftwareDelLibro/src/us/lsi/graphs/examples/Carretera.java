package us.lsi.graphs.examples;

import org.jgraph.graph.DefaultEdge;

import us.lsi.graphs.StringEdgeFactory;

public class Carretera extends DefaultEdge {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static StringEdgeFactory<Ciudad, Carretera> factoria = new Factoria();

	public Carretera(Ciudad c1, Ciudad c2) {
		super();
		super.source = c1;
		super.target = c2;
	}

	
	
	@Override
	public String toString() {
		return "[]";
	}



	private static class Factoria implements
			StringEdgeFactory<Ciudad, Carretera> {

		public Factoria() {
			super();
			// TODO Auto-generated constructor stub
		}

		@Override
		public Carretera createEdge(Ciudad c1, Ciudad c2) {
			// TODO Auto-generated method stub
			return new Carretera(c1, c2);
		}

		@Override
		public Carretera createEdge(Ciudad c1, Ciudad c2, String[] formato) {
			// TODO Auto-generated method stub
			return createEdge(c1, c2);
		}
	}
}

package us.lsi.astar.vuelos;

import us.lsi.graphs.StringVertexFactory;

public class StringFactory implements StringVertexFactory<String> {
	
	private static StringFactory factory = new StringFactory();
	
	public static StringVertexFactory<String> getFactory(){
		return factory;
	}

	@Override
	public String createVertex(String[] formato) {
		// TODO Auto-generated method stub
		return formato[0];
	}

}


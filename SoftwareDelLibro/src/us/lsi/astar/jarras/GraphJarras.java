package us.lsi.astar.jarras;

import us.lsi.graphs.SimpleVirtualGraph;

public class GraphJarras extends SimpleVirtualGraph<VertexJarras, EdgeJarras<VertexJarras>> {

	public GraphJarras() {
	}

	public GraphJarras(VertexJarras... vertexSet) {
		super(vertexSet);
	}

}

package javalibrary.network;

import java.util.ArrayList;
import java.util.HashMap;

public class SpanningTree extends NetworkBase {

	private ArrayList<Arc> order = new ArrayList<Arc>();

	private SpanningTree() {}
	
	@Override
	public boolean addArc(Arc arc) {
		boolean result = super.addArc(arc);
		
		if(result)
			this.order.add(arc);
		
		return result;
	}
	
	public String stateOrder() {
		StringBuilder builder = new StringBuilder();
		
		for(Arc arc : this.order) {
			builder.append((char)arc.id1 + " -> " + (char)arc.id2 + ": " + arc.distance + ", ");
		}
		
		return builder.toString();
	}
	
	public static SpanningTree findMinSpanningTree(NetworkBase base, Algorithm algorithm) {
		if(!Algorithm.MIN_SPANNING_TREE.contains(algorithm))
			return null;
		
		SpanningTree spanningTree = new SpanningTree();
		
		spanningTree.NODES = (HashMap<Integer, Node>)base.NODES.clone();
		
		ArrayList<Arc> arcPool = (ArrayList<Arc>)base.CONNECTIONS.clone();
		
		while(spanningTree.getTotalArcs() < spanningTree.getTotalNodes() - 1) {
			Arc minArc = null;
			
			for(Arc arc : arcPool)
				if(minArc == null || minArc.distance > arc.distance)
					minArc = arc;

			//Check that it doesn't make a closed polygon
			if(!spanningTree.isRouteBetween(minArc.id2, minArc.id1, new ArrayList<Integer>()))
				spanningTree.addArc(minArc);
			
			arcPool.remove(minArc);
		}
		
		return spanningTree;
	}
}

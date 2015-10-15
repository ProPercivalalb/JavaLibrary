package javalibrary.network;

import java.util.ArrayList;

public class TravellingSalesman extends NetworkBase {

	private TravellingSalesman() {}

	public static TravellingSalesman findRouteAll(NetworkBase base, int targetNodeId) {
	
		TravellingSalesman travellingSalesman = base.copyTo(new TravellingSalesman());
		
		ArrayList<NodePair> nodePairs = getPairs(new ArrayList<Integer>(travellingSalesman.NODES.keySet()));
		for(NodePair pair : nodePairs) {
			ShortestPath shortestPath = ShortestPath.findShortestPath(base, pair.node1, pair.node2, Algorithm.DIJKSTRA);
			double shortestDistance = shortestPath.getTotalDistance();
			
			Arc arc = travellingSalesman.getArc(pair.node1, pair.node2);
			if(arc == null) {
				arc = new Arc(pair.node1, pair.node2, shortestDistance);
				travellingSalesman.addArc(arc);
			}
			else {
			
				arc.distances.clear();
				arc.distances.add(shortestDistance);
			}
		}
		
		return travellingSalesman;
		
	}
	
	public static class NodePair {
		public int node1;
		public int node2;
		public NodePair(int node1, int node2) {
			this.node1 = node1;
			this.node2 = node2;
		}
		
		@Override
		public String toString() {
			return "[" + this.node1 + ", " + this.node2 + "]";
		}
	}
	
	public static ArrayList<NodePair> getPairs(ArrayList<Integer> nodes) {
		return getPairs(nodes, nodes.size());
	}
	
	public static ArrayList<NodePair> getPairs(ArrayList<Integer> nodes, int noNodes) {
		ArrayList<NodePair> allCombos = new ArrayList<NodePair>();
		
		if(nodes.isEmpty())
			return allCombos;
		
		int starter = nodes.get(0);
		
		for(int i = 1; i < nodes.size(); i++)
			allCombos.add(new NodePair(starter, nodes.get(i)));
		
		ArrayList<Integer> next = new ArrayList<Integer>(nodes);
		next.remove((Integer)starter);
		allCombos.addAll(getPairs(next, noNodes));
		
		return allCombos;
	}
		
}

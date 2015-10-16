package javalibrary.network;

import java.util.ArrayList;
import java.util.List;

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
		
		for(int nodeId : travellingSalesman.NODES.keySet()) {
			NearestNeighbour nearestNeighbour = NearestNeighbour.findRouteAll(travellingSalesman, nodeId);
			
			List<Integer> routeIds = nearestNeighbour.getRouteIds();
			int distance = 0;
			
			
			for(int i = 0; i < routeIds.size() - 1; i++) {
				int id1 = routeIds.get(i);
				int id2 = routeIds.get(i + 1);
				Arc arc = travellingSalesman.getArc(id1, id2);
				distance += arc.getTotalDistance();
			}
			System.out.println(nearestNeighbour.getRouteIds());
			System.out.println("Distance: " + distance);
			//System.out.println(nearestNeighbour.getRouteIds());
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

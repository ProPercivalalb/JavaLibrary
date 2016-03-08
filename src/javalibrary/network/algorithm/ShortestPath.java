package javalibrary.network.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javalibrary.network.Arc;
import javalibrary.network.NetworkBase;
import javalibrary.network.Node;

public class ShortestPath extends NetworkBase {
	
	private int startId;
	private int endId;
	
	private ShortestPath(int startId, int endId) {
		this.startId = startId;
		this.endId = endId;
	}
	
	public static ShortestPath findShortestPath(NetworkBase base, Node startNode, Node endNode, Algorithm algorithm) {
		return findShortestPath(base, startNode.getId(), endNode.getId(), algorithm);
	}
	
	public static ShortestPath findShortestPath(NetworkBase base, int startId, int endId, Algorithm algorithm) {
		if(!Algorithm.SHORTEST_PATH.contains(algorithm))
			return null;
		
		ShortestPath shortestPath = new ShortestPath(startId, endId);
		
		HashMap<Integer, Route> boxed = new HashMap<Integer, Route>();
		HashMap<Integer, Route> temp = new HashMap<Integer, Route>();
		
		boxed.put(startId, new Route(startId, 0));
		
		List<Arc> startingArcs = base.getArcsConnectedToNode(startId);
		for(Arc arc : startingArcs)
			temp.put(arc.getOtherNode(startId), new Route(startId, arc.getShortestDistance()));
	
		while(temp.size() > 0) {
			//Gets smallest temp value
			int pointId = 0;
			double pointDis = Double.MAX_VALUE;
			for(int id : temp.keySet()) {
				if(temp.get(id).distance < pointDis) {
					pointDis = temp.get(id).getDistance();
					pointId = id;
				}
			}

			//Moves temp distance to final solution
			boxed.put(pointId, temp.get(pointId));
			temp.remove(pointId);
			
			List<Arc> connectedArcs = base.getArcsConnectedToNode(pointId);

			for(Arc arc : connectedArcs) {
				double combinedDistance = arc.getShortestDistance() + boxed.get(pointId).getDistance();
				int targetNode = arc.getOtherNode(pointId);
				
				if(!boxed.containsKey(targetNode) && (!temp.containsKey(targetNode) || temp.get(targetNode).getDistance() > combinedDistance))
					temp.put(targetNode, new Route(pointId, combinedDistance));
			}
		}

		shortestPath.addNode(new Node(startId));
		
		shortestPath.route = new ArrayList<Integer>();
		
		int nextId = endId;
		
		while(nextId != startId) {
			int oldId = nextId;
			shortestPath.route.add(oldId);
			double oldDistance = boxed.get(oldId).getDistance();

			nextId = boxed.get(nextId).getSourceId();
			
			shortestPath.addNode(new Node(oldId));
			shortestPath.addArc(new Arc(nextId, oldId, oldDistance - boxed.get(nextId).getDistance()));
		}
		shortestPath.route.add(startId);
		
		Collections.reverse(shortestPath.route);
		
		return shortestPath;
	}
	
	private ArrayList<Integer> route;
	
	public List<Integer> getRouteIds() {
		return new ArrayList<Integer>(this.route);
	}
	
	public List<Node> getRouteNodes() {
		List<Node> list = new ArrayList<Node>();
		
		List<Integer> nodeIds = this.getRouteIds();
		
		for(Integer nodeId : nodeIds) {
			if(this.NODES.containsKey(nodeId))
				list.add(this.NODES.get(nodeId));
			else
				System.out.println("***** Missing node with id - " + nodeId + " *****");
		}
		
		return list;
	}
	
	public static class Route {
		
		private final int sourceId;
		private final double distance;
		
		public Route(int nodeFrom, double distance) {
			this.sourceId = nodeFrom;
			this.distance = distance;
		}
		
		public int getSourceId() {
			return this.sourceId;
		}
		
		public double getDistance() {
			return this.distance;
		}
		
		@Override
		public String toString() {
			return "From [id=" + (char)this.sourceId + "]";
		}
	}
}

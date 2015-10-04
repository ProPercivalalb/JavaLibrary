package javalibrary.network;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

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
			temp.put(arc.getOtherNode(startId), new Route(startId, arc.distance));
	
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
				double combinedDistance = arc.distance + boxed.get(pointId).getDistance();
				int targetNode = arc.getOtherNode(pointId);
				
				if(!boxed.containsKey(targetNode) && (!temp.containsKey(targetNode) || temp.get(targetNode).getDistance() > combinedDistance))
					temp.put(targetNode, new Route(pointId, combinedDistance));
			}
		}
		
		StringBuilder builder = new StringBuilder("" + (char)startId);
		
		shortestPath.addNode(new Node(startId));
		
		int nextId = endId;
		
		while(nextId != startId) {
			int oldId = nextId;
			double oldDistance = boxed.get(oldId).getDistance();
			
			builder.insert(1, (char)nextId);
			nextId = boxed.get(nextId).getSourceId();
			
			shortestPath.addNode(new Node(oldId));
			shortestPath.addArc(new Arc(nextId, oldId, oldDistance - boxed.get(nextId).getDistance()));
		}
		
		System.out.println((char)startId + "" + (char)endId + " " + builder + " distance: " + boxed.get(endId).getDistance());
		
		return shortestPath;
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

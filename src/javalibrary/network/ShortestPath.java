package javalibrary.network;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class ShortestPath extends NetworkBase {

	private ShortestPath() {}
	
	public static ShortestPath findShortestPath(NetworkBase base, Node startNode, Node endNode, Algorithm algorithm) {
		return findShortestPath(base, startNode.getId(), endNode.getId(), algorithm);
	}
	
	public static ShortestPath findShortestPath(NetworkBase base, int startId, int endId, Algorithm algorithm) {
		if(!Algorithm.SHORTEST_PATH.contains(algorithm))
			return null;
		
		ShortestPath shortestPath = new ShortestPath();
		
		HashMap<Integer, Route> boxed = new HashMap<Integer, Route>();
		HashMap<Integer, Route> temp = new HashMap<Integer, Route>();
		
		boxed.put(startId, new Route(startId, 0));
		
		List<Arc> startingArcs = base.getArcsConnectedToNode(startId);
		for(Arc arc : startingArcs)
			temp.put(arc.getOtherNode(startId), new Route(startId, arc.distance));
	
		while(temp.size() > 0) {
			int pointId = 0;
			int pointDis = Integer.MAX_VALUE;
			
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
				int combinedDistance = arc.distance + boxed.get(pointId).getDistance();
				int targetNode = arc.getOtherNode(pointId);
				
				if(!boxed.containsKey(targetNode) && (!temp.containsKey(targetNode) || temp.get(targetNode).getDistance() > combinedDistance))
					temp.put(targetNode, new Route(pointId, combinedDistance));
			}
		}
		
		StringBuilder builder = new StringBuilder("" + (char)startId);
		
		int nextId = endId;
		
		while(nextId != startId) {
			builder.insert(1, (char)nextId);
			nextId = boxed.get(nextId).getSourceId();
		}
		
		System.out.println(builder + " distance: " + boxed.get(endId).getDistance());
		
		return shortestPath;
	}
	
	public static class Route {
		
		private final int sourceId;
		private final int distance;
		
		public Route(int nodeFrom, int distance) {
			this.sourceId = nodeFrom;
			this.distance = distance;
		}
		
		public int getSourceId() {
			return this.sourceId;
		}
		
		public int getDistance() {
			return this.distance;
		}
		
		@Override
		public String toString() {
			return "From [id=" + (char)this.sourceId + "]";
		}
	}
}

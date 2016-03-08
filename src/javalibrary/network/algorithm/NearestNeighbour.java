package javalibrary.network.algorithm;

import java.util.ArrayList;
import java.util.List;

import javalibrary.network.Arc;
import javalibrary.network.NetworkBase;
import javalibrary.network.Node;
import javalibrary.network.Arc.ArcIndex;

public class NearestNeighbour extends NetworkBase {

	private NearestNeighbour() {}

	public static NearestNeighbour findRouteAll(NetworkBase base, int targetId) {
		NearestNeighbour nearestNeighbour = base.copyTo(new NearestNeighbour());
		nearestNeighbour.route = new ArrayList<Integer>();
		
		
		int nextId = targetId;
		
		while(nearestNeighbour.getTotalNodes() > 0) {
			List<ArcIndex> connections = nearestNeighbour.getAllPathsConnectedToNode(nextId);
			
			ArcIndex smallest = null;
			for(ArcIndex arc : connections) {
				if(smallest == null || smallest.distance > arc.distance) {
					smallest = arc;
				}
				nearestNeighbour.removeArcIndex(arc);
			}
			
			
			nearestNeighbour.route.add(nextId);
			nearestNeighbour.removeNode(nextId);
			
			if(smallest == null)
				break;
			
			nextId = smallest.arc.getOtherNode(nextId);
		}
		
		nearestNeighbour.route.add(targetId);
		
		return nearestNeighbour;
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
}

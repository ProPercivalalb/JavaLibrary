package javalibrary.network;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javalibrary.math.MathHelper;
import javalibrary.network.Arc.ArcIndex;
import javalibrary.network.ShortestPath.Route;
import javalibrary.util.RandomUtil;

public class ChinesePostman extends NetworkBase {

	private ChinesePostman() {}

	public static ChinesePostman findRouteAll(NetworkBase base, int targetNodeId) {
		
		ChinesePostman chinesePostman = base.copy(new ChinesePostman());
		
		int baseDistance = base.getTotalDistance();
		
		/**for(Node node : base.NODES.values())
			chinesePostman.addNode(node);
		for(Arc arc : base.CONNECTIONS)
			chinesePostman.addArc(arc);**/
		
		
		//Needs to add more arcs?
		ArrayList<Integer> oddNodes = base.getOddNodeIds();
		
		if((oddNodes.size() & 1) == 1)
			System.out.println("ERROR ODD NUMBER OF NODES");
		System.out.println(oddNodes);
		
		int shortestDistance = Integer.MAX_VALUE;
		ArrayList<NodePair> shortestPairs = null;
		ArrayList<ShortestPath> shortestPaths = null;
		
		System.out.println("");
		for(ArrayList<NodePair> pairs : getPairs(oddNodes)) {
			
			int distance = 0;
			ArrayList<ShortestPath> paths = new ArrayList<ShortestPath>();
			
			for(NodePair pair : pairs) {
				ShortestPath shortestPath = ShortestPath.findShortestPath(base, pair.node1, pair.node2, Algorithm.DIJKSTRA);
				distance += shortestPath.getTotalDistance();
				paths.add(shortestPath);
			}
			
			if(distance < shortestDistance) {
				shortestDistance = distance;
				shortestPairs = pairs;
				shortestPaths = paths;
			}
		}
		
		NetworkBase combined = new NetworkBase();
		if(!oddNodes.isEmpty()) {
			
			for(ShortestPath shortestPath : shortestPaths) {
				if(combined == null)
					combined = shortestPath;
				else
					combined.combine(shortestPath);
			}
			
		}
		
		for(Arc arc : combined.CONNECTIONS)
			chinesePostman.addArc(arc);
		chinesePostman.print();

		int nextId = targetNodeId;
		chinesePostman.route = new ArrayList<Integer>();
		chinesePostman.route.add(targetNodeId);
		int nodesConnected = 0;

		NetworkBase temp = new NetworkBase();
		for(Node node : chinesePostman.NODES.values())
			temp.addNode(node);
		for(Arc arc : chinesePostman.CONNECTIONS)
			temp.addArc(arc);

		
		int finalConnections = temp.getTotalArcIndexs();
		while(nodesConnected < finalConnections) {
			List<ArcIndex> connectedNodes = temp.getAllPathsConnectedToNode(nextId);
			//System.out.println(connectedNodes);
			ArcIndex testArc = RandomUtil.pickRandomElement(connectedNodes);
			int testNode = testArc.arc.getOtherNode(nextId);
			if(testNode == targetNodeId && connectedNodes.size() > 1)
				continue;
			
	
			temp.removeArcIndex(testArc);
			if(temp.getArcsConnectedToNode(testNode).isEmpty())
				temp.NODES.remove(testNode);
			
			if(connectedNodes.size() <= 1)
				temp.NODES.remove(nextId);
			//System.out.println(temp.getArcsConnectedToNode(testNode).size() + " " + connectedNodes.size() + " " + temp.NODES);
			if(temp.NODES.isEmpty()) {
				break;
			}
				
			
			if(temp.isNetworkBridged()) {
				System.out.println("bridged, " + testArc);
				temp.addArc(testArc);
				continue;
			}
				
			chinesePostman.route.add(testNode);
			System.out.println(chinesePostman.route);
			nextId = testNode;
			nodesConnected++;
		}
		chinesePostman.route.add(targetNodeId);

		System.out.println("PATH " + chinesePostman.route);
		
		System.out.println("Postman route is " + (baseDistance + combined.getTotalDistance()));
		
		return chinesePostman;
	}
	
	private ArrayList<Integer> route;
	
	public List<Integer> getRouteIds() {
		return (List<Integer>)this.route.clone();
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
	
	public static class NodePair {
		public int node1;
		public int node2;
		public NodePair(int node1, int node2) {
			this.node1 = node1;
			this.node2 = node2;
		}
		
		@Override
		public String toString() {
			return this.node1 + ", " + this.node2;
		}
	}
	
	public static ArrayList<ArrayList<NodePair>> getPairs(ArrayList<Integer> oddNodes) {
		return getPairs(oddNodes, oddNodes.size(), new ArrayList<NodePair>());
	}
	
	public static ArrayList<ArrayList<NodePair>> getPairs(ArrayList<Integer> oddNodes, int noNodes, ArrayList<NodePair> previousPairs) {
		ArrayList<ArrayList<NodePair>> allCombos = new ArrayList<ArrayList<NodePair>>();
		
		int starter = MathHelper.findSmallest(oddNodes);
		
		for(Integer j : oddNodes) {
			if(j == starter) continue; //Miss out duplicate
			
			ArrayList<NodePair> newPairs = new ArrayList<NodePair>(previousPairs);
			newPairs.add(new NodePair(starter, j));
			
			ArrayList<Integer> next = (ArrayList<Integer>)oddNodes.clone();
			next.removeAll(Arrays.asList(starter, j));
			
			if(newPairs.size() * 2 < noNodes)
				allCombos.addAll(getPairs(next, noNodes, newPairs));
			else
				allCombos.add(newPairs);
			
		}
		
		return allCombos;
	}
}

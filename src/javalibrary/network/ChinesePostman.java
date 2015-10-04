package javalibrary.network;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javalibrary.math.MathHelper;
import javalibrary.util.RandomUtil;

public class ChinesePostman extends NetworkBase {

	private ChinesePostman() {}

	public static ChinesePostman findRouteAll(NetworkBase base, int targetNodeId) {
		
		ChinesePostman chinesePostman = new ChinesePostman();
		
		int baseDistance = base.getTotalDistance();
		chinesePostman.NODES = (HashMap<Integer, Node>)base.NODES.clone();
		chinesePostman.CONNECTIONS = (ArrayList<Arc>)base.CONNECTIONS.clone();
		
		
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
		
		ShortestPath combined = null;
		
		for(ShortestPath shortestPath : shortestPaths) {
			if(combined == null)
				combined = shortestPath;
			else
				combined.combine(shortestPath);
		}
		
		chinesePostman.CONNECTIONS.addAll(combined.CONNECTIONS);
		

		int nextId = targetNodeId;
		String path = "" + (char)nextId;
		int nodesConnected = 0;


		int finalConnections = chinesePostman.CONNECTIONS.size();
		while(nodesConnected < finalConnections) {
			List<Arc> connectedNodes = chinesePostman.getArcsConnectedToNode(nextId);
			Arc testArc = RandomUtil.pickRandomElement(connectedNodes);
			int testNode = testArc.getOtherNode(nextId);
			if(testNode == targetNodeId && nodesConnected + 1 != finalConnections)
				continue;
	
	
			chinesePostman.CONNECTIONS.remove(testArc);
			if(chinesePostman.getArcsConnectedToNode(testNode).isEmpty())
				chinesePostman.NODES.remove(testNode);
			
			if(connectedNodes.size() == 1)
				chinesePostman.NODES.remove(nextId);
				
			if(chinesePostman.NODES.isEmpty()) {
				path += (char)targetNodeId;
				break;
			}
				
			
			if(chinesePostman.isNetworkBridged()) {
				chinesePostman.CONNECTIONS.add(testArc);
				continue;
			}
				
			path += (char)testNode;
			nextId = testNode;
			nodesConnected++;
		}
		
		System.out.println("PATH " + path);
		
		System.out.println("Postman route is " + (baseDistance + combined.getTotalDistance()));
		
		return chinesePostman;
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

package javalibrary.network;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javalibrary.math.MathUtil;
import javalibrary.network.Arc.ArcIndex;
import javalibrary.network.algorithm.Algorithm;
import javalibrary.network.algorithm.ChinesePostman;
import javalibrary.network.algorithm.ShortestPath;
import javalibrary.network.algorithm.SpanningTree;
import javalibrary.util.RandomUtil;

public class NetworkBase {

	public HashMap<Integer, Node> NODES = new HashMap<Integer, Node>();
	public ArrayList<Arc> CONNECTIONS = new ArrayList<Arc>();
	
	public boolean addNode(Node node) {
		if(!this.NODES.containsKey(node.getId())) {
			this.NODES.put(node.getId(), node);
			return true;
		}
		
		System.out.println("***** Already has node - " + node + " *****");
		return false;
	}
	
	public void removeNode(int nodeId) {
		this.NODES.remove((Integer)nodeId);
	}
	
	public boolean addArc(Arc arc) {
		if(!this.CONNECTIONS.contains(arc)) {
			return this.CONNECTIONS.add(arc.copy());
		}
		else {
			Arc real = this.CONNECTIONS.get(this.CONNECTIONS.indexOf(arc));
			return real.getDistances().addAll(arc.getDistances());
		}
	}
	
	public boolean addArc(ArcIndex arcIndex) {
		if(!this.CONNECTIONS.contains(arcIndex.arc)) {
			return this.CONNECTIONS.add(arcIndex.createArc());
		}
		else {
			Arc real = this.CONNECTIONS.get(this.CONNECTIONS.indexOf(arcIndex.arc));
			return real.getDistances().add(arcIndex.getDistance());
		}
	}
	
	public void removeArcIndex(ArcIndex arcIndex) {
		if(this.CONNECTIONS.contains(arcIndex.arc)) {
			Arc real = this.CONNECTIONS.get(this.CONNECTIONS.indexOf(arcIndex.arc));
			real.getDistances().remove(arcIndex.getDistance());
			if(real.getDistances().isEmpty())
				this.CONNECTIONS.remove(arcIndex.arc);
		}
	}
	
	public void removeArc(Arc arc) {
		if(this.CONNECTIONS.contains(arc))
			this.CONNECTIONS.remove(arc);
	}
	
	public List<Node> getNodesConnectedToNode(Node node) {
		return this.getNodesConnectedToNode(node.getId());
	}
	
	public List<Node> getNodesConnectedToNode(int id) {
		List<Node> list = new ArrayList<Node>();
		
		List<Integer> nodeIds = this.getNodeIdsConnectedToNode(id);
		
		for(Integer nodeId : nodeIds) {
			if(this.NODES.containsKey(nodeId))
				list.add(this.NODES.get(nodeId));
			else
				System.out.println("***** Missing node with id - " + nodeId + " *****");
		}

		return list;
	}
	
	public List<Integer> getNodeIdsConnectedToNode(Node node) {
		return this.getNodeIdsConnectedToNode(node.getId());
	}
	
	public List<Integer> getNodeIdsConnectedToNode(int id) {
		List<Integer> list = new ArrayList<Integer>();
		
		for(Arc arc : this.CONNECTIONS)
			if(arc.hasConnectionWith(id))
				list.add(arc.getOtherNode(id));

		return list;
	}
	
	public ArcIndex getShortestArcFromNode(Node node) {
		return this.getShortestArcFromNode(node.getId());
	}
	
	public ArcIndex getShortestArcFromNode(int id) {
		ArcIndex shortestArc = null;
		
		for(Arc arc : this.CONNECTIONS)
			if(arc.hasConnectionWith(id))
				for(int i = 0; i < arc.distanceCount(); i++)
					if(shortestArc == null || shortestArc.getDistance() > arc.getDistance(i))
						shortestArc = new ArcIndex(arc, i);

		return shortestArc;
	}
	
	public List<Arc> getArcsConnectedToNode(Node node) {
		return this.getArcsConnectedToNode(node.getId());
	}
	
	public List<Arc> getArcsConnectedToNode(int nodeId) {
		List<Arc> list = new ArrayList<Arc>();
		
		for(Arc arc : this.CONNECTIONS)
			if(arc.hasConnectionWith(nodeId))
				list.add(arc);

		return list;
	}
	
	public List<ArcIndex> getAllPathsConnectedToNode(int nodeId) {
		List<ArcIndex> arcIndexs = new ArrayList<ArcIndex>();
		
		List<Arc> arcs = this.getArcsConnectedToNode(nodeId);
		for(Arc arc : arcs)
			for(int i = 0; i < arc.distanceCount(); i++)
				arcIndexs.add(new ArcIndex(arc, i));
		
		return arcIndexs;
	}
	
	public double getTotalDistance() {
		double distance = 0.0D;
		for(Arc arc : this.CONNECTIONS)
			distance += arc.getTotalDistance();
		return distance;
	}
	
	public ArrayList<Integer> getOddDegreeNodeIds() {
		ArrayList<Integer> oddNodes = new ArrayList<Integer>();
		for(int nodeId : this.NODES.keySet()) {
			int count = this.getAllPathsConnectedToNode(nodeId).size();
			if((count & 1) == 1)
				oddNodes.add(nodeId);
		}
		return oddNodes;
	}
	
	public List<Node> getOddDegreeNodes() {
		List<Node> list = new ArrayList<Node>();
		
		List<Integer> nodeIds = this.getOddDegreeNodeIds();
		
		for(Integer nodeId : nodeIds) {
			if(this.NODES.containsKey(nodeId))
				list.add(this.NODES.get(nodeId));
			else
				System.out.println("***** Missing node with id - " + nodeId + " *****");
		}
		
		return list;
	}
	
	public Arc getArc(int id1, int id2) {
		int index = this.CONNECTIONS.indexOf(new Arc(id1, id2, 0));
		return index != -1 ? this.CONNECTIONS.get(index) : null;
	}
	
	public int getTotalNodes() {
		return this.NODES.size();
	}
	
	public int getTotalArcs() {
		return this.CONNECTIONS.size();
	}
	
	public int getTotalArcIndexs() {
		int count = 0;
		for(Arc arc : this.CONNECTIONS)
			count += arc.distanceCount();
		return count;
	}
	
	public boolean isRouteBetween(int to, int from, ArrayList<Integer> ignore) {
		List<Integer> connectedNodes = this.getNodeIdsConnectedToNode(from);
		
		if(connectedNodes.isEmpty())
			return false;
		
		//Contains endpoint
		if(connectedNodes.contains(to))
			return true;

		//Ignore id of node where you just came from so you don't go back down that section
		ignore.add(from);
		
		connectedNodes.removeAll(ignore);
		
		for(Integer id : connectedNodes)
			if(this.isRouteBetween(to, id, ignore))
				return true;
		
		
		return false;
	}
	
	public boolean isNetworkBridged() {
		int randomStartId = RandomUtil.pickRandomKey(this.NODES);
		
		ArrayList<Integer> nodesAccountedFor = this.getAllNodesAssosicatedWith(randomStartId);
		
		if(!nodesAccountedFor.contains(randomStartId))
			nodesAccountedFor.add(randomStartId);
		
		return nodesAccountedFor.size() != this.NODES.size();
	}
	
	public ArrayList<Integer> getAllNodesAssosicatedWith(int nodeId) {
		return this.getAllNodesAssosicatedWith(nodeId, new ArrayList<Arc>());
	}
	
	private ArrayList<Integer> getAllNodesAssosicatedWith(int nodeId, ArrayList<Arc> ignore) {
		ArrayList<Integer> encountedNodes = new ArrayList<Integer>();
		List<Arc> connectedArcs = this.getArcsConnectedToNode(nodeId);
		connectedArcs.removeAll(ignore);
		
		for(Arc arc : connectedArcs) {
			int otherId = arc.getOtherNode(nodeId);
			
			if(!encountedNodes.contains(otherId))
				encountedNodes.add(otherId);
			
			ignore.add(arc);
			
			ArrayList<Integer> encountedNodesSub = getAllNodesAssosicatedWith(otherId, ignore);
			
			for(int encountedId : encountedNodesSub)
				if(!encountedNodes.contains(encountedId))
					encountedNodes.add(encountedId);
		}
		
		return encountedNodes;
	}
	

	public Node getSmallestNode() {
		return this.NODES.get(this.getSmallestNodeId());
	}
	
	public int getSmallestNodeId() {
		return MathUtil.findSmallestInt(this.NODES.keySet());
	}
	
	public Node getLargestNode() {
		return this.NODES.get(this.getLargestNodeId());
	}
	
	public int getLargestNodeId() {
		return MathUtil.findLargestInt(this.NODES.keySet());
	}
	
	public NetworkBase combine(NetworkBase other) {
		for(Node node : other.NODES.values())
			this.addNode(node);
		for(Arc arc : other.CONNECTIONS)
			this.addArc(arc);
		return this;
	}
	
	public <T extends NetworkBase> T copyTo(T to) {
		for(Node node : this.NODES.values())
			to.addNode(node);
		for(Arc arc : this.CONNECTIONS)
			to.addArc(arc.copy());
		return to;
	}
	
	public void print() {
		System.out.println("--------------------------");
		System.out.println("Network");
		System.out.println("Nodes: " + this.NODES.size() + " Connections: " + this.CONNECTIONS.size());
		System.out.println("Nodes...");
		for(int id : NODES.keySet()) {
			System.out.println("  " + id + " connected to: " + this.getNodesConnectedToNode(id));
		}
		//for(Arc arc : this.CONNECTIONS) {
		//	System.out.println(arc);
		//}
		System.out.println("Distance: " + this.getTotalDistance());
		
		System.out.println("--------------------------");
	}
	
	public static void main(String... str) {
		NetworkBase base = new NetworkBase();
		
		/** Page 
		base.addNode(new Node('S'));
		base.addNode(new Node('T'));
		base.addNode(new Node('M'));
		base.addNode(new Node('B'));
		base.addNode(new Node('R'));
		base.addNode(new Node('O'));
		
		base.addArc(new Arc('S', 'T', 4));
		base.addArc(new Arc('S', 'B', 10));
		base.addArc(new Arc('S', 'M', 5));
		base.addArc(new Arc('T', 'M', 3));
		base.addArc(new Arc('M', 'B', 8));
		base.addArc(new Arc('M', 'O', 12));
		base.addArc(new Arc('M', 'R', 15));
		base.addArc(new Arc('B', 'R', 6));
		base.addArc(new Arc('B', 'O', 9));
		base.addArc(new Arc('R', 'O', 4));
		**/
		
		/** Page 
		base.addNode(new Node('A'));
		base.addNode(new Node('B'));
		base.addNode(new Node('C'));
		base.addNode(new Node('D'));
		base.addNode(new Node('E'));
		base.addNode(new Node('F'));
		base.addNode(new Node('G'));
		base.addNode(new Node('H'));
		base.addNode(new Node('I'));
		base.addNode(new Node('J'));
		
		base.addArc(new Arc('A', 'B', 4));
		base.addArc(new Arc('A', 'C', 5));
		base.addArc(new Arc('A', 'D', 8));
		base.addArc(new Arc('B', 'D', 3));
		base.addArc(new Arc('B', 'E', 12));
		base.addArc(new Arc('C', 'D', 1));
		base.addArc(new Arc('C', 'F', 11));
		base.addArc(new Arc('D', 'E', 9));
		base.addArc(new Arc('D', 'F', 4));
		base.addArc(new Arc('D', 'G', 10));
		base.addArc(new Arc('E', 'G', 6));
		base.addArc(new Arc('E', 'H', 10));
		base.addArc(new Arc('F', 'G', 5));
		base.addArc(new Arc('F', 'I', 11));
		base.addArc(new Arc('G', 'H', 3));
		base.addArc(new Arc('G', 'I', 5));
		base.addArc(new Arc('G', 'J', 15));
		base.addArc(new Arc('H', 'J', 14));
		base.addArc(new Arc('I', 'J', 8));
		**/
		
		/** Page 49
		base.addNode(new Node('A'));
		base.addNode(new Node('B'));
		base.addNode(new Node('C'));
		base.addNode(new Node('D'));
		base.addNode(new Node('E'));
		base.addNode(new Node('F'));
		base.addNode(new Node('G'));
		base.addNode(new Node('H'));
		
		base.addArc(new Arc('A', 'B', 50));
		base.addArc(new Arc('A', 'C', 50));
		base.addArc(new Arc('A', 'D', 50));
		base.addArc(new Arc('B', 'D', 50));
		base.addArc(new Arc('C', 'D', 70));
		base.addArc(new Arc('C', 'G', 70));
		base.addArc(new Arc('C', 'H', 120));
		base.addArc(new Arc('D', 'F', 60));
		base.addArc(new Arc('B', 'F', 50));
		base.addArc(new Arc('B', 'E', 70));
		base.addArc(new Arc('E', 'F', 70));
		base.addArc(new Arc('F', 'H', 60));
		base.addArc(new Arc('G', 'H', 70));
		**/
		
		/** Page 53, Ex 3D, Q1
		base.addNode(new Node('A'));
		base.addNode(new Node('B'));
		base.addNode(new Node('C'));
		base.addNode(new Node('D'));
		base.addNode(new Node('E'));
		base.addNode(new Node('F'));
		base.addNode(new Node('G'));
		base.addNode(new Node('H'));
		
		base.addArc(new Arc('A', 'B', 5));
		base.addArc(new Arc('A', 'D', 6));
		base.addArc(new Arc('A', 'C', 9));
		base.addArc(new Arc('B', 'C', 5));
		base.addArc(new Arc('C', 'F', 18));
		base.addArc(new Arc('C', 'D', 4));
		base.addArc(new Arc('D', 'E', 7));
		base.addArc(new Arc('E', 'F', 4));
		base.addArc(new Arc('F', 'H', 12));
		base.addArc(new Arc('F', 'G', 6));
		base.addArc(new Arc('G', 'H', 7));
		base.addArc(new Arc('E', 'H', 10));
		**/
		
		/**Page 53, Ex 3D, Q2
		base.addNode(new Node('A'));
		base.addNode(new Node('B'));
		base.addNode(new Node('C'));
		base.addNode(new Node('D'));
		base.addNode(new Node('E'));
		base.addNode(new Node('F'));
		base.addNode(new Node('G'));
		base.addNode(new Node('H'));
		
		base.addArc(new Arc('A', 'B', 8));
		base.addArc(new Arc('A', 'C', 6));
		base.addArc(new Arc('A', 'D', 5));
		base.addArc(new Arc('C', 'D', 4));
		base.addArc(new Arc('B', 'C', 7));
		base.addArc(new Arc('C', 'E', 15));
		base.addArc(new Arc('C', 'E', 12));
		base.addArc(new Arc('E', 'F', 11));
		base.addArc(new Arc('E', 'H', 9));
		base.addArc(new Arc('F', 'H', 10));
		base.addArc(new Arc('E', 'G', 14));
		base.addArc(new Arc('G', 'H', 13));
		**/
		
		base.addNode(new Node('A'));
		base.addNode(new Node('B'));
		base.addNode(new Node('C'));
		base.addNode(new Node('D'));
		base.addNode(new Node('E'));
		base.addNode(new Node('F'));
		base.addNode(new Node('G'));
		
		base.addArc(new Arc('A', 'B', 150));
		base.addArc(new Arc('A', 'C', 200));
		base.addArc(new Arc('C', 'B', 50));
		base.addArc(new Arc('C', 'E', 300));
		base.addArc(new Arc('C', 'D', 400));
		base.addArc(new Arc('D', 'E', 75));
		base.addArc(new Arc('E', 'F', 150));
		base.addArc(new Arc('B', 'F', 75));
		base.addArc(new Arc('D', 'G', 200));
		base.addArc(new Arc('E', 'G', 100));
		base.addArc(new Arc('G', 'F', 250));
		
		base.print();
		
		System.out.println(base.isNetworkBridged());
		
		
		//Minimum spanning tree
		SpanningTree minSpanningTree = SpanningTree.findMinSpanningTree(base, Algorithm.KRUSKAL);
		
		System.out.println("Min spanning tree");
		minSpanningTree.print();
		
		System.out.println(minSpanningTree.stateOrder());
		
		ShortestPath shortestPath = ShortestPath.findShortestPath(base, 'A', 'G', Algorithm.DIJKSTRA);
		
		System.out.println("Shortest path");
		shortestPath.print();
		
		ChinesePostman chinesePostman = ChinesePostman.findRouteAll(base, 'A');
		
		
		System.out.println("Chinese Postman");
		//chinesePostman.print();
		
		//System.out.println(shortestPath.stateOrder());
	}
}

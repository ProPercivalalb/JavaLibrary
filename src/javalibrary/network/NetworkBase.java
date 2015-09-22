package javalibrary.network;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NetworkBase {

	protected HashMap<Integer, Node> NODES = new HashMap<Integer, Node>();
	protected ArrayList<Arc> CONNECTIONS = new ArrayList<Arc>();
	
	public boolean addNode(Node node) {
		if(!this.NODES.containsKey(node.getId())) {
			this.NODES.put(node.getId(), node);
			return true;
		}
		
		System.out.println("***** Already has node - " + node + " *****");
		return false;
	}
	
	public boolean addArc(Arc arc) {
		if(!this.CONNECTIONS.contains(arc)) {
			this.CONNECTIONS.add(arc);
			return true;
		}
	
		System.out.println("***** Already has arc - " + arc + " *****");
		return false;
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
	
	public Arc getShortestArcFromNode(Node node) {
		return this.getShortestArcFromNode(node.getId());
	}
	
	public Arc getShortestArcFromNode(int id) {
		Arc shortestArc = null;
		
		for(Arc arc : this.CONNECTIONS)
			if(arc.hasConnectionWith(id))
				if(shortestArc == null || shortestArc.distance > arc.distance)
					shortestArc = arc;

		return shortestArc;
	}
	
	public List<Integer> getArcsConnectedToNode(Node node) {
		return this.getNodeIdsConnectedToNode(node.getId());
	}
	
	public List<Arc> getArcsConnectedToNode(int id) {
		List<Arc> list = new ArrayList<Arc>();
		
		for(Arc arc : this.CONNECTIONS)
			if(arc.hasConnectionWith(id))
				list.add(arc);

		return list;
	}
	
	public int getTotalDistance() {
		int distance = 0;
		for(Arc arc : this.CONNECTIONS)
			distance += arc.distance;
		return distance;
	}
	
	public int getTotalNodes() {
		return this.NODES.size();
	}
	
	public int getTotalArcs() {
		return this.CONNECTIONS.size();
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
	
	public void print() {
		System.out.println("--------------------------");
		System.out.println("Network");
		System.out.println("Nodes: " + this.NODES.size() + " Connections: " + this.CONNECTIONS.size());
		System.out.println("Nodes...");
		for(int id : NODES.keySet()) {
			System.out.println("  " + id + " connected to: " + this.getNodesConnectedToNode(id));
		}
		System.out.println("Distance: " + this.getTotalDistance());
		
		System.out.println("--------------------------");
	}
	
	public static void main(String... str) {
		NetworkBase base = new NetworkBase();
		
		/**
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
		
		
		base.print();
		
		//Minimum spanning tree
		SpanningTree minSpanningTree = SpanningTree.findMinSpanningTree(base, Algorithm.KRUSKAL);
		
		minSpanningTree.print();
		
		System.out.println(minSpanningTree.stateOrder());
		
		ShortestPath shortestPath = ShortestPath.findShortestPath(base, 'A', 'J', Algorithm.DIJKSTRA);
		
		shortestPath.print();
		
		//System.out.println(shortestPath.stateOrder());
	}
}
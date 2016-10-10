package javalibrary.network;

import java.util.ArrayList;
import java.util.List;

import javalibrary.math.MathUtil;

public class Arc {

	public int id1;
	public int id2;
	public ArrayList<Double> distances;
	
	public Arc(int id1, int id2, List<Double> distances) {
		this.id1 = id1;
		this.id2 = id2;
		this.distances = new ArrayList<Double>();
		this.distances.addAll(distances);
	}
	
	public Arc(int id1, int id2, double distance) {
		this.id1 = id1;
		this.id2 = id2;
		this.distances = new ArrayList<Double>();
		this.distances.add(distance);
	}
	
	public boolean hasConnectionWith(Node node) {
		return this.hasConnectionWith(node.getId());
	}
	
	public boolean hasConnectionWith(int id) {
		return this.id1 == id || this.id2 == id;
	}
	
	public int getOtherNode(Node node) {
		return this.getOtherNode(node.getId());
	}
	
	public int getOtherNode(int id) {
		return this.id1 == id ? this.id2 : this.id1;
	}
	
	public double getDistance(int index) {
		return this.distances.get(index);
	}
	
	public List<Double> getDistances() {
		return this.distances;
	}
	
	public double getTotalDistance() {
		double distance = 0;
		for(double d : this.distances)
			distance += d;
		return distance;
	}
	
	public double getShortestDistance() {
		return MathUtil.findSmallestDouble(this.distances);
	}
	
	public int distanceCount() {
		return this.distances.size();
	}
	
	@Override
	public int hashCode() {
		int hash = 17;
	    hash = hash * 31 + Math.min(this.id1, this.id2);
	    hash = hash * 31 + Math.max(this.id1, this.id2);
	    return hash;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Arc) {
			Arc other = (Arc)obj;
			return other.hasConnectionWith(this.id1) && other.hasConnectionWith(this.id2);
		}
		return false;
	}
	
	@Override
	public String toString() {
		return String.format("Arc [id=%d, id=%d, distances=%s]", this.id1, this.id2, this.distances);
	}
	
	public Arc copy() {
		return new Arc(this.id1, this.id2, (List<Double>)this.distances.clone());
	}
	
	public static class ArcIndex {
		public Arc arc;
		public double distance;
		
		public ArcIndex(Arc arc, int index) {
			this.arc = arc.copy();
			this.distance = this.arc.getDistance(index);
		}
		
		public double getDistance() {
			return this.distance;
		}
		
		public Arc createArc() {
			return new Arc(this.arc.id1, this.arc.id2, this.getDistance());
		}
		
		@Override
		public String toString() {
			return String.format("Arc [id=%d, id=%d, distance=%f]", this.arc.id1, this.arc.id2, this.getDistance());
		}
	}
}

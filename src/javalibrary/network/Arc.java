package javalibrary.network;

public class Arc {

	public int id1;
	public int id2;
	public double distance;
	
	public Arc(Node node1, Node node2, double distance) {
		this(node1.getId(), node2.getId(), distance);
	}
	
	public Arc(int id1, int id2, double distance) {
		this.id1 = id1;
		this.id2 = id2;
		this.distance = distance;
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
		return String.format("Arc [id=%d, id=%d]", this.id1, this.id2);
	}
}

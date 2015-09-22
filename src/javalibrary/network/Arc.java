package javalibrary.network;

public class Arc {

	public int id1;
	public int id2;
	public int distance;
	public boolean directional;
	
	public Arc(Node node1, Node node2, int distance) {
		this(node1.getId(), node2.getId(), distance);
	}
	
	public Arc(int id1, int id2, int distance) {
		this.id1 = id1;
		this.id2 = id2;
		this.distance = distance;
		this.directional = false;
	}
	
	public Arc setDirectional() {
		this.directional = true;
		return this;
	}
	
	public boolean hasConnectionWith(Node node) {
		return this.hasConnectionWith(node.getId());
	}
	
	public boolean hasConnectionWith(int id) {
		if(this.directional) 
			return this.id1 == id;
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
			return other.id1 == this.id1 && other.id2 == this.id2 || other.id1 == this.id2 && other.id2 == this.id1;
		}
		return false;
	}
	
	@Override
	public String toString() {
		return String.format("Arc [id=%d, id=%d, directional=%b]", this.id1, this.id2, this.directional);
	}
}

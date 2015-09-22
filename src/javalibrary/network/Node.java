package javalibrary.network;

public class Node {

	private final int id;
	
	public Node(int id) {
		this.id = id;
	}
	
	public int getId() {
		return this.id;
	}
	
	@Override
	public int hashCode() {
		return this.id;
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof Node ? ((Node)obj).id == this.id : false;
	}
	
	@Override
	public String toString() {
		return String.format("Node [id=%d]", this.id);
	}
}

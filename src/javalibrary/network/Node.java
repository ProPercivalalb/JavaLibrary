package javalibrary.network;

public class Node {

	private final int id;
	public int x, y;

	public Node(int id) {
		this(id, 0, 0);
	}
	
	public Node(int id, int x, int y) {
		this.id = id;
		this.x = x;
		this.y = y;
	}
	
	public final int getId() {
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

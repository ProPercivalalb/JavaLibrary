package javalibrary.util;

public class ObjectTracker<T> {
	
	public T target;
	
	public ObjectTracker(T target) {
		this.target = target;
	}
	
	public static <T> ObjectTracker<T> create(T target) {
		return new ObjectTracker <T>(target);
	}
	
	public T get() {
		return this.target;
	}
	
	public void set(T target) {
		this.target = target;
	}
}

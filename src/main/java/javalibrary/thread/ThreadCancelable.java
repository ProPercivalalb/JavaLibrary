package javalibrary.thread;

public class ThreadCancelable {

	public Thread thread;
	public Runnable runnable;
	
	public ThreadCancelable(Runnable runnable) {
		this.thread = new Thread(runnable);
		this.runnable = runnable;
	}
	
	
	@SuppressWarnings("deprecation")
	public void cancel() {
		this.thread.stop();
		this.thread = new Thread(this.runnable);
	}
	
	public void start() {
		this.thread.start();
	}
	
	public void restart() {
		this.cancel();
		this.start();
	}
}

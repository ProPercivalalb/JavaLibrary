package javalibrary.thread;

public class Threads {

	public static void runTask(Runnable runnable) {
		runTask(runnable, true);
	}
	
	public static void runTask(Runnable runnable, boolean separateThread) {
		if(separateThread)
		    new Thread(runnable).start();
		else
			runnable.run();
	}
}
